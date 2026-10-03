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

@WebServlet("/editDepartment")
public class EditDepartmentServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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

            String sql =
                    "SELECT * FROM departments WHERE id = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (!rs.next()) {
                response.sendRedirect("viewDepartments");
                return;
            }

            String name = rs.getString("name");
            String description = rs.getString("description");

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
                    "<title>Edit Department | Employee Management System</title>"
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
                            "font-family:Arial,sans-serif;" +
                            "color:#172033;" +
                            "background:" +
                            "radial-gradient(circle at 10% 10%,rgba(59,130,246,.20),transparent 30%)," +
                            "radial-gradient(circle at 90% 15%,rgba(139,92,246,.18),transparent 30%)," +
                            "radial-gradient(circle at 50% 100%,rgba(14,165,233,.12),transparent 35%)," +
                            "linear-gradient(135deg,#eef5ff,#f8f9ff 50%,#eef7ff);" +
                            "}"
            );

            out.println(
                    ".topbar{" +
                            "position:sticky;" +
                            "top:0;" +
                            "z-index:1000;" +
                            "padding:15px 30px;" +
                            "background:rgba(15,23,42,.88);" +
                            "backdrop-filter:blur(16px);" +
                            "-webkit-backdrop-filter:blur(16px);" +
                            "box-shadow:0 8px 30px rgba(15,23,42,.16);" +
                            "}"
            );

            out.println(
                    ".brand{" +
                            "display:flex;" +
                            "align-items:center;" +
                            "gap:11px;" +
                            "color:white;" +
                            "text-decoration:none;" +
                            "font-weight:800;" +
                            "font-size:17px;" +
                            "}"
            );

            out.println(
                    ".brand-icon{" +
                            "width:38px;" +
                            "height:38px;" +
                            "display:flex;" +
                            "align-items:center;" +
                            "justify-content:center;" +
                            "border-radius:12px;" +
                            "background:linear-gradient(135deg,#2563eb,#7c3aed);" +
                            "font-size:18px;" +
                            "}"
            );

            out.println(
                    ".logout-btn{" +
                            "display:inline-flex;" +
                            "align-items:center;" +
                            "gap:7px;" +
                            "padding:9px 15px;" +
                            "border-radius:10px;" +
                            "background:rgba(239,68,68,.15);" +
                            "border:1px solid rgba(248,113,113,.25);" +
                            "color:#fecaca;" +
                            "text-decoration:none;" +
                            "font-size:13px;" +
                            "font-weight:700;" +
                            "transition:.25s;" +
                            "}"
            );

            out.println(
                    ".logout-btn:hover{" +
                            "background:#dc2626;" +
                            "color:white;" +
                            "transform:translateY(-1px);" +
                            "}"
            );

            out.println(
                    ".page-wrap{" +
                            "max-width:760px;" +
                            "margin:0 auto;" +
                            "padding:55px 20px 70px;" +
                            "}"
            );

            out.println(
                    ".hero{" +
                            "text-align:center;" +
                            "margin-bottom:30px;" +
                            "}"
            );

            out.println(
                    ".hero-icon{" +
                            "width:72px;" +
                            "height:72px;" +
                            "margin:0 auto 18px;" +
                            "display:flex;" +
                            "align-items:center;" +
                            "justify-content:center;" +
                            "border-radius:22px;" +
                            "background:linear-gradient(135deg,#2563eb,#7c3aed);" +
                            "color:white;" +
                            "font-size:30px;" +
                            "box-shadow:0 15px 35px rgba(37,99,235,.22);" +
                            "}"
            );

            out.println(
                    ".hero h1{" +
                            "font-size:32px;" +
                            "font-weight:850;" +
                            "margin:0 0 9px;" +
                            "}"
            );

            out.println(
                    ".hero p{" +
                            "margin:0;" +
                            "color:#64748b;" +
                            "font-size:14px;" +
                            "}"
            );

            out.println(
                    ".card-box{" +
                            "background:rgba(255,255,255,.78);" +
                            "backdrop-filter:blur(18px);" +
                            "-webkit-backdrop-filter:blur(18px);" +
                            "border:1px solid rgba(255,255,255,.90);" +
                            "border-radius:25px;" +
                            "padding:32px;" +
                            "box-shadow:0 25px 70px rgba(15,23,42,.12);" +
                            "}"
            );

            out.println(
                    ".record-badge{" +
                            "display:inline-flex;" +
                            "align-items:center;" +
                            "gap:7px;" +
                            "padding:7px 11px;" +
                            "border-radius:999px;" +
                            "background:#eff6ff;" +
                            "color:#2563eb;" +
                            "font-size:12px;" +
                            "font-weight:800;" +
                            "margin-bottom:20px;" +
                            "}"
            );

            out.println(
                    ".form-label-custom{" +
                            "font-size:13px;" +
                            "font-weight:800;" +
                            "color:#334155;" +
                            "margin-bottom:8px;" +
                            "}"
            );

            out.println(
                    ".form-control-custom{" +
                            "width:100%;" +
                            "border:1px solid #dbe3ef;" +
                            "border-radius:12px;" +
                            "padding:12px 14px;" +
                            "font-size:14px;" +
                            "background:rgba(255,255,255,.88);" +
                            "outline:none;" +
                            "transition:.2s;" +
                            "}"
            );

            out.println(
                    ".form-control-custom:focus{" +
                            "border-color:#6366f1;" +
                            "box-shadow:0 0 0 4px rgba(99,102,241,.10);" +
                            "background:white;" +
                            "}"
            );

            out.println(
                    "textarea.form-control-custom{" +
                            "min-height:145px;" +
                            "resize:vertical;" +
                            "}"
            );

            out.println(
                    ".id-box{" +
                            "display:flex;" +
                            "align-items:center;" +
                            "gap:10px;" +
                            "padding:11px 14px;" +
                            "border-radius:12px;" +
                            "background:#f8fafc;" +
                            "border:1px solid #e2e8f0;" +
                            "color:#475569;" +
                            "font-size:13px;" +
                            "margin-bottom:22px;" +
                            "}"
            );

            out.println(
                    ".id-value{" +
                            "font-weight:800;" +
                            "color:#2563eb;" +
                            "}"
            );

            out.println(
                    ".button-row{" +
                            "display:flex;" +
                            "gap:10px;" +
                            "margin-top:25px;" +
                            "}"
            );

            out.println(
                    ".save-btn{" +
                            "border:0;" +
                            "display:inline-flex;" +
                            "align-items:center;" +
                            "justify-content:center;" +
                            "gap:8px;" +
                            "padding:12px 20px;" +
                            "border-radius:12px;" +
                            "background:linear-gradient(135deg,#2563eb,#4f46e5);" +
                            "color:white;" +
                            "font-size:13px;" +
                            "font-weight:800;" +
                            "box-shadow:0 10px 25px rgba(37,99,235,.20);" +
                            "transition:.25s;" +
                            "}"
            );

            out.println(
                    ".save-btn:hover{" +
                            "transform:translateY(-2px);" +
                            "box-shadow:0 14px 30px rgba(37,99,235,.28);" +
                            "}"
            );

            out.println(
                    ".back-btn{" +
                            "display:inline-flex;" +
                            "align-items:center;" +
                            "justify-content:center;" +
                            "gap:8px;" +
                            "padding:12px 18px;" +
                            "border-radius:12px;" +
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
                    ".back-btn:hover{" +
                            "background:#e2e8f0;" +
                            "color:#0f172a;" +
                            "transform:translateY(-1px);" +
                            "}"
            );

            out.println(
                    ".helper{" +
                            "font-size:12px;" +
                            "color:#94a3b8;" +
                            "margin-top:7px;" +
                            "}"
            );

            out.println(
                    "@media(max-width:600px){" +
                            ".topbar{padding:13px 16px;}" +
                            ".brand{font-size:14px;}" +
                            ".brand-icon{width:34px;height:34px;font-size:16px;}" +
                            ".page-wrap{padding:35px 14px 50px;}" +
                            ".hero h1{font-size:26px;}" +
                            ".card-box{padding:22px 18px;border-radius:20px;}" +
                            ".button-row{flex-direction:column;}" +
                            ".save-btn,.back-btn{width:100%;}" +
                            "}"
            );

            out.println("</style>");
            out.println("</head>");

            out.println("<body>");

            out.println("<div class='topbar'>");

            out.println(
                    "<div class='container-fluid d-flex align-items-center " +
                            "justify-content-between px-0'>"
            );

            out.println(
                    "<a href='index.jsp' class='brand'>" +
                            "<span class='brand-icon'>" +
                            "<i class='bi bi-building'></i>" +
                            "</span>" +
                            "<span>Employee Management System</span>" +
                            "</a>"
            );

            out.println(
                    "<a href='logout' class='logout-btn'>" +
                            "<i class='bi bi-box-arrow-right'></i>" +
                            " Logout" +
                            "</a>"
            );

            out.println("</div>");
            out.println("</div>");

            out.println("<main class='page-wrap'>");

            out.println("<div class='hero'>");

            out.println(
                    "<div class='hero-icon'>" +
                            "<i class='bi bi-pencil-square'></i>" +
                            "</div>"
            );

            out.println("<h1>Edit Department</h1>");

            out.println(
                    "<p>Update department information and save your changes.</p>"
            );

            out.println("</div>");

            out.println("<div class='card-box'>");

            out.println(
                    "<div class='record-badge'>" +
                            "<i class='bi bi-shield-check'></i>" +
                            " Secure Department Record" +
                            "</div>"
            );

            out.println(
                    "<div class='id-box'>" +
                            "<i class='bi bi-hash'></i>" +
                            "<span>Department ID:</span>" +
                            "<span class='id-value'>" +
                            id +
                            "</span>" +
                            "</div>"
            );

            out.println(
                    "<form action='editDepartment' method='post'>"
            );

            out.println(
                    "<input type='hidden' name='id' value='" +
                            id +
                            "'>"
            );

            out.println(
                    "<div class='mb-4'>"
            );

            out.println(
                    "<label class='form-label-custom'>Department Name</label>"
            );

            out.println(
                    "<input type='text' " +
                            "name='name' " +
                            "class='form-control-custom' " +
                            "value='" +
                            escapeHtml(name) +
                            "' " +
                            "placeholder='Enter department name' " +
                            "required>"
            );

            out.println(
                    "<div class='helper'>" +
                            "Use a clear and unique department name." +
                            "</div>"
            );

            out.println("</div>");

            out.println(
                    "<div class='mb-2'>"
            );

            out.println(
                    "<label class='form-label-custom'>Description</label>"
            );

            out.println(
                    "<textarea " +
                            "name='description' " +
                            "class='form-control-custom' " +
                            "placeholder='Enter department description'>" +
                            escapeHtml(description) +
                            "</textarea>"
            );

            out.println("</div>");

            out.println("<div class='button-row'>");

            out.println(
                    "<button type='submit' class='save-btn'>" +
                            "<i class='bi bi-check2-circle'></i>" +
                            " Update Department" +
                            "</button>"
            );

            out.println(
                    "<a href='viewDepartments' class='back-btn'>" +
                            "<i class='bi bi-arrow-left'></i>" +
                            " Back to Departments" +
                            "</a>"
            );

            out.println("</div>");

            out.println("</form>");

            out.println("</div>");

            out.println("</main>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while loading the department."
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

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String idText = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        idText = idText == null ? "" : idText.trim();
        name = name == null ? "" : name.trim();
        description = description == null ? "" : description.trim();

        int id;

        try {

            id = Integer.parseInt(idText);

            if (id <= 0) {
                showError(
                        response,
                        "Invalid department ID."
                );
                return;
            }

        } catch (NumberFormatException e) {

            showError(
                    response,
                    "Invalid department ID."
            );
            return;
        }

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

            checkDepartment = con.prepareStatement(
                    "SELECT id FROM departments " +
                            "WHERE LOWER(name) = LOWER(?) " +
                            "AND id <> ?"
            );

            checkDepartment.setString(1, name);
            checkDepartment.setInt(2, id);

            rs = checkDepartment.executeQuery();

            if (rs.next()) {

                showError(
                        response,
                        "This department name already exists."
                );

                return;
            }

            String sql =
                    "UPDATE departments " +
                            "SET name = ?, description = ? " +
                            "WHERE id = ?";

            ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, id);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {

                response.sendRedirect(
                        "viewDepartments?success=updated"
                );

            } else {

                showError(
                        response,
                        "Department record could not be updated."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while updating the department."
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
                "<title>Department Error | Employee Management System</title>"
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
                "body{" +
                        "margin:0;" +
                        "min-height:100vh;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "padding:20px;" +
                        "font-family:Arial,sans-serif;" +
                        "background:" +
                        "radial-gradient(circle at 10% 10%,rgba(59,130,246,.18),transparent 30%)," +
                        "radial-gradient(circle at 90% 20%,rgba(139,92,246,.18),transparent 30%)," +
                        "linear-gradient(135deg,#eef5ff,#f8f9ff,#eef7ff);" +
                        "}"
        );

        out.println(
                ".error-card{" +
                        "width:520px;" +
                        "max-width:100%;" +
                        "padding:38px 30px;" +
                        "border-radius:24px;" +
                        "background:rgba(255,255,255,.82);" +
                        "backdrop-filter:blur(18px);" +
                        "-webkit-backdrop-filter:blur(18px);" +
                        "border:1px solid rgba(255,255,255,.9);" +
                        "box-shadow:0 25px 70px rgba(15,23,42,.12);" +
                        "text-align:center;" +
                        "}"
        );

        out.println(
                ".error-icon{" +
                        "width:72px;" +
                        "height:72px;" +
                        "margin:0 auto 18px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "border-radius:22px;" +
                        "background:#fef2f2;" +
                        "color:#dc2626;" +
                        "font-size:30px;" +
                        "}"
        );

        out.println(
                ".error-card h1{" +
                        "font-size:26px;" +
                        "font-weight:850;" +
                        "margin-bottom:10px;" +
                        "color:#172033;" +
                        "}"
        );

        out.println(
                ".error-message{" +
                        "color:#64748b;" +
                        "font-size:14px;" +
                        "line-height:1.6;" +
                        "margin-bottom:25px;" +
                        "word-break:break-word;" +
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

        out.println("</style>");
        out.println("</head>");

        out.println("<body>");

        out.println("<div class='error-card'>");

        out.println(
                "<div class='error-icon'>" +
                        "<i class='bi bi-exclamation-triangle-fill'></i>" +
                        "</div>"
        );

        out.println("<h1>Update Failed</h1>");

        out.println(
                "<div class='error-message'>" +
                        escapeHtml(message) +
                        "</div>"
        );

        out.println(
                "<a href='viewDepartments' class='back-button'>" +
                        "<i class='bi bi-arrow-left'></i>" +
                        " Back to Departments" +
                        "</a>"
        );

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