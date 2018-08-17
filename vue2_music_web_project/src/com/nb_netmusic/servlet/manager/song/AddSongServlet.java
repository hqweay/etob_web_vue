package com.nb_netmusic.servlet.manager.song;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.Song;
import com.nb_netmusic.service.SongService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/manager/addSong")
public class AddSongServlet extends HttpServlet {
  SongService songService = new SongService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    super.doGet(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");
    SongService songService = new SongService();
    Map<String, String> jsonMap = new HashMap<>();

    BufferedReader reader = request.getReader();
    StringBuilder json = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      json.append(line);
    }
    System.out.println("来了吗");
    System.out.println(json);
    //先这样吧，明天讨论一下 怎么添加
    Song song = JSON.parseObject(json.toString(), Song.class);
    List<Object> params = new ArrayList<>();
    params.add(song.getName());
    params.add(song.getSinger());
    params.add(song.getAlbum());

    if(song.getDuration() == ""){
      params.add("00:00");
    }else{
      params.add(song.getDuration());
    }



    params.add(song.getFrequency());
    params.add(song.getLink());
    params.add(song.getScore());
//    params.add(song.getSong_image());



    Boolean flag = songService.insertSong(params);
    if (flag) {
      jsonMap.put("status", "success");
    } else {
      jsonMap.put("status", "error");
    }

    JSONObject ret_json = JSON.parseObject(JSON.toJSONString(jsonMap));
    PrintWriter out = response.getWriter();
    out.print(ret_json);
    out.flush();
    out.close();
  }
}
