package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;
import java.util.List;
import java.util.ArrayList;

public class AccountDAO extends DBContext {

    public Account login(String username, String password) {
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDisplayName(rs.getString("displayName"));
                account.setAddress(rs.getString("address"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                account.setRole(rs.getInt("role"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Account> getAllAccount() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM Account";

        // Try-with-resources to ensure proper closing of resources
        try (Connection connection = getConnection();  // Get connection from DBContext
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Loop through the result set and create Account objects
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("displayName"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("role")
                );
                list.add(account);  // Add the account object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exceptions specifically
        }

        return list;  // Return the list of accounts
    }

    public boolean checkUsername(String username) {
        String sql = "SELECT * FROM Account WHERE username = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDisplayName(rs.getString("displayName"));
                account.setAddress(rs.getString("address"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                account.setRole(rs.getInt("role"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEmail(String email) {
        String sql = "SELECT * FROM Account WHERE email = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDisplayName(rs.getString("displayName"));
                account.setAddress(rs.getString("address"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                account.setRole(rs.getInt("role"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPhone(String phone) {
        String sql = "SELECT * FROM Account WHERE phone = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDisplayName(rs.getString("displayName"));
                account.setAddress(rs.getString("address"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                account.setRole(rs.getInt("role"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        AccountDAO adao = new AccountDAO();
        List<Account> list = adao.getAllAccount();
        for(Account a : list) {
            System.out.println(a);
        }
    }
}

