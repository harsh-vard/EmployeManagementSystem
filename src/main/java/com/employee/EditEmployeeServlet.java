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

@WebServlet("/editEmployee")
public class EditEmployeeServlet extends HttpServlet {

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

        String idText = request.getParameter("id");

        if (idText == null || idText.trim().isEmpty()) {
            response.sendRedirect("viewEmployees");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            response.sendRedirect("viewEmployees");
            return;
        }

        Connection con = null;
        PreparedStatement employeePs = null;
        PreparedStatement departmentPs = null;

        ResultSet employeeRs = null;
        ResultSet departmentRs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {
                out.println("<h2>Database connection failed.</h2>");
                return;
            }

            /* Get Employee */

            String employeeSql =
                    "SELECT * FROM employees WHERE id = ?";

            employeePs = con.prepareStatement(employeeSql);

            employeePs.setInt(1, id);

            employeeRs = employeePs.executeQuery();

            if (!employeeRs.next()) {
                response.sendRedirect("viewEmployees");
                return;
            }

            String name = employeeRs.getString("name");
            String email = employeeRs.getString("email");
            String phone = employeeRs.getString("phone");
            String currentDepartment =
                    employeeRs.getString("department");
            double salary = employeeRs.getDouble("salary");

            employeeRs.close();
            employeeRs = null;

            employeePs.close();
            employeePs = null;

            /* Get Departments */

            String departmentSql =
                    "SELECT name FROM departments ORDER BY name";

            departmentPs = con.prepareStatement(departmentSql);

            departmentRs = departmentPs.executeQuery();

            /* Page */

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");

            out.println("<head>");

            out.println("<meta charset='UTF-8'>");
            out.println(
                    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
            );

            out.println("<title>Edit Employee | Employee Management System</title>");

            /* Bootstrap */

            out.println(
                    "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' " +
                            "rel='stylesheet'>"
            );

            /* Bootstrap Icons */

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
                    }

                    .page-container {
                        max-width: 850px;
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

                    .form-card {
                        background: rgba(255,255,255,.76);
                        backdrop-filter: blur(18px);
                        -webkit-backdrop-filter: blur(18px);
                        border: 1px solid rgba(255,255,255,.88);
                        border-radius: 24px;
                        box-shadow: 0 20px 60px rgba(15,23,42,.10);
                        overflow: hidden;
                    }

                    .card-header-custom {
                        padding: 21px 25px;
                        border-bottom: 1px solid #e9eef5;
                        display: flex;
                        align-items: center;
                        gap: 12px;
                    }

                    .edit-icon {
                        width: 42px;
                        height: 42px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        border-radius: 12px;
                        background: #eff6ff;
                        color: #2563eb;
                        font-size: 19px;
                    }

                    .card-header-custom strong {
                        font-size: 16px;
                    }

                    .card-header-custom span {
                        display: block;
                        font-size: 12px;
                        color: #64748b;
                        margin-top: 2px;
                    }

                    .form-content {
                        padding: 28px 25px;
                    }

                    .form-group {
                        margin-bottom: 19px;
                    }

                    .form-label-custom {
                        display: block;
                        font-size: 13px;
                        font-weight: 700;
                        color: #334155;
                        margin-bottom: 7px;
                    }

                    .input-wrap {
                        position: relative;
                    }

                    .input-icon {
                        position: absolute;
                        left: 14px;
                        top: 50%;
                        transform: translateY(-50%);
                        color: #94a3b8;
                        pointer-events: none;
                    }

                    .form-control-custom,
                    .form-select-custom {
                        width: 100%;
                        padding: 12px 14px 12px 40px;
                        border: 1px solid #dbe3ef;
                        border-radius: 12px;
                        background: rgba(255,255,255,.92);
                        color: #172033;
                        font-size: 14px;
                        outline: none;
                        transition: .2s;
                    }

                    .form-control-custom:focus,
                    .form-select-custom:focus {
                        border-color: #3b82f6;
                        box-shadow: 0 0 0 4px rgba(59,130,246,.10);
                    }

                    .form-select-custom {
                        appearance: auto;
                    }

                    .salary-input {
                        padding-left: 40px;
                    }

                    .form-row {
                        display: grid;
                        grid-template-columns: 1fr 1fr;
                        gap: 16px;
                    }

                    .employee-id {
                        display: inline-flex;
                        align-items: center;
                        gap: 7px;
                        padding: 7px 11px;
                        border-radius: 9px;
                        background: #f1f5f9;
                        color: #475569;
                        font-size: 12px;
                        font-weight: 700;
                        margin-bottom: 20px;
                    }

                    .buttons {
                        display: flex;
                        gap: 10px;
                        margin-top: 28px;
                        padding-top: 22px;
                        border-top: 1px solid #e9eef5;
                    }

                    .update-button,
                    .back-button {
                        min-height: 44px;
                        border-radius: 11px;
                        padding: 0 20px;
                        text-decoration: none;
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        gap: 7px;
                        font-size: 13px;
                        font-weight: 700;
                        transition: .25s;
                    }

                    .update-button {
                        border: none;
                        color: white;
                        background: linear-gradient(135deg, #2563eb, #4f46e5);
                        cursor: pointer;
                        flex: 1;
                    }

                    .update-button:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 10px 25px rgba(37,99,235,.25);
                    }

                    .back-button {
                        color: #475569;
                        background: #f1f5f9;
                    }

                    .back-button:hover {
                        color: #1e293b;
                        background: #e2e8f0;
                        transform: translateY(-2px);
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

                        .hero h1 {
                            font-size: 28px;
                        }

                        .form-content {
                            padding: 22px 17px;
                        }

                        .card-header-custom {
                            padding: 17px;
                        }

                        .form-row {
                            grid-template-columns: 1fr;
                            gap: 0;
                        }

                        .buttons {
                            flex-direction: column;
                        }

                        .update-button,
                        .back-button {
                            width: 100%;
                        }
                    }
                    """);

            out.println("</style>");
            out.println("</head>");

            out.println("<body>");

            /* Header */

            out.println("<header class='main-header'>");

            out.println("<div class='header-inner'>");

            out.println("<div class='brand'>");

            out.println("<div class='brand-icon'>");
            out.println("<i class='bi bi-people-fill'></i>");
            out.println("</div>");

            out.println("<div>");
            out.println("<div class='brand-title'>Employee Management System</div>");
            out.println("<div class='brand-subtitle'>Employee Management</div>");
            out.println("</div>");

            out.println("</div>");

            out.println("<div class='header-actions'>");

            out.println(
                    "<a href='index.jsp' class='header-button dashboard'>" +
                            "<i class='bi bi-grid-1x2-fill'></i> Dashboard" +
                            "</a>"
            );

            out.println(
                    "<a href='logout' class='header-button logout-button'>" +
                            "<i class='bi bi-box-arrow-right'></i> Logout" +
                            "</a>"
            );

            out.println("</div>");

            out.println("</div>");

            out.println("</header>");

            /* Main */

            out.println("<main class='page-container'>");

            out.println("<section class='hero'>");

            out.println(
                    "<div class='hero-label'>" +
                            "<i class='bi bi-pencil-square'></i> Employee Management" +
                            "</div>"
            );

            out.println("<h1>Edit Employee</h1>");

            out.println(
                    "<p>Update the employee information below.</p>"
            );

            out.println("</section>");

            /* Form Card */

            out.println("<section class='form-card'>");

            out.println("<div class='card-header-custom'>");

            out.println(
                    "<div class='edit-icon'>" +
                            "<i class='bi bi-person-gear'></i>" +
                            "</div>"
            );

            out.println("<div>");

            out.println("<strong>Employee Information</strong>");

            out.println(
                    "<span>Make changes and save the updated record.</span>"
            );

            out.println("</div>");

            out.println("</div>");

            out.println("<div class='form-content'>");

            out.println(
                    "<div class='employee-id'>" +
                            "<i class='bi bi-hash'></i> Employee ID: " +
                            id +
                            "</div>"
            );

            out.println(
                    "<form action='updateEmployee' method='post'>"
            );

            out.println(
                    "<input type='hidden' name='id' value='" +
                            id +
                            "'>"
            );

            /* Name + Email */

            out.println("<div class='form-row'>");

            /* Name */

            out.println("<div class='form-group'>");

            out.println("<label class='form-label-custom'>Name</label>");

            out.println("<div class='input-wrap'>");

            out.println(
                    "<i class='bi bi-person input-icon'></i>"
            );

            out.println(
                    "<input type='text' " +
                            "name='name' " +
                            "class='form-control-custom' " +
                            "value='" +
                            escapeHtml(name) +
                            "' " +
                            "placeholder='Employee name' " +
                            "required>"
            );

            out.println("</div>");
            out.println("</div>");

            /* Email */

            out.println("<div class='form-group'>");

            out.println("<label class='form-label-custom'>Email</label>");

            out.println("<div class='input-wrap'>");

            out.println(
                    "<i class='bi bi-envelope input-icon'></i>"
            );

            out.println(
                    "<input type='email' " +
                            "name='email' " +
                            "class='form-control-custom' " +
                            "value='" +
                            escapeHtml(email) +
                            "' " +
                            "placeholder='Email address' " +
                            "required>"
            );

            out.println("</div>");
            out.println("</div>");

            out.println("</div>");

            /* Phone + Department */

            out.println("<div class='form-row'>");

            /* Phone */

            out.println("<div class='form-group'>");

            out.println("<label class='form-label-custom'>Phone</label>");

            out.println("<div class='input-wrap'>");

            out.println(
                    "<i class='bi bi-telephone input-icon'></i>"
            );

            out.println(
                    "<input type='text' " +
                            "name='phone' " +
                            "class='form-control-custom' " +
                            "value='" +
                            escapeHtml(phone) +
                            "' " +
                            "placeholder='Phone number'>"
            );

            out.println("</div>");
            out.println("</div>");

            /* Department */

            out.println("<div class='form-group'>");

            out.println(
                    "<label class='form-label-custom'>Department</label>"
            );

            out.println("<div class='input-wrap'>");

            out.println(
                    "<i class='bi bi-building input-icon'></i>"
            );

            out.println(
                    "<select name='department' class='form-select-custom'>"
            );

            out.println(
                    "<option value=''>Select Department</option>"
            );

            while (departmentRs.next()) {

                String departmentName =
                        departmentRs.getString("name");

                String selected = "";

                if (currentDepartment != null &&
                        currentDepartment.equalsIgnoreCase(
                                departmentName
                        )) {

                    selected = " selected";
                }

                out.println(
                        "<option value='" +
                                escapeHtml(departmentName) +
                                "'" +
                                selected +
                                ">" +
                                escapeHtml(departmentName) +
                                "</option>"
                );
            }

            out.println("</select>");

            out.println("</div>");
            out.println("</div>");

            out.println("</div>");

            /* Salary */

            out.println("<div class='form-group'>");

            out.println("<label class='form-label-custom'>Salary</label>");

            out.println("<div class='input-wrap'>");

            out.println(
                    "<i class='bi bi-currency-rupee input-icon'></i>"
            );

            out.println(
                    "<input type='number' " +
                            "step='0.01' " +
                            "name='salary' " +
                            "class='form-control-custom salary-input' " +
                            "value='" +
                            salary +
                            "' " +
                            "placeholder='Salary'>"
            );

            out.println("</div>");
            out.println("</div>");

            /* Buttons */

            out.println("<div class='buttons'>");

            out.println(
                    "<button type='submit' class='update-button'>" +
                            "<i class='bi bi-check2-circle'></i> " +
                            "Update Employee" +
                            "</button>"
            );

            out.println(
                    "<a href='viewEmployees' class='back-button'>" +
                            "<i class='bi bi-arrow-left'></i> Back" +
                            "</a>"
            );

            out.println("</div>");

            out.println("</form>");

            out.println("</div>");

            out.println("</section>");

            out.println("</main>");

            out.println(
                    "<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>"
            );

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();

            out.println(
                    "<h2>Something went wrong.</h2>"
            );

        } finally {

            try {
                if (departmentRs != null) {
                    departmentRs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (departmentPs != null) {
                    departmentPs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (employeeRs != null) {
                    employeeRs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (employeePs != null) {
                    employeePs.close();
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