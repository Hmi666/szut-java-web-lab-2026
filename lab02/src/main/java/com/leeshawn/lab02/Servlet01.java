package com.leeshawn.lab02;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/count")
public class Servlet01 implements Servlet {

    private int visitCount;
    private Path countFile;

    @Override
    public void init(ServletConfig config) throws ServletException {
        countFile = Path.of("visit_count.txt");

        try {
            if(!Files.exists(countFile)){
                Files.writeString(countFile,"0");
            }

            visitCount = Integer.parseInt(Files.readString(countFile).trim());

            System.out.println("[init] 历史访问数量：" + visitCount);
        } catch (Exception e){
            System.out.println("[error] 初始化失败" + e);
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        visitCount++;

        System.out.println("[service] 当前访问量：" + visitCount);

        res.setContentType("text/html;charset=UTF-8");

        res.getWriter().println(("<h1>092224110 李理想</h1>\n" +
                                 "<h1>当前访问量："+ visitCount + "</h1>\n"));
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {
        try{
            Files.writeString(
                    countFile,
                    String.valueOf(visitCount)
            );

            System.out.println("[destroy] 最终访问量：" + visitCount);
            System.out.println("保存位置：" + countFile.toAbsolutePath());
        } catch (Exception e){
            System.out.println("文件保存失败：" + e);
        }
    }
}
