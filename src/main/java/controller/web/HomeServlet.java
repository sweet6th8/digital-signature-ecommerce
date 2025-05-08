package controller.web;
import dao.CategoryDAO;
import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;
import model.Product;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
//Chức năng: Hiển thị trang chính của cửa hàng (home.jsp) với danh sách sản phẩm nổi bật.
////Xử lý: Lấy danh sách sản phẩm từ ProductDAO, thiết lập thuộc tính cho request và chuyển hướng đến home.jsp.
@WebServlet(urlPatterns = "")
public class HomeServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
             connection = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           // save bundle type
            HttpSession session = request.getSession();
            String language = request.getParameter("lang");
            language = (language == null) ? "en_US" : language;
            session.setAttribute("lang", language);

            ProductDAO productDAO = new ProductDAO(connection);
            List<Product> productList = productDAO.getAllProducts();
            CategoryDAO categoryDAO = new CategoryDAO(connection);
            List<Category> categoryList = categoryDAO.getAllCategories();
            ServletContext context = getServletContext();
            request.setAttribute("productList", productList);
            context.setAttribute("categoryList", categoryList);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
