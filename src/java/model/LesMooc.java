package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class LesMooc {

    /**
     * @param args the command line arguments
     */
    private int id;
    private String name;
    private String videoLink;
    private Date createdAt;
    private int createdBy;
    private int moocId;
    private String status;
    private String content;
    private double duration;
    private String moocName;
    private String subjectName;
    private String author;
    private String image;
    private String categoryName;

    public LesMooc() {
    }

    public LesMooc(String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content) {
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
    }

    public LesMooc(int id, String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content, double duration, String author) {
        this.id = id;
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
        this.duration = duration;
        this.author = author;
    }

    public LesMooc(int id, String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content, double duration) {
        this.id = id;
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
        this.duration = duration;
    }

    public LesMooc(int id, String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content) {
        this.id = id;
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
    }

    public LesMooc(int id, String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content, double duration, String moocName, String author) {
        this.id = id;
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
        this.duration = duration;
        this.moocName = moocName;
        this.author = author;
    }

    public LesMooc(int id, String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content, String moocName, String author, String image, String categoryName) {
        this.id = id;
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
        this.moocName = moocName;
        this.author = author;
        this.image = image;
        this.categoryName = categoryName;
    }

    public LesMooc(int id, String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content, double duration, String moocName, String subjectName, String author) {
        this.id = id;
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
        this.duration = duration;
        this.moocName = moocName;
        this.subjectName = subjectName;
        this.author = author;
    }

    public LesMooc(int id, String name, String videoLink, Date createdAt, int createdBy, int moocId, String status, String content, double duration, String moocName, String subjectName, String author, String image, String categoryName) {
        this.id = id;
        this.name = name;
        this.videoLink = videoLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.moocId = moocId;
        this.status = status;
        this.content = content;
        this.duration = duration;
        this.moocName = moocName;
        this.subjectName = subjectName;
        this.author = author;
        this.image = image;
        this.categoryName = categoryName;
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

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getMoocId() {
        return moocId;
    }

    public void setMoocId(int moocId) {
        this.moocId = moocId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubjectName() {
        return moocName;
    }

    public void setSubjectName(String moocName) {
        this.moocName = moocName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getMoocName() {
        return moocName;
    }

    public void setMoocName(String moocName) {
        this.moocName = moocName;
    }

    @Override
    public String toString() {
        return "LesMooc{" + "id=" + id + ", name=" + name + ", videoLink=" + videoLink + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", moocId=" + moocId + ", status=" + status + ", content=" + content + ", duration=" + duration + ", moocName=" + moocName + ", subjectName=" + subjectName + ", author=" + author + ", image=" + image + ", categoryName=" + categoryName + '}';
    }

}
