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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 0. 读取当前设备的基本信息
        String username = request.getParameter("username");
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        PrintWriter writer = response.getWriter();

        // 1. 账号不能为空
        if(username == null && username.isEmpty()){
            writer.write("账号不能为空！");
            return;
        }

        // 2. 输出uname + ip + ua 信息
        writer.write("<h3>登录信息</h3>");
        writer.write("<p>账号：" + username + "</p>");
        writer.write("<p>本次IP：" + ip);
        writer.write("<p>User-UA: " + ua);
        writer.write("<hr>");

        String deviceFingerprint = ip + "_" + ua.hashCode();
        ServletContext context = getServletContext();
        Set<String> trustedDevices = (Set<String>) context.getAttribute("trustedDevices");

        if (trustedDevices == null) {
            trustedDevices = new HashSet<>();
            context.setAttribute("trustedDevices", trustedDevices);
        }

        if (trustedDevices.isEmpty()) {
            trustedDevices.add(deviceFingerprint);
            writer.write("第一次登录，已加入可信列表");
        }else if (trustedDevices.contains(deviceFingerprint)) {
            writer.write("可信设备");
        } else {
            writer.write("不可信设备");
            writer.write("<form action=\"/TrustDeviceSelvet\" method=\"post\"> <input type=\"submit\" value=\" 信任设备 \"> </form>");
        }

        System.out.println("trustedDevices = " + trustedDevices);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}