<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>通知查看</title>
</head>
<body>
<div style="width:880px;margin: 0 auto;padding:10px;border:1px solid grey">
<jsp:include page="header.jsp"/>
<h2>校园通知展示页面</h2>
<%
    out.print("<h3>读取四个作用域数据：</h3>");
    // 读取四个作用域数据
    out.print("page：" + pageContext.getAttribute("page") + "<br/>");
    out.print("request：" + request.getAttribute("request") + "<br/>");
    out.print("session：" + session.getAttribute("session") + "<br/>");
    out.print("application：" + application.getAttribute("application") + "<br/>");

    out.print("<hr/>");
    out.print("【全局通知信息】<br/>");
    // 读取全局通知信息
    Object title = application.getAttribute("title");
    Object content = application.getAttribute("content");
    Object publishTime = application.getAttribute("publishTime");
    if (title == null) {
        out.print("暂无已发布通知<br/>");
    } else {
        out.print("标题：" + title + "<br/>");
        out.print("内容：" + content + "<br/>");
        out.print("发布时间：" + publishTime + "<br/>");
    }

    // show.jsp读取web.xml全局参数（application）
    out.print("<hr/>");
    out.print("全局参数 schoolName：" + application.getInitParameter("schoolName") + "<br/>");
    out.print("全局参数 email：" + application.getInitParameter("email") + "<br/>");

    // show.jsp无法读取publish.jsp的局部init-param（局部参数只属于publish.jsp）
    out.print("局部初始化参数 info：" + config.getInitParameter("info") + "（应为 null，局部参数只属于 publish.jsp）<br/>");
%>
<br><br>
<a href="publish.jsp">返回发布页面</a>
</div>
</body>
</html>
