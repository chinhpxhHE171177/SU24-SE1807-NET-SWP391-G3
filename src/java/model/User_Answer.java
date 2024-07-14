/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class User_Answer {
    private int UserAnswerID;
    private int UserID;
    private int QuestionID;
    private int AnswerID;
    private String AnsweredAt;

    public User_Answer() {
    }

    public User_Answer(int UserAnswerID, int UserID, int QuestionID, int AnswerID, String AnsweredAt) {
        this.UserAnswerID = UserAnswerID;
        this.UserID = UserID;
        this.QuestionID = QuestionID;
        this.AnswerID = AnswerID;
        this.AnsweredAt = AnsweredAt;
    }

    public int getUserAnswerID() {
        return UserAnswerID;
    }

    public int getUserID() {
        return UserID;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public int getAnswerID() {
        return AnswerID;
    }

    public String getAnsweredAt() {
        return AnsweredAt;
    }

    public void setUserAnswerID(int UserAnswerID) {
        this.UserAnswerID = UserAnswerID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public void setQuestionID(int QuestionID) {
        this.QuestionID = QuestionID;
    }

    public void setAnswerID(int AnswerID) {
        this.AnswerID = AnswerID;
    }

    public void setAnsweredAt(String AnsweredAt) {
        this.AnsweredAt = AnsweredAt;
    }
    
}
