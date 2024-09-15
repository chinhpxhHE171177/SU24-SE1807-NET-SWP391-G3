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
public class QuestionQuiz {

    private int QuestionId;
    private String QuestionDetail;
    private int QuizId;
    private List<AnswerQuestion> listAnswer;

    public QuestionQuiz() {
    }

    public QuestionQuiz(int QuestionId, String QuestionDetail, int QuizId) {
        this.QuestionId = QuestionId;
        this.QuestionDetail = QuestionDetail;
        this.QuizId = QuizId;
    }

    public QuestionQuiz(int QuestionId, String QuestionDetail, int QuizId, List<AnswerQuestion> listAnswer) {
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

    public List<AnswerQuestion> getListAnswer() {
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

    public void setListAnswer(List<AnswerQuestion> listAnswer) {
        this.listAnswer = listAnswer;
    }
}
