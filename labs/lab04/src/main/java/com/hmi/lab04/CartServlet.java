package com.hmi.lab04;

import com.hmi.lab04.model.Goods;
import com.hmi.lab04.util.CartUtil;
import com.hmi.lab04.util.GoodsRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 实验内容 1：购物车（基于 Cookie）。
 * <ul>
 *     <li>action=add：把商品加入购物车（重复商品数量累加，新商品新增记录），写回 Cookie；</li>
 *     <li>action=view：展示购物车页面（商品名称由编号查出）；</li>
 *     <li>action=clear：清空购物车。</li>
 * </ul>
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String ctx = request.getContextPath();

        if ("add".equals(action)) {
            addToCart(request, response);
            // 添加完成后重定向到购物车页，避免刷新页面时重复添加
            response.sendRedirect(ctx + "/cart?action=view");
            return;
        }
        if ("clear".equals(action)) {
            clearCart(request, response);
            response.sendRedirect(ctx + "/cart?action=view");
            return;
        }
        showCart(request, response);
    }

    /** 加入购物车：读取 Cookie -> 数量累加/新增 -> 写回 Cookie（7 天有效期）。 */
    private void addToCart(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Goods goods = GoodsRepository.findById(id);
        if (goods == null) {
            return;
        }
        Map<String, Integer> cart = readCart(request);
        // 重复商品数量累加，新商品默认为 1（Map.merge 一步完成）
        cart.merge(id, 1, Integer::sum);
        writeCart(response, request, cart);
    }

    /** 展示购物车页面。 */
    private void showCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ctx = request.getContextPath();
        Map<String, Integer> cart = readCart(request);

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"zh-CN\"><head><meta charset=\"UTF-8\">");
        out.println("<title>购物车</title>");
        out.println("<style>");
        out.println("body{font-family:'Microsoft YaHei',sans-serif;margin:40px;color:#222;}");
        out.println("table{border-collapse:collapse;width:720px;}");
        out.println("th,td{border:1px solid #ddd;padding:10px 14px;text-align:left;}");
        out.println("th{background:#f5f5f5;}");
        out.println("a{color:#1a73e8;text-decoration:none;}a:hover{text-decoration:underline;}");
        out.println(".total{font-weight:bold;color:#c00;}");
        out.println(".bar{margin-bottom:16px;}");
        out.println("</style></head><body>");
        out.println("<h2>我的购物车</h2>");
        out.println("<div class=\"bar\"><a href=\"" + ctx + "/goods\">返回商品列表</a>");
        out.println("　|　<a href=\"" + ctx + "/cart?action=clear\">清空购物车</a></div>");

        if (cart.isEmpty()) {
            out.println("<p>购物车为空，快去挑选商品吧！</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>商品编号</th><th>商品名称</th><th>单价（元）</th>"
                    + "<th>数量</th><th>小计（元）</th></tr>");
            double total = 0;
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                Goods goods = GoodsRepository.findById(entry.getKey());
                String name = (goods == null) ? "未知商品" : goods.getName();
                double price = (goods == null) ? 0 : goods.getPrice();
                int quantity = entry.getValue();
                double subtotal = price * quantity;
                total += subtotal;
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + String.format("%.2f", price) + "</td>");
                out.println("<td>" + quantity + "</td>");
                out.println("<td>" + String.format("%.2f", subtotal) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<p class=\"total\">合计：" + String.format("%.2f", total) + " 元</p>");
        }
        out.println("</body></html>");
    }

    /** 从请求的 Cookie 中读取购物车。 */
    private Map<String, Integer> readCart(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CartUtil.CART_COOKIE.equals(cookie.getName())) {
                    return CartUtil.parse(cookie.getValue());
                }
            }
        }
        return CartUtil.parse(null);
    }

    /** 把购物车写回 Cookie，有效期 7 天。 */
    private void writeCart(HttpServletResponse response, HttpServletRequest request,
                           Map<String, Integer> cart) {
        Cookie cookie = new Cookie(CartUtil.CART_COOKIE, CartUtil.serialize(cart));
        cookie.setMaxAge(CartUtil.CART_MAX_AGE);
        cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        response.addCookie(cookie);
    }

    /** 清空购物车（把 Cookie 立即失效）。 */
    private void clearCart(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(CartUtil.CART_COOKIE, "");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        response.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
