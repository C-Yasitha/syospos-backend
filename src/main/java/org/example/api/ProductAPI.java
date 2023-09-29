package org.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.dto.ProductDTO;
import org.example.dto.ResponseDTO;
import org.example.model.Product;
import org.example.model.User;
import org.example.repository.GrnRepositoryImpl;
import org.example.repository.ProductRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ProductAPI extends HttpServlet {

    private ProductRepositoryImpl productRepositoryImpl;
    private GrnRepositoryImpl grnRepositoryImpl;

    public ProductAPI() {
        this.productRepositoryImpl = new ProductRepositoryImpl();
        this.grnRepositoryImpl = new GrnRepositoryImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ResponseDTO response1;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-d H:mm:ss") // setting date format
                .create();

        try {
            Product product = this.productRepositoryImpl.getProductByCode(request.getParameter("productCode"));
            if (product != null) {
                ProductDTO productDTO = new ProductDTO(product.getProductCode(), product.getProductName(), product.getProductDescription(), product.getProductImage(), product.getLowLevel(), product.isService(),
                        product.getProductWeight(), product.getCreatedAt(), product.getUpdatedAt(), product.getCategory(), product.getBrand(), product.getPrice());
                productDTO.setId(product.getId());
                if (request.getParameter("withStock").equals("true")) {
                    //get stock
                    productDTO.setQty(this.grnRepositoryImpl.getStock(productDTO.getId()));
                }
                response1 = new ResponseDTO("success", gson.toJson(productDTO));
            } else {
                response1 = new ResponseDTO("error", "no products found for that code");
            }

            out.print(gson.toJson(response1));
            out.flush();
        }catch(SQLException er){
            response1 = new ResponseDTO("error", er.getMessage());
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

        ProductDTO prd = gson.fromJson(requestBody, ProductDTO.class);
        try {
            this.productRepositoryImpl.saveProduct(prd);

            response1 = new ResponseDTO("success", "");
            out.print(gson.toJson(response1));
            out.flush();
        }catch(SQLException er){
            response1 = new ResponseDTO("error", er.getMessage());
            out.print(gson.toJson(response1));
            out.flush();
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        ProductDTO prd = gson.fromJson(requestBody, ProductDTO.class);

        try {
            this.productRepositoryImpl.updateProduct(prd);

            response1 = new ResponseDTO("success", "");
            out.print(gson.toJson(response1));
            out.flush();
        }catch(SQLException er){
            response1 = new ResponseDTO("error", er.getMessage());
            out.print(gson.toJson(response1));
            out.flush();
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ResponseDTO response1;
        Gson gson = new Gson();

        try {
            this.productRepositoryImpl.deleteProduct(request.getParameter("productCode"));
            response1 = new ResponseDTO("success", "");
            out.print(gson.toJson(response1));
            out.flush();
        }catch (SQLException er){
            response1 = new ResponseDTO("error", er.getMessage());
            out.print(gson.toJson(response1));
            out.flush();
        }
    }


}
