<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
/* 🔐 Admin Login Protection */

if (session.getAttribute("admin") == null) {
    response.sendRedirect("login.jsp");
    return;
}

String adminUsername =
        (String) session.getAttribute("adminUsername");

if (adminUsername == null) {
    adminUsername =
            (String) session.getAttribute("admin");
}

Object employeeId =
        request.getAttribute("employeeId");

String employeeName =
        (String) request.getAttribute("employeeName");

String employeeEmail =
        (String) request.getAttribute("employeeEmail");

String employeePhone =
        (String) request.getAttribute("employeePhone");

String employeeDepartment =
        (String) request.getAttribute("employeeDepartment");

Double employeeSalary =
        (Double) request.getAttribute("employeeSalary");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Employee Report | Employee Management System</title>

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
                            #f8fafc 48%,
                            #ecfeff 100%
                    );

            overflow-x: hidden;
        }


        /* ==============================
           AESTHETIC BACKGROUND
           ============================== */

        body::before {

            content: "";

            position: fixed;

            width: 430px;
            height: 430px;

            top: -170px;
            left: -130px;

            border-radius: 50%;

            background:
                    rgba(99,102,241,.22);

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
                    rgba(14,165,233,.20);

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
                        translate(60px,45px)
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


        /* ==============================
           HEADER
           ============================== */

        .main-header {

            position: sticky;

            top: 0;

            z-index: 1000;

            background:
                    rgba(15,23,42,.90);

            backdrop-filter:
                    blur(18px);

            -webkit-backdrop-filter:
                    blur(18px);

            border-bottom:
                    1px solid
                    rgba(255,255,255,.10);

            box-shadow:
                    0 10px 35px
                    rgba(15,23,42,.15);
        }


        .brand-icon {

            width: 46px;
            height: 46px;

            border-radius: 13px;

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 21px;

            background:
                    linear-gradient(
                            135deg,
                            #6366f1,
                            #2563eb
                    );

            box-shadow:
                    0 8px 25px
                    rgba(37,99,235,.35);

            flex-shrink: 0;
        }


        .brand-title {

            color: white;

            font-size: 19px;

            font-weight: 700;

            margin: 0;
        }


        .brand-subtitle {

            color: #cbd5e1;

            font-size: 11px;

            margin: 4px 0 0;
        }


        .back-btn {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            color: white;

            background:
                    rgba(255,255,255,.08);

            border:
                    1px solid
                    rgba(255,255,255,.15);

            border-radius: 10px;

            padding:
                    9px 15px;

            text-decoration: none;

            font-size: 12px;

            font-weight: 600;

            transition: .25s;
        }


        .back-btn:hover {

            color: white;

            background:
                    rgba(255,255,255,.17);

            transform:
                    translateY(-2px);
        }


        /* ==============================
           MAIN
           ============================== */

        .page-container {

            max-width: 900px;

            margin: auto;

            padding:
                    40px 20px 50px;
        }


        /* ==============================
           HERO
           ============================== */

        .report-hero {

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

            margin-bottom: 24px;
        }


        .report-hero::before {

            content: "";

            position: absolute;

            width: 230px;
            height: 230px;

            right: -80px;
            top: -115px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.10);
        }


        .report-hero::after {

            content: "";

            position: absolute;

            width: 160px;
            height: 160px;

            left: 48%;
            bottom: -115px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.07);
        }


        .hero-content {

            position: relative;

            z-index: 2;
        }


        .report-icon {

            width: 54px;
            height: 54px;

            display: flex;

            align-items: center;

            justify-content: center;

            border-radius: 15px;

            background:
                    rgba(255,255,255,.13);

            border:
                    1px solid
                    rgba(255,255,255,.16);

            font-size: 24px;

            margin-bottom: 15px;
        }


        .report-hero h2 {

            margin: 0 0 7px;

            font-size: 28px;

            font-weight: 700;
        }


        .report-hero p {

            margin: 0;

            color: #dbeafe;

            font-size: 13px;

            line-height: 1.6;
        }


        /* ==============================
           REPORT CARD
           ============================== */

        .report-card {

            background:
                    rgba(255,255,255,.80);

            backdrop-filter:
                    blur(18px);

            -webkit-backdrop-filter:
                    blur(18px);

            border:
                    1px solid
                    rgba(255,255,255,.85);

            border-radius: 20px;

            padding: 28px;

            box-shadow:
                    0 15px 40px
                    rgba(15,23,42,.08);
        }


        .report-top {

            display: flex;

            justify-content: space-between;

            align-items: center;

            gap: 15px;

            margin-bottom: 22px;

            padding-bottom: 18px;

            border-bottom:
                    1px solid #e2e8f0;
        }


        .report-title {

            color: #0f172a;

            font-size: 17px;

            font-weight: 700;
        }


        .report-subtitle {

            color: #64748b;

            font-size: 11px;

            margin-top: 4px;
        }


        .report-badge {

            display: inline-flex;

            align-items: center;

            gap: 6px;

            padding:
                    7px 11px;

            border-radius: 20px;

            background:
                    #eff6ff;

            color:
                    #2563eb;

            border:
                    1px solid #bfdbfe;

            font-size: 10px;

            font-weight: 700;

            white-space: nowrap;
        }


        /* ==============================
           EMPLOYEE ID
           ============================== */

        .employee-id-box {

            display: flex;

            align-items: center;

            gap: 13px;

            padding: 15px;

            margin-bottom: 20px;

            border-radius: 13px;

            background:
                    linear-gradient(
                            135deg,
                            #eef2ff,
                            #eff6ff
                    );

            border:
                    1px solid #dbeafe;
        }


        .employee-id-icon {

            width: 40px;
            height: 40px;

            border-radius: 11px;

            display: flex;

            align-items: center;

            justify-content: center;

            background:
                    #4f46e5;

            color: white;

            font-size: 17px;
        }


        .employee-id-label {

            color: #64748b;

            font-size: 9px;

            text-transform: uppercase;

            letter-spacing: .6px;

            font-weight: 700;
        }


        .employee-id-value {

            color: #1e1b4b;

            font-size: 15px;

            font-weight: 700;

            margin-top: 3px;
        }


        /* ==============================
           INFORMATION GRID
           ============================== */

        .info-grid {

            display: grid;

            grid-template-columns:
                    repeat(2, 1fr);

            gap: 14px;
        }


        .info-item {

            padding: 17px;

            border-radius: 13px;

            background:
                    rgba(248,250,252,.85);

            border:
                    1px solid #e2e8f0;

            transition: .22s;
        }


        .info-item:hover {

            transform:
                    translateY(-2px);

            background:
                    rgba(255,255,255,.95);

            box-shadow:
                    0 8px 22px
                    rgba(15,23,42,.06);
        }


        .info-label {

            color: #64748b;

            font-size: 9px;

            text-transform: uppercase;

            letter-spacing: .6px;

            font-weight: 700;

            margin-bottom: 8px;
        }


        .info-value {

            color: #0f172a;

            font-size: 14px;

            font-weight: 600;

            word-break: break-word;
        }


        .salary-item {

            grid-column:
                    span 2;

            background:
                    linear-gradient(
                            135deg,
                            #ecfdf5,
                            #f0fdf4
                    );

            border-color:
                    #bbf7d0;
        }


        .salary-item .info-label {

            color:
                    #047857;
        }


        .salary-value {

            color:
                    #047857;

            font-size: 21px;

            font-weight: 700;
        }


        /* ==============================
           BUTTONS
           ============================== */

        .button-area {

            display: flex;

            justify-content: center;

            gap: 11px;

            margin-top: 25px;

            padding-top: 20px;

            border-top:
                    1px solid #e2e8f0;
        }


        .print-btn {

            border: none;

            border-radius: 10px;

            padding:
                    11px 20px;

            background:
                    linear-gradient(
                            135deg,
                            #2563eb,
                            #4f46e5
                    );

            color: white;

            font-size: 12px;

            font-weight: 700;

            cursor: pointer;

            box-shadow:
                    0 8px 20px
                    rgba(37,99,235,.20);

            transition: .25s;
        }


        .print-btn:hover {

            transform:
                    translateY(-3px);

            box-shadow:
                    0 12px 26px
                    rgba(37,99,235,.28);
        }


        .back-report-btn {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            border-radius: 10px;

            padding:
                    11px 20px;

            background:
                    #0f172a;

            color: white;

            text-decoration: none;

            font-size: 12px;

            font-weight: 700;

            transition: .25s;
        }


        .back-report-btn:hover {

            color: white;

            background:
                    #1e293b;

            transform:
                    translateY(-3px);
        }


        /* ==============================
           FOOTER
           ============================== */

        .footer {

            text-align: center;

            color: #64748b;

            font-size: 11px;

            padding:
                    24px 0 5px;
        }


        /* ==============================
           MOBILE
           ============================== */

        @media (max-width: 700px) {

            .page-container {

                padding:
                        25px 14px 40px;
            }


            .main-header .container-fluid {

                padding-left: 14px !important;

                padding-right: 14px !important;
            }


            .brand-title {

                font-size: 16px;
            }


            .brand-subtitle {

                font-size: 10px;
            }


            .back-btn {

                width: 100%;
            }


            .report-hero {

                padding: 24px;
            }


            .report-hero h2 {

                font-size: 24px;
            }


            .report-card {

                padding: 20px;
            }


            .report-top {

                align-items: flex-start;

                flex-direction: column;
            }


            .info-grid {

                grid-template-columns: 1fr;
            }


            .salary-item {

                grid-column:
                        span 1;
            }


            .button-area {

                flex-direction: column;
            }


            .print-btn,
            .back-report-btn {

                width: 100%;
            }

        }


        /* ==============================
           PRINT
           ============================== */

        @media print {

            body {

                background: white;
            }


            body::before,
            body::after {

                display: none;
            }


            .main-header {

                display: none;
            }


            .page-container {

                max-width: 100%;

                padding: 0;

                margin: 0;
            }


            .report-hero {

                color: #000;

                background: white;

                box-shadow: none;

                border-radius: 0;

                padding:
                        10px 0 20px;

                border-bottom:
                        2px solid #222;

                margin-bottom: 20px;
            }


            .report-hero::before,
            .report-hero::after {

                display: none;
            }


            .report-icon {

                display: none;
            }


            .report-hero h2 {

                color: #000;

                font-size: 24px;
            }


            .report-hero p {

                color: #555;
            }


            .report-card {

                background: white;

                box-shadow: none;

                border: none;

                padding: 0;
            }


            .info-item {

                background: white;

                border:
                        1px solid #ddd;

                box-shadow: none;
            }


            .button-area {

                display: none;
            }


            .footer {

                display: none;
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


            <a href="viewEmployees"
               class="back-btn">

                ← Employees

            </a>

        </div>

    </div>

</header>


<!-- =====================================
     MAIN
     ===================================== -->

<main class="page-container">


    <!-- HERO -->

    <section class="report-hero">

        <div class="hero-content">

            <div class="report-icon">
                📄
            </div>

            <h2>
                Employee Report
            </h2>

            <p>
                Detailed employee information and salary report.
            </p>

        </div>

    </section>


    <!-- =====================================
         REPORT CARD
         ===================================== -->

    <section class="report-card">


        <div class="report-top">

            <div>

                <div class="report-title">
                    Employee Information
                </div>

                <div class="report-subtitle">
                    Official employee record
                </div>

            </div>


            <div class="report-badge">

                ✓ Verified Record

            </div>

        </div>


        <!-- EMPLOYEE ID -->

        <div class="employee-id-box">

            <div class="employee-id-icon">
                👤
            </div>

            <div>

                <div class="employee-id-label">
                    Employee ID
                </div>

                <div class="employee-id-value">
                    <%= employeeId %>
                </div>

            </div>

        </div>


        <!-- INFORMATION -->

        <div class="info-grid">


            <div class="info-item">

                <div class="info-label">
                    Full Name
                </div>

                <div class="info-value">

                    <%= employeeName != null
                            ? employeeName
                            : "-" %>

                </div>

            </div>


            <div class="info-item">

                <div class="info-label">
                    Email Address
                </div>

                <div class="info-value">

                    <%= employeeEmail != null
                            ? employeeEmail
                            : "-" %>

                </div>

            </div>


            <div class="info-item">

                <div class="info-label">
                    Phone Number
                </div>

                <div class="info-value">

                    <%= employeePhone != null
                            && !employeePhone.trim().isEmpty()
                            ? employeePhone
                            : "-" %>

                </div>

            </div>


            <div class="info-item">

                <div class="info-label">
                    Department
                </div>

                <div class="info-value">

                    <%= employeeDepartment != null
                            && !employeeDepartment.trim().isEmpty()
                            ? employeeDepartment
                            : "-" %>

                </div>

            </div>


            <!-- SALARY -->

            <div class="info-item salary-item">

                <div class="info-label">
                    Salary
                </div>

                <div class="salary-value">

                    &#8377;<%= employeeSalary != null
                            ? String.format(
                                    "%.2f",
                                    employeeSalary
                            )
                            : "0.00" %>

                </div>

            </div>


        </div>


        <!-- BUTTONS -->

        <div class="button-area">


            <button
                    type="button"
                    class="print-btn"
                    onclick="window.print()">

                🖨 Print Report

            </button>


            <a
                    href="viewEmployees"
                    class="back-report-btn">

                ← Back to Employees

            </a>


        </div>


    </section>


    <div class="footer">

        Employee Management System
        &nbsp;•&nbsp;
        Employee Report

    </div>


</main>


<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


</body>

</html>