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
import java.util.List;

@WebServlet(name = "ManageCategoryServlet", urlPatterns = {"/secure/ManageCategoryServlet"})
public class ManageCategoryServlet extends HttpServlet {
    private DataSource dataSource;
    private static final String MANAGE_CATEGORY_PAGE = "/templates/admin/admin_categories.jsp";

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
        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO dao = new CategoryDAO(conn);
            List<Category> categories = dao.getAllCategories();
            request.setAttribute("LIST_CATEGORIES", categories);
        } catch (Exception ex) {
            log("Error retrieving categories:", ex);
            request.setAttribute("mess", "Unable to load categories. Please try again later.");
        } finally {
            request.getRequestDispatcher(MANAGE_CATEGORY_PAGE).forward(request, response);
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
        return "Manages category retrieval for admin panel.";
    }
}