<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Admin Login - Employee Management System</title>

    <!-- Bootstrap -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <style>

        * {
            box-sizing: border-box;
        }

        body {

            margin: 0;

            min-height: 100vh;

            font-family:
                    "Segoe UI",
                    Arial,
                    sans-serif;

            background:
                    linear-gradient(
                            135deg,
                            #0f172a,
                            #1e3a8a,
                            #2563eb,
                            #0f766e
                    );

            background-size: 300% 300%;

            animation: gradientMove 12s ease infinite;

            display: flex;

            align-items: center;

            justify-content: center;

            padding: 20px;

            overflow: hidden;

            position: relative;
        }


        /* Animated Background */

        body::before {

            content: "";

            position: absolute;

            width: 420px;

            height: 420px;

            border-radius: 50%;

            background: rgba(255,255,255,0.10);

            top: -150px;

            left: -120px;

            filter: blur(5px);

            animation: floatOne 9s ease-in-out infinite;
        }


        body::after {

            content: "";

            position: absolute;

            width: 360px;

            height: 360px;

            border-radius: 50%;

            background: rgba(255,255,255,0.08);

            bottom: -130px;

            right: -100px;

            filter: blur(5px);

            animation: floatTwo 11s ease-in-out infinite;
        }


        @keyframes gradientMove {

            0% {
                background-position: 0% 50%;
            }

            50% {
                background-position: 100% 50%;
            }

            100% {
                background-position: 0% 50%;
            }
        }


        @keyframes floatOne {

            0%, 100% {
                transform: translate(0, 0);
            }

            50% {
                transform: translate(50px, 40px);
            }
        }


        @keyframes floatTwo {

            0%, 100% {
                transform: translate(0, 0);
            }

            50% {
                transform: translate(-40px, -30px);
            }
        }


        /* Login Card */

        .login-wrapper {

            width: 100%;

            max-width: 430px;

            position: relative;

            z-index: 2;
        }


        .login-box {

            background:
                    rgba(255,255,255,0.94);

            backdrop-filter: blur(18px);

            -webkit-backdrop-filter: blur(18px);

            border-radius: 28px;

            padding: 42px 38px;

            box-shadow:
                    0 30px 80px rgba(0,0,0,0.30);

            border:
                    1px solid rgba(255,255,255,0.65);

            animation: cardAppear 0.8s ease;
        }


        @keyframes cardAppear {

            from {

                opacity: 0;

                transform:
                        translateY(25px)
                        scale(0.97);
            }

            to {

                opacity: 1;

                transform:
                        translateY(0)
                        scale(1);
            }
        }


        /* Logo */

        .logo-circle {

            width: 82px;

            height: 82px;

            margin: 0 auto 18px;

            border-radius: 24px;

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 42px;

            background:
                    linear-gradient(
                            135deg,
                            #2563eb,
                            #4f46e5
                    );

            box-shadow:
                    0 12px 30px rgba(37,99,235,0.35);
        }


        h1 {

            text-align: center;

            margin: 0;

            font-weight: 800;

            color: #0f172a;

            font-size: 30px;
        }


        .subtitle {

            text-align: center;

            color: #64748b;

            margin-top: 8px;

            margin-bottom: 30px;

            font-size: 14px;
        }


        /* Security Badge */

        .security-badge {

            display: flex;

            align-items: center;

            justify-content: center;

            gap: 7px;

            background: #eff6ff;

            color: #1d4ed8;

            border-radius: 50px;

            padding: 8px 14px;

            width: fit-content;

            margin: 0 auto 25px;

            font-size: 12px;

            font-weight: 700;

            letter-spacing: 0.3px;
        }


        .status-dot {

            width: 8px;

            height: 8px;

            background: #22c55e;

            border-radius: 50%;

            box-shadow:
                    0 0 0 4px rgba(34,197,94,0.12);
        }


        /* Form */

        .form-label {

            font-weight: 700;

            color: #334155;

            font-size: 13px;

            margin-bottom: 7px;
        }


        .input-group-custom {

            position: relative;

            margin-bottom: 20px;
        }


        .input-icon {

            position: absolute;

            left: 14px;

            top: 50%;

            transform: translateY(-50%);

            font-size: 17px;

            z-index: 3;

            opacity: 0.7;
        }


        .form-control {

            height: 50px;

            border-radius: 13px;

            border: 1px solid #dbe2ea;

            padding-left: 45px;

            font-size: 14px;

            background: #f8fafc;

            transition: all 0.25s ease;
        }


        .form-control:focus {

            background: white;

            border-color: #3b82f6;

            box-shadow:
                    0 0 0 4px rgba(59,130,246,0.12);

            outline: none;
        }


        /* Login Button */

        .login-btn {

            width: 100%;

            height: 52px;

            border: none;

            border-radius: 14px;

            color: white;

            font-size: 15px;

            font-weight: 700;

            background:
                    linear-gradient(
                            135deg,
                            #2563eb,
                            #4f46e5
                    );

            box-shadow:
                    0 12px 25px rgba(37,99,235,0.28);

            transition: all 0.25s ease;
        }


        .login-btn:hover {

            transform: translateY(-2px);

            box-shadow:
                    0 16px 32px rgba(37,99,235,0.35);
        }


        .login-btn:active {

            transform: translateY(0);
        }


        /* Footer */

        .footer {

            text-align: center;

            margin-top: 25px;

            padding-top: 20px;

            border-top:
                    1px solid #e2e8f0;

            color: #94a3b8;

            font-size: 12px;
        }


        .footer strong {

            color: #64748b;
        }


        /* Mobile */

        @media (max-width: 500px) {

            body {

                padding: 15px;
            }

            .login-box {

                padding: 32px 24px;

                border-radius: 24px;
            }

            h1 {

                font-size: 26px;
            }

            .logo-circle {

                width: 72px;

                height: 72px;

                font-size: 36px;
            }
        }

    </style>

</head>


<body>


<div class="login-wrapper">

    <div class="login-box">


        <!-- Logo -->

        <div class="logo-circle">
            👨‍💼
        </div>


        <h1>
            Admin Login
        </h1>


        <p class="subtitle">
            Employee Management System
        </p>


        <!-- Security Status -->

        <div class="security-badge">

            <span class="status-dot"></span>

            Secure Admin Access

        </div>


        <!-- Login Form -->

        <form
                action="login"
                method="post">


            <div class="input-group-custom">

                <label class="form-label">
                    Username
                </label>

                <span class="input-icon">
                    👤
                </span>

                <input
                        type="text"
                        name="username"
                        class="form-control"
                        placeholder="Enter your username"
                        autocomplete="username"
                        required>

            </div>


            <div class="input-group-custom">

                <label class="form-label">
                    Password
                </label>

                <span class="input-icon">
                    🔒
                </span>

                <input
                        type="password"
                        name="password"
                        class="form-control"
                        placeholder="Enter your password"
                        autocomplete="current-password"
                        required>

            </div>


            <button
                    type="submit"
                    class="login-btn">

                🔐 &nbsp; Login to Dashboard

            </button>


        </form>


        <!-- Footer -->

        <div class="footer">

            <strong>Employee Management System</strong>

            <br>

            Admin Panel • Secure Access

        </div>


    </div>

</div>


</body>

</html>