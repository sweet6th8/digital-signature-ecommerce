/*
 * Manage Product Servlet
 */
package controller.admin.management.product;

import dao.CategoryDAO;
import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "ManageProductServlet", urlPatterns = {"/secure/ManageProductServlet"})
public class ManageProductServlet extends HttpServlet {
    private DataSource dataSource;
    private static final String MANAGE_PRODUCT_PAGE = "/templates/admin/admin_products.jsp";


    @Override
    public void init() throws ServletException {
        // Initialize the data source
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (Connection conn = dataSource.getConnection()) {
            ProductDAO productDAO = new ProductDAO(conn);
            List<Product> productList = productDAO.getAllProducts(); // Load all products
            request.setAttribute("LISTPRODUCTS", productList); // Attach to request

            // Fetch categories for product filtering or insert operations
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            List<Category> categoryList = categoryDAO.getAllCategories();
            request.setAttribute("LIST_CATEGORIES", categoryList); // Attach to request
        } catch (Exception ex) {
            log("Error retrieving products:", ex);
            request.setAttribute("mess", "Unable to load products. Please try again later.");
        } finally {
            request.getRequestDispatcher(MANAGE_PRODUCT_PAGE).forward(request, response);
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

    @Override
    public String getServletInfo() {
        return "Manages product retrieval and basic operations for admin panel.";
    }
}