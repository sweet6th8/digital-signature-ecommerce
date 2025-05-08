package controller.admin.management.user;

import dao.DBConnectionPool;
import dao.ProductDAO;
import dao.UserDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import model.Product;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.UUID;

@WebServlet(name = "EditUserServlet", urlPatterns = {"/secure/EditUserServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold for in-memory storage
        maxFileSize = 1024 * 1024 * 10,      // 10MB maximum file size
        maxRequestSize = 1024 * 1024 * 50    // 50MB maximum request size
)
public class EditUserServlet extends HttpServlet {

    private static final String EDIT_PAGE = "/templates/admin/admin_edit_user.jsp";
    private static final String MANAGE_USER_CONTROLLER = "/secure/ManageUserServlet";
    private DataSource dataSource;


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
            String uid = request.getParameter("uid");

            // Fetch the user by ID
            UserDAO dao = new UserDAO(conn);
            User user = dao.getUserById(Integer.parseInt( uid));

            // Set user to request scope
            request.setAttribute("user", user);
        } catch (Exception ex) {
            log("EditUserServlet Error: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(EDIT_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = dataSource.getConnection()) {
            // Gather user edits from request
            int id = Integer.parseInt(request.getParameter("id"));
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");

            // Populate User object with updated information
            User user = new User();
            user.setId(id);
            user.setFullName(firstname.concat(" ").concat(lastname));
            user.setEmail(email);
            user.setAddress(address);
            user.setPhone(phone);
            System.out.println(user);

            // Handle file upload
            Part filePart = request.getPart("photo"); // Match "name" in input field
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = getServletContext().getRealPath("/") + "uploads";

            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdir();
            }


            String uploadedFilePath = null;
            if (fileName != null && !fileName.isEmpty()) {
                uploadedFilePath = "uploads/" + fileName; // Relative path
                filePart.write(uploadDir + File.separator + fileName);
            }

            // Update the user
            UserDAO dao = new UserDAO(conn);
            boolean result = dao.updateUser(user);

            if (result) {
                request.setAttribute("mess", "User updated successfully!");
            } else {
                request.setAttribute("mess", "Error: Unable to update user.");
            }
        } catch (Exception ex) {
            log("EditUserServlet Error: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(MANAGE_USER_CONTROLLER).forward(request, response);
        }
    }
}