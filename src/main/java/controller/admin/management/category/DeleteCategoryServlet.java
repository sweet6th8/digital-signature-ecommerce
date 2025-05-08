/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin.management.category;

import dao.CategoryDAO;
import dao.DBConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "DeleteCategoryServlet", urlPatterns = {"/secure/DeleteCategoryServlet"})
public class DeleteCategoryServlet extends HttpServlet {
    private DataSource dataSource;
    private static final String MANAGE_CATEGORY_CONTROLLER = "/secure/ManageCategoryServlet";

    @Override
    public void init() throws ServletException {
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String cid = request.getParameter("cid");

        // Validate input
        if (cid == null || !cid.matches("\\d+")) {
            request.setAttribute("mess", "Invalid category ID.");
            request.getRequestDispatcher(MANAGE_CATEGORY_CONTROLLER).forward(request, response);
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO dao = new CategoryDAO(conn);
            dao.deleteCategory(Integer.parseInt(cid));
            request.setAttribute("mess", "Category deleted successfully.");
        } catch (Exception ex) {
            log("Error deleting category:", ex);
            request.setAttribute("mess", "Failed to delete category. Please try again.");
        } finally {
            response.sendRedirect(request.getContextPath()+MANAGE_CATEGORY_CONTROLLER); // Better UX
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles deletion of categories.";
    }
}