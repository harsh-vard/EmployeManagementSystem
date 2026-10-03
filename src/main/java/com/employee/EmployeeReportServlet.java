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

@WebServlet("/employeeReport")
public class EmployeeReportServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Admin Login Protection
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect("viewEmployees");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            int employeeId = Integer.parseInt(id);

            if (employeeId <= 0) {
                response.sendRedirect("viewEmployees");
                return;
            }

            con = DBConnection.getConnection();

            if (con == null) {
                response.getWriter().println(
                        "Error: Database connection failed."
                );
                return;
            }

            String sql =
                    "SELECT id, name, email, phone, department, salary " +
                            "FROM employees " +
                            "WHERE id = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, employeeId);

            rs = ps.executeQuery();

            if (rs.next()) {

                request.setAttribute(
                        "employeeId",
                        rs.getInt("id")
                );

                request.setAttribute(
                        "employeeName",
                        rs.getString("name")
                );

                request.setAttribute(
                        "employeeEmail",
                        rs.getString("email")
                );

                request.setAttribute(
                        "employeePhone",
                        rs.getString("phone")
                );

                request.setAttribute(
                        "employeeDepartment",
                        rs.getString("department")
                );

                request.setAttribute(
                        "employeeSalary",
                        rs.getDouble("salary")
                );

                request.getRequestDispatcher(
                        "employeeReport.jsp"
                ).forward(request, response);

            } else {

                response.sendRedirect("viewEmployees");

            }

        } catch (NumberFormatException e) {

            response.sendRedirect("viewEmployees");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.getMessage()
            );

        } finally {

            try {
                if (rs != null) rs.close();
            } catch (Exception ignored) {
            }

            try {
                if (ps != null) ps.close();
            } catch (Exception ignored) {
            }

            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }
}