package com.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/auditLogs")
public class AuditLogServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Login Protection
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Map<String, Object>> logs = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {

                response.getWriter().println(
                        "Error: Database connection failed."
                );

                return;
            }

            String sql =
                    "SELECT id, username, action, details, log_time " +
                            "FROM audit_logs " +
                            "ORDER BY log_time DESC, id DESC";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                Map<String, Object> log = new HashMap<>();

                log.put("id", rs.getInt("id"));
                log.put("username", rs.getString("username"));
                log.put("action", rs.getString("action"));
                log.put("details", rs.getString("details"));
                log.put("logTime", rs.getTimestamp("log_time"));

                logs.add(log);
            }

            request.setAttribute("auditLogs", logs);

            request.getRequestDispatcher(
                    "auditLogs.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.getMessage()
            );

        } finally {

            try {
                if (rs != null) rs.close();
            } catch (Exception ignored) {
            }

            try {
                if (ps != null) ps.close();
            } catch (Exception ignored) {
            }

            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }
}