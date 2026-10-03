<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.employee.DBConnection" %>

<%
if (session.getAttribute("admin") == null) {
    response.sendRedirect("login.jsp");
    return;
}

int totalEmployees = 0;
int totalDepartments = 0;
double averageSalary = 0;
double highestSalary = 0;

String success = request.getParameter("success");

String adminUsername =
        (String) session.getAttribute("adminUsername");

if (adminUsername == null) {
    adminUsername =
            (String) session.getAttribute("admin");
}

Connection con = null;

PreparedStatement ps1 = null;
PreparedStatement ps2 = null;
PreparedStatement ps3 = null;
PreparedStatement ps4 = null;
PreparedStatement ps5 = null;
PreparedStatement ps6 = null;
PreparedStatement ps7 = null;

ResultSet rs1 = null;
ResultSet rs2 = null;
ResultSet rs3 = null;
ResultSet rs4 = null;
ResultSet rs5 = null;
ResultSet rs6 = null;
ResultSet rs7 = null;

try {

    con = DBConnection.getConnection();

    if (con != null) {

        ps1 = con.prepareStatement(
                "SELECT COUNT(*) FROM employees"
        );

        rs1 = ps1.executeQuery();

        if (rs1.next()) {
            totalEmployees = rs1.getInt(1);
        }


        ps2 = con.prepareStatement(
                "SELECT COUNT(*) FROM departments"
        );

        rs2 = ps2.executeQuery();

        if (rs2.next()) {
            totalDepartments = rs2.getInt(1);
        }


        ps3 = con.prepareStatement(
                "SELECT COALESCE(AVG(salary), 0) FROM employees"
        );

        rs3 = ps3.executeQuery();

        if (rs3.next()) {
            averageSalary = rs3.getDouble(1);
        }


        ps4 = con.prepareStatement(
                "SELECT COALESCE(MAX(salary), 0) FROM employees"
        );

        rs4 = ps4.executeQuery();

        if (rs4.next()) {
            highestSalary = rs4.getDouble(1);
        }


        ps5 = con.prepareStatement(
                "SELECT d.name, COUNT(e.id) " +
                "FROM departments d " +
                "LEFT JOIN employees e " +
                "ON LOWER(d.name) = LOWER(e.department) " +
                "GROUP BY d.id, d.name " +
                "ORDER BY COUNT(e.id) DESC, d.name"
        );

        rs5 = ps5.executeQuery();


        ps6 = con.prepareStatement(
                "SELECT " +
                "COALESCE(MIN(salary), 0), " +
                "COALESCE(MAX(salary), 0), " +
                "COALESCE(AVG(salary), 0) " +
                "FROM employees"
        );

        rs6 = ps6.executeQuery();


        ps7 = con.prepareStatement(
                "SELECT id, name, email, department, salary " +
                "FROM employees " +
                "ORDER BY id DESC " +
                "LIMIT 5"
        );

        rs7 = ps7.executeQuery();
    }

} catch (Exception e) {

    e.printStackTrace();

} finally {

    try {
        if (rs1 != null) rs1.close();
    } catch (Exception ignored) {
    }

    try {
        if (rs2 != null) rs2.close();
    } catch (Exception ignored) {
    }

    try {
        if (rs3 != null) rs3.close();
    } catch (Exception ignored) {
    }

    try {
        if (rs4 != null) rs4.close();
    } catch (Exception ignored) {
    }
}
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Employee Management System</title>


    <!-- Bootstrap 5 -->

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">


    <style>

        * {
            box-sizing: border-box;
        }


        html {
            scroll-behavior: smooth;
        }


        body {

            margin: 0;

            font-family:
                    Arial,
                    Helvetica,
                    sans-serif;

            color: #172033;

            min-height: 100vh;

            background:
                    linear-gradient(
                            135deg,
                            #eef2ff 0%,
                            #f8fafc 45%,
                            #ecfeff 100%
                    );

            overflow-x: hidden;
        }


        /* =====================================
           AESTHETIC BACKGROUND
           ===================================== */

        body::before {

            content: "";

            position: fixed;

            width: 430px;
            height: 430px;

            top: -170px;
            left: -130px;

            border-radius: 50%;

            background:
                    rgba(99,102,241,0.28);

            filter:
                    blur(80px);

            pointer-events: none;

            z-index: -2;

            animation:
                    floatOne 12s ease-in-out infinite alternate;
        }


        body::after {

            content: "";

            position: fixed;

            width: 450px;
            height: 450px;

            right: -170px;
            bottom: -190px;

            border-radius: 50%;

            background:
                    rgba(14,165,233,0.25);

            filter:
                    blur(85px);

            pointer-events: none;

            z-index: -2;

            animation:
                    floatTwo 14s ease-in-out infinite alternate;
        }


        @keyframes floatOne {

            from {
                transform:
                        translate(0,0)
                        scale(1);
            }

            to {
                transform:
                        translate(65px,50px)
                        scale(1.10);
            }
        }


        @keyframes floatTwo {

            from {
                transform:
                        translate(0,0)
                        scale(1);
            }

            to {
                transform:
                        translate(-55px,-45px)
                        scale(1.08);
            }
        }


        /* =====================================
           HEADER
           ===================================== */

        .main-header {

            position: sticky;

            top: 0;

            z-index: 1000;

            background:
                    rgba(15,23,42,0.88);

            backdrop-filter:
                    blur(18px);

            -webkit-backdrop-filter:
                    blur(18px);

            border-bottom:
                    1px solid
                    rgba(255,255,255,0.10);

            box-shadow:
                    0 10px 35px
                    rgba(15,23,42,0.15);
        }


        .brand-icon {

            width: 48px;
            height: 48px;

            border-radius: 14px;

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 23px;

            background:
                    linear-gradient(
                            135deg,
                            #6366f1,
                            #2563eb
                    );

            box-shadow:
                    0 8px 25px
                    rgba(37,99,235,0.35);
        }


        .brand-title {

            color: white;

            font-size: 20px;

            font-weight: 700;

            margin: 0;
        }


        .brand-subtitle {

            color: #cbd5e1;

            font-size: 12px;

            margin: 4px 0 0;
        }


        .profile-btn {

            color: white;

            border:
                    1px solid
                    rgba(255,255,255,0.15);

            background:
                    rgba(255,255,255,0.08);

            border-radius: 10px;

            padding:
                    9px 15px;

            text-decoration: none;

            font-size: 13px;

            font-weight: 600;

            transition: .25s;
        }


        .profile-btn:hover {

            color: white;

            background:
                    rgba(255,255,255,0.17);

            transform:
                    translateY(-2px);
        }


        .logout-btn {

            color: white;

            background:
                    linear-gradient(
                            135deg,
                            #ef4444,
                            #dc2626
                    );

            border-radius: 10px;

            padding:
                    9px 16px;

            text-decoration: none;

            font-size: 13px;

            font-weight: 600;

            transition: .25s;
        }


        .logout-btn:hover {

            color: white;

            transform:
                    translateY(-2px);

            box-shadow:
                    0 8px 20px
                    rgba(239,68,68,.25);
        }


        /* =====================================
           MAIN
           ===================================== */

        .dashboard-container {

            max-width: 1280px;

            margin: auto;

            padding:
                    35px 20px 50px;
        }


        /* =====================================
           WELCOME
           ===================================== */

        .welcome-card {

            position: relative;

            overflow: hidden;

            border-radius: 22px;

            padding: 30px;

            color: white;

            background:
                    linear-gradient(
                            135deg,
                            #1e3a8a,
                            #4f46e5,
                            #6366f1
                    );

            box-shadow:
                    0 18px 45px
                    rgba(37,99,235,.20);

            margin-bottom: 30px;
        }


        .welcome-card::before {

            content: "";

            position: absolute;

            width: 240px;
            height: 240px;

            right: -80px;
            top: -120px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.10);
        }


        .welcome-card::after {

            content: "";

            position: absolute;

            width: 170px;
            height: 170px;

            left: 48%;
            bottom: -120px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.07);
        }


        .welcome-card h2 {

            position: relative;

            z-index: 2;

            margin: 0 0 7px;

            font-size: 30px;

            font-weight: 700;
        }


        .welcome-card p {

            position: relative;

            z-index: 2;

            margin: 0;

            color: #dbeafe;

            font-size: 14px;
        }


        .active-badge {

            position: relative;

            z-index: 2;

            display: inline-block;

            padding:
                    9px 14px;

            border-radius: 25px;

            background:
                    rgba(255,255,255,.13);

            border:
                    1px solid
                    rgba(255,255,255,.18);

            font-size: 12px;

            font-weight: 700;
        }


        /* =====================================
           SUCCESS
           ===================================== */

        .success-message {

            background:
                    rgba(236,253,245,.92);

            color:
                    #047857;

            border:
                    1px solid #a7f3d0;

            border-radius:
                    12px;

            padding:
                    14px 17px;

            margin-bottom:
                    25px;

            font-weight:
                    600;

            font-size:
                    14px;
        }


        /* =====================================
           SECTION TITLE
           ===================================== */

        .section-title {

            font-size: 19px;

            font-weight: 700;

            color: #0f172a;

            margin-bottom: 15px;
        }


        /* =====================================
           QUICK ACTIONS
           ===================================== */

        .action-card {

            min-height: 82px;

            border-radius: 16px;

            color: white;

            text-decoration: none;

            padding: 15px;

            display: flex;

            align-items: center;

            gap: 12px;

            position: relative;

            overflow: hidden;

            transition: .28s;

            box-shadow:
                    0 10px 25px
                    rgba(15,23,42,.10);
        }


        .action-card::after {

            content: "";

            position: absolute;

            width: 90px;
            height: 90px;

            right: -30px;
            bottom: -40px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.09);
        }


        .action-card:hover {

            color: white;

            transform:
                    translateY(-5px);

            box-shadow:
                    0 17px 32px
                    rgba(15,23,42,.16);
        }


        .action-icon {

            width: 42px;
            height: 42px;

            border-radius: 12px;

            background:
                    rgba(255,255,255,.14);

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 18px;

            flex-shrink: 0;
        }


        .action-text {

            display: flex;

            flex-direction: column;

            gap: 3px;

            position: relative;

            z-index: 2;
        }


        .action-text strong {

            font-size: 13px;
        }


        .action-text small {

            font-size: 10px;

            opacity: .78;
        }


        .action-blue {

            background:
                    linear-gradient(
                            135deg,
                            #2563eb,
                            #1d4ed8
                    );
        }


        .action-green {

            background:
                    linear-gradient(
                            135deg,
                            #059669,
                            #047857
                    );
        }


        .action-purple {

            background:
                    linear-gradient(
                            135deg,
                            #7c3aed,
                            #6d28d9
                    );
        }


        .action-orange {

            background:
                    linear-gradient(
                            135deg,
                            #f97316,
                            #ea580c
                    );
        }


        .action-dark {

            background:
                    linear-gradient(
                            135deg,
                            #334155,
                            #1e293b
                    );
        }


        /* =====================================
           GLASS CARDS
           ===================================== */

        .glass-card {

            background:
                    rgba(255,255,255,.72);

            backdrop-filter:
                    blur(16px);

            -webkit-backdrop-filter:
                    blur(16px);

            border:
                    1px solid
                    rgba(255,255,255,.85);

            border-radius:
                    18px;

            box-shadow:
                    0 12px 35px
                    rgba(15,23,42,.07);

            transition:
                    .25s;
        }


        .glass-card:hover {

            transform:
                    translateY(-3px);

            box-shadow:
                    0 17px 40px
                    rgba(15,23,42,.11);
        }


        /* =====================================
           KPI
           ===================================== */

        .kpi-card {

            position: relative;

            overflow: hidden;

            padding: 23px;

            min-height: 145px;
        }


        .kpi-card::after {

            content: "";

            position: absolute;

            width: 100px;
            height: 100px;

            right: -30px;
            bottom: -40px;

            border-radius: 50%;

            background:
                    rgba(37,99,235,.07);
        }


        .kpi-blue {
            border-top: 4px solid #2563eb;
        }


        .kpi-green {
            border-top: 4px solid #059669;
        }


        .kpi-purple {
            border-top: 4px solid #7c3aed;
        }


        .kpi-orange {
            border-top: 4px solid #f97316;
        }


        .kpi-icon {

            position: absolute;

            top: 18px;
            right: 20px;

            font-size: 22px;
        }


        .kpi-label {

            color: #64748b;

            font-size: 11px;

            font-weight: 700;

            text-transform: uppercase;

            letter-spacing: .6px;
        }


        .kpi-value {

            color: #0f172a;

            font-size: 28px;

            font-weight: 700;

            margin-top: 9px;
        }


        /* =====================================
           ANALYTICS
           ===================================== */

        .analytics-card {

            padding: 26px;
        }


        .analytics-card h3 {

            color: #0f172a;

            font-size: 19px;

            font-weight: 700;

            margin: 0 0 6px;
        }


        .analytics-subtitle {

            color: #64748b;

            font-size: 13px;

            margin-bottom: 23px;
        }


        .department-row {

            margin-bottom: 20px;

            animation:
                    slideUp .6s ease both;
        }


        .department-info {

            display: flex;

            justify-content: space-between;

            margin-bottom: 7px;

            font-size: 13px;
        }


        .department-name {

            color: #334155;

            font-weight: 700;
        }


        .department-count {

            color: #64748b;
        }


        .progress {

            height: 10px;

            background: #e2e8f0;

            border-radius: 20px;

            overflow: hidden;
        }


        .department-progress {

            background:
                    linear-gradient(
                            90deg,
                            #2563eb,
                            #60a5fa
                    );

            border-radius: 20px;

            animation:
                    growBar 1s ease-out;
        }


        @keyframes growBar {

            from {
                width: 0 !important;
            }
        }


        @keyframes slideUp {

            from {
                opacity: 0;
                transform: translateY(10px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }


        /* =====================================
           SALARY
           ===================================== */

        .salary-box {

            background:
                    rgba(248,250,252,.82);

            border:
                    1px solid #e2e8f0;

            border-radius: 12px;

            padding: 16px;

            transition: .22s;
        }


        .salary-box:hover {

            background: white;

            transform:
                    translateY(-3px);

            box-shadow:
                    0 8px 20px
                    rgba(15,23,42,.07);
        }


        .salary-box h6 {

            color: #64748b;

            font-size: 10px;

            font-weight: 700;

            text-transform: uppercase;

            margin-bottom: 8px;
        }


        .salary-value {

            color: #0f172a;

            font-size: 18px;

            font-weight: 700;
        }


        .salary-progress {

            background:
                    linear-gradient(
                            90deg,
                            #7c3aed,
                            #a78bfa
                    );

            border-radius: 20px;

            animation:
                    growBar 1s ease-out;
        }


        /* =====================================
           RECENT EMPLOYEES
           ===================================== */

        .recent-card {

            padding: 26px;

            margin-bottom: 30px;
        }


        .table-wrapper {

            overflow-x: auto;
        }


        .recent-table {

            min-width: 720px;

            margin-bottom: 0;
        }


        .recent-table thead th {

            background: #f8fafc;

            color: #475569;

            font-size: 10px;

            text-transform: uppercase;

            letter-spacing: .5px;

            padding: 13px;

            border-bottom:
                    1px solid #e2e8f0;
        }


        .recent-table tbody td {

            padding: 14px 13px;

            font-size: 13px;

            color: #475569;

            border-bottom:
                    1px solid #f1f5f9;
        }


        .recent-table tbody tr {

            transition: .2s;
        }


        .recent-table tbody tr:hover {

            background:
                    rgba(239,246,255,.65);
        }


        .recent-name {

            color: #0f172a !important;

            font-weight: 700;
        }


        .recent-salary {

            color: #2563eb !important;

            font-weight: 700;
        }


        .id-badge {

            display: inline-block;

            padding: 5px 9px;

            border-radius: 7px;

            background: #eff6ff;

            color: #1d4ed8;

            font-size: 10px;

            font-weight: 700;
        }


        .no-data {

            text-align: center;

            color: #94a3b8;

            padding: 20px !important;

            font-size: 13px;
        }


        /* =====================================
           FORM
           ===================================== */

        .form-card {

            padding: 28px;

            max-width: 800px;

            margin-bottom: 35px;
        }


        .form-label {

            color: #475569;

            font-size: 12px;

            font-weight: 700;

            margin-bottom: 7px;
        }


        .form-control,
        .form-select {

            border:
                    1px solid #cbd5e1;

            border-radius: 10px;

            padding:
                    12px 13px;

            font-size: 13px;

            background:
                    rgba(255,255,255,.88);

            transition: .22s;
        }


        .form-control:focus,
        .form-select:focus {

            border-color:
                    #6366f1;

            box-shadow:
                    0 0 0 4px
                    rgba(99,102,241,.10);
        }


        .submit-btn {

            border: none;

            border-radius: 10px;

            padding:
                    13px 23px;

            background:
                    linear-gradient(
                            135deg,
                            #2563eb,
                            #4f46e5
                    );

            color: white;

            font-size: 13px;

            font-weight: 700;

            box-shadow:
                    0 8px 20px
                    rgba(37,99,235,.22);

            transition: .25s;
        }


        .submit-btn:hover {

            transform:
                    translateY(-3px);

            box-shadow:
                    0 12px 25px
                    rgba(37,99,235,.30);
        }


        /* =====================================
           FOOTER
           ===================================== */

        .footer {

            text-align: center;

            color: #64748b;

            font-size: 12px;

            padding:
                    18px 0 5px;

            border-top:
                    1px solid
                    rgba(148,163,184,.30);
        }


        /* =====================================
           MOBILE
           ===================================== */

        @media (max-width: 768px) {

            .dashboard-container {

                padding:
                        25px 14px 40px;
            }


            .welcome-card {

                padding: 24px;
            }


            .welcome-card h2 {

                font-size: 24px;
            }


            .brand-title {

                font-size: 17px;
            }


            .brand-subtitle {

                font-size: 11px;
            }


            .header-actions {

                width: 100%;
            }


            .header-actions a {

                flex: 1;

                text-align: center;
            }


            .analytics-card,
            .recent-card,
            .form-card {

                padding: 20px;
            }

        }


        @media (prefers-reduced-motion: reduce) {

            *,
            *::before,
            *::after {

                animation-duration:
                        .01ms !important;

                animation-iteration-count:
                        1 !important;

                transition-duration:
                        .01ms !important;
            }
        }

    </style>

</head>


<body>


<!-- =====================================
     HEADER
     ===================================== -->

<header class="main-header">

    <div class="container-fluid px-4 py-3">

        <div class="d-flex
                    flex-column
                    flex-md-row
                    justify-content-between
                    align-items-center
                    gap-3">

            <div class="d-flex align-items-center gap-3">

                <div class="brand-icon">
                    👥
                </div>

                <div>

                    <h1 class="brand-title">
                        Employee Management System
                    </h1>

                    <p class="brand-subtitle">
                        Logged in as: <%= adminUsername %>
                    </p>

                </div>

            </div>


            <div class="d-flex
                        header-actions
                        gap-2">

                <a href="adminProfile"
                   class="profile-btn">

                    👤 Admin Profile

                </a>


                <a href="logout"
                   class="logout-btn">

                    Logout

                </a>

            </div>

        </div>

    </div>

</header>


<!-- =====================================
     MAIN
     ===================================== -->

<main class="dashboard-container">


    <!-- WELCOME -->

    <section class="welcome-card">

        <div class="d-flex
                    flex-column
                    flex-md-row
                    justify-content-between
                    align-items-md-center
                    gap-3">

            <div>

                <h2>
                    Admin Dashboard
                </h2>

                <p>
                    Manage your workforce, departments and HR operations from one place.
                </p>

            </div>


            <div class="active-badge">

                ● System Active

            </div>

        </div>

    </section>


    <!-- SUCCESS -->

    <%
    if ("added".equals(success)) {
    %>

        <div class="success-message">

            ✓ Employee added successfully.

        </div>

    <%
    }
    %>


    <!-- =====================================
         QUICK ACTIONS
         ===================================== -->

    <h2 class="section-title">
        Quick Actions
    </h2>


    <div class="row g-3 mb-4">


        <div class="col-12 col-sm-6 col-lg-3">

            <a href="#addEmployee"
               class="action-card action-blue">

                <span class="action-icon">
                    +
                </span>

                <span class="action-text">

                    <strong>
                        Add Employee
                    </strong>

                    <small>
                        Create new employee record
                    </small>

                </span>

            </a>

        </div>


        <div class="col-12 col-sm-6 col-lg-3">

            <a href="viewEmployees"
               class="action-card action-dark">

                <span class="action-icon">
                    👥
                </span>

                <span class="action-text">

                    <strong>
                        View Employees
                    </strong>

                    <small>
                        Search and manage employees
                    </small>

                </span>

            </a>

        </div>


        <div class="col-12 col-sm-6 col-lg-3">

            <a href="addDepartment.jsp"
               class="action-card action-green">

                <span class="action-icon">
                    🏢
                </span>

                <span class="action-text">

                    <strong>
                        Add Department
                    </strong>

                    <small>
                        Create a department
                    </small>

                </span>

            </a>

        </div>


        <div class="col-12 col-sm-6 col-lg-3">

            <a href="viewDepartments"
               class="action-card action-green">

                <span class="action-icon">
                    📂
                </span>

                <span class="action-text">

                    <strong>
                        Departments
                    </strong>

                    <small>
                        Manage departments
                    </small>

                </span>

            </a>

        </div>


        <div class="col-12 col-sm-6 col-lg-3">

            <a href="hr-assistant.jsp"
               class="action-card action-purple">

                <span class="action-icon">
                    🤖
                </span>

                <span class="action-text">

                    <strong>
                        AI HR Assistant
                    </strong>

                    <small>
                        Ask HR-related questions
                    </small>

                </span>

            </a>

        </div>


        <div class="col-12 col-sm-6 col-lg-3">

            <a href="auditLogs"
               class="action-card action-purple">

                <span class="action-icon">
                    📋
                </span>

                <span class="action-text">

                    <strong>
                        Activity Log
                    </strong>

                    <small>
                        Track system activity
                    </small>

                </span>

            </a>

        </div>


        <div class="col-12 col-sm-6 col-lg-3">

            <a href="exportEmployees"
               class="action-card action-orange">

                <span class="action-icon">
                    📥
                </span>

                <span class="action-text">

                    <strong>
                        Export Employees
                    </strong>

                    <small>
                        Download employee data
                    </small>

                </span>

            </a>

        </div>


    </div>


    <!-- =====================================
         OVERVIEW
         ===================================== -->

    <h2 class="section-title">
        Dashboard Overview
    </h2>


    <div class="row g-3 mb-4">


        <!-- EMPLOYEES -->

        <div class="col-12 col-md-6 col-xl-3">

            <div class="glass-card
                        kpi-card
                        kpi-blue">

                <div class="kpi-icon">
                    👥
                </div>

                <div class="kpi-label">
                    Total Employees
                </div>

                <div class="kpi-value">
                    <%= totalEmployees %>
                </div>

            </div>

        </div>


        <!-- DEPARTMENTS -->

        <div class="col-12 col-md-6 col-xl-3">

            <div class="glass-card
                        kpi-card
                        kpi-green">

                <div class="kpi-icon">
                    🏢
                </div>

                <div class="kpi-label">
                    Total Departments
                </div>

                <div class="kpi-value">
                    <%= totalDepartments %>
                </div>

            </div>

        </div>


        <!-- AVERAGE SALARY -->

        <div class="col-12 col-md-6 col-xl-3">

            <div class="glass-card
                        kpi-card
                        kpi-purple">

                <div class="kpi-icon">
                    💰
                </div>

                <div class="kpi-label">
                    Average Salary
                </div>

                <div class="kpi-value">

                    &#8377;<%= String.format(
                            "%.2f",
                            averageSalary
                    ) %>

                </div>

            </div>

        </div>


        <!-- HIGHEST SALARY -->

        <div class="col-12 col-md-6 col-xl-3">

            <div class="glass-card
                        kpi-card
                        kpi-orange">

                <div class="kpi-icon">
                    📈
                </div>

                <div class="kpi-label">
                    Highest Salary
                </div>

                <div class="kpi-value">

                    &#8377;<%= String.format(
                            "%.2f",
                            highestSalary
                    ) %>

                </div>

            </div>

        </div>


    </div>


    <!-- =====================================
         ANALYTICS
         ===================================== -->

    <div class="row g-4 mb-4">


        <!-- DEPARTMENT ANALYTICS -->

        <div class="col-12 col-lg-7">

            <div class="glass-card analytics-card h-100">

                <h3>
                    Department Analytics
                </h3>

                <div class="analytics-subtitle">
                    Employee distribution across departments
                </div>


                <%
                boolean hasDepartmentData = false;

                if (rs5 != null) {

                    while (rs5.next()) {

                        hasDepartmentData = true;

                        String departmentName =
                                rs5.getString(1);

                        int employeeCount =
                                rs5.getInt(2);

                        int barWidth = 0;

                        if (totalEmployees > 0) {

                            barWidth =
                                    (int) Math.round(
                                            (employeeCount * 100.0)
                                            / totalEmployees
                                    );
                        }

                        if (employeeCount > 0
                                && barWidth < 3) {

                            barWidth = 3;
                        }
                %>


                <div class="department-row">

                    <div class="department-info">

                        <span class="department-name">
                            <%= departmentName %>
                        </span>

                        <span class="department-count">
                            <%= employeeCount %> employee(s)
                        </span>

                    </div>


                    <div class="progress">

                        <div
                                class="progress-bar
                                       department-progress"
                                style="width:<%= barWidth %>%;">
                        </div>

                    </div>

                </div>


                <%
                    }
                }


                if (!hasDepartmentData) {
                %>

                    <div class="no-data">
                        No department data available.
                    </div>

                <%
                }
                %>

            </div>

        </div>


        <!-- SALARY ANALYTICS -->

        <div class="col-12 col-lg-5">

            <div class="glass-card analytics-card h-100">

                <h3>
                    Salary Analytics
                </h3>

                <div class="analytics-subtitle">
                    Overview of employee salary distribution
                </div>


                <%
                double minimumSalary = 0;
                double maximumSalary = 0;
                double salaryAverage = 0;

                if (rs6 != null && rs6.next()) {

                    minimumSalary =
                            rs6.getDouble(1);

                    maximumSalary =
                            rs6.getDouble(2);

                    salaryAverage =
                            rs6.getDouble(3);
                }


                double averagePercentage = 0;

                if (maximumSalary > 0) {

                    averagePercentage =
                            (salaryAverage
                                    / maximumSalary)
                                    * 100;

                    if (averagePercentage > 100) {
                        averagePercentage = 100;
                    }
                }
                %>


                <div class="row g-2">


                    <div class="col-12 col-sm-4">

                        <div class="salary-box">

                            <h6>
                                Minimum
                            </h6>

                            <div class="salary-value">

                                &#8377;<%= String.format(
                                        "%.2f",
                                        minimumSalary
                                ) %>

                            </div>

                        </div>

                    </div>


                    <div class="col-12 col-sm-4">

                        <div class="salary-box">

                            <h6>
                                Average
                            </h6>

                            <div class="salary-value">

                                &#8377;<%= String.format(
                                        "%.2f",
                                        salaryAverage
                                ) %>

                            </div>

                        </div>

                    </div>


                    <div class="col-12 col-sm-4">

                        <div class="salary-box">

                            <h6>
                                Maximum
                            </h6>

                            <div class="salary-value">

                                &#8377;<%= String.format(
                                        "%.2f",
                                        maximumSalary
                                ) %>

                            </div>

                        </div>

                    </div>


                </div>


                <div class="mt-4">

                    <div class="d-flex
                                justify-content-between
                                mb-2">

                        <span class="small text-secondary">
                            Average compared with maximum
                        </span>

                        <span class="small fw-bold text-primary">

                            <%= String.format(
                                    "%.1f",
                                    averagePercentage
                            ) %>%

                        </span>

                    </div>


                    <div class="progress">

                        <div
                                class="progress-bar
                                       salary-progress"
                                style="width:<%= averagePercentage %>%;">
                        </div>

                    </div>

                </div>

            </div>

        </div>


    </div>


    <!-- =====================================
         RECENT EMPLOYEES
         ===================================== -->

    <div class="glass-card recent-card">

        <h3 class="mb-1">
            Recent Employees
        </h3>

        <div class="analytics-subtitle">
            Latest 5 employees added to the system
        </div>


        <div class="table-wrapper">

            <table class="table recent-table align-middle">

                <thead>

                <tr>

                    <th>
                        ID
                    </th>

                    <th>
                        Name
                    </th>

                    <th>
                        Email
                    </th>

                    <th>
                        Department
                    </th>

                    <th>
                        Salary
                    </th>

                </tr>

                </thead>


                <tbody>


                <%
                boolean hasRecentEmployees = false;

                if (rs7 != null) {

                    while (rs7.next()) {

                        hasRecentEmployees = true;

                        int employeeId =
                                rs7.getInt("id");

                        String employeeName =
                                rs7.getString("name");

                        String employeeEmail =
                                rs7.getString("email");

                        String employeeDepartment =
                                rs7.getString("department");

                        double employeeSalary =
                                rs7.getDouble("salary");
                %>


                <tr>

                    <td>

                        <span class="id-badge">
                            #<%= employeeId %>
                        </span>

                    </td>


                    <td class="recent-name">
                        <%= employeeName %>
                    </td>


                    <td>
                        <%= employeeEmail %>
                    </td>


                    <td>
                        <%= employeeDepartment != null
                                ? employeeDepartment
                                : "-" %>
                    </td>


                    <td class="recent-salary">

                        &#8377;<%= String.format(
                                "%.2f",
                                employeeSalary
                        ) %>

                    </td>

                </tr>


                <%
                    }
                }


                if (!hasRecentEmployees) {
                %>


                <tr>

                    <td colspan="5"
                        class="no-data">

                        No employees available yet.

                    </td>

                </tr>


                <%
                }
                %>


                </tbody>

            </table>

        </div>

    </div>


    <!-- =====================================
         ADD EMPLOYEE
         ===================================== -->

    <h2 id="addEmployee"
        class="section-title">

        Add New Employee

    </h2>


    <div class="glass-card form-card">

        <form action="addEmployee"
              method="post">


            <div class="row g-3">


                <!-- NAME -->

                <div class="col-12 col-md-6">

                    <label class="form-label">
                        Employee Name
                    </label>

                    <input
                            type="text"
                            name="name"
                            class="form-control"
                            placeholder="Enter employee name"
                            required>

                </div>


                <!-- EMAIL -->

                <div class="col-12 col-md-6">

                    <label class="form-label">
                        Email Address
                    </label>

                    <input
                            type="email"
                            name="email"
                            class="form-control"
                            placeholder="Enter employee email"
                            required>

                </div>


                <!-- PHONE -->

                <div class="col-12 col-md-6">

                    <label class="form-label">
                        Phone Number
                    </label>

                    <input
                            type="text"
                            name="phone"
                            class="form-control"
                            placeholder="Enter phone number">

                </div>


                <!-- DEPARTMENT -->

                <div class="col-12 col-md-6">

                    <label class="form-label">
                        Department
                    </label>

                    <select
                            name="department"
                            class="form-select">

                        <option value="">
                            Select Department
                        </option>


                        <%
                        PreparedStatement departmentDropdownPs =
                                null;

                        ResultSet departmentDropdownRs =
                                null;

                        try {

                            if (con != null) {

                                departmentDropdownPs =
                                        con.prepareStatement(
                                                "SELECT name FROM departments " +
                                                "ORDER BY name"
                                        );

                                departmentDropdownRs =
                                        departmentDropdownPs.executeQuery();

                                while (
                                        departmentDropdownRs.next()
                                ) {
                        %>


                                    <option
                                            value="<%= departmentDropdownRs.getString("name") %>">

                                        <%= departmentDropdownRs.getString("name") %>

                                    </option>


                        <%
                                }
                            }

                        } catch (Exception ignored) {

                        } finally {

                            try {

                                if (departmentDropdownRs != null) {
                                    departmentDropdownRs.close();
                                }

                            } catch (Exception ignored) {
                            }


                            try {

                                if (departmentDropdownPs != null) {
                                    departmentDropdownPs.close();
                                }

                            } catch (Exception ignored) {
                            }
                        }
                        %>


                    </select>

                </div>


                <!-- SALARY -->

                <div class="col-12">

                    <label class="form-label">
                        Salary
                    </label>

                    <input
                            type="number"
                            name="salary"
                            step="0.01"
                            class="form-control"
                            placeholder="Enter salary"
                            required>

                </div>


            </div>


            <button
                    type="submit"
                    class="submit-btn mt-4">

                + Add Employee

            </button>


        </form>

    </div>


    <!-- =====================================
         FOOTER
         ===================================== -->

    <div class="footer">

        Employee Management System
        &nbsp;•&nbsp;
        Admin Dashboard

    </div>


</main>


<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


<!-- =====================================
     CLOSE DATABASE RESOURCES
     ===================================== -->

<%

try {
    if (rs5 != null) rs5.close();
} catch (Exception ignored) {
}


try {
    if (rs6 != null) rs6.close();
} catch (Exception ignored) {
}


try {
    if (rs7 != null) rs7.close();
} catch (Exception ignored) {
}


try {
    if (ps1 != null) ps1.close();
} catch (Exception ignored) {
}


try {
    if (ps2 != null) ps2.close();
} catch (Exception ignored) {
}


try {
    if (ps3 != null) ps3.close();
} catch (Exception ignored) {
}


try {
    if (ps4 != null) ps4.close();
} catch (Exception ignored) {
}


try {
    if (ps5 != null) ps5.close();
} catch (Exception ignored) {
}


try {
    if (ps6 != null) ps6.close();
} catch (Exception ignored) {
}


try {
    if (ps7 != null) ps7.close();
} catch (Exception ignored) {
}


try {
    if (con != null) con.close();
} catch (Exception ignored) {
}

%>


</body>

</html>