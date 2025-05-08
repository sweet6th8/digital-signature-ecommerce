package controller.web;

import dao.DBConnectionPool;
import dao.SavedDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/secure/SavedServlet"})
public class SavedProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = DBConnectionPool.getDataSource().getConnection()) {
            int id = Integer.parseInt(req.getParameter("id"));
            SavedDao sv = new SavedDao(con);
            List<Product> list = sv.getAllProduct(id);
            req.setAttribute("products", list);
            req.getRequestDispatcher("/templates/User/saved.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("/").forward(req, resp);
        }
    }
}
