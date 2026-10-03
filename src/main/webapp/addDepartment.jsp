<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
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
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Add Department | Employee Management System</title>

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


        /* ===============================
           AESTHETIC BACKGROUND
           =============================== */

        body::before {

            content: "";

            position: fixed;

            width: 420px;
            height: 420px;

            top: -160px;
            left: -120px;

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

            width: 430px;
            height: 430px;

            right: -160px;
            bottom: -180px;

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
                        translate(55px,45px)
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
                        translate(-50px,-40px)
                        scale(1.08);
            }
        }


        /* ===============================
           HEADER
           =============================== */

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


        .profile-btn {

            color: white;

            border:
                    1px solid
                    rgba(255,255,255,.15);

            background:
                    rgba(255,255,255,.08);

            border-radius: 10px;

            padding:
                    9px 14px;

            text-decoration: none;

            font-size: 12px;

            font-weight: 600;

            transition: .25s;
        }


        .profile-btn:hover {

            color: white;

            background:
                    rgba(255,255,255,.17);

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
                    9px 15px;

            text-decoration: none;

            font-size: 12px;

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


        /* ===============================
           MAIN
           =============================== */

        .page-container {

            max-width: 900px;

            margin: auto;

            padding:
                    40px 20px 55px;
        }


        /* ===============================
           PAGE HERO
           =============================== */

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

            width: 220px;
            height: 220px;

            right: -75px;
            top: -110px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.10);
        }


        .page-hero::after {

            content: "";

            position: absolute;

            width: 150px;
            height: 150px;

            left: 45%;
            bottom: -105px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.07);
        }


        .page-hero-content {

            position: relative;

            z-index: 2;
        }


        .page-icon {

            width: 50px;
            height: 50px;

            display: flex;

            align-items: center;

            justify-content: center;

            border-radius: 14px;

            background:
                    rgba(255,255,255,.13);

            border:
                    1px solid
                    rgba(255,255,255,.16);

            font-size: 22px;

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


        /* ===============================
           FORM CARD
           =============================== */

        .form-card {

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

            padding: 30px;

            box-shadow:
                    0 15px 40px
                    rgba(15,23,42,.08);

            animation:
                    slideUp .55s ease both;
        }


        .form-section-title {

            color: #0f172a;

            font-size: 17px;

            font-weight: 700;

            margin-bottom: 5px;
        }


        .form-section-subtitle {

            color: #64748b;

            font-size: 12px;

            margin-bottom: 25px;
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


        textarea.form-control {

            min-height: 125px;

            resize: vertical;
        }


        .input-icon {

            position: relative;
        }


        .field-hint {

            color: #94a3b8;

            font-size: 10px;

            margin-top: 6px;
        }


        /* ===============================
           BUTTONS
           =============================== */

        .button-row {

            display: flex;

            flex-wrap: wrap;

            gap: 10px;

            margin-top: 28px;

            padding-top: 22px;

            border-top:
                    1px solid #e2e8f0;
        }


        .submit-btn {

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


        .submit-btn:hover {

            color: white;

            transform:
                    translateY(-3px);

            box-shadow:
                    0 12px 25px
                    rgba(37,99,235,.30);
        }


        .back-btn {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            border-radius: 10px;

            padding:
                    12px 20px;

            background:
                    #334155;

            color: white;

            text-decoration: none;

            font-size: 13px;

            font-weight: 600;

            transition: .25s;
        }


        .back-btn:hover {

            color: white;

            background:
                    #1e293b;

            transform:
                    translateY(-3px);
        }


        /* ===============================
           INFO BOX
           =============================== */

        .info-box {

            margin-top: 20px;

            padding: 14px 16px;

            border-radius: 12px;

            background:
                    rgba(239,246,255,.75);

            border:
                    1px solid #dbeafe;

            color: #475569;

            font-size: 11px;

            line-height: 1.6;
        }


        .info-box strong {

            color: #1d4ed8;
        }


        /* ===============================
           FOOTER
           =============================== */

        .footer {

            text-align: center;

            color: #64748b;

            font-size: 11px;

            padding:
                    20px 0 5px;
        }


        /* ===============================
           ANIMATION
           =============================== */

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


        /* ===============================
           MOBILE
           =============================== */

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


            .form-card {

                padding: 21px;
            }


            .button-row {

                flex-direction: column;
            }


            .submit-btn,
            .back-btn {

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

<main class="page-container">


    <!-- PAGE HERO -->

    <section class="page-hero">

        <div class="page-hero-content">

            <div class="page-icon">
                🏢
            </div>

            <h2>
                Add Department
            </h2>

            <p>
                Create a new department and keep your organization structure organized.
            </p>

        </div>

    </section>


    <!-- FORM -->

    <div class="form-card">

        <div class="form-section-title">
            Department Information
        </div>

        <div class="form-section-subtitle">
            Enter the basic information for the new department.
        </div>


        <form action="addDepartment"
              method="post">


            <div class="mb-4">

                <label class="form-label">
                    Department Name
                </label>

                <input
                        type="text"
                        name="name"
                        class="form-control"
                        placeholder="e.g. Finance, Human Resources, IT"
                        required
                >

                <div class="field-hint">
                    Choose a clear and unique department name.
                </div>

            </div>


            <div class="mb-2">

                <label class="form-label">
                    Description
                </label>

                <textarea
                        name="description"
                        class="form-control"
                        placeholder="Enter a short description about this department..."
                ></textarea>

                <div class="field-hint">
                    A short description helps identify the department's purpose.
                </div>

            </div>


            <div class="button-row">

                <button
                        type="submit"
                        class="submit-btn">

                    ✓ Add Department

                </button>


                <a
                        href="index.jsp"
                        class="back-btn">

                    ← Dashboard

                </a>

            </div>


        </form>


        <div class="info-box">

            <strong>Tip:</strong>
            Department names should be meaningful and consistent
            so employee records remain easy to manage.

        </div>

    </div>


    <!-- FOOTER -->

    <div class="footer">

        Employee Management System
        &nbsp;•&nbsp;
        Department Management

    </div>


</main>


<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


</body>

</html>