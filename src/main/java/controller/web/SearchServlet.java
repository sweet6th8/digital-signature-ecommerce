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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnectionPool.getDataSource().getConnection()) { // Get connection from pool
            resp.setContentType("text/html; charset=UTF-8");
            req.setCharacterEncoding("UTF-8"); // Support for Unicode character inputs
            String txtSearch = req.getParameter("txt");

            // Validate search input
            if (txtSearch == null || txtSearch.trim().isEmpty()) {
                req.setAttribute("errorMessage", "Please enter a valid search term.");
                req.getRequestDispatcher("/templates/error.jsp").forward(req, resp);
                return;
            }

            ProductDAO dao = new ProductDAO(connection);
            List<Product> products = dao.searchProductByName(txtSearch);

            // Add search results and the search query back to the request scope
            req.setAttribute("productList", products);
            req.setAttribute("txtS", txtSearch);

            // Forward to the category page
            req.getRequestDispatcher("/templates/category.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error connecting to the database", e);
        }
    }
}