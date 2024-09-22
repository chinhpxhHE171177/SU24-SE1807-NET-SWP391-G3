package model;

import java.sql.Date;

public class Users {

    public static final int ADMIN = 1;
    public static final int USER = 2;


    private String userid;
    private String displayname;
    private boolean gender;
    private Date datework;
    private Date dob;
    private String password;
    private String title; // chức danh
    private String levels;
    private String department;
    private String room;
    private String group;
    private String podline;
    private String status;
    private String location;
    private String phonenumber;
    private String email;
    private int role;
    private String avatar;

    public Users() {
    }

    public Users(String userid, String displayname, boolean gender, Date datework, Date dob, String password, String title, String levels, String department, String room, String group, String podline, String status, String location, String phonenumber, String email, int role, String avatar) {
        this.userid = userid;
        this.displayname = displayname;
        this.gender = gender;
        this.datework = datework;
        this.dob = dob;
        this.password = password;
        this.title = title;
        this.levels = levels;
        this.department = department;
        this.room = room;
        this.group = group;
        this.podline = podline;
        this.status = status;
        this.location = location;
        this.phonenumber = phonenumber;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
    }

    public Users(String userid, String displayname, boolean gender, Date dob, String password, String location, String phonenumber, String email, String avatar) {
        this.userid = userid;
        this.displayname = displayname;
        this.gender = gender;
        this.dob = dob;
        this.password = password;
        this.location = location;
        this.phonenumber = phonenumber;
        this.email = email;
        this.avatar = avatar;
    }

    public Users(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDatework() {
        return datework;
    }

    public void setDatework(Date datework) {
        this.datework = datework;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPodline() {
        return podline;
    }

    public void setPodline(String podline) {
        this.podline = podline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userid='" + userid + '\'' +
                ", displayname='" + displayname + '\'' +
                ", gender=" + gender +
                ", datework=" + datework +
                ", dob=" + dob +
                ", password='" + password + '\'' +
                ", title='" + title + '\'' +
                ", levels='" + levels + '\'' +
                ", department='" + department + '\'' +
                ", room='" + room + '\'' +
                ", group='" + group + '\'' +
                ", podline='" + podline + '\'' +
                ", status='" + status + '\'' +
                ", location='" + location + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
