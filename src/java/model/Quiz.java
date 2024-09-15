/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Quiz {

    private int QuizID;
    private String title;
    private String image;
    private String description;
    private int Level;
    private int CategoryID;
    private int SubjectID;
    private String CreateAt;
    private int createById;
    private int duration;
    private Date createdAt;
    private int Type;
    private Boolean Status;

    private String subjectName;
    private String author;

    public Quiz() {
    }

    public Quiz(int QuizID, String title, String image, String description, int Level, int CategoryID, int SubjectID, String CreateAt, int createById) {
        this.QuizID = QuizID;
        this.title = title;
        this.image = image;
        this.description = description;
        this.Level = Level;
        this.CategoryID = CategoryID;
        this.SubjectID = SubjectID;
        this.CreateAt = CreateAt;
        this.createById = createById;
    }

    public Quiz(int QuizID, String title, String image, String description, int Level, int CategoryID, int SubjectID, int createById, int duration, Date createdAt, int Type, Boolean Status, String subjectName, String author) {
        this.QuizID = QuizID;
        this.title = title;
        this.image = image;
        this.description = description;
        this.Level = Level;
        this.CategoryID = CategoryID;
        this.SubjectID = SubjectID;
        this.createById = createById;
        this.duration = duration;
        this.createdAt = createdAt;
        this.Type = Type;
        this.Status = Status;
        this.subjectName = subjectName;
        this.author = author;
    }

    public Quiz(int QuizID, String title, String description, int Level, int CategoryID, int SubjectID, int createById, int duration, Date createdAt, int Type, Boolean Status, String subjectName, String author) {
        this.QuizID = QuizID;
        this.title = title;
        this.description = description;
        this.Level = Level;
        this.CategoryID = CategoryID;
        this.SubjectID = SubjectID;
        this.createById = createById;
        this.duration = duration;
        this.createdAt = createdAt;
        this.Type = Type;
        this.Status = Status;
        this.subjectName = subjectName;
        this.author = author;
    }

    public void setQuizID(int QuizID) {
        this.QuizID = QuizID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

    public void setSubjectID(int SubjectID) {
        this.SubjectID = SubjectID;
    }

    public void setCreateAt(String CreateAt) {
        this.CreateAt = CreateAt;
    }

    public void setCreateById(int createById) {
        this.createById = createById;
    }

    public int getQuizID() {
        return QuizID;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return Level;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public int getSubjectID() {
        return SubjectID;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public int getCreateById() {
        return createById;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Quiz(String subjectName, String author) {
        this.subjectName = subjectName;
        this.author = author;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean Status) {
        this.Status = Status;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Quiz{" + "QuizID=" + QuizID + ", title=" + title + ", image=" + image + ", description=" + description + ", Level=" + Level + ", CategoryID=" + CategoryID + ", SubjectID=" + SubjectID + ", CreateAt=" + CreateAt + ", createById=" + createById + ", duration=" + duration + ", createdAt=" + createdAt + ", Type=" + Type + ", Status=" + Status + ", subjectName=" + subjectName + ", author=" + author + '}';
    }

}
