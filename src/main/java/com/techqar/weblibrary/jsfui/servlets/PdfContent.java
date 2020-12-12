package com.techqar.weblibrary.jsfui.servlets;

import com.techqar.weblibrary.jsfui.controller.BookController;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "PdfContent", urlPatterns = {"/PdfContent"})
public class PdfContent extends HttpServlet {

    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (OutputStream out = response.getOutputStream()) {

            long id = Long.parseLong(request.getParameter("id"));
            long viewCount = Long.parseLong(request.getParameter("viewCount"));

            if(context == null) {
                response.sendRedirect(request.getContextPath() + "/error/error-pdf.html");
            } else {
                BookController bookController = context.getBean("bookController", BookController.class);

                byte[] content = bookController.getContent(id);

                response.setContentType(MediaType.APPLICATION_PDF_VALUE);

                bookController.updateViewCount(viewCount, id);

                response.setContentLength(content.length);
                out.write(content);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
