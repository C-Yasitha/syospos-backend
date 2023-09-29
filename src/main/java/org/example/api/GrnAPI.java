package org.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dto.GrnDTO;
import org.example.dto.ProductDTO;
import org.example.dto.ResponseDTO;
import org.example.repository.GrnRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GrnAPI extends HttpServlet {
    private GrnRepositoryImpl grnRepositoryImpl;

    public GrnAPI() {
        this.grnRepositoryImpl = new GrnRepositoryImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        List<GrnDTO> grnList = this.grnRepositoryImpl.getAllGrns();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-d H:mm:ss") // setting date format
                .create();
        ResponseDTO response1 = new ResponseDTO("success",gson.toJson(grnList));
        out.print(gson.toJson(response1));
        out.flush();
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

        GrnDTO grn = gson.fromJson(requestBody, GrnDTO.class);

        this.grnRepositoryImpl.saveGrn(grn);

        response1 = new ResponseDTO("success","");
        out.print(gson.toJson(response1));
        out.flush();
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ResponseDTO response1;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-d H:mm:ss") // setting date format
                .create();

        this.grnRepositoryImpl.moveGrn(Integer.parseInt(request.getParameter("getId")));

        response1 = new ResponseDTO("success","");
        out.print(gson.toJson(response1));
        out.flush();

    }
}
