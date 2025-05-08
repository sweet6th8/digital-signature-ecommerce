package controller.web;

import dao.DBConnectionPool;
import dao.SavedDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "Add to SavedServlet" , urlPatterns = {"/secure/AddToSaved"})
public class AddToSavedServlet extends HttpServlet {
    private Connection con ;
    @Override
    public void init() throws ServletException {
        try {con = DBConnectionPool.getDataSource().getConnection();}
        catch (SQLException e) {throw new RuntimeException(e);}
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getSession().getAttribute("userId").toString());
        int productId = Integer.parseInt(req.getParameter("productId"));
        SavedDao sd = new SavedDao(con);
        try {
          if (sd.addProduct(userId,productId,1)) {
              req.getSession().setAttribute("id", "all");
              req.getRequestDispatcher("/category").include(req, resp);
          }
          else   req.getRequestDispatcher("/").forward(req, resp);
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
