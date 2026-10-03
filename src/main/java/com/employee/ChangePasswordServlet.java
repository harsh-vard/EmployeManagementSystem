package com.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Login Protection
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username =
                (String) request.getSession().getAttribute("adminUsername");

        if (username == null) {
            username =
                    (String) request.getSession().getAttribute("admin");
        }

        String currentPassword =
                request.getParameter("currentPassword");

        String newPassword =
                request.getParameter("newPassword");

        String confirmPassword =
                request.getParameter("confirmPassword");


        if (currentPassword == null ||
                newPassword == null ||
                confirmPassword == null) {

            response.sendRedirect(
                    "adminProfile.jsp?error=missing"
            );

            return;
        }


        if (newPassword.trim().isEmpty() ||
                confirmPassword.trim().isEmpty()) {

            response.sendRedirect(
                    "adminProfile.jsp?error=missing"
            );

            return;
        }


        if (!newPassword.equals(confirmPassword)) {

            response.sendRedirect(
                    "adminProfile.jsp?error=mismatch"
            );

            return;
        }


        if (newPassword.length() < 6) {

            response.sendRedirect(
                    "adminProfile.jsp?error=short"
            );

            return;
        }


        Connection con = null;
        PreparedStatement checkPs = null;
        PreparedStatement updatePs = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {

                response.sendRedirect(
                        "adminProfile.jsp?error=db"
                );

                return;
            }


            /* Verify current password */

            String checkSql =
                    "SELECT id FROM admins " +
                            "WHERE username = ? AND password = ?";

            checkPs = con.prepareStatement(checkSql);

            checkPs.setString(1, username);
            checkPs.setString(2, currentPassword);

            rs = checkPs.executeQuery();


            if (!rs.next()) {

                response.sendRedirect(
                        "adminProfile.jsp?error=current"
                );

                return;
            }


            /* Update password */

            String updateSql =
                    "UPDATE admins " +
                            "SET password = ? " +
                            "WHERE username = ?";

            updatePs = con.prepareStatement(updateSql);

            updatePs.setString(1, newPassword);
            updatePs.setString(2, username);

            int updated =
                    updatePs.executeUpdate();


            if (updated > 0) {

                response.sendRedirect(
                        "adminProfile.jsp?success=password"
                );

            } else {

                response.sendRedirect(
                        "adminProfile.jsp?error=update"
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "adminProfile.jsp?error=db"
            );

        } finally {

            try {
                if (rs != null) rs.close();
            } catch (Exception ignored) {
            }

            try {
                if (checkPs != null) checkPs.close();
            } catch (Exception ignored) {
            }

            try {
                if (updatePs != null) updatePs.close();
            } catch (Exception ignored) {
            }

            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }
}