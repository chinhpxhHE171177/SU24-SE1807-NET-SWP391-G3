/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;


public class QuizDetailVM {
    private int QuizId;
    private String Title;
    private List<QuestionQuiz> listQuestion;

    public QuizDetailVM() {
    }

    public QuizDetailVM(int QuizId, String Title, List<QuestionQuiz> listQuestion) {
        this.QuizId = QuizId;
        this.Title = Title;
        this.listQuestion = listQuestion;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

 
    public int getQuizId() {
        return QuizId;
    }

    public List<QuestionQuiz> getListQuestion() {
        return listQuestion;
    }

    public void setQuizId(int QuizId) {
        this.QuizId = QuizId;
    }

    public void setListQuestion(List<QuestionQuiz> listQuestion) {
        this.listQuestion = listQuestion;
    }
    
    
}
