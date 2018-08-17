package com.nb_netmusic.servlet.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nb_netmusic.bean.User;
import com.nb_netmusic.service.UserService;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/*
    8.3 10:19
    8.3 11:40 已完成
 */

@WebServlet("/api/user/updateInfo")
public class UpdateServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        JSONObject ret_json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = request.getReader();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonObject = JSON.parseObject(sb.toString());
        String filename = (String) jsonObject.get("filename");
        String suffix = filename.substring(filename.indexOf('.'));
        String realFileName = jsonObject.getString("username") + suffix;


//        String path = "/home/hqweay/Desktop/nb2/nb_net_music/src/res/images";
//        String path = "/home/hqweay/Desktop/nb2/nb_net_music/src/res/images";
        //根据情况修改
        String path = "/opt/tomcat/webapps/images/";

        File file = new File( path+ File.separator + realFileName);

        System.out.println(file.getAbsolutePath());

        jsonObject.remove("filename");


        String encodedImg = (String) jsonObject.get("profileImg");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] binImg = decoder.decodeBuffer(encodedImg);

        //error
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(binImg);
        fos.flush();
        fos.close();
        ///opt/tomcat/webapps/ROOT/WEB-INF/classes/res/images
        //这里部署到服务器得修改
        String image_url = request.getScheme() + request.getServerName() + ":"+request.getServerPort()+"/images/";
        jsonObject.put("profileImg", image_url+realFileName);

        User u = JSON.parseObject(jsonObject.toJSONString(), User.class);
        List<Object> params = new ArrayList<>();

        // 可能会添加多了一个用户名
        params.add(u.getUsername());
        params.add(u.getPassword());
//        params.add(u.getType());
        params.add(u.getEmail());
        params.add(u.getSex());
        params.add(u.getProfileImg());
        params.add(u.getUsername());

        if (userService.updateUserInfo(params)) {
            ret_json.put("status", "success");
        } else {
            ret_json.put("status", "error");
        }
        System.out.println(ret_json);
        PrintWriter out = response.getWriter();
        out.print(ret_json);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
