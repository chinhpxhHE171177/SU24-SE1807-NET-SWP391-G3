/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author ADMIN
 */

public class QuizHistoryVM {
    private int QuizID;
    private String title;
    private String image;
    private String description;
    private int Level;
    private String Category;
    private String Subject;
    private String CreateAt;
    private int createById;
    private int Score;
    private String CompleteAt;

    public QuizHistoryVM() {
    }

    public QuizHistoryVM(int QuizID, String title, String image, String description, int Level, String Category, String Subject, String CreateAt, int createById, int Score, String CompleteAt) {
        this.QuizID = QuizID;
        this.title = title;
        this.image = image;
        this.description = description;
        this.Level = Level;
        this.Category = Category;
        this.Subject = Subject;
        this.CreateAt = CreateAt;
        this.createById = createById;
        this.Score = Score;
        this.CompleteAt = CompleteAt;
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

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public void setCreateAt(String CreateAt) {
        this.CreateAt = CreateAt;
    }

    public void setCreateById(int createById) {
        this.createById = createById;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public void setCompleteAt(String CompleteAt) {
        this.CompleteAt = CompleteAt;
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

    public String getCategory() {
        return Category;
    }

    public String getSubject() {
        return Subject;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public int getCreateById() {
        return createById;
    }

    public int getScore() {
        return Score;
    }

    public String getCompleteAt() {
        return CompleteAt;
    }
    
}
