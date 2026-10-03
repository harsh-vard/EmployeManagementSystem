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

@WebServlet("/employeeDetails")
public class EmployeeDetailsServlet extends HttpServlet {

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

            showError(
                    response,
                    "Invalid employee ID."
            );

            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            int employeeId = Integer.parseInt(id);

            if (employeeId <= 0) {

                showError(
                        response,
                        "Invalid employee ID."
                );

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
                    "SELECT * FROM employees WHERE id = ?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, employeeId);

            rs = ps.executeQuery();

            if (!rs.next()) {

                showNotFound(response);

                return;
            }

            String name = rs.getString("name");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String department = rs.getString("department");
            double salary = rs.getDouble("salary");

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
                    "<title>Employee Details | Employee Management System</title>"
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

            response.getWriter().println("<style>");

            response.getWriter().println("""
                    * {
                        box-sizing: border-box;
                    }

                    body {
                        margin: 0;
                        min-height: 100vh;
                        font-family: Arial, sans-serif;
                        color: #172033;
                        background:
                            radial-gradient(circle at 10% 10%, rgba(59,130,246,.18), transparent 30%),
                            radial-gradient(circle at 90% 20%, rgba(139,92,246,.18), transparent 30%),
                            radial-gradient(circle at 50% 100%, rgba(14,165,233,.14), transparent 35%),
                            linear-gradient(135deg, #eef5ff, #f8f9ff 45%, #eef7ff);
                        overflow-x: hidden;
                    }

                    body::before {
                        content: "";
                        position: fixed;
                        width: 300px;
                        height: 300px;
                        border-radius: 50%;
                        background: rgba(59,130,246,.12);
                        filter: blur(70px);
                        top: 80px;
                        left: -100px;
                        animation: floatOne 8s ease-in-out infinite;
                        pointer-events: none;
                    }

                    body::after {
                        content: "";
                        position: fixed;
                        width: 330px;
                        height: 330px;
                        border-radius: 50%;
                        background: rgba(124,58,237,.10);
                        filter: blur(80px);
                        right: -120px;
                        bottom: 30px;
                        animation: floatTwo 10s ease-in-out infinite;
                        pointer-events: none;
                    }

                    @keyframes floatOne {
                        0%, 100% {
                            transform: translate(0, 0);
                        }

                        50% {
                            transform: translate(50px, 35px);
                        }
                    }

                    @keyframes floatTwo {
                        0%, 100% {
                            transform: translate(0, 0);
                        }

                        50% {
                            transform: translate(-45px, -30px);
                        }
                    }

                    .main-header {
                        position: sticky;
                        top: 0;
                        z-index: 1000;
                        padding: 16px 35px;
                        background: rgba(15, 23, 42, .88);
                        backdrop-filter: blur(18px);
                        -webkit-backdrop-filter: blur(18px);
                        border-bottom: 1px solid rgba(255,255,255,.10);
                        box-shadow: 0 8px 30px rgba(15,23,42,.18);
                    }

                    .header-inner {
                        max-width: 1100px;
                        margin: auto;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        gap: 20px;
                    }

                    .brand {
                        display: flex;
                        align-items: center;
                        gap: 12px;
                        color: white;
                    }

                    .brand-icon {
                        width: 44px;
                        height: 44px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        border-radius: 13px;
                        background: linear-gradient(135deg, #3b82f6, #7c3aed);
                        font-size: 21px;
                        box-shadow: 0 8px 20px rgba(59,130,246,.30);
                    }

                    .brand-title {
                        font-size: 18px;
                        font-weight: 700;
                    }

                    .brand-subtitle {
                        font-size: 12px;
                        color: #aebbd0;
                        margin-top: 2px;
                    }

                    .header-actions {
                        display: flex;
                        align-items: center;
                        gap: 9px;
                    }

                    .header-button {
                        color: white;
                        text-decoration: none;
                        padding: 9px 15px;
                        border-radius: 10px;
                        border: 1px solid rgba(255,255,255,.12);
                        background: rgba(255,255,255,.07);
                        font-size: 14px;
                        transition: .25s;
                    }

                    .header-button:hover {
                        color: white;
                        background: rgba(255,255,255,.14);
                        transform: translateY(-2px);
                    }

                    .logout-button {
                        background: rgba(220,53,69,.85);
                        border-color: rgba(220,53,69,.5);
                    }

                    .page-container {
                        max-width: 950px;
                        margin: auto;
                        padding: 42px 20px 55px;
                        position: relative;
                        z-index: 1;
                    }

                    .hero {
                        text-align: center;
                        margin-bottom: 25px;
                    }

                    .hero-label {
                        display: inline-flex;
                        align-items: center;
                        gap: 7px;
                        padding: 7px 13px;
                        border-radius: 999px;
                        background: rgba(59,130,246,.10);
                        color: #2563eb;
                        font-size: 12px;
                        font-weight: 700;
                        margin-bottom: 12px;
                    }

                    .hero h1 {
                        margin: 0;
                        font-size: 34px;
                        font-weight: 800;
                        letter-spacing: -.7px;
                    }

                    .hero p {
                        margin: 8px 0 0;
                        color: #64748b;
                    }

                    .profile-card {
                        background: rgba(255,255,255,.76);
                        backdrop-filter: blur(18px);
                        -webkit-backdrop-filter: blur(18px);
                        border: 1px solid rgba(255,255,255,.88);
                        border-radius: 24px;
                        box-shadow: 0 20px 60px rgba(15,23,42,.10);
                        overflow: hidden;
                    }

                    .profile-header {
                        padding: 35px 25px;
                        text-align: center;
                        color: white;
                        background:
                            linear-gradient(135deg, rgba(37,99,235,.98), rgba(79,70,229,.98));
                        position: relative;
                        overflow: hidden;
                    }

                    .profile-header::after {
                        content: "";
                        position: absolute;
                        width: 180px;
                        height: 180px;
                        border-radius: 50%;
                        background: rgba(255,255,255,.10);
                        right: -60px;
                        top: -80px;
                    }

                    .avatar {
                        width: 82px;
                        height: 82px;
                        margin: 0 auto 15px;
                        border-radius: 24px;
                        background: rgba(255,255,255,.16);
                        border: 1px solid rgba(255,255,255,.25);
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 35px;
                        position: relative;
                        z-index: 1;
                    }

                    .profile-header h2 {
                        margin: 0 0 6px;
                        font-size: 25px;
                        font-weight: 800;
                        position: relative;
                        z-index: 1;
                    }

                    .profile-header p {
                        margin: 0;
                        color: rgba(255,255,255,.78);
                        font-size: 13px;
                        position: relative;
                        z-index: 1;
                    }

                    .details {
                        padding: 28px 30px;
                    }

                    .detail-row {
                        display: flex;
                        align-items: center;
                        gap: 20px;
                        padding: 16px 0;
                        border-bottom: 1px solid #e9eef5;
                    }

                    .detail-row:last-child {
                        border-bottom: none;
                    }

                    .label-wrap {
                        width: 190px;
                        flex-shrink: 0;
                        display: flex;
                        align-items: center;
                        gap: 9px;
                        color: #64748b;
                        font-size: 13px;
                        font-weight: 700;
                    }

                    .label-icon {
                        width: 31px;
                        height: 31px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        border-radius: 9px;
                        background: #eff6ff;
                        color: #2563eb;
                        font-size: 13px;
                    }

                    .value {
                        flex: 1;
                        color: #172033;
                        font-size: 14px;
                        font-weight: 600;
                        word-break: break-word;
                    }

                    .department-badge {
                        display: inline-block;
                        padding: 7px 11px;
                        border-radius: 999px;
                        background: #f3e8ff;
                        color: #7e22ce;
                        font-size: 12px;
                        font-weight: 700;
                    }

                    .salary-value {
                        color: #047857;
                        font-size: 17px;
                        font-weight: 800;
                    }

                    .employee-id-badge {
                        display: inline-flex;
                        align-items: center;
                        gap: 6px;
                        padding: 6px 10px;
                        border-radius: 8px;
                        background: #eff6ff;
                        color: #2563eb;
                        font-size: 12px;
                        font-weight: 800;
                    }

                    .buttons {
                        padding: 0 30px 30px;
                        display: grid;
                        grid-template-columns: repeat(4, 1fr);
                        gap: 10px;
                    }

                    .action-button {
                        min-height: 44px;
                        padding: 10px 12px;
                        border-radius: 11px;
                        text-decoration: none;
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        gap: 6px;
                        border: none;
                        cursor: pointer;
                        font-size: 12px;
                        font-weight: 700;
                        transition: .25s;
                    }

                    .action-button:hover {
                        transform: translateY(-2px);
                    }

                    .edit-button {
                        background: #ecfdf5;
                        color: #047857;
                    }

                    .edit-button:hover {
                        color: #047857;
                        box-shadow: 0 8px 20px rgba(16,185,129,.15);
                    }

                    .delete-button {
                        background: #fef2f2;
                        color: #dc2626;
                    }

                    .delete-button:hover {
                        color: #dc2626;
                        box-shadow: 0 8px 20px rgba(220,38,38,.12);
                    }

                    .print-button {
                        background: #eff6ff;
                        color: #2563eb;
                    }

                    .print-button:hover {
                        color: #2563eb;
                        box-shadow: 0 8px 20px rgba(37,99,235,.15);
                    }

                    .back-button {
                        background: #f1f5f9;
                        color: #475569;
                    }

                    .back-button:hover {
                        color: #475569;
                        background: #e2e8f0;
                    }

                    .state-card {
                        max-width: 520px;
                        margin: 70px auto;
                        padding: 40px 30px;
                        text-align: center;
                        background: rgba(255,255,255,.80);
                        backdrop-filter: blur(18px);
                        -webkit-backdrop-filter: blur(18px);
                        border: 1px solid rgba(255,255,255,.88);
                        border-radius: 24px;
                        box-shadow: 0 20px 60px rgba(15,23,42,.10);
                    }

                    .state-icon {
                        width: 70px;
                        height: 70px;
                        margin: 0 auto 18px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        border-radius: 20px;
                        background: #fef2f2;
                        color: #dc2626;
                        font-size: 29px;
                    }

                    .state-card h2 {
                        font-size: 25px;
                        font-weight: 800;
                        margin-bottom: 10px;
                    }

                    .state-card p {
                        color: #64748b;
                        font-size: 14px;
                        margin-bottom: 24px;
                    }

                    @media (max-width: 750px) {

                        .main-header {
                            padding: 13px 18px;
                        }

                        .brand-title {
                            font-size: 15px;
                        }

                        .brand-subtitle {
                            display: none;
                        }

                        .header-button.dashboard {
                            display: none;
                        }

                        .header-button {
                            padding: 8px 10px;
                            font-size: 12px;
                        }

                        .page-container {
                            padding: 30px 15px 40px;
                        }

                        .hero h1 {
                            font-size: 28px;
                        }

                        .details {
                            padding: 22px 18px;
                        }

                        .detail-row {
                            display: block;
                        }

                        .label-wrap {
                            width: auto;
                            margin-bottom: 9px;
                        }

                        .buttons {
                            grid-template-columns: 1fr 1fr;
                            padding: 0 18px 22px;
                        }
                    }

                    @media (max-width: 450px) {

                        .profile-header {
                            padding: 30px 18px;
                        }

                        .profile-header h2 {
                            font-size: 22px;
                        }

                        .buttons {
                            grid-template-columns: 1fr;
                        }
                    }

                    @media print {

                        .main-header,
                        .hero,
                        .buttons {
                            display: none !important;
                        }

                        body {
                            background: white;
                        }

                        body::before,
                        body::after {
                            display: none;
                        }

                        .page-container {
                            max-width: 100%;
                            padding: 0;
                        }

                        .profile-card {
                            box-shadow: none;
                            border: 1px solid #ddd;
                        }

                        .profile-header {
                            background: #333 !important;
                            -webkit-print-color-adjust: exact;
                            print-color-adjust: exact;
                        }
                    }
                    """);

            response.getWriter().println(
                    "</style>"
            );

            response.getWriter().println(
                    "</head>"
            );

            response.getWriter().println(
                    "<body>"
            );

            // Header
            response.getWriter().println(
                    "<header class='main-header'>"
            );

            response.getWriter().println(
                    "<div class='header-inner'>"
            );

            response.getWriter().println(
                    "<div class='brand'>"
            );

            response.getWriter().println(
                    "<div class='brand-icon'>" +
                            "<i class='bi bi-people-fill'></i>" +
                            "</div>"
            );

            response.getWriter().println(
                    "<div>"
            );

            response.getWriter().println(
                    "<div class='brand-title'>" +
                            "Employee Management System" +
                            "</div>"
            );

            response.getWriter().println(
                    "<div class='brand-subtitle'>" +
                            "Employee Directory" +
                            "</div>"
            );

            response.getWriter().println(
                    "</div>"
            );

            response.getWriter().println(
                    "</div>"
            );

            response.getWriter().println(
                    "<div class='header-actions'>"
            );

            response.getWriter().println(
                    "<a href='index.jsp' class='header-button dashboard'>" +
                            "<i class='bi bi-grid-1x2-fill'></i> Dashboard" +
                            "</a>"
            );

            response.getWriter().println(
                    "<a href='logout' class='header-button logout-button'>" +
                            "<i class='bi bi-box-arrow-right'></i> Logout" +
                            "</a>"
            );

            response.getWriter().println(
                    "</div>"
            );

            response.getWriter().println(
                    "</div>"
            );

            response.getWriter().println(
                    "</header>"
            );

            // Main
            response.getWriter().println(
                    "<main class='page-container'>"
            );

            response.getWriter().println(
                    "<section class='hero'>"
            );

            response.getWriter().println(
                    "<div class='hero-label'>" +
                            "<i class='bi bi-person-vcard-fill'></i> " +
                            "Employee Profile" +
                            "</div>"
            );

            response.getWriter().println(
                    "<h1>Employee Details</h1>"
            );

            response.getWriter().println(
                    "<p>View complete information for this employee.</p>"
            );

            response.getWriter().println(
                    "</section>"
            );

            response.getWriter().println(
                    "<section class='profile-card'>"
            );

            // Profile Header
            response.getWriter().println(
                    "<div class='profile-header'>"
            );

            response.getWriter().println(
                    "<div class='avatar'>" +
                            "<i class='bi bi-person-fill'></i>" +
                            "</div>"
            );

            response.getWriter().println(
                    "<h2>" +
                            escapeHtml(name) +
                            "</h2>"
            );

            response.getWriter().println(
                    "<p>Employee ID: #" +
                            employeeId +
                            "</p>"
            );

            response.getWriter().println(
                    "</div>"
            );

            // Details
            response.getWriter().println(
                    "<div class='details'>"
            );

            // ID
            response.getWriter().println(
                    "<div class='detail-row'>" +
                            "<div class='label-wrap'>" +
                            "<span class='label-icon'>" +
                            "<i class='bi bi-hash'></i>" +
                            "</span>" +
                            "Employee ID" +
                            "</div>" +
                            "<div class='value'>" +
                            "<span class='employee-id-badge'>" +
                            "#" + employeeId +
                            "</span>" +
                            "</div>" +
                            "</div>"
            );

            // Name
            response.getWriter().println(
                    "<div class='detail-row'>" +
                            "<div class='label-wrap'>" +
                            "<span class='label-icon'>" +
                            "<i class='bi bi-person'></i>" +
                            "</span>" +
                            "Full Name" +
                            "</div>" +
                            "<div class='value'>" +
                            escapeHtml(name) +
                            "</div>" +
                            "</div>"
            );

            // Email
            response.getWriter().println(
                    "<div class='detail-row'>" +
                            "<div class='label-wrap'>" +
                            "<span class='label-icon'>" +
                            "<i class='bi bi-envelope'></i>" +
                            "</span>" +
                            "Email Address" +
                            "</div>" +
                            "<div class='value'>" +
                            escapeHtml(email) +
                            "</div>" +
                            "</div>"
            );

            // Phone
            response.getWriter().println(
                    "<div class='detail-row'>" +
                            "<div class='label-wrap'>" +
                            "<span class='label-icon'>" +
                            "<i class='bi bi-telephone'></i>" +
                            "</span>" +
                            "Phone Number" +
                            "</div>" +
                            "<div class='value'>" +
                            escapeHtml(phone) +
                            "</div>" +
                            "</div>"
            );

            // Department
            response.getWriter().println(
                    "<div class='detail-row'>" +
                            "<div class='label-wrap'>" +
                            "<span class='label-icon'>" +
                            "<i class='bi bi-building'></i>" +
                            "</span>" +
                            "Department" +
                            "</div>" +
                            "<div class='value'>" +
                            "<span class='department-badge'>" +
                            escapeHtml(department) +
                            "</span>" +
                            "</div>" +
                            "</div>"
            );

            // Salary
            response.getWriter().println(
                    "<div class='detail-row'>" +
                            "<div class='label-wrap'>" +
                            "<span class='label-icon'>" +
                            "<i class='bi bi-currency-rupee'></i>" +
                            "</span>" +
                            "Salary" +
                            "</div>" +
                            "<div class='value'>" +
                            "<span class='salary-value'>&#8377;" +
                            String.format("%.2f", salary) +
                            "</span>" +
                            "</div>" +
                            "</div>"
            );

            response.getWriter().println(
                    "</div>"
            );

            // Buttons
            response.getWriter().println(
                    "<div class='buttons'>"
            );

            response.getWriter().println(
                    "<a href='editEmployee?id=" +
                            employeeId +
                            "' class='action-button edit-button'>" +
                            "<i class='bi bi-pencil-fill'></i>" +
                            " Edit" +
                            "</a>"
            );

            response.getWriter().println(
                    "<a href='deleteEmployee?id=" +
                            employeeId +
                            "' " +
                            "class='action-button delete-button' " +
                            "onclick=\"return confirm('Are you sure you want to delete this employee?');\">" +
                            "<i class='bi bi-trash3-fill'></i>" +
                            " Delete" +
                            "</a>"
            );

            response.getWriter().println(
                    "<a href='employeeReport?id=" +
                            employeeId +
                            "' class='action-button print-button'>" +
                            "<i class='bi bi-printer-fill'></i>" +
                            " Print Report" +
                            "</a>"
            );

            response.getWriter().println(
                    "<a href='viewEmployees' class='action-button back-button'>" +
                            "<i class='bi bi-arrow-left'></i>" +
                            " Back" +
                            "</a>"
            );

            response.getWriter().println(
                    "</div>"
            );

            response.getWriter().println(
                    "</section>"
            );

            response.getWriter().println(
                    "</main>"
            );

            response.getWriter().println(
                    "</body>"
            );

            response.getWriter().println(
                    "</html>"
            );

        } catch (NumberFormatException e) {

            showError(
                    response,
                    "Invalid employee ID."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while loading employee details."
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

    private void showNotFound(
            HttpServletResponse response)
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
                "<title>Employee Not Found | Employee Management System</title>"
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

                        "body {" +
                        "margin:0;" +
                        "min-height:100vh;" +
                        "font-family:Arial,sans-serif;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "padding:20px;" +
                        "background:" +
                        "linear-gradient(135deg,#eef5ff,#f8f9ff);" +
                        "color:#172033;" +
                        "}" +

                        ".state-card {" +
                        "width:520px;" +
                        "max-width:100%;" +
                        "padding:40px 30px;" +
                        "text-align:center;" +
                        "background:rgba(255,255,255,.82);" +
                        "border:1px solid rgba(255,255,255,.90);" +
                        "border-radius:24px;" +
                        "box-shadow:0 20px 60px rgba(15,23,42,.10);" +
                        "}" +

                        ".state-icon {" +
                        "width:70px;" +
                        "height:70px;" +
                        "margin:0 auto 18px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "border-radius:20px;" +
                        "background:#fef2f2;" +
                        "color:#dc2626;" +
                        "font-size:29px;" +
                        "}" +

                        ".state-card h2 {" +
                        "font-size:25px;" +
                        "font-weight:800;" +
                        "margin-bottom:10px;" +
                        "}" +

                        ".state-card p {" +
                        "color:#64748b;" +
                        "font-size:14px;" +
                        "margin-bottom:24px;" +
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
                "<div class='state-card'>"
        );

        response.getWriter().println(
                "<div class='state-icon'>" +
                        "<i class='bi bi-person-x-fill'></i>" +
                        "</div>"
        );

        response.getWriter().println(
                "<h2>Employee Not Found</h2>"
        );

        response.getWriter().println(
                "<p>The requested employee record does not exist.</p>"
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
                "<title>Employee Details | Employee Management System</title>"
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

                        "body {" +
                        "margin:0;" +
                        "min-height:100vh;" +
                        "font-family:Arial,sans-serif;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "padding:20px;" +
                        "background:" +
                        "linear-gradient(135deg,#eef5ff,#f8f9ff);" +
                        "}" +

                        ".state-card {" +
                        "width:520px;" +
                        "max-width:100%;" +
                        "padding:40px 30px;" +
                        "text-align:center;" +
                        "background:rgba(255,255,255,.82);" +
                        "border:1px solid rgba(255,255,255,.90);" +
                        "border-radius:24px;" +
                        "box-shadow:0 20px 60px rgba(15,23,42,.10);" +
                        "}" +

                        ".state-icon {" +
                        "width:70px;" +
                        "height:70px;" +
                        "margin:0 auto 18px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "border-radius:20px;" +
                        "background:#fff7ed;" +
                        "color:#ea580c;" +
                        "font-size:29px;" +
                        "}" +

                        ".state-card h2 {" +
                        "font-size:25px;" +
                        "font-weight:800;" +
                        "margin-bottom:10px;" +
                        "}" +

                        ".state-card p {" +
                        "color:#64748b;" +
                        "font-size:14px;" +
                        "line-height:1.6;" +
                        "margin-bottom:24px;" +
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
                "<div class='state-card'>"
        );

        response.getWriter().println(
                "<div class='state-icon'>" +
                        "<i class='bi bi-exclamation-triangle-fill'></i>" +
                        "</div>"
        );

        response.getWriter().println(
                "<h2>Something Went Wrong</h2>"
        );

        response.getWriter().println(
                "<p>" +
                        escapeHtml(message) +
                        "</p>"
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