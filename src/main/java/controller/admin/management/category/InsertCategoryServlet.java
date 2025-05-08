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
import model.Category;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "InsertCategoryServlet", urlPatterns = {"/secure/InsertCategoryServlet"})
public class InsertCategoryServlet extends HttpServlet {
    private DataSource dataSource;
    private static final String MANAGE_CATEGORY_CONTROLLER = "/ManageCategoryServlet";

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
        String cateTitle = request.getParameter("title");
        String cateDescription = request.getParameter("description");

        // Validate input
        if (cateTitle == null || cateTitle.trim().isEmpty() || cateDescription == null || cateDescription.trim().isEmpty()) {
            request.setAttribute("mess", "Please provide valid inputs for title and description.");
            request.getRequestDispatcher("/templates/admin/admin_categories_insert.jsp").forward(request, response); // Return to form
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO dao = new CategoryDAO(conn);
            Category category = new Category(); // No ID required
            category.setTitle(cateTitle);
            category.setDescription(cateDescription);

            dao.addCategory(category);
            request.setAttribute("mess", "Category added successfully!");
        } catch (Exception ex) {
            log("InsertCategoryServlet error:", ex);
            request.setAttribute("mess", "Failed to insert category. Please try again.");
        } finally {
            request.getRequestDispatcher("templates/admin/admin_categories_insert.jsp").forward(request, response); // Stay on the page
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
        return "Handles creation of new categories for admin.";
    }
}