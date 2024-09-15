package model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class Question {

    /**
     * @param args the command line arguments
     */
    private int questionID;
    private String questionDetail;
    private int quizID;
    private int type;
    
    private List<Answer> answers; // Add this property

    public Question() {
    }

    public Question(int questionID, String questionDetail, int quizID) {
        this.questionID = questionID;
        this.questionDetail = questionDetail;
        this.quizID = quizID;
    }

    public Question(int questionID, String questionDetail, int quizID, List<Answer> answers) {
        this.questionID = questionID;
        this.questionDetail = questionDetail;
        this.quizID = quizID;
        this.answers = answers;
    }

    public Question(int questionID, String questionDetail, int quizID, int type) {
        this.questionID = questionID;
        this.questionDetail = questionDetail;
        this.quizID = quizID;
        this.type = type;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Question{" + "questionID=" + questionID + ", questionDetail=" + questionDetail + ", quizID=" + quizID + ", type=" + type + ", answers=" + answers + '}';
    }
}
