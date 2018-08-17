package com.nb_netmusic.servlet.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.User;
import com.nb_netmusic.service.UserService;

@WebServlet("/api/user/validate")
public class ValidateServlet extends HttpServlet {
  private UserService us = new UserService();

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");

    Map<String, String> jsonMap = new HashMap<String, String>();

    String status = null;
    String type = "0";

    BufferedReader reader = request.getReader();
    StringBuilder json = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      json.append(line);
    }
    //获得 user 对象
    User u = JSON.parseObject(json.toString(), User.class);
    if (u != null) {
      //   System.out.println(u.toString());
      //参数
      List<Object> params = new ArrayList<>();
      params.add(u.getUsername());
      params.add(u.getPassword());

      //先查查数据库有没有这个用户名
      List<Object> hasUserNameParams = new ArrayList<>();
      hasUserNameParams.add(u.getUsername());
      //有的话才开始登录
      if (us.hasUserName(hasUserNameParams)) {
        //登录
        List<User> data = us.validateAccount(params);
        if (data.isEmpty()) {
          jsonMap.put("status", "error");
        } else {
          jsonMap.put("status", "success");
          jsonMap.put("type", String.valueOf(data.get(0).getType()));
          // 创建session
          HttpSession session = request.getSession(true);
          session.setAttribute("type",data.get(0).getType());
        }
      } else {
        jsonMap.put("status", "noUser");
      }
    }


//        System.out.println(JSON.toJSONString(jsonMap));
    JSONObject ret_json = JSON.parseObject(JSON.toJSONString(jsonMap));

    PrintWriter out = response.getWriter();
    out.print(ret_json);
    out.flush();
    out.close();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
}
