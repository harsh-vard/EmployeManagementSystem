package com.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            String sql =
                    "SELECT id, username FROM admins " +
                            "WHERE username = ? AND password = ?";

            ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession();

                // Admin information session me save
                session.setAttribute("admin", username);
                session.setAttribute("adminId", rs.getInt("id"));
                session.setAttribute("adminUsername", rs.getString("username"));

                response.sendRedirect("index.jsp");

            } else {

                response.setContentType("text/html");

                response.getWriter().println(
                        "<h2>Invalid username or password.</h2>"
                );

                response.getWriter().println(
                        "<a href='login.jsp'>Back to Login</a>"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.getMessage()
            );

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}