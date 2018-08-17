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
import java.util.List;

@WebServlet("/api/song/nextSong")
public class NextSongServlet extends HttpServlet {
    private SongService ss = new SongService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        Song nextSong = ss.getNextSong(id, null);

        PrintWriter out = response.getWriter();

        out.print(JSON.toJSONString(nextSong));
        out.flush();
        out.close();
    }
}
