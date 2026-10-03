package com.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/exportEmployees")
public class ExportEmployeesServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Admin Login Protection
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=employees.csv"
        );

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {
                response.getWriter().println(
                        "Error,Database connection failed"
                );
                return;
            }

            String sql =
                    "SELECT id, name, email, phone, department, salary " +
                            "FROM employees " +
                            "ORDER BY id";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            PrintWriter out = response.getWriter();

            // CSV Header
            out.println(
                    "ID,Name,Email,Phone,Department,Salary"
            );

            while (rs.next()) {

                int id = rs.getInt("id");

                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String department = rs.getString("department");

                double salary = rs.getDouble("salary");

                out.println(
                        id + "," +
                                csvValue(name) + "," +
                                csvValue(email) + "," +
                                csvValue(phone) + "," +
                                csvValue(department) + "," +
                                String.format("%.2f", salary)
                );
            }

            out.flush();

        } catch (Exception e) {

            e.printStackTrace();

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

    private String csvValue(String value) {

        if (value == null) {
            return "";
        }

        String escaped =
                value.replace("\"", "\"\"");

        return "\"" + escaped + "\"";
    }
}