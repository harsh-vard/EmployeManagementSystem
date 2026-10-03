<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>AI HR Assistant | Employee Management System</title>

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
                    rgba(99,102,241,.24);

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
                    rgba(14,165,233,.21);

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

            max-width: 950px;

            margin: auto;

            padding:
                    40px 20px 50px;
        }


        /* ==============================
           HERO
           ============================== */

        .assistant-hero {

            position: relative;

            overflow: hidden;

            border-radius: 22px;

            padding: 32px;

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


        .assistant-hero::before {

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


        .assistant-hero::after {

            content: "";

            position: absolute;

            width: 180px;
            height: 180px;

            left: 48%;
            bottom: -130px;

            border-radius: 50%;

            background:
                    rgba(255,255,255,.07);
        }


        .hero-content {

            position: relative;

            z-index: 2;
        }


        .ai-icon {

            width: 58px;
            height: 58px;

            display: flex;

            align-items: center;

            justify-content: center;

            border-radius: 16px;

            background:
                    rgba(255,255,255,.13);

            border:
                    1px solid
                    rgba(255,255,255,.18);

            font-size: 27px;

            margin-bottom: 16px;

            box-shadow:
                    0 8px 25px
                    rgba(0,0,0,.10);
        }


        .assistant-hero h2 {

            margin: 0 0 8px;

            font-size: 29px;

            font-weight: 700;
        }


        .assistant-hero p {

            margin: 0;

            color: #dbeafe;

            font-size: 13px;

            line-height: 1.6;
        }


        /* ==============================
           ASSISTANT CARD
           ============================== */

        .assistant-card {

            background:
                    rgba(255,255,255,.79);

            backdrop-filter:
                    blur(18px);

            -webkit-backdrop-filter:
                    blur(18px);

            border:
                    1px solid
                    rgba(255,255,255,.88);

            border-radius: 20px;

            padding: 28px;

            box-shadow:
                    0 15px 40px
                    rgba(15,23,42,.08);

            margin-bottom: 22px;
        }


        .card-heading {

            display: flex;

            align-items: center;

            gap: 13px;

            margin-bottom: 20px;
        }


        .heading-icon {

            width: 43px;
            height: 43px;

            display: flex;

            align-items: center;

            justify-content: center;

            border-radius: 12px;

            background:
                    #eef2ff;

            font-size: 19px;
        }


        .heading-title {

            color: #0f172a;

            font-size: 17px;

            font-weight: 700;

            margin: 0;
        }


        .heading-subtitle {

            color: #64748b;

            font-size: 11px;

            margin-top: 3px;
        }


        /* ==============================
           TEXTAREA
           ============================== */

        .question-label {

            color: #334155;

            font-size: 12px;

            font-weight: 700;

            margin-bottom: 8px;

            display: block;
        }


        .question-box {

            width: 100%;

            min-height: 135px;

            resize: vertical;

            padding: 14px 15px;

            border:
                    1px solid #cbd5e1;

            border-radius: 13px;

            background:
                    rgba(255,255,255,.90);

            color: #0f172a;

            font-family:
                    Arial,
                    Helvetica,
                    sans-serif;

            font-size: 13px;

            line-height: 1.6;

            transition: .22s;
        }


        .question-box::placeholder {

            color: #94a3b8;
        }


        .question-box:hover {

            border-color:
                    #94a3b8;
        }


        .question-box:focus {

            outline: none;

            border-color:
                    #6366f1;

            box-shadow:
                    0 0 0 4px
                    rgba(99,102,241,.10);

            background: white;
        }


        /* ==============================
           BUTTONS
           ============================== */

        .button-row {

            display: flex;

            gap: 10px;

            flex-wrap: wrap;

            margin-top: 15px;
        }


        .ask-button {

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
                    rgba(37,99,235,.22);

            transition: .25s;
        }


        .ask-button:hover {

            transform:
                    translateY(-3px);

            box-shadow:
                    0 12px 26px
                    rgba(37,99,235,.30);
        }


        .clear-button {

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


        .clear-button:hover {

            color: white;

            background:
                    #1e293b;

            transform:
                    translateY(-3px);
        }


        /* ==============================
           EXAMPLES
           ============================== */

        .examples {

            margin-top: 28px;

            padding-top: 23px;

            border-top:
                    1px solid #e2e8f0;
        }


        .examples-heading {

            display: flex;

            align-items: center;

            gap: 8px;

            color: #0f172a;

            font-size: 15px;

            font-weight: 700;

            margin-bottom: 14px;
        }


        .examples-grid {

            display: grid;

            grid-template-columns:
                    repeat(2, 1fr);

            gap: 10px;
        }


        .example-item {

            display: flex;

            align-items: flex-start;

            gap: 9px;

            padding:
                    12px 13px;

            border-radius: 11px;

            background:
                    rgba(248,250,252,.85);

            border:
                    1px solid #e2e8f0;

            color: #475569;

            font-size: 11px;

            line-height: 1.5;

            transition: .22s;
        }


        .example-item:hover {

            transform:
                    translateY(-2px);

            border-color:
                    #c7d2fe;

            background:
                    #f8faff;
        }


        .example-number {

            width: 22px;
            height: 22px;

            border-radius: 7px;

            display: flex;

            align-items: center;

            justify-content: center;

            flex-shrink: 0;

            background:
                    #eef2ff;

            color:
                    #4f46e5;

            font-size: 9px;

            font-weight: 700;
        }


        /* ==============================
           INFO BOX
           ============================== */

        .info-box {

            display: flex;

            align-items: flex-start;

            gap: 11px;

            padding:
                    14px 16px;

            border-radius: 12px;

            background:
                    #eff6ff;

            border:
                    1px solid #bfdbfe;

            color:
                    #1e40af;

            font-size: 11px;

            line-height: 1.6;

            margin-top: 18px;
        }


        .info-icon {

            font-size: 17px;

            flex-shrink: 0;
        }


        /* ==============================
           FOOTER
           ============================== */

        .footer {

            text-align: center;

            color: #64748b;

            font-size: 11px;

            padding:
                    22px 0 5px;
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


            .assistant-hero {

                padding: 24px;
            }


            .assistant-hero h2 {

                font-size: 24px;
            }


            .assistant-card {

                padding: 20px;
            }


            .examples-grid {

                grid-template-columns: 1fr;
            }


            .button-row {

                flex-direction: column;
            }


            .ask-button,
            .clear-button {

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

    <section class="assistant-hero">

        <div class="hero-content">

            <div class="ai-icon">
                🤖
            </div>

            <h2>
                AI HR Assistant
            </h2>

            <p>
                Ask questions about employees, departments, salaries and HR information.
            </p>

        </div>

    </section>


    <!-- =====================================
         ASSISTANT
         ===================================== -->

    <section class="assistant-card">


        <div class="card-heading">

            <div class="heading-icon">
                💬
            </div>

            <div>

                <h3 class="heading-title">
                    Ask your HR Assistant
                </h3>

                <div class="heading-subtitle">
                    Type your question below and get an instant HR answer.
                </div>

            </div>

        </div>


        <form action="hrAssistant"
              method="post">


            <label class="question-label">

                Your Question

            </label>


            <textarea
                    name="question"
                    class="question-box"
                    placeholder="Example: How many employees are there?"
                    required></textarea>


            <div class="button-row">


                <button
                        type="submit"
                        class="ask-button">

                    🤖 Ask Assistant

                </button>


                <a
                        href="hr-assistant.jsp"
                        class="clear-button">

                    ↻ Clear

                </a>


            </div>


        </form>


        <!-- =================================
             EXAMPLES
             ================================= -->

        <div class="examples">


            <div class="examples-heading">

                💡 Example Questions

            </div>


            <div class="examples-grid">


                <div class="example-item">

                    <span class="example-number">
                        1
                    </span>

                    <span>
                        How many employees are there?
                    </span>

                </div>


                <div class="example-item">

                    <span class="example-number">
                        2
                    </span>

                    <span>
                        How many departments are there?
                    </span>

                </div>


                <div class="example-item">

                    <span class="example-number">
                        3
                    </span>

                    <span>
                        What is the average salary?
                    </span>

                </div>


                <div class="example-item">

                    <span class="example-number">
                        4
                    </span>

                    <span>
                        Show me the highest salary.
                    </span>

                </div>


                <div class="example-item">

                    <span class="example-number">
                        5
                    </span>

                    <span>
                        Which department does an employee belong to?
                    </span>

                </div>


                <div class="example-item">

                    <span class="example-number">
                        6
                    </span>

                    <span>
                        How many employees are in each department?
                    </span>

                </div>


            </div>


            <div class="info-box">

                <span class="info-icon">
                    ℹ️
                </span>

                <span>
                    You can ask natural questions about employees,
                    departments and salary information stored in the system.
                </span>

            </div>


        </div>


    </section>


    <!-- FOOTER -->

    <div class="footer">

        Employee Management System
        &nbsp;•&nbsp;
        AI HR Assistant

    </div>


</main>


<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


</body>

</html>