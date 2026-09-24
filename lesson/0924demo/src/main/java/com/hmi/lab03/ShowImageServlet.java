package com.hmi.lab03;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@WebServlet("/show")
public class ShowImageServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
        ServletOutputStream out = resp.getOutputStream();
        String path = getServletContext().getRealPath("img/im1g.png");
        System.out.println("path = " + path);
        try {
            BufferedImage read = ImageIO.read(new File(path));
            ImageIO.write(read, "png", out);
        }catch (Exception e){
            resp.setContentType("text/html");
            out.write("092224110 李理想".getBytes());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }


}
