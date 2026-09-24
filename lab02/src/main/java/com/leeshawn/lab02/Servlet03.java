package com.leeshawn.lab02;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(value = "/init",
        initParams = {
            @WebInitParam(name = "sname",value = "李理想"),
                @WebInitParam(name="sno",value = "092224110")
        }
)
public class Servlet03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sname = getInitParameter("sname");
        String sno = getInitParameter("sno");

        System.out.println(sname + sno);
    }
}
