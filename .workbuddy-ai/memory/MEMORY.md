# Project Memory — java-web-labs

## 项目概况
- Maven 多模块 Java Web 课程实验项目（父 pom: com.leeshawn:java-web-labs，packaging pom，Java 11）。
- 模块分类：labs/lab01-03、lesson/XXXXdemo（课堂演示）、homework/homeworkXX（作业）。
- Servlet API: jakarta.servlet-api 6.1.0 (provided)；JSP API: jakarta.servlet.jsp-api 3.0.0 (provided)。

## 模块约定
- 子模块 pom 继承根 pom，relativePath 为 ../../pom.xml，packaging 为 war。
- 目录结构：src/main/webapp/WEB-INF/web.xml（jakartaee 6.0 空壳声明）+ JSP/HTML 页面；Java 源码包名 com.hmi.lab03。
- 理论题答案写在模块根目录 answers.txt（中文，"（题号）问题 + 答：…"格式）。
- 新建模块后须在根 pom <modules> 注册。

## 历史作业
- homework02：Servlet 登录（LoginServlet + login.html）。
- homework03（2026-10-16）：JSP 留言板 message.jsp，application 域 List<Message> 存留言，见 2026-10-16 日志。
