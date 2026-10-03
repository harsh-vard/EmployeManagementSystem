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

@WebServlet("/addEmployee")
public class AddEmployeeServlet extends HttpServlet {

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

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String salaryText = request.getParameter("salary");

        name = name == null ? "" : name.trim();
        email = email == null ? "" : email.trim();
        phone = phone == null ? "" : phone.trim();
        department = department == null ? "" : department.trim();
        salaryText = salaryText == null ? "" : salaryText.trim();

        // Name validation
        if (name.isEmpty()) {

            showError(
                    response,
                    "Name is required."
            );

            return;
        }

        // Email validation
        if (email.isEmpty()
                || !email.matches(
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
        PreparedStatement ps = null;
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

            // Duplicate email check
            checkEmail = con.prepareStatement(
                    "SELECT id FROM employees " +
                            "WHERE LOWER(email) = LOWER(?)"
            );

            checkEmail.setString(1, email);

            rs = checkEmail.executeQuery();

            if (rs.next()) {

                showError(
                        response,
                        "An employee with this email already exists."
                );

                return;
            }

            // Insert employee
            String sql =
                    "INSERT INTO employees " +
                            "(name, email, phone, department, salary) " +
                            "VALUES (?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, department);
            ps.setDouble(5, salary);

            int rowsInserted =
                    ps.executeUpdate();

            if (rowsInserted > 0) {

                response.sendRedirect(
                        "index.jsp?success=added"
                );

            } else {

                showError(
                        response,
                        "Employee could not be added."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while adding the employee."
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
                "<title>Add Employee | Employee Management System</title>"
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
                        "overflow:hidden;" +
                        "}" +

                        ".error-card {" +
                        "width:520px;" +
                        "max-width:100%;" +
                        "padding:40px 30px;" +
                        "border-radius:24px;" +
                        "background:rgba(255,255,255,.80);" +
                        "backdrop-filter:blur(18px);" +
                        "-webkit-backdrop-filter:blur(18px);" +
                        "border:1px solid rgba(255,255,255,.88);" +
                        "box-shadow:0 20px 60px rgba(15,23,42,.12);" +
                        "text-align:center;" +
                        "}" +

                        ".error-icon {" +
                        "width:72px;" +
                        "height:72px;" +
                        "margin:0 auto 18px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "border-radius:20px;" +
                        "background:#fff7ed;" +
                        "color:#ea580c;" +
                        "font-size:30px;" +
                        "}" +

                        ".error-card h1 {" +
                        "font-size:26px;" +
                        "font-weight:800;" +
                        "margin:0 0 10px;" +
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
                        "padding:11px 19px;" +
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
                        "<i class='bi bi-person-x-fill'></i>" +
                        "</div>"
        );

        response.getWriter().println(
                "<h1>Unable to Add Employee</h1>"
        );

        response.getWriter().println(
                "<div class='error-message'>" +
                        escapeHtml(message) +
                        "</div>"
        );

        response.getWriter().println(
                "<a href='index.jsp' class='back-button'>" +
                        "<i class='bi bi-arrow-left'></i>" +
                        " Back to Dashboard" +
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