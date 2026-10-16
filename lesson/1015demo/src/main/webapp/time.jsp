<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Date date = new Date();
    String datestr = null;
    String format = request.getParameter("format");
    if (format != null && !format.trim().isEmpty()) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        datestr = sdf.format(date);
    }
%>
<div style="color:#666;margin-bottom:10px;">
    当前时间：<%= datestr == null ? date : datestr %>
</div>
