/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.lang.System.Logger.Level;
import java.util.List;
import model.Category;
import model.Quiz;

/**
 *
 * @author Datnt
 */
public class QuizHomePageVM {
    private List<Quiz> listQuiz;
    private String SubjectName;
    private int SubjectId;

    public QuizHomePageVM() {
    }

    public QuizHomePageVM(List<Quiz> listQuiz, String SubjectName, int SubjectId) {
        this.listQuiz = listQuiz;
        this.SubjectName = SubjectName;
        this.SubjectId = SubjectId;
    }

    public void setSubjectId(int SubjectId) {
        this.SubjectId = SubjectId;
    }

    public int getSubjectId() {
        return SubjectId;
    }

    public List<Quiz> getListQuiz() {
        return listQuiz;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setListQuiz(List<Quiz> listQuiz) {
        this.listQuiz = listQuiz;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName = SubjectName;
    }

   
}
