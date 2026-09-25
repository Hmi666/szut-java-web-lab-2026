package com.hmi.lab03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/exam")
public class ExamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String q1 = request.getParameter("q1");
        String[] q2s = request.getParameterValues("q2");
        String q3 = request.getParameter("q3");
        String q4 = request.getParameter("q4");

        int score = 0;

        if ("c".equals(q1)) score += 25;
        if (Arrays.equals(q2s, new String[]{"a","c"})) score += 25;
        if ("HttpServlet".equals(q3)) score += 25;
        if ("c".equals(q4)) score += 25;

        System.out.println("score = " + score);

        response.getWriter().write("092224110 李理想，你的成绩是："+score);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}