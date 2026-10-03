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

@WebServlet("/updateEmployee")
public class UpdateEmployeeServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Admin Login Protection
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String salaryText = request.getParameter("salary");

        // Clean input
        id = id == null ? "" : id.trim();
        name = name == null ? "" : name.trim();
        email = email == null ? "" : email.trim();
        phone = phone == null ? "" : phone.trim();
        department = department == null ? "" : department.trim();
        salaryText = salaryText == null ? "" : salaryText.trim();

        // ID validation
        int employeeId;

        try {

            employeeId = Integer.parseInt(id);

            if (employeeId <= 0) {

                showError(
                        response,
                        "Invalid employee ID."
                );

                return;
            }

        } catch (Exception e) {

            showError(
                    response,
                    "Invalid employee ID."
            );

            return;
        }

        // Name validation
        if (name.isEmpty()) {

            showError(
                    response,
                    "Employee name is required."
            );

            return;
        }

        if (name.length() < 2) {

            showError(
                    response,
                    "Employee name must contain at least 2 characters."
            );

            return;
        }

        // Email validation
        if (email.isEmpty()) {

            showError(
                    response,
                    "Email address is required."
            );

            return;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            showError(
                    response,
                    "Please enter a valid email address."
            );

            return;
        }

        // Salary validation
        double salary;

        try {

            salary = Double.parseDouble(salaryText);

            if (salary < 0) {

                showError(
                        response,
                        "Salary cannot be negative."
                );

                return;
            }

        } catch (Exception e) {

            showError(
                    response,
                    "Please enter a valid salary."
            );

            return;
        }

        Connection con = null;
        PreparedStatement checkEmail = null;
        PreparedStatement updateEmployee = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {

                showError(
                        response,
                        "Database connection failed."
                );

                return;
            }

            // Check duplicate email
            String checkSql =
                    "SELECT id FROM employees " +
                            "WHERE LOWER(email) = LOWER(?) AND id <> ?";

            checkEmail = con.prepareStatement(checkSql);

            checkEmail.setString(1, email);
            checkEmail.setInt(2, employeeId);

            rs = checkEmail.executeQuery();

            if (rs.next()) {

                showError(
                        response,
                        "Another employee is already using this email address."
                );

                return;
            }

            // Update employee
            String updateSql =
                    "UPDATE employees SET " +
                            "name = ?, " +
                            "email = ?, " +
                            "phone = ?, " +
                            "department = ?, " +
                            "salary = ? " +
                            "WHERE id = ?";

            updateEmployee =
                    con.prepareStatement(updateSql);

            updateEmployee.setString(1, name);
            updateEmployee.setString(2, email);
            updateEmployee.setString(3, phone);
            updateEmployee.setString(4, department);
            updateEmployee.setDouble(5, salary);
            updateEmployee.setInt(6, employeeId);

            int rowsUpdated =
                    updateEmployee.executeUpdate();

            if (rowsUpdated > 0) {

                response.sendRedirect(
                        "viewEmployees?success=updated"
                );

            } else {

                showError(
                        response,
                        "Employee record could not be updated."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while updating the employee."
            );

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (checkEmail != null) {
                    checkEmail.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (updateEmployee != null) {
                    updateEmployee.close();
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

    private void showError(
            HttpServletResponse response,
            String message)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().println(
                "<!DOCTYPE html>"
        );

        response.getWriter().println(
                "<html lang='en'>"
        );

        response.getWriter().println(
                "<head>"
        );

        response.getWriter().println(
                "<meta charset='UTF-8'>"
        );

        response.getWriter().println(
                "<meta name='viewport' " +
                        "content='width=device-width, initial-scale=1.0'>"
        );

        response.getWriter().println(
                "<title>Update Employee | Employee Management System</title>"
        );

        // Bootstrap
        response.getWriter().println(
                "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' " +
                        "rel='stylesheet'>"
        );

        // Bootstrap Icons
        response.getWriter().println(
                "<link rel='stylesheet' " +
                        "href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css'>"
        );

        response.getWriter().println(
                "<style>" +

                        "* {" +
                        "box-sizing:border-box;" +
                        "}" +

                        "body {" +
                        "margin:0;" +
                        "min-height:100vh;" +
                        "font-family:Arial,sans-serif;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "padding:20px;" +
                        "color:#172033;" +
                        "background:" +
                        "radial-gradient(circle at 10% 10%,rgba(59,130,246,.18),transparent 30%)," +
                        "radial-gradient(circle at 90% 20%,rgba(139,92,246,.18),transparent 30%)," +
                        "linear-gradient(135deg,#eef5ff,#f8f9ff 45%,#eef7ff);" +
                        "}" +

                        ".error-card {" +
                        "width:520px;" +
                        "max-width:100%;" +
                        "padding:38px 30px;" +
                        "border-radius:24px;" +
                        "background:rgba(255,255,255,.80);" +
                        "backdrop-filter:blur(18px);" +
                        "-webkit-backdrop-filter:blur(18px);" +
                        "border:1px solid rgba(255,255,255,.88);" +
                        "box-shadow:0 20px 60px rgba(15,23,42,.12);" +
                        "text-align:center;" +
                        "}" +

                        ".error-icon {" +
                        "width:70px;" +
                        "height:70px;" +
                        "margin:0 auto 18px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "border-radius:20px;" +
                        "background:#fef2f2;" +
                        "color:#dc2626;" +
                        "font-size:30px;" +
                        "}" +

                        ".error-card h1 {" +
                        "font-size:26px;" +
                        "font-weight:800;" +
                        "margin-bottom:10px;" +
                        "}" +

                        ".error-message {" +
                        "color:#64748b;" +
                        "font-size:14px;" +
                        "line-height:1.6;" +
                        "margin-bottom:25px;" +
                        "word-break:break-word;" +
                        "}" +

                        ".back-button {" +
                        "display:inline-flex;" +
                        "align-items:center;" +
                        "gap:7px;" +
                        "padding:11px 18px;" +
                        "border-radius:11px;" +
                        "background:linear-gradient(135deg,#2563eb,#4f46e5);" +
                        "color:white;" +
                        "text-decoration:none;" +
                        "font-size:13px;" +
                        "font-weight:700;" +
                        "transition:.25s;" +
                        "}" +

                        ".back-button:hover {" +
                        "color:white;" +
                        "transform:translateY(-2px);" +
                        "box-shadow:0 10px 25px rgba(37,99,235,.25);" +
                        "}" +

                        "@media(max-width:600px) {" +

                        ".error-card {" +
                        "padding:30px 20px;" +
                        "}" +

                        ".error-card h1 {" +
                        "font-size:23px;" +
                        "}" +

                        "}" +

                        "</style>"
        );

        response.getWriter().println(
                "</head>"
        );

        response.getWriter().println(
                "<body>"
        );

        response.getWriter().println(
                "<div class='error-card'>"
        );

        response.getWriter().println(
                "<div class='error-icon'>" +
                        "<i class='bi bi-exclamation-triangle-fill'></i>" +
                        "</div>"
        );

        response.getWriter().println(
                "<h1>Update Failed</h1>"
        );

        response.getWriter().println(
                "<div class='error-message'>" +
                        escapeHtml(message) +
                        "</div>"
        );

        response.getWriter().println(
                "<a href='viewEmployees' class='back-button'>" +
                        "<i class='bi bi-arrow-left'></i>" +
                        " Back to Employee List" +
                        "</a>"
        );

        response.getWriter().println(
                "</div>"
        );

        response.getWriter().println(
                "</body>"
        );

        response.getWriter().println(
                "</html>"
        );
    }

    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}