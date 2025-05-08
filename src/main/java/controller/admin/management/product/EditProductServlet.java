/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin.management.product;


import dao.CategoryDAO;
import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Category;
import model.Product;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;


@WebServlet(name = "EditProductServlet", urlPatterns = {"/secure/EditProductServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold for in-memory storage
        maxFileSize = 1024 * 1024 * 10,      // 10MB maximum file size
        maxRequestSize = 1024 * 1024 * 50    // 50MB maximum request size
)

public class EditProductServlet extends HttpServlet {
    private DataSource dataSource;
    private static final String EDIT_PAGE = "/templates/admin/admin_edit_product.jsp";


    @Override
    public void init() throws ServletException {
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = dataSource.getConnection()) {
            // Validate PID
            String pidParam = request.getParameter("pid");
            if (pidParam == null || !pidParam.matches("\\d+")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID.");
                return;
            }
            int productId = Integer.parseInt(pidParam);

            ProductDAO dao = new ProductDAO(conn);
            Product product = dao.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
                return;
            }
            request.setAttribute("PRODUCT", product);


            List<Category> categories = new CategoryDAO(conn).getAllCategories();
            if (categories == null) categories = List.of();
            request.setAttribute("LIST_CATEGORIES", categories);

        } catch (Exception ex) {
            log("EditProductServlet error: " + ex.getMessage());
        }
        request.getRequestDispatcher(EDIT_PAGE).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String id = getPartValue(request.getPart("id"));
            String name = getPartValue(request.getPart("name"));
            String description = getPartValue(request.getPart("description"));
            String priceStr = getPartValue(request.getPart("price"));
            String stockStr = getPartValue(request.getPart("stock"));
            String categoryIdStr = getPartValue(request.getPart("categoryId"));


            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            int categoryId = Integer.parseInt(categoryIdStr);


            Part filePart = request.getPart("photo"); // Match "name" in input field
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = getServletContext().getRealPath("/") + "uploads";

            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdir();
            }


            String uploadedFilePath = null;
            if (fileName != null && !fileName.isEmpty()) {
                uploadedFilePath = "uploads/" + fileName;
                filePart.write(uploadDir + File.separator + fileName);
            } else {

                try (Connection connection = dataSource.getConnection()) {
                    ProductDAO productDAO = new ProductDAO(connection);
                    Product existingProduct = productDAO.getProductById(Integer.parseInt(id));
                    uploadedFilePath = existingProduct.getPhoto();
                }
            }




            Product product = new Product();
            product.setId(Integer.parseInt(id));
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(new Category(categoryId, null));

            product.setPhoto(uploadedFilePath);


            try (Connection connection = dataSource.getConnection()) {
                ProductDAO productDAO = new ProductDAO(connection);
                productDAO.updateProduct(product);
            }


            response.sendRedirect("ManageProductServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error updating product");
        }
    }


    private String getPartValue(Part part) throws IOException {
        if (part == null) {
            return null;
        }
        try (var inputStream = part.getInputStream()) {
            return new String(inputStream.readAllBytes());
        }
    }
}