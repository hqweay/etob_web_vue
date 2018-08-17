package com.nb_netmusic.servlet.song;

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
import java.util.Collections;
import java.util.List;
import java.util.Random;

@WebServlet("/api/song/getRandomSong")
public class RandomSongServlet extends HttpServlet {
    SongService ss = new SongService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Song> l = ss.getSongList(null);
        List<Song> retL = new ArrayList<>();
        Random rand = new Random();
        int[] num = new int[10];
        int index = 0;
        do {
            int temp = rand.nextInt(l.size());
            boolean flag = false;
            for (int aNum : num) {
                if (temp == aNum) {
                    flag = true;
                }
            }
            if (!flag) {
                num[index] = temp;
                index += 1;
            }
        } while (index != num.length);
        for (int n :
                num) {
            retL.add(l.get(n));
        }
        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(retL));
        out.flush();
        out.close();
    }
}
