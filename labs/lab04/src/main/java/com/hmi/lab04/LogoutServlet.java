package com.hmi.lab04;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 实验内容 2：退出登录。
 * <p>销毁当前 Session，同时删除浏览器中的「记住我」Cookie，最后重定向回登录页。</p>
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ctx = request.getContextPath();
        String cookiePath = ctx.isEmpty() ? "/" : ctx;

        // 1. 销毁当前 Session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 2. 删除「记住我」Cookie
        Cookie cookie = new Cookie(LoginServlet.REMEMBER_COOKIE, "");
        cookie.setMaxAge(0);
        cookie.setPath(cookiePath);
        response.addCookie(cookie);

        // 3. 重定向回登录页
        response.sendRedirect(ctx + "/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
