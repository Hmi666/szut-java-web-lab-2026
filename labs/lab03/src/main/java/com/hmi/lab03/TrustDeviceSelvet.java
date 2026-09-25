package com.hmi.lab03;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/TrustDeviceSelvet")
public class TrustDeviceSelvet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 0. 读取当前设备的基本信息
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        String deviceFingerprint = ip + "_" + ua.hashCode();

        ServletContext context = getServletContext();
        Set<String> trustedDevices = (Set<String>) context.getAttribute("trustedDevices");
        if (trustedDevices == null) {
            trustedDevices = new HashSet<>();
            context.setAttribute("trustedDevices", trustedDevices);
        }
        trustedDevices.add(deviceFingerprint);

        PrintWriter writer = response.getWriter();
        writer.write("已信任当前设备 <br>");
        writer.write("<a href=\"login.html\">返回登录页面</a>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}