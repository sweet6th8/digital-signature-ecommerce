package model;

import java.util.ArrayList;

public class User {
    private int id; // ID người dùng
    private String username; // Tên đăng nhập
    private String password; // Mật khẩu
    private String email; // Địa chỉ email
    private String fullName; // Họ và tên
    private String address; // Địa chỉ
    private String phone; // Số điện thoại
    private boolean gender ;
    private String role ;
    private String image;
    private ArrayList<Product> FavoriteProducts ;

    // Constructor
    public User() {}

    public User(String email, String fullName, String address ,String phone,String image,  String password , int id) {
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.password = password;
        this.id = id;
    }
    // Getter và Setter

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
public String getFirstName() {
        String [] names = getFullName().split(" ",2);
    return names[0];

    }
public String getLastName() {
        String [] names = getFullName().split(" ",2);
        return names[1];
}

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +

                '}';
    }

    public void setFavoriteProducts(ArrayList<Product> arrayList) {
        this.FavoriteProducts = arrayList;

    }
}
