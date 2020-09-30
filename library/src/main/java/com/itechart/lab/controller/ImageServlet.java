package com.itechart.lab.controller;

import com.itechart.lab.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        OutputStream oImage;
        ResultSet rs = null;
        BookService bookService = BookService.getInstance();
        PreparedStatement pstmt = null;
      int bookId = Integer.parseInt( request.getParameter("bookId"));
        try {

            byte barray[] = bookService.retrieveImage(bookId);
            response.setContentType("image/gif");
            oImage=response.getOutputStream();
            oImage.write(barray);
            oImage.flush();
            oImage.close();

        }
        catch(Exception ex){

        }finally {
            try{


            }catch(Exception ex){

            }
        }
    }
}
