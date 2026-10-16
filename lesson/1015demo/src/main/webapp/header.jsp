<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String schoolName = application.getInitParameter("schoolName");
    if (schoolName == null || schoolName.trim().isEmpty()) {
        schoolName = "校园通知系统";
    }
%>
<div style="background:#f5f7fa;border-bottom:1px solid #ddd;padding:10px 0;margin-bottom:15px;text-align:center;">
    <h1 style="margin:0;font-size:22px;"><%= schoolName %> 校园通知系统</h1>
    <jsp:include page="time.jsp">
        <jsp:param name="format" value="yyyy-MM-dd HH:mm:ss"/>
    </jsp:include>
</div>
