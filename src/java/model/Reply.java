package model;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Reply {

    /**
     * @param args the command line arguments
     */
//    [ReplyID] INT IDENTITY(1,1) NOT NULL,
//    [RatingID] INT NOT NULL,
//    [UserID] INT NOT NULL,
//    [Content] NVARCHAR(500) NULL,
//    [DateReply] DATETIME NULL,
//    [Status] BIT NULL,
//    [Like] VARCHAR(100) NULL,
    
    private int id;
    private int ratingId;
    private int userId;
    private String comment;
    private Timestamp dateReply;
    private boolean status;
    private int like;
    private String fullname;
    private String avatar;

    public Reply() {
    }

    public Reply(int id, int ratingId, int userId, String comment, Timestamp dateReply, boolean status, int like) {
        this.id = id;
        this.ratingId = ratingId;
        this.userId = userId;
        this.comment = comment;
        this.dateReply = dateReply;
        this.status = status;
        this.like = like;
    }

    public Reply(int id, int ratingId, int userId, String comment, Timestamp dateReply, boolean status, int like, String fullname, String avatar) {
        this.id = id;
        this.ratingId = ratingId;
        this.userId = userId;
        this.comment = comment;
        this.dateReply = dateReply;
        this.status = status;
        this.like = like;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDateReply() {
        return dateReply;
    }

    public void setDateReply(Timestamp dateReply) {
        this.dateReply = dateReply;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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

    @Override
    public String toString() {
        return "Reply{" + "id=" + id + ", ratingId=" + ratingId + ", userId=" + userId + ", comment=" + comment + ", dateReply=" + dateReply + ", status=" + status + ", like=" + like + ", fullname=" + fullname + ", avatar=" + avatar + '}';
    }
}
