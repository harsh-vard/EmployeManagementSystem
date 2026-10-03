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
import java.sql.SQLException;

@WebServlet("/addDepartment")
public class AddDepartmentServlet extends HttpServlet {

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
        String description = request.getParameter("description");

        name = name == null ? "" : name.trim();
        description = description == null ? "" : description.trim();

        // Department name validation
        if (name.isEmpty()) {
            showError(
                    response,
                    "Department name is required."
            );
            return;
        }

        if (name.length() < 2) {
            showError(
                    response,
                    "Department name must contain at least 2 characters."
            );
            return;
        }

        Connection con = null;
        PreparedStatement checkDepartment = null;
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

            // Duplicate department check
            checkDepartment = con.prepareStatement(
                    "SELECT id FROM departments " +
                            "WHERE LOWER(name) = LOWER(?)"
            );

            checkDepartment.setString(1, name);

            rs = checkDepartment.executeQuery();

            if (rs.next()) {

                showError(
                        response,
                        "This department already exists."
                );

                return;
            }

            // Insert department
            String sql =
                    "INSERT INTO departments " +
                            "(name, description) " +
                            "VALUES (?, ?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, description);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {

                response.sendRedirect(
                        "viewDepartments?success=added"
                );

            } else {

                showError(
                        response,
                        "Department could not be added."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            showError(
                    response,
                    "Database error occurred while adding the department."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while adding the department."
            );

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (checkDepartment != null) {
                    checkDepartment.close();
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

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println(
                "<meta name='viewport' " +
                        "content='width=device-width, initial-scale=1.0'>"
        );

        out.println(
                "<title>Add Department Error | Employee Management System</title>"
        );

        out.println(
                "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' " +
                        "rel='stylesheet'>"
        );

        out.println(
                "<link rel='stylesheet' " +
                        "href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css'>"
        );

        out.println("<style>");

        out.println(
                "*{" +
                        "box-sizing:border-box;" +
                        "}"
        );

        out.println(
                "body{" +
                        "margin:0;" +
                        "min-height:100vh;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "padding:20px;" +
                        "font-family:Arial,sans-serif;" +
                        "color:#172033;" +
                        "background:" +
                        "radial-gradient(circle at 10% 10%,rgba(59,130,246,.18),transparent 30%)," +
                        "radial-gradient(circle at 90% 20%,rgba(139,92,246,.18),transparent 30%)," +
                        "radial-gradient(circle at 50% 100%,rgba(14,165,233,.12),transparent 35%)," +
                        "linear-gradient(135deg,#eef5ff,#f8f9ff 50%,#eef7ff);" +
                        "}"
        );

        out.println(
                ".error-card{" +
                        "width:540px;" +
                        "max-width:100%;" +
                        "padding:40px 32px;" +
                        "border-radius:25px;" +
                        "background:rgba(255,255,255,.82);" +
                        "backdrop-filter:blur(18px);" +
                        "-webkit-backdrop-filter:blur(18px);" +
                        "border:1px solid rgba(255,255,255,.90);" +
                        "box-shadow:0 25px 70px rgba(15,23,42,.12);" +
                        "text-align:center;" +
                        "}"
        );

        out.println(
                ".error-icon{" +
                        "width:74px;" +
                        "height:74px;" +
                        "margin:0 auto 18px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "border-radius:22px;" +
                        "background:#fff7ed;" +
                        "color:#ea580c;" +
                        "font-size:31px;" +
                        "}"
        );

        out.println(
                ".error-card h1{" +
                        "font-size:26px;" +
                        "font-weight:850;" +
                        "margin:0 0 10px;" +
                        "}"
        );

        out.println(
                ".error-message{" +
                        "color:#64748b;" +
                        "font-size:14px;" +
                        "line-height:1.6;" +
                        "margin-bottom:26px;" +
                        "word-break:break-word;" +
                        "}"
        );

        out.println(
                ".button-row{" +
                        "display:flex;" +
                        "justify-content:center;" +
                        "gap:10px;" +
                        "flex-wrap:wrap;" +
                        "}"
        );

        out.println(
                ".back-button{" +
                        "display:inline-flex;" +
                        "align-items:center;" +
                        "gap:8px;" +
                        "padding:11px 18px;" +
                        "border-radius:11px;" +
                        "background:linear-gradient(135deg,#2563eb,#4f46e5);" +
                        "color:white;" +
                        "text-decoration:none;" +
                        "font-size:13px;" +
                        "font-weight:800;" +
                        "transition:.25s;" +
                        "}"
        );

        out.println(
                ".back-button:hover{" +
                        "color:white;" +
                        "transform:translateY(-2px);" +
                        "box-shadow:0 10px 25px rgba(37,99,235,.25);" +
                        "}"
        );

        out.println(
                ".dashboard-button{" +
                        "display:inline-flex;" +
                        "align-items:center;" +
                        "gap:8px;" +
                        "padding:11px 18px;" +
                        "border-radius:11px;" +
                        "background:#f1f5f9;" +
                        "border:1px solid #e2e8f0;" +
                        "color:#334155;" +
                        "text-decoration:none;" +
                        "font-size:13px;" +
                        "font-weight:800;" +
                        "transition:.25s;" +
                        "}"
        );

        out.println(
                ".dashboard-button:hover{" +
                        "background:#e2e8f0;" +
                        "color:#0f172a;" +
                        "}"
        );

        out.println(
                "@media(max-width:600px){" +
                        ".error-card{padding:30px 20px;}" +
                        ".error-card h1{font-size:23px;}" +
                        ".button-row{flex-direction:column;}" +
                        ".back-button,.dashboard-button{width:100%;justify-content:center;}" +
                        "}"
        );

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='error-card'>");

        out.println(
                "<div class='error-icon'>" +
                        "<i class='bi bi-exclamation-triangle-fill'></i>" +
                        "</div>"
        );

        out.println(
                "<h1>Unable to Add Department</h1>"
        );

        out.println(
                "<div class='error-message'>" +
                        escapeHtml(message) +
                        "</div>"
        );

        out.println("<div class='button-row'>");

        out.println(
                "<a href='addDepartment.jsp' class='back-button'>" +
                        "<i class='bi bi-arrow-left'></i>" +
                        " Back to Add Department" +
                        "</a>"
        );

        out.println(
                "<a href='viewDepartments' class='dashboard-button'>" +
                        "<i class='bi bi-building'></i>" +
                        " View Departments" +
                        "</a>"
        );

        out.println("</div>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
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