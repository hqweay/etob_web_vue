package com.nb_netmusic.servlet.manager.user;

import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/manage/user/GetUserByPage")
public class GetUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        Map<String, List<User>> jsonMap = new HashMap<>();
//    Map<String, Integer> jsonCount = new HashMap<>();

        //Post 可以用 json 转 对象
        //Get 这里不行
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        Page page = new Page();
        page.setPageNum(Integer.parseInt(pageNum));
        page.setPageSize(Integer.parseInt(pageSize));

        //参数
        List<Object> params = new ArrayList<>();
        params.add(page.getPageNum());
        params.add(page.getPageSize());
        UserService us = new UserService();
        //这里的 获取条数需要查询出所有的song...有点浪费
        int userCount = us.getCount();

        //  jsonMap.put("songs", songList);
//    jsonCount.put("count", songCount);
        List<User> UserList = us.getUserListByPage(params);

        JSONObject ret_json = new JSONObject();

        ret_json.put("users",UserList );
        ret_json.put("count", userCount);

        System.out.println("哈哈哈");
        System.out.println(userCount);

        PrintWriter out = response.getWriter();
        out.print(ret_json);
        out.flush();
        out.close();


    }
}
