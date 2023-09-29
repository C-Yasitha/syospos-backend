package org.example.repository;

import org.example.dao.GrnRepository;
import org.example.database.DatabaseQueryExecutor;
import org.example.dto.GrnDTO;
import org.example.dto.GrnItemDTO;
import org.example.dto.InvoiceItemDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrnRepositoryImpl implements GrnRepository {
    private DatabaseQueryExecutor queryExecutor;

    public GrnRepositoryImpl() {
        queryExecutor = new DatabaseQueryExecutor();
    }

    public void saveGrn(GrnDTO grn) throws SQLException {
        //save grn first
        int returnId = 0;
        String query = "INSERT INTO grns (grn_date, supplier_name, total, is_shelf) VALUES (?, ?, ?, ?)";
        returnId = queryExecutor.executeUpdate(query,grn.getGrnDate(),grn.getSupplierName(),grn.getTotal(),grn.isShelf());

        //save grn items
        if(returnId!=0){
            for (GrnItemDTO grnItems:grn.getGrnItems()) {
                String queryItem = "INSERT INTO grn_items (grn_id, product_id, product_name, exp_date, qty, cost) VALUES (?, ?, ?, ?, ?, ?)";
                queryExecutor.executeUpdate(queryItem,returnId,grnItems.getProductId(),grnItems.getProductName(),grnItems.getExpDate(),grnItems.getQty(),grnItems.getCost());
            }
        }
    }


    public List<GrnDTO> getAllGrns() throws SQLException {
        List<GrnDTO> grns = new ArrayList<>();
        String query = "SELECT * FROM grns";
            ResultSet resultSet = queryExecutor.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String supplierName = resultSet.getString("supplier_name");
                Float total = resultSet.getFloat("total");
                Boolean isShelf = resultSet.getBoolean("is_shelf");
                Date getnDate = resultSet.getDate("grn_date");

                //get grn items
                List<GrnItemDTO> grn_items = new ArrayList<>();
                String query2 = "SELECT * FROM grn_items WHERE grn_id = ?";
                ResultSet resultSet2 = queryExecutor.executeQuery(query2,id);
                while (resultSet2.next()) {
                    GrnItemDTO grnItemDTO = new GrnItemDTO(resultSet2.getInt("product_id"),resultSet2.getString("product_name"),resultSet2.getDate("exp_date"),resultSet2.getFloat("qty"),resultSet2.getFloat("cost"));
                    grnItemDTO.setGrnId(id);
                    grnItemDTO.setId(resultSet2.getInt("id"));
                    grn_items.add(grnItemDTO);
                }

                GrnDTO grnDTO = new GrnDTO(supplierName,total,isShelf,grn_items);
                grnDTO.setId(id);
                grnDTO.setGrnDate(getnDate);

                grns.add(grnDTO);
            }
        return grns;
    }

    public void moveGrn(int id) throws SQLException{
        String query = "UPDATE grns SET is_shelf = !is_shelf WHERE id = ?";
        queryExecutor.executeUpdate(query,id);
    }

    public Float getStock(int productId) throws SQLException{
        String query = "SELECT SUM(gi.qty) AS stock FROM grn_items gi, grns g WHERE g.id=gi.grn_id AND gi.product_id= ? AND gi.qty>0 AND g.is_shelf=1 AND exp_date >= CURDATE() GROUP BY gi.product_id";

        ResultSet resultSet = queryExecutor.executeQuery(query,productId);
        if (resultSet.next()) {
            return resultSet.getFloat("stock");
        }else{
            return 0.00F;
        }

    }

    public void reduceStock(List<InvoiceItemDTO> invoiceItems) throws SQLException{

        for (InvoiceItemDTO invoiceItemDTO : invoiceItems){
            String query = "UPDATE grn_items SET qty = CASE " +
                    "WHEN (SELECT SUM(qty) FROM grn_items WHERE product_id = ? AND exp_date >= CURDATE()) >= ? THEN " +
                    "    CASE " +
                    "        WHEN (SELECT SUM(qty) FROM grn_items WHERE product_id = ? AND exp_date >= CURDATE()) - ? >= 0 THEN " +
                    "            (SELECT SUM(qty) FROM grn_items WHERE product_id = ? AND exp_date >= CURDATE()) - ? " +
                    "        ELSE 0 " +
                    "    END " +
                    "ELSE " +
                    "    CASE " +
                    "        WHEN (SELECT SUM(qty) FROM grn_items WHERE product_id = ? AND exp_date >= CURDATE()) - ? >= 0 THEN " +
                    "            qty - ? " +
                    "        ELSE 0 " +
                    "    END " +
                    "END " +
                    "WHERE product_id = ? AND exp_date >= CURDATE()";

            queryExecutor.executeUpdate(query,invoiceItemDTO.getProductCode(),invoiceItemDTO.getQty(),invoiceItemDTO.getProductCode(),invoiceItemDTO.getQty(),invoiceItemDTO.getProductCode(),invoiceItemDTO.getQty(),invoiceItemDTO.getProductCode(),invoiceItemDTO.getQty(),invoiceItemDTO.getQty(),invoiceItemDTO.getProductCode());

        }

    }
}
