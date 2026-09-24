package com.leeshawn.lab02;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/prime")
public class Servlet02 extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        String label = "092224110 李理想";
        System.out.println(label);
        res.getWriter().println(label);

        for (int i = 2; i <= 100; i++){
            boolean b = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if(i % j == 0){
                    b = false;
                    break;
                }
            }

            if(b){
                res.getWriter().print(i + " ");
                System.out.print(i+ " ");
            }
        }
    }
}
