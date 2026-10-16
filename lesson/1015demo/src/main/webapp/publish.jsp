<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="error.jsp" %>
<%@ page import="java.util.Date" %>
<html>
<head>
    <title>校园通知发布</title>
</head>
<body>
<div style="width:880px;margin: 0 auto;padding:10px;border:1px solid grey">
<jsp:include page="header.jsp"/>
<h2>校园通知发布页面</h2>
<%-- 表单，提交到本页  --%>
<form method="post">
    通知标题：<input type="text" name="title" /><br/><br/>
    通知内容：<textarea rows="5" cols="30" name="content"></textarea><br/><br/>
    <input type="submit" value="发布通知">
</form>

<%
    //解决post中文乱码
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=UTF-8");
    // ===================== 九大内置对象演示 =====================
    // =====================1. 四大作用域赋值 =====================
    pageContext.setAttribute("page", "这是Page级别的数据，只在当前页面有效");
    request.setAttribute("request", "这是一次请求级别的数据，在同一次请求中有效");
    session.setAttribute("session", "这是一次会话级别的数据，在一次会话过程中有效");
    application.setAttribute("application", "这是application级别的数据");

    // =====================2.使用out对象进行的输出=================
    out.print("page：" + pageContext.getAttribute("page") + "<br/>");
    out.print("request：" + request.getAttribute("request") + "<br/>");
    out.print("session：" + session.getAttribute("session") + "<br/>");
    out.print("application：" + application.getAttribute("application") + "<br/>");

    // =====================3.获取表单提交数据=====================
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    boolean published = false;
    if (title != null && content != null && !title.trim().isEmpty() && !content.trim().isEmpty()) {
        application.setAttribute("title", title);
        application.setAttribute("content", content);
        application.setAttribute("publishTime", new Date());
        published = true;
    }

    // =====================4. application读取web.xml【全局参数】 ==========
    String schoolName = application.getInitParameter("schoolName");
    String email = application.getInitParameter("email");
    out.print("全局参数 schoolName：" + schoolName + "<br/>");
    out.print("全局参数 email：" + email + "<br/>");

    // =====================5. config读取JSP【页面局部初始化参数】 ==========
    String info = config.getInitParameter("info");
    out.print("局部初始化参数 info：" + info + "<br/>");
%>

<% if (published) { %>
<p style="color:green;font-size:18px;">✅ 通知发布成功！</p>
<% } %>

<br>
<a href="show.jsp">查看已发布通知</a>
</div>
</body>
</html>
