package org.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dto.InvoiceItemDTO;
import org.example.dto.ResponseDTO;
import org.example.repository.InvoiceRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReportAPI extends HttpServlet {

    private InvoiceRepositoryImpl invoiceRepositoryImpl;

    public ReportAPI() {
        this.invoiceRepositoryImpl = new InvoiceRepositoryImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        ResponseDTO response1;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-d H:mm:ss") // setting date format
                .create();

        try {
            String report = request.getParameter("report");
            String date = request.getParameter("date");

            switch (report) {
                case "TotalSale":
                    List<InvoiceItemDTO> invoiceItemDTOS = this.invoiceRepositoryImpl.getTotalSale(date);
                    String outPut = " <table>\n";
                    Float totalQty = 0.00F;
                    Float totalPrice = 0.00F;
                    for (InvoiceItemDTO invoiceItemDTO : invoiceItemDTOS) {
                        outPut += "        <tr>\n" +
                                "            <th>" + invoiceItemDTO.getProductName() + "</th>\n" +
                                "            <th>" + invoiceItemDTO.getProductCode() + "</th>\n" +
                                "            <th>" + invoiceItemDTO.getQty() + "</th>\n" +
                                "            <th>" + invoiceItemDTO.getPrice() + "</th>\n" +
                                "        </tr>\n";
                        totalQty += invoiceItemDTO.getQty();
                        totalPrice += invoiceItemDTO.getPrice();
                    }

                    outPut += "    </table>\n" +
                            "    <h3>Total Quantity: " + totalQty + "</h3>\n" +
                            "    <h3>Total Revenue: " + totalPrice + "</h3>\n";

                    response1 = new ResponseDTO("success", outPut);
                    out.print(gson.toJson(response1));
                    out.flush();

                    break;

                default:
                    response1 = new ResponseDTO("error", "report not found");
                    out.print(gson.toJson(response1));
                    out.flush();
            }

        }catch(Exception e){
            response1 = new ResponseDTO("error", "report parameters are invalid");
            out.print(gson.toJson(response1));
            out.flush();
        }
    }
}
