package controller.admin.management.user;

import dao.DBConnectionPool;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/secure/DeleteUserServlet"})
public class DeleteUserServlet extends HttpServlet {

    private static final String MANAGE_USER_CONTROLLER = "/secure/ManageUserServlet";
    private DataSource dataSource;


    @Override
    public void init() throws ServletException {
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = dataSource.getConnection()) {
            String uid = request.getParameter("uid");
            // Initialize UserDAO
            UserDAO dao = new UserDAO(conn);
            System.out.println("Deleting !");
            // Delete user by id
            boolean result = dao.deleteUser(Integer.parseInt(uid));
            System.out.println(result);
            if (result) {
                request.setAttribute("mess", "User deleted successfully!");
            } else {
                request.setAttribute("mess", "Error: Unable to delete user!");
            }
        } catch (Exception ex) {
            log("DeleteUserServlet Error: " + ex.getMessage());
        } finally {
            // Forwarding request back to ManageUserServlet
            request.getRequestDispatcher(MANAGE_USER_CONTROLLER).forward(request, response);
        }
    }
}