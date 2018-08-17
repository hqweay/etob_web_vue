package com.nb_netmusic.servlet.manager.song;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.HashMap;
import java.util.Map;

/*
     暂时解决
 */
@WebServlet("/api/manager/deleteSong")
public class DeleteSongServlet extends HttpServlet {
    SongService songService = new SongService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //删除，一串......
        //Sql 有按数组删除的方法
        //但是我们怎么搞呢
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Map jsonMap = new HashMap();

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = JSON.parseObject(sb.toString());

        JSONArray ids = json.getJSONArray("ids");
        ArrayList<Object> params = new ArrayList<>();
        params.addAll(ids);
        if (songService.deleteSong(params)) {
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
