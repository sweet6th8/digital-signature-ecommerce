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
import java.sql.SQLException;

//Chức năng: Hiển thị thông tin chi tiết của một sản phẩm cụ thể.
//Xử lý: Nhận ID sản phẩm từ yêu cầu, lấy thông tin sản phẩm từ ProductDAO,
//thiết lập thuộc tính cho request và chuyển hướng đến product-detail.jsp.
@WebServlet("/product")
public class ProductServlet extends HttpServlet {
   private  Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String productId = request.getParameter("id");

            if (productId != null) {
                ProductDAO productDAO = new ProductDAO(connection);
                Product product = productDAO.getProductById(Integer.parseInt(productId));

                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/templates/product-detail.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
            }
        }
}
