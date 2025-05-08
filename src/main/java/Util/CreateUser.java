package Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

public class CreateUser {
    public static User createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();


        String FirstName = req.getParameter("FirstName");
        String LastName = req.getParameter("LastName");
        String Email = req.getParameter("Email");
        boolean gender = req.getParameter("gender").equalsIgnoreCase("1");
        String password = req.getParameter("password");
        String checkPassword = req.getParameter("Checkpassword");
        String country = req.getParameter("Country");
        String city = req.getParameter("City");
        String phone = req.getParameter("phone");

        if (!validation.isValidEmail(Email)) {
            req.setAttribute("message", "Invalid Email");
            req.getRequestDispatcher("/templates/RegisterServlet.jsp").forward(req, resp);
            return null;
        }
        if (!checkPassword.equals(password)) {
            req.setAttribute("message", "Password is not same");
            req.getRequestDispatcher("/templates/RegisterServlet.jsp").forward(req, resp);
            return null;
        }
        user.setFullName(FirstName.concat(" " + LastName));
        user.setGender(gender);
        user.setEmail(Email);
        user.setPhone(phone);
        user.setUsername(FirstName);
        user.setAddress(country+","+city);
        user.setFavoriteProducts(new ArrayList<>());
        user.setPassword(GeneratePassword.hashPassword(password));
        user.setImage("/static/images/avatars/user.png");
        return user;

    }
}
