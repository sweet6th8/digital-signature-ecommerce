package controller.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/secure/*")
public class FilterServlet implements jakarta.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        try {
            String role = (String) session.getAttribute("role");
            if (role.equals("Admin") || role.equals("User")) {
                chain.doFilter(request, response);
            }
            else {
                res.sendRedirect(req.getContextPath() + "/templates/login.jsp");
            }
        }
        catch ( Exception e) {
            res.sendRedirect(req.getContextPath() + "/templates/login.jsp");
        }



    }
}
