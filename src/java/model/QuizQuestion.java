/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author minh1
 */
public class QuizQuestion {
    private int QuestionID ; 
    private String QuestionDetail ; 
    private int QuizId;

    public QuizQuestion() {
    }

    public QuizQuestion(int QuestionID, String QuestionDetail, int QuizId) {
        this.QuestionID = QuestionID;
        this.QuestionDetail = QuestionDetail;
        this.QuizId = QuizId;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int QuestionID) {
        this.QuestionID = QuestionID;
    }

    public String getQuestionDetail() {
        return QuestionDetail;
    }

    public void setQuestionDetail(String QuestionDetail) {
        this.QuestionDetail = QuestionDetail;
    }

    public int getQuizId() {
        return QuizId;
    }

    public void setQuizId(int QuizId) {
        this.QuizId = QuizId;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" + "QuestionID=" + QuestionID + ", QuestionDetail=" + QuestionDetail + ", QuizId=" + QuizId + '}';
    }
    
}
