/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author Datnt
 */
public class Question {

    private int QuestionId;
    private String QuestionDetail;
    private int QuizId;
    private List<Answer> listAnswer;

    public Question() {
    }

    public Question(int QuestionId, String QuestionDetail, int QuizId) {
        this.QuestionId = QuestionId;
        this.QuestionDetail = QuestionDetail;
        this.QuizId = QuizId;
    }

    public Question(int QuestionId, String QuestionDetail, int QuizId, List<Answer> listAnswer) {
        this.QuestionId = QuestionId;
        this.QuestionDetail = QuestionDetail;
        this.QuizId = QuizId;
        this.listAnswer = listAnswer;
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

    public List<Answer> getListAnswer() {
        return listAnswer;
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

    public void setListAnswer(List<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }

}
