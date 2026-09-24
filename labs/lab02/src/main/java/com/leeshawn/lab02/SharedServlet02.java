package com.leeshawn.lab02;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/shared_get")
public class SharedServlet02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String name =(String) context.getAttribute("name");
        String sex =(String) context.getAttribute("sex");

        String result ="get:\n name:"+name+"\n sex:"+ sex;

        System.out.println(result);
        resp.getWriter().print(result);
    }
}
