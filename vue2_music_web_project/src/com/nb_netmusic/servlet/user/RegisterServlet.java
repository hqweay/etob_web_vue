package com.nb_netmusic.servlet.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.User;
import com.nb_netmusic.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/user/register")
public class RegisterServlet extends HttpServlet {
    private UserService us = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        int status = 0;
        BufferedReader reader = request.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        JSONObject tempJson = JSON.parseObject(json.toString());

        String fileName = tempJson.getString("profile_name");
        tempJson.remove("profile_name");
        User u = JSON.parseObject(JSON.toJSONString(tempJson), User.class);

        List<Object> params = new ArrayList<>();
        params.add(u.getUsername());
        params.add(u.getPassword());
        params.add(u.getType());
        params.add(u.getEmail());
        params.add(u.getSex());
        params.add(u.getProfileImg());


        System.out.println(fileName);
        System.out.println(u.toString());
        if (us.insertUser(params)) {
            status = 1;
        }
        String text_json = String.format("{\"status\":%d}", status);
        JSONObject ret_json = JSON.parseObject(text_json);

        PrintWriter out = response.getWriter();
        out.print(ret_json);
        out.flush();
        out.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
