package controller.admin.management.user;

import dao.DBConnectionPool;
import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "InsertUserServlet", urlPatterns = {"/secure/InsertUserServlet"})
public class InsertUserServlet extends HttpServlet {

    private static final String MANAGE_USER_CONTROLLER = "/ManageUserServlet";
    private static final String INSERT_USER_PAGE = "/templates/admin/admin_user_insert.jsp";
    private DataSource dataSource;


    @Override
    public void init() throws ServletException {
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = dataSource.getConnection()) {
            // Gather user input
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));

            // Populate User object
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setFullName(fullName);
            user.setAddress(address);
            user.setPhone(phone);
            user.setGender(gender);

            // Call UserDAO method to save the user
            UserDAO dao = new UserDAO(conn);
            boolean result = dao.registerUser(user);

            if (result) {
                request.setAttribute("mess", "User added successfully!");
                request.getRequestDispatcher(MANAGE_USER_CONTROLLER).forward(request, response);
            } else {
                request.setAttribute("mess", "Error: Unable to RegisterServlet user.");
                request.getRequestDispatcher(INSERT_USER_PAGE).forward(request, response);
            }
        } catch (Exception ex) {
            log("InsertUserServlet Error: " + ex.getMessage());
            request.setAttribute("mess", "Error: Unable to RegisterServlet user!");
            request.getRequestDispatcher(INSERT_USER_PAGE).forward(request, response);
        }
    }
}