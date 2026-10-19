package com.hmi.lab04;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 实验内容 2：登录处理。
 * <ul>
 *     <li>校验预设账号（用户名＝本人姓名，密码＝本人学号）；</li>
 *     <li>登录成功：创建 Session 保存登录用户；</li>
 *     <li>勾选「记住我」：额外创建有效期 7 天的 Cookie 保存用户名；</li>
 *     <li>未勾选「记住我」：只创建 Session，并删除旧的「记住我」Cookie；</li>
 *     <li>页面跳转统一使用重定向（sendRedirect）。</li>
 * </ul>
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /** 预设用户名（本人姓名）。 */
    public static final String PRESET_USERNAME = "李理想";
    /** 预设密码（本人学号）。 */
    public static final String PRESET_PASSWORD = "092224110";

    /** 「记住我」Cookie 名称。 */
    public static final String REMEMBER_COOKIE = "rememberMe";
    /** 「记住我」Cookie 有效期：7 天（秒）。 */
    public static final int REMEMBER_MAX_AGE = 7 * 24 * 60 * 60;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        String ctx = request.getContextPath();
        String cookiePath = ctx.isEmpty() ? "/" : ctx;

        // 校验账号密码
        if (PRESET_USERNAME.equals(username) && PRESET_PASSWORD.equals(password)) {
            // 1. 无论是否勾选「记住我」，都创建 Session 保存登录状态
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", username);

            // 2. 处理「记住我」Cookie
            if ("true".equals(rememberMe)) {
                // 勾选：创建有效期 7 天的 Cookie 持久保存用户名
                Cookie cookie = new Cookie(REMEMBER_COOKIE,
                        URLEncoder.encode(username, StandardCharsets.UTF_8));
                cookie.setMaxAge(REMEMBER_MAX_AGE);
                cookie.setPath(cookiePath);
                response.addCookie(cookie);
            } else {
                // 未勾选：删除旧的「记住我」Cookie，浏览器关闭后登录状态失效
                Cookie cookie = new Cookie(REMEMBER_COOKIE, "");
                cookie.setMaxAge(0);
                cookie.setPath(cookiePath);
                response.addCookie(cookie);
            }

            // 3. 登录成功，重定向到主页
            response.sendRedirect(ctx + "/index");
        } else {
            // 登录失败，重定向回登录页并带上错误标记
            response.sendRedirect(ctx + "/login.html?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
