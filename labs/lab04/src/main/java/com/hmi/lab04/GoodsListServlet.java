package com.hmi.lab04;

import com.hmi.lab04.model.Goods;
import com.hmi.lab04.util.GoodsRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 实验内容 1：商品列表页。
 * <p>展示所有商品，每个商品提供「加入购物车」链接（携带商品编号 id）。</p>
 */
@WebServlet("/goods")
public class GoodsListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"zh-CN\"><head><meta charset=\"UTF-8\">");
        out.println("<title>商品列表</title>");
        out.println("<style>");
        out.println("body{font-family:'Microsoft YaHei',sans-serif;margin:40px;color:#222;}");
        out.println("table{border-collapse:collapse;width:720px;}");
        out.println("th,td{border:1px solid #ddd;padding:10px 14px;text-align:left;}");
        out.println("th{background:#f5f5f5;}");
        out.println("a{color:#1a73e8;text-decoration:none;}a:hover{text-decoration:underline;}");
        out.println(".bar{margin-bottom:16px;}");
        out.println("</style></head><body>");
        out.println("<h2>商品列表</h2>");
        out.println("<div class=\"bar\"><a href=\"" + ctx + "/cart?action=view\">查看购物车</a></div>");
        out.println("<table>");
        out.println("<tr><th>商品编号</th><th>商品名称</th><th>单价（元）</th><th>操作</th></tr>");

        for (Goods goods : GoodsRepository.findAll()) {
            out.println("<tr>");
            out.println("<td>" + goods.getId() + "</td>");
            out.println("<td>" + goods.getName() + "</td>");
            out.println("<td>" + String.format("%.2f", goods.getPrice()) + "</td>");
            out.println("<td><a href=\"" + ctx + "/cart?action=add&id=" + goods.getId()
                    + "\">加入购物车</a></td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
