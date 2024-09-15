package model;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class UserQuizChoices {

    /**
     * @param args the command line arguments
     */
//     [choiceId] INT IDENTITY(1,1) NOT NULL,
//    [userId] INT NULL,
//    [QuizID] INT NULL,
//    [questionId] INT NULL,
//    [selectedAnswerId] INT NULL,
//    [isCorrect] bit NULL,
//    [startTime] DATETIME,
//    [endTime] DATETIME
    private int choiceId;
    private Integer userId;
    private Integer quizId;
    private Integer questionId;
    private Integer selectedAnswerId;
    private Boolean isCorrect;
    private java.sql.Timestamp startTime;
    private java.sql.Timestamp endTime;
    
    private String questionDetail;
    private String answerDetail;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    
    private int resultId;

    public UserQuizChoices() {
    }

    public UserQuizChoices(int choiceId, Integer userId, Integer quizId, Integer questionId, Integer selectedAnswerId, Boolean isCorrect, Timestamp startTime, Timestamp endTime) {
        this.choiceId = choiceId;
        this.userId = userId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.selectedAnswerId = selectedAnswerId;
        this.isCorrect = isCorrect;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UserQuizChoices(int choiceId, Integer userId, Integer quizId, Integer questionId, Integer selectedAnswerId, Boolean isCorrect, Timestamp startTime, Timestamp endTime, String questionDetail, String answerDetail, String option1, String option2, String option3, String option4) {
        this.choiceId = choiceId;
        this.userId = userId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.selectedAnswerId = selectedAnswerId;
        this.isCorrect = isCorrect;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questionDetail = questionDetail;
        this.answerDetail = answerDetail;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public UserQuizChoices(int choiceId, Integer userId, Integer quizId, Integer questionId, Integer selectedAnswerId, Boolean isCorrect, Timestamp startTime, Timestamp endTime, int resultId) {
        this.choiceId = choiceId;
        this.userId = userId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.selectedAnswerId = selectedAnswerId;
        this.isCorrect = isCorrect;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resultId = resultId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getSelectedAnswerId() {
        return selectedAnswerId;
    }

    public void setSelectedAnswerId(Integer selectedAnswerId) {
        this.selectedAnswerId = selectedAnswerId;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }

    public String getAnswerDetail() {
        return answerDetail;
    }

    public void setAnswerDetail(String answerDetail) {
        this.answerDetail = answerDetail;
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

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    @Override
    public String toString() {
        return "UserQuizChoices{" + "choiceId=" + choiceId + ", userId=" + userId + ", quizId=" + quizId + ", questionId=" + questionId + ", selectedAnswerId=" + selectedAnswerId + ", isCorrect=" + isCorrect + ", startTime=" + startTime + ", endTime=" + endTime + ", questionDetail=" + questionDetail + ", answerDetail=" + answerDetail + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + ", resultId=" + resultId + '}';
    }
    
}
