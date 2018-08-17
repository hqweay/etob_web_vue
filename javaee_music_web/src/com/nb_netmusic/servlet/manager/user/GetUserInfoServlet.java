package com.nb_netmusic.servlet.manager.user;

import com.alibaba.fastjson.JSON;
import com.nb_netmusic.bean.Song;
import com.nb_netmusic.bean.User;
import com.nb_netmusic.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/manage/getUser")
public class GetUserInfoServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");

        List<Object> params = new ArrayList<>();
        params.add(username);
        List<User> users = userService.getUserByUsername(params);

        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(users.get(0)));
        out.flush();
        out.close();
    }
}
