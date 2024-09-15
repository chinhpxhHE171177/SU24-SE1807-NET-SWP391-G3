package model;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserQuizResult {

    /**
     * @param args the command line arguments
     */
    private int resultID;
    private int userID;
    private int quizID;
    private double score;
    private Date completedAt;
    private boolean status;

    private String title;
    private String description;
    private int level;
    private int createdBy;
    private Date createdAt;
    private int duration;

    public UserQuizResult() {
    }

    public UserQuizResult(int resultID, int userID, int quizID, double score, Date completedAt) {
        this.resultID = resultID;
        this.userID = userID;
        this.quizID = quizID;
        this.score = score;
        this.completedAt = completedAt;
    }

    public UserQuizResult(int resultID, int userID, int quizID, double score, Date completedAt, boolean status) {
        this.resultID = resultID;
        this.userID = userID;
        this.quizID = quizID;
        this.score = score;
        this.completedAt = completedAt;
        this.status = status;
    }

    public UserQuizResult(int userID, int quizID, double score, Date completedAt, boolean status) {
        this.userID = userID;
        this.quizID = quizID;
        this.score = score;
        this.completedAt = completedAt;
        this.status = status;
    }

    public UserQuizResult(int resultID, int userID, int quizID, double score, Date completedAt, boolean status, String title, String description, int level, int createdBy, Date createdAt, int duration) {
        this.resultID = resultID;
        this.userID = userID;
        this.quizID = quizID;
        this.score = score;
        this.completedAt = completedAt;
        this.status = status;
        this.title = title;
        this.description = description;
        this.level = level;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.duration = duration;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "UserQuizResult{" + "resultID=" + resultID + ", userID=" + userID + ", quizID=" + quizID + ", score=" + score + ", completedAt=" + completedAt + ", status=" + status + ", title=" + title + ", description=" + description + ", level=" + level + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", duration=" + duration + '}';
    }

   
}
