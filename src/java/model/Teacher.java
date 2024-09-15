package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Teacher {

    /**
     * @param args the command line arguments
     */
//     ,[UserID]
//      ,[Position]
//      ,[Department]
//      ,[Qualifications]
//      ,[DateWork]
//      ,[About]
    private int id;
    private int userId;
    private String position;
    private String department;
    private String qualification;
    private Date dateWork;
    private String about;
    private String slogan;
    private String fullname;

    public Teacher() {
    }

    public Teacher(int id, int userId, String position, String department, String qualification, Date dateWork, String about) {
        this.id = id;
        this.userId = userId;
        this.position = position;
        this.department = department;
        this.qualification = qualification;
        this.dateWork = dateWork;
        this.about = about;
    }

    public Teacher(int id, int userId, String position, String department, String qualification, Date dateWork, String about, String fullname) {
        this.id = id;
        this.userId = userId;
        this.position = position;
        this.department = department;
        this.qualification = qualification;
        this.dateWork = dateWork;
        this.about = about;
        this.fullname = fullname;
    }

    public Teacher(int id, int userId, String position, String department, String qualification, Date dateWork, String about, String slogan, String fullname) {
        this.id = id;
        this.userId = userId;
        this.position = position;
        this.department = department;
        this.qualification = qualification;
        this.dateWork = dateWork;
        this.about = about;
        this.slogan = slogan;
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Date getDateWork() {
        return dateWork;
    }

    public void setDateWork(Date dateWork) {
        this.dateWork = dateWork;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", userId=" + userId + ", position=" + position + ", department=" + department + ", qualification=" + qualification + ", dateWork=" + dateWork + ", about=" + about + ", fullname=" + fullname + ", slogan=" + slogan + '}';
    }

}
