<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.List, java.util.ArrayList, java.util.Date" %>
<%--
  homework03：简易用户留言板（单页自处理）
  涉及知识点：
  （1）page 指令：设置页面编码、指定 Java 语言、导入 java.util 包的集合类和日期类
  （2）声明 <%! ... %>：定义 Message 类封装留言信息
  （3）application 内置对象：全局共享保存所有留言，服务器运行期间不丢失
  （4）脚本片段 <% ... %>：处理表单提交逻辑；request 获取参数，out 输出提示信息
  （5）表达式 <%= ... %>：输出所有历史留言（留言人、内容、时间）
--%>
<%!
    // 使用 JSP 声明元素定义 Message 类（生成 Servlet 的成员），用于封装留言信息
    class Message {
        private String username;   // 留言人
        private String content;    // 留言内容
        private Date time;         // 留言时间

        public Message(String username, String content, Date time) {
            this.username = username;
            this.content = content;
            this.time = time;
        }

        public String getUsername() {   // 留言人 getter
            return username;
        }

        public String getContent() {    // 留言内容 getter
            return content;
        }

        public Date getTime() {         // 留言时间 getter
            return time;
        }
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>简易留言板</title>
    <style>
        body { font-family: "Microsoft YaHei", Arial, sans-serif; background: #f5f6fa; margin: 0; }
        .box { width: 660px; margin: 30px auto; background: #ffffff; padding: 24px 32px;
               border: 1px solid #dddddd; border-radius: 8px; }
        h2 { text-align: center; color: #333333; border-bottom: 2px solid #4a90d9;
             padding-bottom: 10px; }
        .tip-error { color: #c0392b; font-weight: bold; }
        .tip-ok { color: #27ae60; font-weight: bold; }
        form p label { display: inline-block; width: 70px; }
        input[type="text"] { width: 300px; padding: 6px; border: 1px solid #cccccc;
                             border-radius: 4px; }
        textarea { width: 480px; height: 90px; padding: 6px; border: 1px solid #cccccc;
                   border-radius: 4px; vertical-align: top; }
        input[type="submit"] { padding: 8px 28px; background: #4a90d9; color: #ffffff;
                               border: none; border-radius: 4px; cursor: pointer; }
        hr { border: none; border-top: 1px dashed #cccccc; margin: 24px 0; }
        .msg-item { border: 1px solid #eeeeee; border-left: 4px solid #4a90d9;
                    border-radius: 4px; padding: 10px 14px; margin-bottom: 12px;
                    background: #fafbfc; }
        .msg-head { color: #888888; font-size: 13px; margin-bottom: 6px; }
        .msg-head b { color: #4a90d9; }
        .msg-content { color: #333333; line-height: 1.6; }
        .empty { color: #999999; text-align: center; }
    </style>
</head>
<body>
<div class="box">
    <h2>简易留言板</h2>

    <%
        // ============ 脚本片段：获取/初始化全局留言集合 ============
        // 设置请求编码，防止 POST 中文参数乱码
        request.setCharacterEncoding("UTF-8");

        // 使用 application 内置对象存取留言集合（全局共享，所有用户可见，
        // 存放在 application 域中，服务器运行期间不会丢失）
        List<Message> messages = (List<Message>) application.getAttribute("messages");
        if (messages == null) {                     // 服务器启动后第一次访问时初始化
            messages = new ArrayList<Message>();
            application.setAttribute("messages", messages);
        }

        // ============ 脚本片段：处理表单提交逻辑 ============
        // 使用 request 内置对象获取表单参数 username 和 content
        String username = request.getParameter("username");
        String content = request.getParameter("content");

        // 表单提交后参数不为 null；未提交（首次访问）则只显示下面的表单
        if (username != null || content != null) {
            boolean empty = (username.trim().isEmpty()) || (content.trim().isEmpty());
            if (empty) {
                // 使用 out 内置对象输出提示信息
                out.print("<p class='tip-error'>留言不能为空！</p>");
            } else {
                // 创建 Message 对象并添加到 application 域的集合中
                Message msg = new Message(username.trim(), content.trim(), new Date());
                messages.add(msg);
                out.print("<p class='tip-ok'>留言成功！</p>");
            }
        }
    %>

    <%-- 留言提交表单：通过 username 和 content 参数提交到本页面 --%>
    <form action="message.jsp" method="post">
        <p>
            <label for="username">留 言 人：</label>
            <input type="text" id="username" name="username"
                   placeholder="请输入您的昵称"/>
        </p>
        <p>
            <label for="content">留言内容：</label>
            <textarea id="content" name="content"
                      placeholder="请输入留言内容（不能为空）"></textarea>
        </p>
        <p>
            <label></label>
            <input type="submit" value="提交留言"/>
        </p>
    </form>

    <hr/>

    <h3>历史留言（共 <%= messages.size() %> 条）</h3>
    <%
        // ============ 脚本片段遍历 + 表达式输出所有历史留言 ============
        if (messages.isEmpty()) {
    %>
    <p class="empty">暂无留言，快来抢沙发吧～</p>
    <%
        } else {
            for (Message m : messages) {
    %>
    <div class="msg-item">
        <div class="msg-head">
            留言人：<b><%= m.getUsername() %></b>
            &nbsp;|&nbsp; 留言时间：<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(m.getTime()) %>
        </div>
        <div class="msg-content"><%= m.getContent() %></div>
    </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
