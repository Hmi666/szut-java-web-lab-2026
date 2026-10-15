<%--
  Created by IntelliJ IDEA.
  User: hmi
  Date: 15/10/26
  Time: 8:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>计算圆的面积</title>
</head>
<body>
<form method="get" action="area.jsp">
  请输入半径：<input  step="any" required name="radius"
                   value="<%= request.getParameter("radius") == null ? "" : request.getParameter("radius") %>">
  <input type="submit" value="计算面积">
</form>

<%!
  double getArea(double r) {
    return Math.PI * r * r;
  }
%>

<%
  String radiusStr = request.getParameter("radius");
  if (radiusStr != null && !radiusStr.trim().isEmpty()) {
    try {
      double r = Double.parseDouble(radiusStr);
      if (r < 0) {
%>
<p style="color:red;">半径不能为负数，请重新输入。</p>
<%
      } else {
        double area = getArea(r);
%>
<p>半径=<%= r %> 的圆的面积= <%= area %></p>
<%
      }
    } catch (NumberFormatException e) {
%>
<p style="color:red;">输入无效，请输入合法的数字半径。</p>
<%
    }
  }
%>


<div style="position: fixed;bottom: 0;height: 30px;width: 100%;align-content: center; font-size: 9px;background-color: aliceblue;text-align: center;color: #777">092224110 李理想</div>
</body>
</html>
