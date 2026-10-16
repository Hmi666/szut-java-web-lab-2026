<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
<div style="width:880px;margin: 0 auto;padding:10px;border:1px solid grey">
    <h2 style="color:red;">页面出错了</h2>
    <p>错误信息：<%= exception == null ? "未知错误" : exception.getMessage() %></p>
    <a href="publish.jsp">返回发布页面</a>
</div>
</body>
</html>
