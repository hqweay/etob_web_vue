package com.nb_netmusic.servlet.manager.song;

import com.alibaba.fastjson.JSON;
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
import java.util.List;

@WebServlet("/api/manage/editSong")
public class UpdateSongServlet extends HttpServlet {
    SongService ss = new SongService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        boolean status = false;
        BufferedReader br = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String textJson = sb.toString();

        Song s = JSON.toJavaObject(JSON.parseObject(textJson), Song.class);

        //update nb_song set id=?,name=?,singer=?,album=?,frequency=?,duration=?,link=?,score=?,song_image=? where id=?

        List<Object> params = new ArrayList<>();
        params.add(s.getId());
        params.add(s.getName());
        params.add(s.getSinger());
        params.add(s.getAlbum());
        params.add(s.getFrequency());
        params.add(s.getDuration());
        params.add(s.getLink());
        params.add(s.getScore());
        params.add(s.getSong_image());
        params.add(s.getId());

        status = ss.updateSongById(params);

        PrintWriter out = response.getWriter();
        out.write(String.format("{\"status\":\"%s\"}", status));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
