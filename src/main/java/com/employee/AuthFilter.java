package com.employee;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/index.jsp",
        "/viewEmployees",
        "/editEmployee",
        "/updateEmployee",
        "/deleteEmployee",
        "/addEmployee",
        "/hr-assistant.jsp",
        "/hrAssistant"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn =
                session != null &&
                        session.getAttribute("admin") != null;

        if (loggedIn) {

            chain.doFilter(request, response);

        } else {

            res.sendRedirect("login.jsp");
        }
    }
}