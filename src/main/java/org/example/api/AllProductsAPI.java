package org.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dto.ProductDTO;
import org.example.dto.ResponseDTO;
import org.example.repository.ProductRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AllProductsAPI extends HttpServlet {
    private ProductRepositoryImpl productRepositoryImpl;

    public AllProductsAPI() {
        this.productRepositoryImpl = new ProductRepositoryImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        List<ProductDTO> prdList = this.productRepositoryImpl.getAllProducts();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-d H:mm:ss") // setting date format
                .create();
        ResponseDTO response1 = new ResponseDTO("success",gson.toJson(prdList));
        out.print(gson.toJson(response1));
        out.flush();

    }
}
