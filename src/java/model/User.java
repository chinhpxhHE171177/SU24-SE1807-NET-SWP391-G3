package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class User {

    /**
     * @param args the command line arguments
     */
//    [UserID] [int] IDENTITY(0,1) NOT NULL,
//	[FullName] [nvarchar](200) NULL,
//	[UserName] [nvarchar](250) NULL,
//	[DateOrBirth] [date] NULL,
//	[Email] [nvarchar](200) NOT NULL,
//	[Password] [nvarchar](250) NOT NULL,
//	[Phone] [varchar](50) NULL,
//	[Address] [nvarchar](300) NULL,
//	[Gender] [bit] NULL,
//	[RoleID] [int] NOT NULL,
//	[Avatar] [nvarchar](400) NULL,
//	[Create_at] [date] NULL
    private int id;
    private String fullname;
    private String username;
    private Date dob;
    private String email;
    private String password;
    private String phone;
    private String address;
    private boolean gender;
    private int roleId;
    private String avatar;
    private Date createAt;
    private String Role;
    private Timestamp CreAt;
    private String Description;

    public User() {
    }

    public User(String Role) {
        this.Role = Role;
    }

    public User(int id, String fullname, String username, String email, String password, boolean gender, int roleId, Date createAt, String Role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.roleId = roleId;
        this.createAt = createAt;
        this.Role = Role;
    }

    

    
    public User(String username, String email, String password, boolean gender, int roleId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.roleId = roleId;
    }

    public User(int id, String fullname, String username, Date dob, String email, String password, String phone, String address, boolean gender, int roleId, String avatar, Date createAt, String Role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.roleId = roleId;
        this.avatar = avatar;
        this.createAt = createAt;
        this.Role = Role;
    }

    public User(int id, String fullname, String username, Date dob, String email, String password, String phone, String address, boolean gender, int roleId, String avatar, Date createAt) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.roleId = roleId;
        this.avatar = avatar;
        this.createAt = createAt;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public Timestamp getCreAt() {
        return CreAt;
    }

    public void setCreAt(Timestamp CreAt) {
        this.CreAt = CreAt;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fullname=" + fullname + ", username=" + username + ", dob=" + dob + ", email=" + email + ", password=" + password + ", phone=" + phone + ", address=" + address + ", gender=" + gender + ", roleId=" + roleId + ", avatar=" + avatar + ", createAt=" + createAt + ", Role=" + Role + ", CreAt=" + CreAt + '}';
    }

}
