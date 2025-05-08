package controller.web;

import dao.DBConnectionPool;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "Active", urlPatterns = {"/Active"})
public class ActiveAccountServlet extends HttpServlet {
    Connection con;
    @Override
    public void init() throws ServletException {
        try {  con = DBConnectionPool.getDataSource().getConnection();}
        catch (SQLException e) { throw new RuntimeException(e);}
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            UserDAO udao = new UserDAO(con);
            try {
                if (!udao.updateActive(userId)) req.getRequestDispatcher("/templates/LoginServlet.jsp").forward(req, resp);
                else req.getRequestDispatcher("/").forward(req, resp);
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        } catch (Exception e) {  req.getRequestDispatcher("/").forward(req, resp);}
    }
}
