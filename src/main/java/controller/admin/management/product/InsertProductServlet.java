/*
 * Insert Product Servlet
 */
package controller.admin.management.product;

import dao.CategoryDAO;
import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Category;
import model.Product;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;

/**
 * Servlet for inserting a new product.
 */
@WebServlet(name = "InsertProductServlet", urlPatterns = {"/secure/InsertProductServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class InsertProductServlet extends HttpServlet {

    private DataSource dataSource;
    private static final String INSERT_PRODUCT_PAGE = "/templates/admin/admin_products_insert.jsp";
    private static final String MANAGE_PRODUCT_SERVLET = "ManageProductServlet";

    @Override
    public void init() throws ServletException {
        dataSource = DBConnectionPool.getDataSource(); // Initialize your DataSource
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Product product = new Product();

        try (Connection conn = dataSource.getConnection()) {
            // Retrieve form data
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = new CategoryDAO(conn).getCategoryById(categoryId);

            // File upload handling
            Part filePart = request.getPart("photo"); // "photo" matches the input field name in the form
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/") + "upload_dir";

            // Create the upload directory if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Save the uploaded file to the server
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // Set product properties
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setPhoto("upload_dir/" + fileName); // Store the relative path
            product.setCategory(category);

            // Save the product to the database
            ProductDAO productDAO = new ProductDAO(conn);
            if (productDAO.addProduct(product)) {
                request.setAttribute("mess", "Product added successfully!");
            } else {
                request.setAttribute("mess", "Failed to add product. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mess", "An error occurred: " + e.getMessage());
        }

        // Redirect to the manage products page with success/error message
        request.getRequestDispatcher(MANAGE_PRODUCT_SERVLET).forward(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = dataSource.getConnection()) {
            // Retrieve the list of categories
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            List<Category> categories = categoryDAO.getAllCategories();

            // Set the list of categories in the request scope
            request.setAttribute("LIST_CATEGORIES", categories);

            // Forward the request to the JSP page
            request.getRequestDispatcher(INSERT_PRODUCT_PAGE).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while loading the page.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Insert Product Servlet";
    }
}