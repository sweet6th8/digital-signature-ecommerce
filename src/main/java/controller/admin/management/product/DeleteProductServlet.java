/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin.management.product;


import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "DeleteProductServlet", urlPatterns = {"/secure/DeleteProductServlet"})
public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            ProductDAO dao = new ProductDAO(conn);
            int productId = Integer.parseInt(request.getParameter("pid"));
            dao.deleteProduct(productId);
            request.setAttribute("mess", "Product deleted successfully.");
        } catch (Exception ex) {
            log("DeleteProductServlet error: " + ex.getMessage());
        }
        response.sendRedirect("/secure/ManageProductServlet");
    }
}
