/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.logging.Logger;


public class User_Quiz_Result {
    private int ResultID;
    private int UserID;
    private int QuizID;
    private float score;
    private String completedAt;

    public User_Quiz_Result() {
    }

    public User_Quiz_Result(int ResultID, int UserID, int QuizID, float score, String completedAt) {
        this.ResultID = ResultID;
        this.UserID = UserID;
        this.QuizID = QuizID;
        this.score = score;
        this.completedAt = completedAt;
    }

    public void setResultID(int ResultID) {
        this.ResultID = ResultID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public void setQuizID(int QuizID) {
        this.QuizID = QuizID;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public int getResultID() {
        return ResultID;
    }

    public int getUserID() {
        return UserID;
    }

    public int getQuizID() {
        return QuizID;
    }

    public float getScore() {
        return score;
    }

    public String getCompletedAt() {
        return completedAt;
    }
    
}
