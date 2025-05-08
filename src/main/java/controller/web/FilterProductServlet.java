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

@WebServlet("/Filter")
public class FilterProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnectionPool.getDataSource().getConnection()) {

            String minPrice = req.getParameter("minPrice");
            String maxPrice = req.getParameter("maxPrice");

            ProductDAO dao = new ProductDAO(connection);
            List<Product> products = dao.filteringProductByPrice(Double.parseDouble(minPrice), Double.parseDouble(maxPrice));
            req.setAttribute("productList", products);

            req.getRequestDispatcher("/templates/category.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error connecting to the database", e);
        }


    }
}
