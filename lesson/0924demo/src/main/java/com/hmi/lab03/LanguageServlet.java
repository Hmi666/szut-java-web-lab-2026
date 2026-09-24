package com.hmi.lab03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/LanguageServlet")
public class LanguageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String header = request.getHeader("accept-language");
        String ip = request.getRemoteAddr();
        String method = request.getMethod();

        PrintWriter writer = response.getWriter();

        String language = header.split(",")[0];

        System.out.println(language);

        Map<String,String> text = new HashMap<>();
        text.put("en-US","welcome to my website");
        text.put("zh-cn","欢迎访问我的网站");

        String s = text.get(language);
        if(s !=null){
            writer.print(s);
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}