package com.hmi.lab03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ExamServlet")
public class ExamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();


        String q1 = request.getParameter("q1");
        String q2 = request.getParameter("q2");

        HttpSession session = request.getSession();
        Boolean isSubmitted = (Boolean) session.getAttribute("isSubmitted");

        if(isSubmitted!=null && isSubmitted){
            writer.write("092224110 李理想，试卷已经提交，不可再次提交");
            writer.write("<a href='/ResetServlet'>重置试卷</a>");
            return;
        }

        int score = 0;
        if(q1 != null && q2 != null){
            if ("B".equals(q1)){
                score+=50;
            }

            if("A".equals(q2)){
                score+=50;
            }

        }
        writer.write("092224110 李理想，试卷提交成功，你的分数是：" + score);
        writer.write("\n请不要重复提交试卷");

        session.setAttribute("isSubmitted",true);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}