package controller.web;


import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

//Chức năng: Hiển thị danh sách sản phẩm theo danh mục (ví dụ: quần áo nam, quần áo nữ).
//Xử lý: Nhận ID danh mục từ yêu cầu, lấy danh sách sản phẩm theo danh mục từ ProductDAO,
//thiết lập thuộc tính cho request và chuyển hướng đến category.jsp
@WebServlet(urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DBConnectionPool.getDataSource().getConnection()) {
            String categoryId = request.getParameter("id");

            categoryId = (categoryId == null) ? "all" : categoryId;

            String pageParam = request.getParameter("page"); // Get current page from request
            String sizeParam = request.getParameter("size"); // Get page size (number of items per page)

            int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1; // Default to page 1
            int size = (sizeParam != null) ? Integer.parseInt(sizeParam) : 10; // Default page size: 10
            int offset = (page - 1) * size;


            ProductDAO productDAO = new ProductDAO(connection);
            List<Product> products;
            int totalProducts;

            // If the category ID is "all", fetch all products with pagination
            if ("all".equals(categoryId)) {
                products = productDAO.getAllProductsPaginated(offset, size); // Implement paginated method in ProductDAO
                totalProducts = productDAO.countAllProducts(); // Implement method to count all products
            } else if (categoryId != null) {
                // Fetch products for the specific category with pagination
                int categoryIdParsed = Integer.parseInt(categoryId);
                products = productDAO.getProductsByCategoryIdPaginated(categoryIdParsed, offset, size);
                totalProducts = productDAO.countProductsByCategoryId(categoryIdParsed); // Count products for category
            } else {
                products = null; // No specific category or all products requested
                totalProducts = 0;
            }

            int totalPages = (int) Math.ceil((double) totalProducts / size);

            // Set attributes for the request
            request.setAttribute("productList", products);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // Forward to JSP
            request.getRequestDispatcher("/templates/category.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error connecting to the database", e);
        }
    }
}
