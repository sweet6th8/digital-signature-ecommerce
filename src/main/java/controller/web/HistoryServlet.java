package controller.web;

import dao.DBConnectionPool;
import dao.OrderItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.HistoryProduct;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/secure/HistoryServlet"})
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = DBConnectionPool.getDataSource().getConnection()) {
            int id = Integer.parseInt(req.getParameter("id"));
            OrderItemDAO orderItemDAO = new OrderItemDAO(con);
            List<HistoryProduct> list = orderItemDAO.getAllHistoryProducts(id);
            req.setAttribute("list", list);
            req.getRequestDispatcher("/templates/User/historyProduct.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
