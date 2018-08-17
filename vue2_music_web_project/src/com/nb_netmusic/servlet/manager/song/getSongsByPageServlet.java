package com.nb_netmusic.servlet.manager.song;

import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/manager/getSongsByPage")
public class getSongsByPageServlet extends HttpServlet {
  SongService songService = new SongService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");

    Map<String, List<Song>> jsonMap = new HashMap<>();
//    Map<String, Integer> jsonCount = new HashMap<>();

    //Post 可以用 json 转 对象
    //Get 这里不行
    String pageNum = request.getParameter("pageNum");
    String pageSize = request.getParameter("pageSize");
    Page page = new Page();
    page.setPageNum(Integer.parseInt(pageNum));
    page.setPageSize(Integer.parseInt(pageSize));

    //参数
    List<Object> params = new ArrayList<>();
    params.add(page.getPageNum());
    params.add(page.getPageSize());

    List<Song> songList = songService.getSongListByPage(params);
    //这里的 获取条数需要查询出所有的song...有点浪费
    int songCount = songService.getCount();

  //  jsonMap.put("songs", songList);
//    jsonCount.put("count", songCount);

    JSONObject ret_json = new JSONObject();

    ret_json.put("songs", songList);
    ret_json.put("count", songCount);

    System.out.println("哈哈哈");
    System.out.println(songCount);

    PrintWriter out = response.getWriter();
    out.print(ret_json);
    out.flush();
    out.close();

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
}
