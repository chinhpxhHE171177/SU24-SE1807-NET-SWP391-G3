package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Subject {

    /**
     * @param args the command line arguments
     */
//    [SubjectID] [int] IDENTITY(0,1) NOT NULL,
//	[Subject_Name] [nvarchar](200) NULL,
//	[Description] [nvarchar](500) NULL,
//	[Image] [nvarchar] (200) NULL,
//	[LessonId] [int] NULL,
//	[PackageId] [int] NULL,
//	[CategoryId] [int] NULL,
//	[UserId] [int] NOT NULL,
//	[RatingID] [int] NULL,
//	[Created_at] [date] NULL
    private int id;
    private String name;
    private String description;
    private String image;
    private boolean status;
    private int packageId;
    private int categoryId;
    private int created_by;
    private Date created_at;
    private String category_name;
    private String package_name;
    private String userName;
    private int numberOfLessons;
    private int totalEnroll; //tong user dk subject
    public Subject() {
    }

    public Subject(int id, String name, String description, String image, boolean status, int packageId, int categoryId, int created_by, Date created_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.status = status;
        this.packageId = packageId;
        this.categoryId = categoryId;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public Subject(String name, String description, String image, boolean status, int packageId, int categoryId, int created_by, Date created_at) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.status = status;
        this.packageId = packageId;
        this.categoryId = categoryId;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public Subject(int id, String name, String description, String image, boolean status, int packageId, int categoryId, int created_by, Date created_at, String category_name, String package_name, String userName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.status = status;
        this.packageId = packageId;
        this.categoryId = categoryId;
        this.created_by = created_by;
        this.created_at = created_at;
        this.category_name = category_name;
        this.package_name = package_name;
        this.userName = userName;
    }

    public Subject(int id, String name, String description, String image, boolean status, int packageId, int categoryId, int created_by, Date created_at, String category_name, String package_name, String userName, int numberOfLessons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.status = status;
        this.packageId = packageId;
        this.categoryId = categoryId;
        this.created_by = created_by;
        this.created_at = created_at;
        this.category_name = category_name;
        this.package_name = package_name;
        this.userName = userName;
        this.numberOfLessons = numberOfLessons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(int numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }

    public int getTotalEnroll() {
        return totalEnroll;
    }

    public void setTotalEnroll(int totalEnroll) {
        this.totalEnroll = totalEnroll;
    }

    @Override
    public String toString() {
        return "Subject{" + "id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + ", status=" + status + ", packageId=" + packageId + ", categoryId=" + categoryId + ", created_by=" + created_by + ", created_at=" + created_at + ", category_name=" + category_name + ", package_name=" + package_name + ", userName=" + userName + ", numberOfLessons=" + numberOfLessons + '}';
    }
}
