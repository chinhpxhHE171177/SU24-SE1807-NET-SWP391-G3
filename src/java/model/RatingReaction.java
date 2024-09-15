package model;

/**
 *
 * @author Admin
 */
public class RatingReaction {

    /**
     * @param args the command line arguments
     */
//     ReactionID INT IDENTITY(1,1) NOT NULL,
//    UserID INT NOT NULL,
//    RatingID INT NOT NULL,
//    Status BIT NOT NULL, 
    
    private int id;
    private int userId;
    private int ratingId;
    private boolean status;

    public RatingReaction() {
    }

    public RatingReaction(int id, int userId, int ratingId, boolean status) {
        this.id = id;
        this.userId = userId;
        this.ratingId = ratingId;
        this.status = status;
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

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RatingReaction{" + "id=" + id + ", userId=" + userId + ", ratingId=" + ratingId + ", status=" + status + '}';
    }
    
    
}
