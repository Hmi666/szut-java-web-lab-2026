package com.leeshawn._907demo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


@WebServlet(value = "/language",
        initParams = {
            @WebInitParam(name="en",value = "welcome to my website 092224110 Li lixiang"),
                @WebInitParam(name="zh", value = "欢迎访问我的网站 092224110 李理想")
        }
)
public class Servlet02 implements Servlet {
    HashMap<String,String> map = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        String en = config.getInitParameter("en");
        String zh = config.getInitParameter("zh");
        map.put("en",en);
        map.put("zh",zh);

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        if(lang == null || !map.containsKey(lang)){
            lang = "zh";
        }

        String context = map.get(lang);
        res.setContentType("text/html;charset=UTF-8");
        res.getWriter().println("<h1>" + context + "</h1>");

        System.out.println(context);
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }
}
