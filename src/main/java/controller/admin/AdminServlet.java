package controller.admin;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name="Admin" , urlPatterns = {"/secure/admin"})
public class AdminServlet extends HttpServlet {
    private Connection connection;
    @Override
    public void init() throws ServletException {
        try {
            this.connection = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     HttpSession session = req.getSession();
     String role = (String) session.getAttribute("role");
     if (role.equals("Admin")) {
         req.setAttribute("Message", "Welcome admin  ");
         ProductDAO pdao = new ProductDAO(connection);
         UserDAO udao= new UserDAO(connection);
         OrderDAO odao= new OrderDAO(connection);
         OrderItemDAO odi= new OrderItemDAO(connection);

         try {
             req.setAttribute("TOTALUSERS",udao.totalUser());
             req.setAttribute("TOTALPRODUCTS",pdao.totalProducts());
             req.setAttribute("TOTALORDERS",odao.totalOrders());
             req.setAttribute("PRODUCTSLOW",pdao.productStock());
             req.setAttribute("lists",odi.getAllProducts());
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
         req.getRequestDispatcher("/templates/admin/admin_home.jsp").forward(req, resp);
     }
     else {
         req.setAttribute("Message", "Please admin account  !");
         req.getRequestDispatcher("/templates/login").forward(req, resp);
     }
    }
}