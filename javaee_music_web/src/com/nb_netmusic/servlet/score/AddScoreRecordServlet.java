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

@WebServlet("/api/score/insertScore")
public class AddScoreRecordServlet extends HttpServlet {
    private ScoreService ss = new ScoreService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = request.getReader();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonObject = JSONObject.parseObject(sb.toString());

        Score s = JSON.toJavaObject(jsonObject, Score.class);

        //三个参数
        List<Object> params = new ArrayList<>();
        params.add(s.getUsername());
        params.add(s.getId());
        params.add(s.getScore());

        String result = "error";
        try {
            if (ss.addUserSongScore(params)) {
                result = "success";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        out.print(String.format("{\"status\":\"%s\"}", result));
        out.flush();
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
