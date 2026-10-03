package com.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/deleteDepartment")
public class DeleteDepartmentServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Admin Login Protection
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String idText = request.getParameter("id");

        if (idText == null || idText.trim().isEmpty()) {
            response.sendRedirect("viewDepartments");
            return;
        }

        int id;

        try {

            id = Integer.parseInt(idText);

            if (id <= 0) {
                response.sendRedirect("viewDepartments");
                return;
            }

        } catch (NumberFormatException e) {

            response.sendRedirect("viewDepartments");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {

                response.sendRedirect(
                        "viewDepartments?success=error"
                );
                return;
            }

            String sql =
                    "DELETE FROM departments WHERE id = ?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {

                response.sendRedirect(
                        "viewDepartments?success=deleted"
                );

            } else {

                response.sendRedirect(
                        "viewDepartments?success=notfound"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "viewDepartments?success=error"
            );

        } finally {

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