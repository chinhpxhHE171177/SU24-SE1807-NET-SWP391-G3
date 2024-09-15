package model;

import java.sql.Timestamp;
import java.util.List;

public class Ratings {

    public int ratingId;
    private int userId;
    private int lessonId;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private int like;
    private boolean isReply;
    private boolean status;
    private String fullname;
    private String avatar;
    private List<Reply> replies; // Thêm danh sách các reply

    public Ratings() {
    }

    public Ratings(int ratingId, int userId, int lessonId, int rating, String comment, Timestamp createdAt, int like, boolean isReply, boolean status) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.lessonId = lessonId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.like = like;
        this.isReply = isReply;
        this.status = status;
    }

    public Ratings(int ratingId, int userId, int lessonId, int rating, String comment, Timestamp createdAt, int like, boolean isReply, boolean status, String fullname, String avatar) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.lessonId = lessonId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.like = like;
        this.isReply = isReply;
        this.status = status;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isIsReply() {
        return isReply;
    }

    public void setIsReply(boolean isReply) {
        this.isReply = isReply;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Ratings{" + "ratingId=" + ratingId + ", userId=" + userId + ", lessonId=" + lessonId + ", rating=" + rating + ", comment=" + comment + ", createdAt=" + createdAt + ", like=" + like + ", isReply=" + isReply + ", status=" + status + ", fullname=" + fullname + ", avatar=" + avatar + ", replies=" + replies + '}';
    }
}
