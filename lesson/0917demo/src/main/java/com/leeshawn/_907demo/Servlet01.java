package com.leeshawn._907demo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.nio.charset.StandardCharsets;

@WebServlet("/count")
public class Servlet01 implements Servlet {

    private static final String COUNT_KEY = "count";

    private ServletConfig servletConfig;

    private ServletContext servletContext;

    private File countFile;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
        this.servletContext = config.getServletContext();
        this.countFile = new File(servletContext.getRealPath("/WEB-INF/count.txt"));

        int count = readCount();
        servletContext.setAttribute(COUNT_KEY, count);
        System.out.println("[init - 092224110李理想] 访问量：" + count);
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        int count;
        synchronized (servletContext) {
            count = getContextCount() + 1;
            servletContext.setAttribute(COUNT_KEY, count);
        }
        writeCount(count);
        System.out.println("[service - 092224110李理想] 访问量：" + count);

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<html><body>");
        out.println("<h1>当前访问量：" + count + "</h1>");
        out.println("</body></html>");
    }

    private int getContextCount() {
        Object value = servletContext.getAttribute(COUNT_KEY);
        return value instanceof Integer ? (Integer) value : 0;
    }

    private int readCount() {
        if (!countFile.exists()) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(countFile), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("读取访问量失败：" + e.getMessage());
        }
        return 0;
    }

    private void writeCount(int count) {
        File parent = countFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(countFile), StandardCharsets.UTF_8))) {
            writer.print(count);
        } catch (IOException e) {
            System.out.println("写入访问量失败：" + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "count servlet";
    }

    @Override
    public void destroy() {
        int count = getContextCount();
        writeCount(count);
        System.out.println("[destroy - 092224110李理想] 访问量：" + count);
    }
}
