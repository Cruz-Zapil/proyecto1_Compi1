package com.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.DAO.DatabaseConnection;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) { // Si el usuario existe
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("welcome.jsp");
            } else {
            //    response.sendRedirect("index.jsp?message=Invalid%20username%20or%20password");
            response.sendRedirect("welcome.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
          //  response.sendRedirect("index.jsp?message=Error%20in%20database%20connection");
          response.sendRedirect("welcome.jsp");
        }
    }
}
