package com.nb_netmusic.servlet.manager.user;

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

@WebServlet("/api/manage/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    private UserService us = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        boolean result;
        String retJson;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String textJson = sb.toString();
        JSONObject json = JSON.parseObject(textJson);
        User u = JSON.toJavaObject(json, User.class);

        List<Object> params = new ArrayList<>();
        params.add(u.getUsername());
        result = us.deleteUserByUsername(params);
        if (result) {
            retJson = "{\"status\":\"success\"}";
        } else {
            retJson = "{\"status\":\"error\"}";
        }
        PrintWriter out = response.getWriter();
        out.print(retJson);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
