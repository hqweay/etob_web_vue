package com.nb_netmusic.servlet.user;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/user/logout")
public class LogoutServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doGet(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    JSONObject jsonObject = new JSONObject();

    HttpSession session = req.getSession(true);
    session.removeAttribute("type");

    jsonObject.put("status", "logout");

    PrintWriter out = resp.getWriter();
    out.print(jsonObject);
    out.flush();
    out.close();


  }
}
