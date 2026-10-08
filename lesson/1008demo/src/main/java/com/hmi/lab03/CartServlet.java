package com.hmi.lab03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        Map<String, Integer> cartMap = new LinkedHashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    parseCart(cookie.getValue(), cartMap);
                }
            }
        }

        String action = request.getParameter("action");
        if ("clear".equals(action)) {
            cartMap.clear();
            Cookie cart = new Cookie("cart", "");
            cart.setMaxAge(0);
            response.addCookie(cart);
        } else {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                cartMap.put(id, cartMap.getOrDefault(id, 0) + 1);
                Cookie cart = new Cookie("cart", encodeCart(cartMap));
                cart.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cart);
            }
        }

        PrintWriter out = response.getWriter();
        out.write("<div style='width:600px;margin:50px auto;background:#fff;padding:30px;border-radius:10px;box-shadow:0 2px 10px #ddd;'>");
        out.write("<h3 style='text-align:center;color:#2c3e50;margin-bottom:20px'>购物车</h3>");
        out.write("<p style='font-size:18px'>购物车商品列表：</p>");
        out.write("<ul>");
        if (cartMap.isEmpty()) {
            out.write("<li>购物车为空</li>");
        } else {
            for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                out.write("<li>商品ID：" + entry.getKey() + "，数量：" + entry.getValue() + "</li>");
            }
        }
        out.write("</ul>");
        out.write("<a href='goodslist.html'>返回商品页面</a>");
        out.write("</div>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    private void parseCart(String value, Map<String, Integer> cartMap) {
        if (value == null || value.isEmpty()) {
            return;
        }
        for (String item : value.split("#")) {
            if (item.isEmpty()) {
                continue;
            }
            int colon = item.lastIndexOf(':');
            if (colon > 0 && colon < item.length() - 1) {
                String id = item.substring(0, colon);
                try {
                    int qty = Integer.parseInt(item.substring(colon + 1));
                    cartMap.put(id, cartMap.getOrDefault(id, 0) + qty);
                    continue;
                } catch (NumberFormatException ignored) {
                    // 旧格式：只有商品ID
                }
            }
            cartMap.put(item, cartMap.getOrDefault(item, 0) + 1);
        }
    }

    private String encodeCart(Map<String, Integer> cartMap) {
        StringJoiner joiner = new StringJoiner("#");
        for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
            joiner.add(entry.getKey() + ":" + entry.getValue());
        }
        return joiner.toString();
    }
}
