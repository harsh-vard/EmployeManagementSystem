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

@WebServlet("/hrAssistant")
public class HrAssistantServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String question = request.getParameter("question");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>AI HR Assistant</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background:#f4f6f8; margin:40px; }");
        out.println(".result { background:white; padding:25px; max-width:800px; margin:auto; box-shadow:0 0 10px #ccc; border-radius:8px; }");
        out.println("table { border-collapse:collapse; width:100%; margin-top:20px; }");
        out.println("th, td { border:1px solid #ccc; padding:10px; text-align:left; }");
        out.println("th { background:#333; color:white; }");
        out.println("a { display:inline-block; margin-top:20px; margin-right:15px; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<div class='result'>");
        out.println("<h1>AI HR Assistant 🤖</h1>");

        try {

            Connection con = DBConnection.getConnection();

            if (con == null) {
                out.println("<h2>Database connection failed.</h2>");
                out.println("</div></body></html>");
                return;
            }

            String q = question == null ? "" : question.toLowerCase().trim();

            // Remove common question words
            q = q.replace("what is", "")
                    .replace("what are", "")
                    .replace("who is", "")
                    .replace("who are", "")
                    .replace("how many", "")
                    .replace("show me", "")
                    .replace("please", "")
                    .trim();

            // TOTAL EMPLOYEES
            if (q.contains("total employees")
                    || q.contains("employees count")
                    || q.contains("employee count")
                    || q.equals("employees")
                    || q.contains("number of employees")) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT COUNT(*) FROM employees"
                );

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h2>Total Employees: " + rs.getInt(1) + "</h2>");
                }

                rs.close();
                ps.close();
            }

            // TOTAL DEPARTMENTS
            else if (q.contains("total departments")
                    || q.contains("department count")
                    || q.contains("departments count")
                    || q.contains("number of departments")) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT COUNT(*) FROM departments"
                );

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h2>Total Departments: " + rs.getInt(1) + "</h2>");
                }

                rs.close();
                ps.close();
            }

            // AVERAGE SALARY
            else if (q.contains("average salary")
                    || q.contains("avg salary")
                    || q.contains("average pay")
                    || q.equals("salary average")) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT COALESCE(AVG(salary), 0) FROM employees"
                );

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h2>Average Salary: &#8377;"
                            + String.format("%.2f", rs.getDouble(1))
                            + "</h2>");
                }

                rs.close();
                ps.close();
            }

            // HIGHEST SALARY
            else if (q.contains("highest salary")
                    || q.contains("maximum salary")
                    || q.contains("highest pay")
                    || q.contains("highest paid")) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT name, salary FROM employees ORDER BY salary DESC LIMIT 1"
                );

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h2>Highest Salary Employee</h2>");
                    out.println("<p><b>Name:</b> " + rs.getString("name") + "</p>");
                    out.println("<p><b>Salary:</b> &#8377;"
                            + String.format("%.2f", rs.getDouble("salary"))
                            + "</p>");
                }

                rs.close();
                ps.close();
            }

            // LOWEST SALARY
            else if (q.contains("lowest salary")
                    || q.contains("minimum salary")
                    || q.contains("lowest pay")
                    || q.contains("lowest paid")) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT name, salary FROM employees ORDER BY salary ASC LIMIT 1"
                );

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h2>Lowest Salary Employee</h2>");
                    out.println("<p><b>Name:</b> " + rs.getString("name") + "</p>");
                    out.println("<p><b>Salary:</b> &#8377;"
                            + String.format("%.2f", rs.getDouble("salary"))
                            + "</p>");
                }

                rs.close();
                ps.close();
            }

            // EMPLOYEE COUNT BY DEPARTMENT
            else if (q.contains("employees in each department")
                    || q.contains("employees per department")
                    || q.contains("department wise employees")
                    || q.contains("department wise employee count")
                    || q.contains("employees by department")) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT department, COUNT(*) AS employee_count " +
                                "FROM employees " +
                                "GROUP BY department " +
                                "ORDER BY department"
                );

                ResultSet rs = ps.executeQuery();

                out.println("<h2>Employees by Department</h2>");

                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Department</th>");
                out.println("<th>Employee Count</th>");
                out.println("</tr>");

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    out.println("<tr>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("<td>" + rs.getInt("employee_count") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");

                if (!found) {
                    out.println("<h3>No employee data found.</h3>");
                }

                rs.close();
                ps.close();
            }

            // DEPARTMENT SEARCH
            else if (q.contains("employees from")
                    || q.contains("employees in")
                    || q.contains("department")) {

                String department = q;

                if (q.contains("employees from")) {
                    department = q.substring(
                            q.indexOf("employees from")
                                    + "employees from".length()).trim();
                } else if (q.contains("employees in")) {
                    department = q.substring(
                            q.indexOf("employees in")
                                    + "employees in".length()).trim();
                } else if (q.contains("department")) {
                    department = q.replace("department", "").trim();
                }

                PreparedStatement ps = con.prepareStatement(
                        "SELECT name, email, phone, department, salary " +
                                "FROM employees WHERE LOWER(department) = LOWER(?)"
                );

                ps.setString(1, department);

                ResultSet rs = ps.executeQuery();

                out.println("<h2>Employees from "
                        + department.toUpperCase() + "</h2>");

                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Name</th>");
                out.println("<th>Email</th>");
                out.println("<th>Phone</th>");
                out.println("<th>Department</th>");
                out.println("<th>Salary</th>");
                out.println("</tr>");

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    out.println("<tr>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("<td>" + rs.getString("phone") + "</td>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("<td>&#8377;"
                            + String.format("%.2f", rs.getDouble("salary"))
                            + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");

                if (!found) {
                    out.println("<h3>No employees found in this department.</h3>");
                }

                rs.close();
                ps.close();
            }

            // ALL EMPLOYEES
            else if (q.contains("employee list")
                    || q.contains("list employees")
                    || q.contains("all employees")
                    || q.equals("show employees")
                    || q.equals("employees list")) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT name, email, phone, department, salary FROM employees"
                );

                ResultSet rs = ps.executeQuery();

                out.println("<h2>Employee List</h2>");

                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Name</th>");
                out.println("<th>Email</th>");
                out.println("<th>Phone</th>");
                out.println("<th>Department</th>");
                out.println("<th>Salary</th>");
                out.println("</tr>");

                while (rs.next()) {

                    out.println("<tr>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("<td>" + rs.getString("phone") + "</td>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("<td>&#8377;"
                            + String.format("%.2f", rs.getDouble("salary"))
                            + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");

                rs.close();
                ps.close();
            }

            // HELP
            else {

                out.println("<h2>You can ask:</h2>");
                out.println("<ul>");
                out.println("<li>Average salary</li>");
                out.println("<li>Highest salary</li>");
                out.println("<li>Lowest salary</li>");
                out.println("<li>Total employees</li>");
                out.println("<li>Total departments</li>");
                out.println("<li>Employee list</li>");
                out.println("<li>Employees in each department</li>");
                out.println("<li>IT employees</li>");
                out.println("</ul>");
            }

            con.close();

        } catch (Exception e) {

            out.println("<h2>Error: " + e.getMessage() + "</h2>");

        }

        out.println("<br>");
        out.println("<a href='hr-assistant.jsp'>Ask Another Question</a>");
        out.println("<a href='index.jsp'>Back to Dashboard</a>");

        out.println("</div>");
        out.println("</body></html>");
    }
}