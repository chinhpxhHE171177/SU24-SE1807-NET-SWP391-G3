package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class Progress {

    /**
     * @param args the command line arguments
     */
//   [ProgressID] INT IDENTITY(1,1) NOT NULL,
//    [UserID] INT NULL, 
//    [LesMoocID] INT NULL,
//    [SubjectID] INT NULL,
//    [State] INT CHECK ([State] IN (1, 2, 3)) NULL, --'Not Started', 'In Progress', 'Completed'
//    [CreatedAt] DATE NULL DEFAULT GETDATE(),
    private int id;
    private int userId;
    private int quizId;
    private int subjectId;
    private int state;
    private Date completedAt;

    public Progress() {
    }

    public Progress(int id, int userId, int quizId, int subjectId, int state, Date completedAt) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.subjectId = subjectId;
        this.state = state;
        this.completedAt = completedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return "Progress{" + "id=" + id + ", userId=" + userId + ", quizId=" + quizId + ", subjectId=" + subjectId + ", state=" + state + ", completedAt=" + completedAt + '}';
    }

}
