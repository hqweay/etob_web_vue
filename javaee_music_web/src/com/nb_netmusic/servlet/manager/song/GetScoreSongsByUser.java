package com.nb_netmusic.servlet.manager.song;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.Score;
import com.nb_netmusic.bean.Song;
import com.nb_netmusic.service.ScoreService;
import com.nb_netmusic.service.SongService;

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

@WebServlet("/api/score/getScoreSongsByUser")
public class GetScoreSongsByUser extends HttpServlet {
  private SongService songService = new SongService();

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");


    String username = request.getParameter("username");



    List<Object> params = new ArrayList<>();
    params.add(username);

    List songs = songService.getScoreSongsByUser(params);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("songs", songs);

    PrintWriter out = response.getWriter();
    out.print(jsonObject);
    out.flush();
    out.close();
  }
}
