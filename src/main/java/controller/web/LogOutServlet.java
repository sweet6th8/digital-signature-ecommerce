package controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/secure/logout"})
public class LogOutServlet extends HttpServlet {
    private final static String  Root = "/";
    private final static String LoginPage = "/templates/login.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Lấy session nếu tồn tại
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                System.out.println("User " + user.getUsername() + " has logged out.");
            }
            session.invalidate(); // Kết thúc phiên
            resp.sendRedirect(req.getContextPath() + Root);
            return;
        }
        resp.sendRedirect(req.getContextPath() + LoginPage);

    }
}
