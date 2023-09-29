package org.example.api;

import com.google.gson.Gson;
import org.example.ConfigLoader;
import org.example.dto.ResponseDTO;
import org.example.model.User;
import org.example.repository.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserAPI extends HttpServlet {
    private UserRepositoryImpl userRepositoryImpl;

    public UserAPI() {
        this.userRepositoryImpl = new UserRepositoryImpl();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        ResponseDTO response1;
        Gson gson = new Gson();

        // Read request body
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        String requestBody = sb.toString();

        User user = gson.fromJson(requestBody, User.class);
        boolean auth = this.userRepositoryImpl.authenticateUser(user);
       // boolean auth = true;
        if(auth){
            response1 = new ResponseDTO("success","true");
        }else{
            response1 = new ResponseDTO("error","false");
        }

        out.print(gson.toJson(response1));
        out.flush();
    }
}
