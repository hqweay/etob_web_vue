package com.nb_netmusic.servlet.song;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

@WebServlet("/api/song/getSongById")
public class GetSongByIdServlet extends HttpServlet {

    SongService ss = new SongService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        JSONObject jsonObject = new JSONObject();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String textJson = sb.toString();
        JSONObject json = JSON.parseObject(textJson);

        List<Object> params = new ArrayList<>();
        params.add(json.get("id"));
        try {
            List result = ss.getSongListById(params);
            jsonObject.put("status", "success");
            jsonObject.put("song",result.get(0));
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("status", "error");
        }
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();

    }
}
