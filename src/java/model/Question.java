/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Datnt
 */
public class Question {
    private int QuestionId;
    private String QuestionDetail;
    private int QuizId;

    public Question() {
    }

    public Question(int QuestionId, String QuestionDetail, int QuizId) {
        this.QuestionId = QuestionId;
        this.QuestionDetail = QuestionDetail;
        this.QuizId = QuizId;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public String getQuestionDetail() {
        return QuestionDetail;
    }

    public int getQuizId() {
        return QuizId;
    }

    public void setQuestionId(int QuestionId) {
        this.QuestionId = QuestionId;
    }

    public void setQuestionDetail(String QuestionDetail) {
        this.QuestionDetail = QuestionDetail;
    }

    public void setQuizId(int QuizId) {
        this.QuizId = QuizId;
    }
    
}
