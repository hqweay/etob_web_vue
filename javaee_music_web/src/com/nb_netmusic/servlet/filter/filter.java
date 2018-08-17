package com.nb_netmusic.servlet.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "filter",urlPatterns = "/api/manager/*")
public class filter implements Filter {
  @Override
  public void destroy() {

  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpSession session = request.getSession();

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");

    JSONObject jsonObject = new JSONObject();

    Integer type = (Integer)session.getAttribute("type");
    if(null == type || 0 == type){
      jsonObject.put("status", "不要尝试非法操作");
      PrintWriter out = response.getWriter();
      out.print(jsonObject);
      out.flush();
      out.close();
    }else if(1 == type){
      filterChain.doFilter(servletRequest,servletResponse);
    }


  }

}
