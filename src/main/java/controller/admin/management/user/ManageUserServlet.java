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
import java.util.List;

@WebServlet(name = "ManageUserServlet", urlPatterns = {"/secure/ManageUserServlet"})
public class ManageUserServlet extends HttpServlet {

    private final String MANAGE_USER_PAGE = "/templates/admin/admin_users.jsp";
    private final String INSERT_USER_PAGE = "/templates/admin/admin_user_insert.jsp";
    private DataSource dataSource;


    @Override
    public void init() throws ServletException {
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = MANAGE_USER_PAGE;
        try (Connection conn = dataSource.getConnection()) {
            String action = request.getParameter("action");
            UserDAO dao = new UserDAO(conn);

            if (action == null) {
                // Fetch all users
                List<User> userList = dao.getAllUsers();
                request.setAttribute("LISTUSERS", userList);
            } else if ("Insert".equals(action)) {
                url = INSERT_USER_PAGE;
            } else if ("Update".equals(action)) {
                // Reload user list after an update
                List<User> userList = dao.getAllUsers();
                request.setAttribute("LISTUSERS", userList);
            }
        } catch (Exception ex) {
            log("ManageUserServlet Error: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}