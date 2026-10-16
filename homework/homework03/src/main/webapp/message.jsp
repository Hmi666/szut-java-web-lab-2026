<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.List, java.util.ArrayList, java.util.Date" %>
<%!
    class Message {
        private String username;
        private String content;
        private Date time;

        public Message(String username, String content, Date time) {
            this.username = username;
            this.content = content;
            this.time = time;
        }

        public String getUsername() {
            return username;
        }

        public String getContent() {
            return content;
        }

        public Date getTime() {
            return time;
        }
    }

    private String escapeHtml(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>简易留言板</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/basecoat-css@1.0.2/dist/basecoat.cdn.min.css"/>
</head>

<body class="min-h-screen bg-zinc-100 px-4 py-10">
<main class="mx-auto w-full max-w-2xl">

    <div class="card">
        <header>
            <h2>简易留言板</h2>
            <p>留言保存在 application 域中，服务器运行期间全局共享</p>
        </header>

        <section class="flex flex-col gap-5">

            <%
                request.setCharacterEncoding("UTF-8");

                List<Message> messages = (List<Message>) application.getAttribute("messages");
                if (messages == null) {
                    messages = new ArrayList<Message>();
                    application.setAttribute("messages", messages);
                }

                String username = request.getParameter("username");
                String content = request.getParameter("content");

                if (username != null && content != null) {
                    boolean isEmpty = username.trim().isEmpty() || content.trim().isEmpty();

                    if (isEmpty) {
                        out.print("<div class='alert' data-variant='destructive'>"
                                + "<h2>留言不能为空！</h2>"
                                + "<section>请把「留言人」和「留言内容」都填写完整后再提交。</section>"
                                + "</div>");
                    } else {
                        Message message = new Message(username.trim(), content.trim(), new Date());
                        messages.add(message);
                        out.print("<div class='alert'>"
                                + "<h2>留言成功！</h2>"
                                + "<section>您的留言已保存到 application 域，所有访问者都能看到。</section>"
                                + "</div>");
                    }
                }
            %>

            <form action="message.jsp" method="post" class="fieldset">
                <div class="field">
                    <label for="username">留言人</label>
                    <input class="input" type="text" id="username" name="username"
                           placeholder="请输入您的昵称" autocomplete="off"/>
                </div>

                <div class="field">
                    <label for="content">留言内容</label>
                    <textarea class="textarea" id="content" name="content" rows="4"
                              placeholder="请输入留言内容（不能为空）"></textarea>
                </div>

                <div class="flex justify-end">
                    <button type="submit" class="btn">提交留言</button>
                </div>
            </form>
        </section>

        <section class="flex flex-col gap-4">
            <hr/>

            <h3 class="text-base font-semibold">
                历史留言（共 <span class="badge"><%= messages.size() %></span> 条）
            </h3>

            <%
                if (messages.isEmpty()) {
            %>
            <section class="empty">
                <header>
                    <figure>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                             stroke-linejoin="round">
                            <path d="M22 17a2 2 0 0 1-2 2H6l-4 4V5a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2z"/>
                        </svg>
                    </figure>
                    <h3>暂无留言</h3>
                    <p>还没有人留言，快来抢沙发吧～</p>
                </header>
            </section>
            <%
                } else {
            %>
            <div class="item-group" role="list">
                <%
                    java.text.SimpleDateFormat fmt =
                            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (Message m : messages) {
                %>
                <article class="item" data-variant="outline" role="listitem">
                    <section>
                        <h4><%= escapeHtml(m.getUsername()) %></h4>
                        <p class="whitespace-pre-wrap break-words"><%= escapeHtml(m.getContent()) %></p>
                    </section>
                    <aside class="text-zinc-500">
                        <%= fmt.format(m.getTime()) %>
                    </aside>
                </article>
                <%
                    }
                %>
            </div>
            <%
                }
            %>
        </section>
    </div>
</main>
</body>
</html>
