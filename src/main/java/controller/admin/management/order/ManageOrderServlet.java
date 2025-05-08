package controller.admin.management.order;

import dao.DBConnectionPool;
import dao.OrderItemDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderItem;
import model.OrderProduct;
import model.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "Order" , urlPatterns = {"/secure/ManageOrderServlet"})
public class ManageOrderServlet extends HttpServlet {
    private DataSource dataSource;

    private final String MANAGE_ORDER_PAGE = "/templates/admin/admin_order.jsp";
    private final String VIEW_ORDER__DETAIL_PAGE = "/templates/admin/admin_order_detail.jsp";

    @Override
    public void init() throws ServletException {
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = dataSource.getConnection()) {
            OrderItemDAO dao = new OrderItemDAO(conn);
            List<OrderProduct> lists = dao.getAllProducts();
            request.setAttribute("lists", lists);
            log("Before setting attribute");
            request.setAttribute("lists", lists);
            log("After setting attribute");

            request.getRequestDispatcher(MANAGE_ORDER_PAGE).forward(request, response);
        } catch (Exception ex) {
            log("ManageUserServlet Error: " + ex.getMessage());
            // Forward to an error page or display a meaningful error message
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            request.getRequestDispatcher("/").forward(request, response);
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