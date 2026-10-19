<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // 项目入口：统一跳转到主页 IndexServlet
    response.sendRedirect(request.getContextPath() + "/index");
%>
