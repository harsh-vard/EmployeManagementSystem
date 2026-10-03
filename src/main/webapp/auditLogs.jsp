<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
/* 🔐 Login Protection */

if (session.getAttribute("admin") == null) {
    response.sendRedirect("login.jsp");
    return;
}

/* UTF-8 Response */

response.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=UTF-8");

List<Map<String, Object>> logs =
        (List<Map<String, Object>>) request.getAttribute("auditLogs");

String username =
        (String) session.getAttribute("adminUsername");

if (username == null) {
    username =
            (String) session.getAttribute("admin");
}

/* Date Format */

SimpleDateFormat dateFormat =
        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Activity Log | Employee Management System</title>

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

            max-width: 1200px;

            margin: auto;

            padding:
                    40px 20px 50px;
        }


        /* ==============================
           HERO
           ============================== */

        .page-hero {

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

            margin-bottom: 25px;
        }


        .page-hero::before {

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


        .page-hero::after {

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


        .page-icon {

            width: 52px;
            height: 52px;

            display: flex;

            align-items: center;

            justify-content: center;

            border-radius: 14px;

            background:
                    rgba(255,255,255,.13);

            border:
                    1px solid
                    rgba(255,255,255,.16);

            font-size: 23px;

            margin-bottom: 15px;
        }


        .page-hero h2 {

            margin: 0 0 7px;

            font-size: 28px;

            font-weight: 700;
        }


        .page-hero p {

            margin: 0;

            color: #dbeafe;

            font-size: 13px;

            line-height: 1.6;
        }


        /* ==============================
           STATS
           ============================== */

        .stats-row {

            display: grid;

            grid-template-columns:
                    repeat(3, 1fr);

            gap: 15px;

            margin-bottom: 22px;
        }


        .stat-card {

            background:
                    rgba(255,255,255,.76);

            backdrop-filter:
                    blur(14px);

            border:
                    1px solid
                    rgba(255,255,255,.85);

            border-radius: 17px;

            padding: 18px;

            box-shadow:
                    0 12px 30px
                    rgba(15,23,42,.06);

            transition: .25s;
        }


        .stat-card:hover {

            transform:
                    translateY(-3px);

            box-shadow:
                    0 16px 35px
                    rgba(15,23,42,.10);
        }


        .stat-icon {

            width: 40px;
            height: 40px;

            display: flex;

            align-items: center;

            justify-content: center;

            border-radius: 11px;

            background:
                    #eef2ff;

            font-size: 18px;

            margin-bottom: 10px;
        }


        .stat-label {

            color: #64748b;

            font-size: 10px;

            font-weight: 700;

            text-transform: uppercase;

            letter-spacing: .5px;
        }


        .stat-value {

            color: #0f172a;

            font-size: 22px;

            font-weight: 700;

            margin-top: 4px;
        }


        /* ==============================
           LOG CARD
           ============================== */

        .log-card {

            background:
                    rgba(255,255,255,.78);

            backdrop-filter:
                    blur(16px);

            -webkit-backdrop-filter:
                    blur(16px);

            border:
                    1px solid
                    rgba(255,255,255,.85);

            border-radius: 20px;

            box-shadow:
                    0 15px 40px
                    rgba(15,23,42,.08);

            overflow: hidden;
        }


        .log-header {

            padding:
                    20px 22px;

            display: flex;

            align-items: center;

            justify-content: space-between;

            gap: 15px;

            border-bottom:
                    1px solid #e2e8f0;
        }


        .log-header-title {

            font-size: 16px;

            font-weight: 700;

            color: #0f172a;
        }


        .log-header-subtitle {

            color: #64748b;

            font-size: 11px;

            margin-top: 4px;
        }


        .live-badge {

            display: inline-flex;

            align-items: center;

            gap: 6px;

            padding:
                    7px 11px;

            border-radius: 20px;

            background:
                    #ecfdf5;

            color:
                    #047857;

            border:
                    1px solid #a7f3d0;

            font-size: 10px;

            font-weight: 700;

            white-space: nowrap;
        }


        .live-dot {

            width: 7px;
            height: 7px;

            border-radius: 50%;

            background: #10b981;

            animation:
                    pulse 1.8s infinite;
        }


        @keyframes pulse {

            0%,
            100% {
                opacity: 1;
            }

            50% {
                opacity: .35;
            }
        }


        /* ==============================
           TABLE
           ============================== */

        .table-wrapper {

            width: 100%;

            overflow-x: auto;
        }


        table {

            width: 100%;

            border-collapse: collapse;

            min-width: 850px;
        }


        thead th {

            background:
                    #f8fafc;

            color:
                    #475569;

            text-align: left;

            padding:
                    14px 16px;

            font-size: 10px;

            text-transform: uppercase;

            letter-spacing: .5px;

            border-bottom:
                    1px solid #e2e8f0;

            white-space: nowrap;
        }


        tbody td {

            padding:
                    15px 16px;

            border-bottom:
                    1px solid #edf2f7;

            font-size: 12px;

            vertical-align: middle;
        }


        tbody tr {

            transition: .20s;
        }


        tbody tr:hover {

            background:
                    rgba(239,246,255,.70);
        }


        tbody tr:last-child td {

            border-bottom: none;
        }


        .id-badge {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            min-width: 30px;

            height: 27px;

            padding:
                    0 8px;

            border-radius: 8px;

            background:
                    #eef2ff;

            color:
                    #4338ca;

            font-size: 10px;

            font-weight: 700;
        }


        .user-cell {

            font-weight: 700;

            color: #334155;
        }


        .action-badge {

            display: inline-flex;

            align-items: center;

            padding:
                    6px 9px;

            border-radius: 8px;

            background:
                    #eff6ff;

            color:
                    #2563eb;

            font-size: 10px;

            font-weight: 700;

            white-space: nowrap;
        }


        .details-cell {

            color:
                    #64748b;

            max-width: 390px;

            word-break: break-word;
        }


        .time-cell {

            color:
                    #64748b;

            white-space: nowrap;

            font-size: 11px;
        }


        .empty {

            text-align: center;

            padding:
                    55px 20px !important;

            color:
                    #64748b;

            font-size: 13px;
        }


        .empty-icon {

            font-size: 34px;

            margin-bottom: 10px;
        }


        /* ==============================
           FOOTER
           ============================== */

        .footer {

            text-align: center;

            color: #64748b;

            font-size: 11px;

            padding:
                    25px 0 5px;
        }


        /* ==============================
           MOBILE
           ============================== */

        @media (max-width: 768px) {

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

                padding:
                        10px;
            }


            .page-hero {

                padding: 24px;
            }


            .page-hero h2 {

                font-size: 24px;
            }


            .stats-row {

                grid-template-columns: 1fr;

                gap: 12px;
            }


            .log-header {

                align-items: flex-start;

                flex-direction: column;
            }


            .live-badge {

                align-self: flex-start;
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
                        Logged in as: <%= username %>
                    </p>

                </div>

            </div>


            <a href="index.jsp"
               class="back-btn">

                ← Dashboard

            </a>

        </div>

    </div>

</header>


<!-- =====================================
     MAIN
     ===================================== -->

<main class="page-container">


    <!-- HERO -->

    <section class="page-hero">

        <div class="hero-content">

            <div class="page-icon">
                📋
            </div>

            <h2>
                Activity Log
            </h2>

            <p>
                Monitor recent employee and department activities recorded by the system.
            </p>

        </div>

    </section>


    <!-- =====================================
         STAT CARDS
         ===================================== -->

    <div class="stats-row">

        <div class="stat-card">

            <div class="stat-icon">
                📊
            </div>

            <div class="stat-label">
                Total Activities
            </div>

            <div class="stat-value">

                <%
                int totalLogs = 0;

                if (logs != null) {
                    totalLogs = logs.size();
                }
                %>

                <%= totalLogs %>

            </div>

        </div>


        <div class="stat-card">

            <div class="stat-icon">
                👤
            </div>

            <div class="stat-label">
                Current Admin
            </div>

            <div class="stat-value"
                 style="font-size:17px;">

                <%= username %>

            </div>

        </div>


        <div class="stat-card">

            <div class="stat-icon">
                🔐
            </div>

            <div class="stat-label">
                System Status
            </div>

            <div class="stat-value"
                 style="font-size:17px;">

                Protected

            </div>

        </div>

    </div>


    <!-- =====================================
         ACTIVITY TABLE
         ===================================== -->

    <section class="log-card">


        <div class="log-header">

            <div>

                <div class="log-header-title">
                    System Activity
                </div>

                <div class="log-header-subtitle">
                    Employee and department actions are listed below.
                </div>

            </div>


            <div class="live-badge">

                <span class="live-dot"></span>

                Activity Tracking Active

            </div>

        </div>


        <div class="table-wrapper">

            <table>

                <thead>

                <tr>

                    <th>
                        #
                    </th>

                    <th>
                        User
                    </th>

                    <th>
                        Action
                    </th>

                    <th>
                        Details
                    </th>

                    <th>
                        Date & Time
                    </th>

                </tr>

                </thead>


                <tbody>

                <%
                if (logs != null && !logs.isEmpty()) {

                    for (Map<String, Object> log : logs) {

                        int id =
                                (Integer) log.get("id");

                        String logUsername =
                                (String) log.get("username");

                        String action =
                                (String) log.get("action");

                        String details =
                                (String) log.get("details");

                        Timestamp logTime =
                                (Timestamp) log.get("logTime");
                %>


                <tr>

                    <td>

                        <span class="id-badge">

                            <%= id %>

                        </span>

                    </td>


                    <td class="user-cell">

                        <%= logUsername != null
                                ? logUsername
                                : "System" %>

                    </td>


                    <td>

                        <span class="action-badge">

                            <%= action != null
                                    ? action
                                    : "-" %>

                        </span>

                    </td>


                    <td class="details-cell">

                        <%= details != null
                                ? details
                                : "-" %>

                    </td>


                    <td class="time-cell">

                        <%
                        if (logTime != null) {
                        %>

                            <%= dateFormat.format(logTime) %>

                        <%
                        } else {
                        %>

                            -

                        <%
                        }
                        %>

                    </td>

                </tr>


                <%
                    }

                } else {
                %>


                <tr>

                    <td colspan="5"
                        class="empty">

                        <div class="empty-icon">
                            📭
                        </div>

                        <strong>
                            No activity recorded yet
                        </strong>

                        <div style="margin-top:6px;">
                            Employee and department activities will appear here.
                        </div>

                    </td>

                </tr>


                <%
                }
                %>

                </tbody>

            </table>

        </div>

    </section>


    <div class="footer">

        Employee Management System
        &nbsp;•&nbsp;
        Activity Log

    </div>


</main>


<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


</body>

</html>