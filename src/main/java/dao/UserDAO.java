package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String SQL_INSERT_USER = "INSERT INTO [dbo].[User] (username, password, email, fullName, address, phone, gender,role,Img , Active) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_LOGIN_USER = "SELECT * FROM [dbo].[User] WHERE email = ? AND password = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE [dbo].[User] SET password = ? WHERE email = ?";
    public static final String SQL_GET_PASSWORD = "SELECT password FROM [dbo].[User] WHERE email = ?";
    public static final String SQL_GET_USERID = "SELECT id FROM [dbo].[User] WHERE email = ?";
    public static final String SQL_CHECK_ACTIVE = "SELECT Active FROM [dbo].[User] WHERE email = ?";
    public static final String SQL_UPDATE_ACTIVE ="UPDATE [dbo].[User]  SET ACTIVE = ? WHERE  id = ? ";
    public static final String SQL_COUNT_USER ="SELECT COUNT(id) FROM [dbo].[User]";
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }
    public boolean updateActive (int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ACTIVE);
        ps.setBoolean(1, true);
        ps.setInt(2, id);
        System.out.println(ps.executeUpdate());
        return ps.executeUpdate() > 0;
    }
    public int totalUser () throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_COUNT_USER);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    public boolean checkActive(String email) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_CHECK_ACTIVE);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getBoolean(1);
        }
    return false;
    }
    public int getUserId(String email) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_GET_USERID);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);

        }
        return -1;
    }
    public String getPassword(String email) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_GET_PASSWORD);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString(1);

        }
        return null;
    }
    public boolean updatePassword(String email, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_PASSWORD);
        ps.setString(1, password);
        ps.setString(2, email);
        int result = ps.executeUpdate();
        return result == 1;
    }

    public boolean registerUser(User user) throws SQLException {

        try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT_USER)) {
            CreatePreparedStatement(user, ps);
            int gender = user.isGender() ? 1 : 0;
            String role = "User";
            String Img = "/static/images/avatars/user.png";
            ps.setInt(7, gender);
            ps.setString(8, role);
            ps.setString(9, Img);
            ps.setBoolean(10,false);
            ps.executeUpdate();
            System.out.println("Successfully added user to the database!");
            return true;
        } catch (SQLException e) {
            throw new SQLException("Error registering user", e);
        }
    }

    public User getLogin(String email, String password) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_LOGIN_USER)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving user by LoginServlet", e);
        }
        return null;
    }

    private void CreatePreparedStatement(User user, PreparedStatement ps) throws SQLException {

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getFullName());
        ps.setString(5, user.getAddress());
        ps.setString(6, user.getPhone());

    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("fullName"));
        user.setAddress(rs.getString("address"));
        user.setPhone(rs.getString("phone"));
        user.setGender(rs.getBoolean("gender"));
        user.setRole(rs.getString("role"));
        user.setImage(rs.getString("Img"));
        return user;
    }

    public List<User> getAllUsers() throws SQLException {
        String SQL_GET_ALL_USERS = "SELECT * FROM [dbo].[User]";
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SQL_GET_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving all users", e);
        }

        return users;
    }

    public User getUserById(int userId) throws SQLException {
        String SQL_GET_USER_BY_ID = "SELECT * FROM [dbo].[User] WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL_GET_USER_BY_ID)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving user by ID", e);
        }
        return null;
    }


    public boolean updateUser(User user) throws SQLException {
        String SQL_UPDATE_USER = "UPDATE [dbo].[User] SET  email = ?, fullName = ?, address = ?, phone = ? , img = ?  , password = ?  WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getImage());
            ps.setString(6, user.getPassword());
            ps.setInt(7, user.getId());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new SQLException("Error updating user", e);
        }
    }


    public boolean deleteUser(int userId ) throws SQLException {
        String SQL_DELETE_USER = "DELETE FROM [dbo].[User]" +
                "WHERE id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_USER)) {
            ps.setInt(1, userId);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting user", e);
        }
    }

    public boolean checkEmailExist(String email) throws SQLException {
        String query = "SELECT email FROM [dbo].[User] WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {

                return true;

            }
        }
        return false;
    }
}