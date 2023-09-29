package org.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dto.GrnDTO;
import org.example.dto.InvoiceDTO;
import org.example.dto.ProductDTO;
import org.example.dto.ResponseDTO;
import org.example.repository.GrnRepositoryImpl;
import org.example.repository.InvoiceRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class InvoiceAPI extends HttpServlet {

    private InvoiceRepositoryImpl invoiceRepositoryImpl;
    private GrnRepositoryImpl grnRepositoryImpl;

    public InvoiceAPI()  {
        this.invoiceRepositoryImpl = new InvoiceRepositoryImpl();
        this.grnRepositoryImpl = new GrnRepositoryImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-d H:mm:ss") // setting date format
                .create();

        try {
            List<InvoiceDTO> invList = this.invoiceRepositoryImpl.getAllInvoices();

            ResponseDTO response1 = new ResponseDTO("success", gson.toJson(invList));
            out.print(gson.toJson(response1));
            out.flush();
        }catch(SQLException er){
            ResponseDTO response1 = new ResponseDTO("error", er.getMessage());
            out.print(gson.toJson(response1));
            out.flush();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        ResponseDTO response1;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-d H:mm:ss") // setting date format
                .create();

        // Read request body
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        String requestBody = sb.toString();

        InvoiceDTO inv = gson.fromJson(requestBody, InvoiceDTO.class);

        try {
            //stock reduce
            this.grnRepositoryImpl.reduceStock(inv.getProducts());
            //save invoice
            this.invoiceRepositoryImpl.saveInvoice(inv);

            response1 = new ResponseDTO("success", "");
            out.print(gson.toJson(response1));
            out.flush();
        }catch(SQLException er){
            response1 = new ResponseDTO("error", er.getMessage());
            out.print(gson.toJson(response1));
            out.flush();
        }
    }
}
