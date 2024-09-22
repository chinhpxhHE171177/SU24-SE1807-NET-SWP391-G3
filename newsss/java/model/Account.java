package model;

public class Account {

    public static final int ADMIN = 1;
    public static final int USER = 2;

    private int id;
    private String username;
    private String password;
    private String displayName;
    private String address;
    private String email;
    private String phone;
    private int role;

    public Account() {
    }

    public Account(int id, String username, String password, String displayName, String address, String email, String phone, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Account(String username, String password, String displayName, String address, String email, String phone, int role) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String displayName, int role, int id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
    }

    public Account(String username, String password, String displayName, String email, String phone) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
    }

    public Account(String username, String password, String displayName, String address, String email, String phone) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.address = address;
        this.email = email;
        this.phone = phone;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", username=" + username + ", password=" + password + ", displayName=" + displayName + ", address=" + address + ", email=" + email + ", phone=" + phone + ", role=" + role + '}';
    }

}
