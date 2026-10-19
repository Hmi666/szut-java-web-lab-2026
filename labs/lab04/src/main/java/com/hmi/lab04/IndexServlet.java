package com.hmi.lab04;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 实验内容 2：主页。
 * <p>访问主页时先判断 Session 中是否存在登录用户：</p>
 * <ul>
 *     <li>Session 有效：直接展示欢迎页；</li>
 *     <li>Session 失效：读取「记住我」Cookie，存在用户名则重建 Session，完成自动登录；</li>
 *     <li>两者都没有：重定向到登录页。</li>
 * </ul>
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();

        // 1. 先判断 Session 中是否存在登录用户
        HttpSession session = request.getSession(false);
        String loginUser = (session != null) ? (String) session.getAttribute("loginUser") : null;
        boolean autoLogin = false;

        // 2. Session 失效时，读取「记住我」Cookie 自动登录
        if (loginUser == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (LoginServlet.REMEMBER_COOKIE.equals(cookie.getName())
                            && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                        loginUser = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                        // 重建 Session，完成自动登录
                        request.getSession().setAttribute("loginUser", loginUser);
                        autoLogin = true;
                        break;
                    }
                }
            }
        }

        // 3. 仍无登录用户，重定向到登录页
        if (loginUser == null) {
            response.sendRedirect(ctx + "/login.html");
            return;
        }

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"zh-CN\"><head><meta charset=\"UTF-8\">");
        out.println("<title>主页</title>");
        out.println("<style>");
        out.println("body{font-family:'Microsoft YaHei',sans-serif;margin:40px;color:#222;}");
        out.println("a{color:#1a73e8;text-decoration:none;}a:hover{text-decoration:underline;}");
        out.println(".tip{color:#888;font-size:13px;}");
        out.println("</style></head><body>");
        out.println("<h2>欢迎您，" + loginUser + "！</h2>");
        if (autoLogin) {
            out.println("<p class=\"tip\">（Session 已失效，通过「记住我」Cookie 自动登录）</p>");
        }
        out.println("<p><a href=\"" + ctx + "/goods\">进入商品列表 / 购物车</a></p>");
        out.println("<p><a href=\"" + ctx + "/logout\">退出登录</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
