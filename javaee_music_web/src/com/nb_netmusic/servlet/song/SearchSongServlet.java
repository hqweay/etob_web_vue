package com.nb_netmusic.servlet.song;

import com.alibaba.fastjson.JSON;
import com.nb_netmusic.bean.Song;
import com.nb_netmusic.service.SongService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/song/getSong")
public class SearchSongServlet extends HttpServlet {
    private SongService ss = new SongService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String song_json_array;
        if (name==null) {
            List<Song> songs = ss.getSongList(null);
            song_json_array = JSON.toJSONString(songs);
            System.out.println(song_json_array);
        } else {
            List<Object> params = new ArrayList<>();
            Song s = new Song();
            s.setName(name);
            params.add(s.getName());
            params.add(s.getName());
            params.add(s.getName());
            List<Song> songs = ss.getSongListByName(params);
            song_json_array = JSON.toJSONString(songs);
        }

        PrintWriter out = response.getWriter();
        out.print(song_json_array);
        out.flush();
        out.close();
    }

}
