package com.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/deleteEmployee")
public class DeleteEmployeeServlet extends HttpServlet {

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

        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {

            response.sendRedirect("viewEmployees");

            return;
        }

        Connection con = null;
        PreparedStatement ps = null;

        try {

            int employeeId = Integer.parseInt(id);

            if (employeeId <= 0) {

                response.sendRedirect("viewEmployees");

                return;
            }

            con = DBConnection.getConnection();

            if (con == null) {

                showError(
                        response,
                        "Database connection failed."
                );

                return;
            }

            String sql =
                    "DELETE FROM employees WHERE id = ?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, employeeId);

            int rowsDeleted =
                    ps.executeUpdate();

            if (rowsDeleted > 0) {

                response.sendRedirect(
                        "viewEmployees?success=deleted"
                );

            } else {

                response.sendRedirect(
                        "viewEmployees?success=notfound"
                );
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "viewEmployees"
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while deleting the employee."
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
                "<title>Delete Employee | Employee Management System</title>"
        );

        response.getWriter().println(
                "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' " +
                        "rel='stylesheet'>"
        );

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
                        "background:#fef2f2;" +
                        "color:#dc2626;" +
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
                        "<i class='bi bi-trash3-fill'></i>" +
                        "</div>"
        );

        response.getWriter().println(
                "<h1>Delete Failed</h1>"
        );

        response.getWriter().println(
                "<div class='error-message'>" +
                        escapeHtml(message) +
                        "</div>"
        );

        response.getWriter().println(
                "<a href='viewEmployees' class='back-button'>" +
                        "<i class='bi bi-arrow-left'></i>" +
                        " Back to Employees" +
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