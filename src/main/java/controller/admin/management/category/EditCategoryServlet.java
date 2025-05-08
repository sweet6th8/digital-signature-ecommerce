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


@WebServlet(name = "EditCategoryServlet", urlPatterns = {"/secure/EditCategoryServlet"})
public class EditCategoryServlet extends HttpServlet {
    private DataSource dataSource;
    private static final String MANAGE_CATEGORY_CONTROLLER = "/secure/ManageCategoryServlet";


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
        String cId = request.getParameter("id");

        // Validate ID
        if (cId == null || !cId.matches("\\d+")) {
            request.setAttribute("mess", "Invalid category ID.");
            request.getRequestDispatcher(MANAGE_CATEGORY_CONTROLLER).forward(request, response);
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO dao = new CategoryDAO(conn);

            // Fetch existing category details
            Category category = dao.getCategoryById(Integer.parseInt(cId));
            if (category == null) {
                request.setAttribute("mess", "Category not found.");
                request.getRequestDispatcher(MANAGE_CATEGORY_CONTROLLER).forward(request, response);
                return;
            }

            // Pass category data to JSP for rendering
            request.setAttribute("category", category);
            request.getRequestDispatcher("/templates/admin/admin_categories_edit.jsp").forward(request, response);

        } catch (Exception ex) {
            log("Error retrieving category for edit:", ex);
            request.setAttribute("mess", "An error occurred while fetching category details.");
            request.getRequestDispatcher(MANAGE_CATEGORY_CONTROLLER).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cId = request.getParameter("id");
        String cName = request.getParameter("title");
        String cDescription = request.getParameter("description");

        // Validate input
        if (cId == null || !cId.matches("\\d+") || cName == null || cName.trim().isEmpty()) {
            request.setAttribute("mess", "Please provide valid inputs for updating the category.");
            request.getRequestDispatcher(MANAGE_CATEGORY_CONTROLLER).forward(request, response);
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO dao = new CategoryDAO(conn);

            // Update category
            Category category = new Category(Integer.parseInt(cId), cName, cDescription);
            dao.updateCategory(category);

            request.setAttribute("mess", "Category updated successfully.");
            response.sendRedirect(MANAGE_CATEGORY_CONTROLLER);

        } catch (Exception ex) {
            log("Error editing category:", ex);
            request.setAttribute("mess", "Failed to update category. Please try again.");
            request.getRequestDispatcher(MANAGE_CATEGORY_CONTROLLER).forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Handles updating of categories.";
    }
}