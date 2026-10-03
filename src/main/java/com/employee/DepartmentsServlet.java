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

@WebServlet("/viewDepartments")
public class DepartmentsServlet extends HttpServlet {

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

        PrintWriter out = response.getWriter();

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

            String success = request.getParameter("success");
            String search = request.getParameter("search");

            String sql;

            if (search == null || search.trim().isEmpty()) {

                sql =
                        "SELECT * FROM departments " +
                                "ORDER BY id";

            } else {

                sql =
                        "SELECT * FROM departments " +
                                "WHERE name LIKE ? " +
                                "OR description LIKE ? " +
                                "ORDER BY id";
            }

            ps = con.prepareStatement(sql);

            if (search != null && !search.trim().isEmpty()) {

                String searchValue =
                        "%" + search.trim() + "%";

                ps.setString(1, searchValue);
                ps.setString(2, searchValue);
            }

            rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");

            out.println(
                    "<html lang='en'>"
            );

            out.println("<head>");

            out.println(
                    "<meta charset='UTF-8'>"
            );

            out.println(
                    "<meta name='viewport' " +
                            "content='width=device-width, initial-scale=1.0'>"
            );

            out.println(
                    "<title>Departments | Employee Management System</title>"
            );

            // Bootstrap
            out.println(
                    "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' " +
                            "rel='stylesheet'>"
            );

            // Bootstrap Icons
            out.println(
                    "<link rel='stylesheet' " +
                            "href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css'>"
            );

            out.println("<style>");

            out.println("""
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
                        max-width: 1250px;
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
                        max-width: 1200px;
                        margin: auto;
                        padding: 42px 25px 55px;
                        position: relative;
                        z-index: 1;
                    }

                    .hero {
                        display: flex;
                        justify-content: space-between;
                        align-items: flex-end;
                        gap: 20px;
                        margin-bottom: 25px;
                    }

                    .hero-label {
                        display: inline-flex;
                        align-items: center;
                        gap: 7px;
                        padding: 7px 12px;
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

                    .department-count {
                        display: inline-flex;
                        align-items: center;
                        gap: 8px;
                        padding: 12px 16px;
                        border-radius: 15px;
                        background: rgba(255,255,255,.72);
                        border: 1px solid rgba(255,255,255,.85);
                        box-shadow: 0 10px 30px rgba(15,23,42,.07);
                        color: #475569;
                        font-size: 13px;
                        font-weight: 700;
                    }

                    .message {
                        padding: 14px 18px;
                        border-radius: 14px;
                        margin-bottom: 20px;
                        font-weight: 600;
                        box-shadow: 0 8px 25px rgba(15,23,42,.05);
                    }

                    .success-message {
                        background: rgba(220,252,231,.92);
                        color: #166534;
                        border: 1px solid #bbf7d0;
                    }

                    .error-message {
                        background: rgba(254,226,226,.92);
                        color: #991b1b;
                        border: 1px solid #fecaca;
                    }

                    .top-section {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        gap: 15px;
                        margin-bottom: 20px;
                    }

                    .section-title {
                        display: flex;
                        align-items: center;
                        gap: 10px;
                    }

                    .section-icon {
                        width: 40px;
                        height: 40px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        border-radius: 11px;
                        background: #eff6ff;
                        color: #2563eb;
                    }

                    .section-title strong {
                        font-size: 17px;
                    }

                    .top-buttons {
                        display: flex;
                        gap: 9px;
                    }

                    .top-button {
                        display: inline-flex;
                        align-items: center;
                        gap: 7px;
                        padding: 10px 15px;
                        border-radius: 11px;
                        text-decoration: none;
                        font-size: 13px;
                        font-weight: 700;
                        transition: .25s;
                    }

                    .top-button:hover {
                        transform: translateY(-2px);
                    }

                    .add-button {
                        background: linear-gradient(135deg, #059669, #10b981);
                        color: white;
                        box-shadow: 0 8px 20px rgba(16,185,129,.18);
                    }

                    .add-button:hover {
                        color: white;
                    }

                    .dashboard-button {
                        background: #f1f5f9;
                        color: #475569;
                    }

                    .dashboard-button:hover {
                        color: #1e293b;
                        background: #e2e8f0;
                    }

                    .search-card {
                        padding: 20px;
                        border-radius: 20px;
                        background: rgba(255,255,255,.72);
                        backdrop-filter: blur(16px);
                        -webkit-backdrop-filter: blur(16px);
                        border: 1px solid rgba(255,255,255,.85);
                        box-shadow: 0 15px 45px rgba(15,23,42,.08);
                        margin-bottom: 25px;
                    }

                    .search-title {
                        font-size: 15px;
                        font-weight: 700;
                        margin-bottom: 12px;
                        color: #334155;
                    }

                    .search-form {
                        display: flex;
                        gap: 10px;
                    }

                    .search-input {
                        flex: 1;
                        border: 1px solid #dbe3ef;
                        border-radius: 12px;
                        padding: 12px 15px;
                        outline: none;
                        background: rgba(255,255,255,.92);
                        font-size: 14px;
                        transition: .2s;
                    }

                    .search-input:focus {
                        border-color: #3b82f6;
                        box-shadow: 0 0 0 4px rgba(59,130,246,.10);
                    }

                    .search-button {
                        border: none;
                        border-radius: 12px;
                        padding: 12px 20px;
                        color: white;
                        background: linear-gradient(135deg, #2563eb, #4f46e5);
                        font-weight: 700;
                        cursor: pointer;
                        transition: .25s;
                    }

                    .search-button:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 8px 20px rgba(37,99,235,.25);
                    }

                    .clear-button {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        padding: 0 18px;
                        border-radius: 12px;
                        background: #f1f5f9;
                        color: #475569;
                        text-decoration: none;
                        font-weight: 600;
                    }

                    .clear-button:hover {
                        background: #e2e8f0;
                        color: #1e293b;
                    }

                    .table-card {
                        background: rgba(255,255,255,.76);
                        backdrop-filter: blur(18px);
                        -webkit-backdrop-filter: blur(18px);
                        border: 1px solid rgba(255,255,255,.85);
                        border-radius: 22px;
                        box-shadow: 0 18px 55px rgba(15,23,42,.09);
                        overflow: hidden;
                    }

                    .table-heading {
                        padding: 20px 22px;
                        border-bottom: 1px solid #e9eef5;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                    }

                    .table-heading strong {
                        font-size: 16px;
                    }

                    .live-badge {
                        display: inline-flex;
                        align-items: center;
                        gap: 6px;
                        padding: 6px 10px;
                        border-radius: 999px;
                        background: #ecfdf5;
                        color: #047857;
                        font-size: 11px;
                        font-weight: 700;
                    }

                    .live-dot {
                        width: 7px;
                        height: 7px;
                        border-radius: 50%;
                        background: #10b981;
                        animation: pulseDot 1.7s infinite;
                    }

                    @keyframes pulseDot {
                        0%, 100% {
                            opacity: 1;
                            transform: scale(1);
                        }

                        50% {
                            opacity: .45;
                            transform: scale(.7);
                        }
                    }

                    .table-wrap {
                        overflow-x: auto;
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                        min-width: 850px;
                    }

                    th {
                        padding: 14px 16px;
                        background: #f8fafc;
                        color: #64748b;
                        font-size: 11px;
                        text-transform: uppercase;
                        letter-spacing: .6px;
                        font-weight: 800;
                        border-bottom: 1px solid #e8edf4;
                        text-align: left;
                    }

                    td {
                        padding: 16px;
                        border-bottom: 1px solid #eef2f7;
                        font-size: 13px;
                        color: #334155;
                        vertical-align: middle;
                    }

                    tbody tr {
                        transition: .2s ease;
                    }

                    tbody tr:hover {
                        background: rgba(239,246,255,.72);
                    }

                    tbody tr:last-child td {
                        border-bottom: none;
                    }

                    .id-badge {
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        min-width: 36px;
                        height: 30px;
                        padding: 0 9px;
                        border-radius: 9px;
                        background: #eff6ff;
                        color: #2563eb;
                        font-weight: 800;
                        font-size: 12px;
                    }

                    .department-name {
                        font-weight: 800;
                        color: #172033;
                    }

                    .description {
                        color: #64748b;
                        line-height: 1.5;
                    }

                    .action-btn {
                        display: inline-flex;
                        align-items: center;
                        gap: 5px;
                        padding: 7px 10px;
                        border-radius: 9px;
                        text-decoration: none;
                        font-size: 11px;
                        font-weight: 700;
                        margin-right: 5px;
                        transition: .2s;
                    }

                    .action-btn:hover {
                        transform: translateY(-2px);
                    }

                    .edit-btn {
                        background: #ecfdf5;
                        color: #047857;
                    }

                    .edit-btn:hover {
                        color: #047857;
                    }

                    .delete-btn {
                        background: #fef2f2;
                        color: #dc2626;
                    }

                    .delete-btn:hover {
                        color: #dc2626;
                    }

                    .empty-state {
                        text-align: center;
                        padding: 55px 20px;
                        color: #64748b;
                    }

                    .empty-icon {
                        width: 65px;
                        height: 65px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        margin: 0 auto 15px;
                        border-radius: 18px;
                        background: #eff6ff;
                        color: #3b82f6;
                        font-size: 28px;
                    }

                    @media (max-width: 700px) {

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

                        .hero {
                            display: block;
                        }

                        .hero h1 {
                            font-size: 28px;
                        }

                        .department-count {
                            margin-top: 18px;
                        }

                        .top-section {
                            display: block;
                        }

                        .top-buttons {
                            margin-top: 15px;
                            width: 100%;
                        }

                        .top-button {
                            flex: 1;
                            justify-content: center;
                        }

                        .search-card {
                            padding: 16px;
                        }

                        .search-form {
                            flex-direction: column;
                        }

                        .search-button,
                        .clear-button {
                            width: 100%;
                            min-height: 44px;
                        }

                        .table-heading {
                            padding: 17px;
                        }
                    }

                    @media (max-width: 450px) {

                        .top-buttons {
                            flex-direction: column;
                        }

                        .top-button {
                            width: 100%;
                        }
                    }
                    """);

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            // Header
            out.println(
                    "<header class='main-header'>"
            );

            out.println(
                    "<div class='header-inner'>"
            );

            out.println(
                    "<div class='brand'>"
            );

            out.println(
                    "<div class='brand-icon'>" +
                            "<i class='bi bi-people-fill'></i>" +
                            "</div>"
            );

            out.println(
                    "<div>"
            );

            out.println(
                    "<div class='brand-title'>" +
                            "Employee Management System" +
                            "</div>"
            );

            out.println(
                    "<div class='brand-subtitle'>" +
                            "Department Directory" +
                            "</div>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "<div class='header-actions'>"
            );

            out.println(
                    "<a href='index.jsp' " +
                            "class='header-button dashboard'>" +
                            "<i class='bi bi-grid-1x2-fill'></i> Dashboard" +
                            "</a>"
            );

            out.println(
                    "<a href='logout' " +
                            "class='header-button logout-button'>" +
                            "<i class='bi bi-box-arrow-right'></i> Logout" +
                            "</a>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "</header>"
            );

            // Main
            out.println(
                    "<main class='page-container'>"
            );

            out.println(
                    "<section class='hero'>"
            );

            out.println(
                    "<div>"
            );

            out.println(
                    "<div class='hero-label'>" +
                            "<i class='bi bi-building-fill'></i> " +
                            "Department Management" +
                            "</div>"
            );

            out.println(
                    "<h1>Departments</h1>"
            );

            out.println(
                    "<p>Manage and organize departments from one place.</p>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "<div class='department-count'>" +
                            "<i class='bi bi-diagram-3-fill'></i>" +
                            " Department Records" +
                            "</div>"
            );

            out.println(
                    "</section>"
            );

            // Messages
            if ("updated".equals(success)) {

                out.println(
                        "<div class='message success-message'>" +
                                "<i class='bi bi-check-circle-fill'></i> " +
                                "Department updated successfully." +
                                "</div>"
                );

            } else if ("deleted".equals(success)) {

                out.println(
                        "<div class='message success-message'>" +
                                "<i class='bi bi-check-circle-fill'></i> " +
                                "Department deleted successfully." +
                                "</div>"
                );

            } else if ("added".equals(success)) {

                out.println(
                        "<div class='message success-message'>" +
                                "<i class='bi bi-check-circle-fill'></i> " +
                                "Department added successfully." +
                                "</div>"
                );

            } else if ("notfound".equals(success)) {

                out.println(
                        "<div class='message error-message'>" +
                                "<i class='bi bi-exclamation-circle-fill'></i> " +
                                "Department was not found." +
                                "</div>"
                );

            } else if ("error".equals(success)) {

                out.println(
                        "<div class='message error-message'>" +
                                "<i class='bi bi-exclamation-triangle-fill'></i> " +
                                "Something went wrong. Please try again." +
                                "</div>"
                );
            }

            // Top section
            out.println(
                    "<div class='top-section'>"
            );

            out.println(
                    "<div class='section-title'>"
            );

            out.println(
                    "<div class='section-icon'>" +
                            "<i class='bi bi-diagram-3-fill'></i>" +
                            "</div>"
            );

            out.println(
                    "<strong>Department List</strong>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "<div class='top-buttons'>"
            );

            // Fixed: open JSP directly
            out.println(
                    "<a href='addDepartment.jsp' " +
                            "class='top-button add-button'>" +
                            "<i class='bi bi-plus-circle-fill'></i>" +
                            " Add Department" +
                            "</a>"
            );

            out.println(
                    "<a href='index.jsp' " +
                            "class='top-button dashboard-button'>" +
                            "<i class='bi bi-arrow-left'></i>" +
                            " Dashboard" +
                            "</a>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "</div>"
            );

            // Search
            out.println(
                    "<section class='search-card'>"
            );

            out.println(
                    "<div class='search-title'>" +
                            "<i class='bi bi-search'></i> " +
                            "Search Departments" +
                            "</div>"
            );

            out.println(
                    "<form method='get' " +
                            "action='viewDepartments' " +
                            "class='search-form'>"
            );

            out.println(
                    "<input type='text' " +
                            "name='search' " +
                            "class='search-input' " +
                            "placeholder='Search by department name or description' " +
                            "value='" +
                            escapeHtml(
                                    search == null ? "" : search
                            ) +
                            "'>"
            );

            out.println(
                    "<button type='submit' " +
                            "class='search-button'>" +
                            "<i class='bi bi-search'></i> Search" +
                            "</button>"
            );

            out.println(
                    "<a href='viewDepartments' " +
                            "class='clear-button'>" +
                            "<i class='bi bi-x-circle'></i>&nbsp; Clear" +
                            "</a>"
            );

            out.println(
                    "</form>"
            );

            out.println(
                    "</section>"
            );

            // Table
            out.println(
                    "<section class='table-card'>"
            );

            out.println(
                    "<div class='table-heading'>"
            );

            out.println(
                    "<strong>" +
                            "<i class='bi bi-table'></i> " +
                            "Department Records" +
                            "</strong>"
            );

            out.println(
                    "<span class='live-badge'>" +
                            "<span class='live-dot'></span> Live Data" +
                            "</span>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "<div class='table-wrap'>"
            );

            out.println(
                    "<table>"
            );

            out.println(
                    "<thead>"
            );

            out.println(
                    "<tr>"
            );

            out.println(
                    "<th>ID</th>"
            );

            out.println(
                    "<th>Department Name</th>"
            );

            out.println(
                    "<th>Description</th>"
            );

            out.println(
                    "<th>Actions</th>"
            );

            out.println(
                    "</tr>"
            );

            out.println(
                    "</thead>"
            );

            out.println(
                    "<tbody>"
            );

            boolean found = false;

            while (rs.next()) {

                found = true;

                int id =
                        rs.getInt("id");

                String name =
                        rs.getString("name");

                String description =
                        rs.getString("description");

                out.println(
                        "<tr>"
                );

                out.println(
                        "<td>" +
                                "<span class='id-badge'>" +
                                id +
                                "</span>" +
                                "</td>"
                );

                out.println(
                        "<td>" +
                                "<span class='department-name'>" +
                                escapeHtml(name) +
                                "</span>" +
                                "</td>"
                );

                out.println(
                        "<td>" +
                                "<span class='description'>" +
                                escapeHtml(description) +
                                "</span>" +
                                "</td>"
                );

                out.println(
                        "<td>"
                );

                out.println(
                        "<a class='action-btn edit-btn' " +
                                "href='editDepartment?id=" +
                                id +
                                "'>" +
                                "<i class='bi bi-pencil-fill'></i> Edit" +
                                "</a>"
                );

                out.println(
                        "<a class='action-btn delete-btn' " +
                                "href='deleteDepartment?id=" +
                                id +
                                "' " +
                                "onclick=\"return confirm(" +
                                "'Are you sure you want to delete this department?'" +
                                ");\">" +
                                "<i class='bi bi-trash3-fill'></i> Delete" +
                                "</a>"
                );

                out.println(
                        "</td>"
                );

                out.println(
                        "</tr>"
                );
            }

            if (!found) {

                out.println(
                        "<tr>"
                );

                out.println(
                        "<td colspan='4' class='empty-state'>"
                );

                out.println(
                        "<div class='empty-icon'>" +
                                "<i class='bi bi-building-x'></i>" +
                                "</div>"
                );

                out.println(
                        "<strong>No departments found</strong>"
                );

                out.println(
                        "<div style='margin-top:6px;'>" +
                                "Try another search or add a new department." +
                                "</div>"
                );

                out.println(
                        "</td>"
                );

                out.println(
                        "</tr>"
                );
            }

            out.println(
                    "</tbody>"
            );

            out.println(
                    "</table>"
            );

            out.println(
                    "</div>"
            );

            out.println(
                    "</section>"
            );

            out.println(
                    "</main>"
            );

            out.println(
                    "</body>"
            );

            out.println(
                    "</html>"
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    response,
                    "Something went wrong while loading departments."
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

    private void showError(
            HttpServletResponse response,
            String message)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out =
                response.getWriter();

        out.println(
                "<!DOCTYPE html>"
        );

        out.println(
                "<html lang='en'>"
        );

        out.println(
                "<head>"
        );

        out.println(
                "<meta charset='UTF-8'>"
        );

        out.println(
                "<meta name='viewport' " +
                        "content='width=device-width, initial-scale=1.0'>"
        );

        out.println(
                "<title>Departments | Employee Management System</title>"
        );

        out.println(
                "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' " +
                        "rel='stylesheet'>"
        );

        out.println(
                "<link rel='stylesheet' " +
                        "href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css'>"
        );

        out.println(
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

                        ".error-card {" +
                        "width:520px;" +
                        "max-width:100%;" +
                        "padding:40px 30px;" +
                        "text-align:center;" +
                        "background:rgba(255,255,255,.82);" +
                        "border:1px solid rgba(255,255,255,.90);" +
                        "border-radius:24px;" +
                        "box-shadow:0 20px 60px rgba(15,23,42,.10);" +
                        "}" +

                        ".error-icon {" +
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

                        ".error-card h2 {" +
                        "font-size:25px;" +
                        "font-weight:800;" +
                        "margin-bottom:10px;" +
                        "}" +

                        ".error-message {" +
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

        out.println(
                "</head>"
        );

        out.println(
                "<body>"
        );

        out.println(
                "<div class='error-card'>"
        );

        out.println(
                "<div class='error-icon'>" +
                        "<i class='bi bi-exclamation-triangle-fill'></i>" +
                        "</div>"
        );

        out.println(
                "<h2>Something Went Wrong</h2>"
        );

        out.println(
                "<div class='error-message'>" +
                        escapeHtml(message) +
                        "</div>"
        );

        out.println(
                "<a href='index.jsp' class='back-button'>" +
                        "<i class='bi bi-arrow-left'></i>" +
                        " Back to Dashboard" +
                        "</a>"
        );

        out.println(
                "</div>"
        );

        out.println(
                "</body>"
        );

        out.println(
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