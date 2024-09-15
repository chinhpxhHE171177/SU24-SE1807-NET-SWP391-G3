/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class AnswerQuestion {
    private int AnswerID;
    private boolean IsCorrect; 
    private String AnswerContent;  
    private int QuestionID;

    public AnswerQuestion() {
    }

    public AnswerQuestion(int AnswerID, boolean IsCorrect, String AnswerContent, int QuestionID) {
        this.AnswerID = AnswerID;
        this.IsCorrect = IsCorrect;
        this.AnswerContent = AnswerContent;
        this.QuestionID = QuestionID;
    }

    public int getAnswerID() {
        return AnswerID;
    }

    public boolean getIsCorrect() {
        return IsCorrect;
    }

    public String getAnswerContent() {
        return AnswerContent;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setAnswerID(int AnswerID) {
        this.AnswerID = AnswerID;
    }

    public void setIsCorrect(boolean IsCorrect) {
        this.IsCorrect = IsCorrect;
    }

    public void setAnswerContent(String AnswerContent) {
        this.AnswerContent = AnswerContent;
    }

    public void setQuestionID(int QuestionID) {
        this.QuestionID = QuestionID;
    }

  
  
}
