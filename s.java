/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author joeljchalappuram
 */
public class InsertEmployeeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("employeecode");
        String first = request.getParameter("firstname");
        String last = request.getParameter("lastname");
        String email = request.getParameter("email");
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABC", "root", "Joel*123*abc");
            String sql = "INSERT INTO employee (employeecode, firstname, lastname, email) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(code));
            ps.setString(2, first);
            ps.setString(3, last);
            ps.setString(4, email);
            ps.executeUpdate();
            response.sendRedirect("display.jsp");

        }
        catch(Exception e){
            response.getWriter().println("Error: " + e.getMessage());
        }
        }  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String ageStr = request.getParameter("age");

        String message = "";

        // Validate email format using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        boolean isEmailValid = Pattern.matches(emailRegex, email);

        // Validate age
        int age = 0;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            message = "Invalid age format!";
        }

        if (!isEmailValid) {
            message = "Invalid email format!";
        } else if (age < 18) {
            message = "You must be 18 or older to register.";
        }

        if (!message.isEmpty()) {
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
            dispatcher.forward(request, response);
        } else {
            // Success - for now, just show confirmation (could save to DB here)
            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h2>Registration Successful</h2>");
            response.getWriter().println("<p>Name: " + name + "</p>");
            response.getWriter().println("<p>Email: " + email + "</p>");
            response.getWriter().println("<p>Age: " + age + "</p>");
            response.getWriter().println("</body></html>");
        }
    }
}
