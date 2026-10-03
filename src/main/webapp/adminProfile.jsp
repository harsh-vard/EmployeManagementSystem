<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
/* 🔐 Admin Login Protection */

if (session.getAttribute("admin") == null) {
    response.sendRedirect("login.jsp");
    return;
}

String username =
        (String) session.getAttribute("adminUsername");

if (username == null) {
    username =
            (String) session.getAttribute("admin");
}

String success = request.getParameter("success");
String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Admin Profile | Employee Management System</title>

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


        /* =================================
           AESTHETIC BACKGROUND
           ================================= */

        body::before {

            content: "";

            position: fixed;

            width: 430px;
            height: 430px;

            top: -170px;
            left: -130px;

            border-radius: 50%;

            background:
                    rgba(99,102,241,.25);

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
                    rgba(14,165,233,.23);

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


        /* =================================
           HEADER
           ================================= */

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


        /* =================================
           MAIN
           ================================= */

        .page-container {

            max-width: 950px;

            margin: auto;

            padding:
                    40px 20px 55px;
        }


        /* =================================
           PAGE HERO
           ================================= */

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


        /* =================================
           ALERTS
           ================================= */

        .alert-modern {

            border-radius: 12px;

            padding:
                    14px 17px;

            font-size: 13px;

            font-weight: 600;

            border-width: 1px;

            margin-bottom: 20px;

            animation:
                    slideUp .45s ease both;
        }


        .alert-success-modern {

            background:
                    rgba(236,253,245,.90);

            color: #047857;

            border-color:
                    #a7f3d0;
        }


        .alert-error-modern {

            background:
                    rgba(254,242,242,.92);

            color: #b91c1c;

            border-color:
                    #fecaca;
        }


        /* =================================
           GLASS CARDS
           ================================= */

        .glass-card {

            background:
                    rgba(255,255,255,.76);

            backdrop-filter:
                    blur(16px);

            -webkit-backdrop-filter:
                    blur(16px);

            border:
                    1px solid
                    rgba(255,255,255,.85);

            border-radius:
                    20px;

            box-shadow:
                    0 15px 40px
                    rgba(15,23,42,.08);

            transition:
                    .25s;

            margin-bottom: 22px;
        }


        .glass-card:hover {

            box-shadow:
                    0 18px 45px
                    rgba(15,23,42,.11);
        }


        /* =================================
           PROFILE CARD
           ================================= */

        .profile-card {

            padding: 28px;
        }


        .card-icon {

            width: 46px;
            height: 46px;

            border-radius: 13px;

            display: flex;

            align-items: center;

            justify-content: center;

            background:
                    #eff6ff;

            font-size: 21px;

            margin-bottom: 15px;
        }


        .card-title {

            color: #0f172a;

            font-size: 18px;

            font-weight: 700;

            margin-bottom: 5px;
        }


        .card-subtitle {

            color: #64748b;

            font-size: 12px;

            margin-bottom: 20px;
        }


        .profile-info {

            padding: 18px;

            border-radius: 13px;

            background:
                    rgba(248,250,252,.85);

            border:
                    1px solid #e2e8f0;
        }


        .profile-label {

            color: #64748b;

            font-size: 10px;

            text-transform: uppercase;

            letter-spacing: .6px;

            font-weight: 700;

            margin-bottom: 7px;
        }


        .profile-value {

            color: #0f172a;

            font-size: 18px;

            font-weight: 700;

            display: flex;

            align-items: center;

            gap: 8px;
        }


        .user-badge {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            width: 30px;
            height: 30px;

            border-radius: 9px;

            background:
                    linear-gradient(
                            135deg,
                            #6366f1,
                            #2563eb
                    );

            color: white;

            font-size: 13px;
        }


        /* =================================
           PASSWORD CARD
           ================================= */

        .password-card {

            padding: 28px;
        }


        .security-badge {

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

            margin-bottom: 15px;
        }


        .form-label {

            color: #334155;

            font-size: 12px;

            font-weight: 700;

            margin-bottom: 7px;
        }


        .form-control {

            border:
                    1px solid #cbd5e1;

            border-radius: 11px;

            padding:
                    12px 13px;

            font-size: 13px;

            background:
                    rgba(255,255,255,.90);

            transition:
                    .22s;
        }


        .form-control:hover {

            border-color:
                    #94a3b8;
        }


        .form-control:focus {

            border-color:
                    #6366f1;

            box-shadow:
                    0 0 0 4px
                    rgba(99,102,241,.10);

            background: white;
        }


        .password-hint {

            color: #94a3b8;

            font-size: 10px;

            margin-top: 6px;
        }


        .change-btn {

            border: none;

            border-radius: 10px;

            padding:
                    12px 21px;

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


        .change-btn:hover {

            color: white;

            transform:
                    translateY(-3px);

            box-shadow:
                    0 12px 25px
                    rgba(37,99,235,.30);
        }


        /* =================================
           FOOTER
           ================================= */

        .footer {

            text-align: center;

            color: #64748b;

            font-size: 11px;

            padding:
                    18px 0 5px;
        }


        /* =================================
           ANIMATION
           ================================= */

        @keyframes slideUp {

            from {

                opacity: 0;

                transform:
                        translateY(12px);
            }

            to {

                opacity: 1;

                transform:
                        translateY(0);
            }
        }


        /* =================================
           MOBILE
           ================================= */

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


            .header-actions {

                width: 100%;
            }


            .header-actions a {

                flex: 1;

                text-align: center;
            }


            .page-hero {

                padding: 24px;
            }


            .page-hero h2 {

                font-size: 24px;
            }


            .profile-card,
            .password-card {

                padding: 21px;
            }


            .change-btn {

                width: 100%;
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


            <div class="d-flex
                        header-actions
                        gap-2">

                <a href="index.jsp"
                   class="back-btn">

                    ← Dashboard

                </a>

            </div>

        </div>

    </div>

</header>


<!-- =====================================
     MAIN
     ===================================== -->

<main class="page-container">


    <!-- PAGE HERO -->

    <section class="page-hero">

        <div class="hero-content">

            <div class="page-icon">
                👤
            </div>

            <h2>
                Admin Profile
            </h2>

            <p>
                Manage your administrator account and update your password securely.
            </p>

        </div>

    </section>


    <!-- =====================================
         SUCCESS MESSAGE
         ===================================== -->

    <%
    if ("password".equals(success)) {
    %>

        <div class="alert-modern
                    alert-success-modern">

            ✓ Password changed successfully.

        </div>

    <%
    }
    %>


    <!-- =====================================
         ERROR MESSAGES
         ===================================== -->

    <%
    if ("current".equals(error)) {
    %>

        <div class="alert-modern
                    alert-error-modern">

            ✗ Current password is incorrect.

        </div>

    <%
    } else if ("mismatch".equals(error)) {
    %>

        <div class="alert-modern
                    alert-error-modern">

            ✗ New password and confirm password do not match.

        </div>

    <%
    } else if ("short".equals(error)) {
    %>

        <div class="alert-modern
                    alert-error-modern">

            ✗ New password must contain at least 6 characters.

        </div>

    <%
    } else if ("missing".equals(error)) {
    %>

        <div class="alert-modern
                    alert-error-modern">

            ✗ Please fill all password fields.

        </div>

    <%
    } else if ("db".equals(error)) {
    %>

        <div class="alert-modern
                    alert-error-modern">

            ✗ Database error. Please try again.

        </div>

    <%
    } else if ("update".equals(error)) {
    %>

        <div class="alert-modern
                    alert-error-modern">

            ✗ Password could not be updated.

        </div>

    <%
    }
    %>


    <!-- =====================================
         PROFILE INFORMATION
         ===================================== -->

    <div class="glass-card profile-card">

        <div class="card-icon">
            👤
        </div>

        <div class="card-title">
            Profile Information
        </div>

        <div class="card-subtitle">
            Your administrator account information.
        </div>


        <div class="profile-info">

            <div class="profile-label">
                Username
            </div>

            <div class="profile-value">

                <span class="user-badge">
                    👤
                </span>

                <%= username %>

            </div>

        </div>

    </div>


    <!-- =====================================
         CHANGE PASSWORD
         ===================================== -->

    <div class="glass-card password-card">

        <div class="security-badge">

            ● Account Security

        </div>

        <div class="card-title">
            Change Password
        </div>

        <div class="card-subtitle">
            Update your admin account password securely.
        </div>


        <form action="changePassword"
              method="post">


            <div class="mb-4">

                <label class="form-label">
                    Current Password
                </label>

                <input
                        type="password"
                        name="currentPassword"
                        class="form-control"
                        placeholder="Enter your current password"
                        required>

            </div>


            <div class="mb-4">

                <label class="form-label">
                    New Password
                </label>

                <input
                        type="password"
                        name="newPassword"
                        class="form-control"
                        placeholder="Enter your new password"
                        minlength="6"
                        required>

                <div class="password-hint">
                    Password must contain at least 6 characters.
                </div>

            </div>


            <div class="mb-2">

                <label class="form-label">
                    Confirm New Password
                </label>

                <input
                        type="password"
                        name="confirmPassword"
                        class="form-control"
                        placeholder="Confirm your new password"
                        minlength="6"
                        required>

            </div>


            <button
                    type="submit"
                    class="change-btn mt-4">

                🔒 Change Password

            </button>


        </form>

    </div>


    <!-- =====================================
         FOOTER
         ===================================== -->

    <div class="footer">

        Employee Management System
        &nbsp;•&nbsp;
        Admin Profile

    </div>


</main>


<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


</body>

</html>