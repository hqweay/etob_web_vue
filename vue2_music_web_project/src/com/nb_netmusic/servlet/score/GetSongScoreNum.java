package com.nb_netmusic.servlet.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.Score;
import com.nb_netmusic.service.ScoreService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/score/getSongScoreNum")
public class GetSongScoreNum extends HttpServlet {
  private ScoreService ss = new ScoreService();

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");


    int songId = Integer.parseInt(request.getParameter("id"));



    //三个参数
    List<Object> params = new ArrayList<>();
    params.add(songId);

    int count = ss.getSongScoreNum(params);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("num", count);

    PrintWriter out = response.getWriter();
    out.print(jsonObject);
    out.flush();
    out.close();
  }
}
