/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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

}
