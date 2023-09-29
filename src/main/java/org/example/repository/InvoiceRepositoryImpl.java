package org.example.repository;

import org.example.dao.InvoiceRepository;
import org.example.database.DatabaseQueryExecutor;
import org.example.dto.InvoiceDTO;
import org.example.dto.InvoiceItemDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepository {

    private DatabaseQueryExecutor queryExecutor;

    public InvoiceRepositoryImpl() {
        queryExecutor = new DatabaseQueryExecutor();
    }
    public void saveInvoice(InvoiceDTO invoice) throws SQLException {
        //save invoice first
        int returnId = 0;
        String query = "INSERT INTO invoices (customer, inv_date, total, discount,tendered) VALUES (?, ?, ?, ?, ?)";

        returnId = queryExecutor.executeUpdate(query,invoice.getCustomer(),invoice.getInvDate(),invoice.getTotal(),invoice.getDiscount(),invoice.getTendered());

        //save invoice items
        if(returnId!=0){
            for (InvoiceItemDTO invoiceItems:invoice.getProducts()) {
                String queryItem = "INSERT INTO invoice_items (invoice_id, product_code, product_name, price, qty ) VALUES (?, ?, ?, ?, ?)";

                queryExecutor.executeUpdate(queryItem,returnId,invoiceItems.getProductCode(),invoiceItems.getProductName(),invoiceItems.getPrice(),invoiceItems.getQty());

            }
        }
    }

    public List<InvoiceDTO> getAllInvoices() throws SQLException {
        List<InvoiceDTO> invoices = new ArrayList<>();
        String query = "SELECT * FROM invoices";

        ResultSet resultSet = queryExecutor.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String customer = resultSet.getString("customer");
            Date invDate = resultSet.getDate("inv_date");
            Float total = resultSet.getFloat("total");
            Float discount = resultSet.getFloat("discount");
            Float tendered = resultSet.getFloat("tendered");

            //get invoice items
            List<InvoiceItemDTO> invoice_items = new ArrayList<>();
            String query2 = "SELECT * FROM invoice_items WHERE invoice_id = ?";
            ResultSet resultSet2 = queryExecutor.executeQuery(query2,id);
            while (resultSet2.next()) {
                InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO(resultSet2.getInt("product_code"),
                        resultSet2.getString("product_name"),
                        resultSet2.getFloat("price"),
                        resultSet2.getFloat("qty"));
                invoiceItemDTO.setId(resultSet2.getInt("id"));
                invoiceItemDTO.setInvoiceId(id);
                invoice_items.add(invoiceItemDTO);
            }

            InvoiceDTO invoiceDTO = new InvoiceDTO(customer,invDate,total,discount,tendered,invoice_items);
            invoiceDTO.setId(id);

            invoices.add(invoiceDTO);
        }

        return invoices;
    }

    public List<InvoiceItemDTO> getTotalSale(String date){
        List<InvoiceItemDTO> invoiceItems = new ArrayList<>();
        String query = "SELECT ii.product_code,ii.product_name,SUM(ii.price) as price,SUM(qty) as qty FROM invoice_items ii,invoices i WHERE i.id=ii.invoice_id AND i.inv_date = ? GROUP BY ii.product_code";
        try {
            ResultSet resultSet = queryExecutor.executeQuery(query,date);
            while (resultSet.next()) {
                InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO(resultSet.getInt("product_code"),resultSet.getString("product_name"),resultSet.getFloat("price"),resultSet.getFloat("qty"));
                invoiceItems.add(invoiceItemDTO);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your application's error handling mechanism
        }
        return invoiceItems;
    }
}
