package model;

/**
 *
 * @author Admin
 */
public class Answer {

    /**
     * @param args the command line arguments
     */
    private int answerID;
    private String answerDetail;
    private int isAnswer;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int questionID;

    public Answer() {
    }

    public Answer(int answerID, String answerDetail, int isAnswer, String option1, String option2, String option3, String option4, int questionID) {
        this.answerID = answerID;
        this.answerDetail = answerDetail;
        this.isAnswer = isAnswer;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.questionID = questionID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getAnswerDetail() {
        return answerDetail;
    }

    public void setAnswerDetail(String answerDetail) {
        this.answerDetail = answerDetail;
    }

    public int getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(int isAnswer) {
        this.isAnswer = isAnswer;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    @Override
    public String toString() {
        return "Answer{" + "answerID=" + answerID + ", answerDetail=" + answerDetail + ", isAnswer=" + isAnswer + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + ", questionID=" + questionID + '}';
    }
   
}
