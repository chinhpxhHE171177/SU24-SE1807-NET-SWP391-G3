package model;

/**
 *
 * @author Admin
 */
public class Ratings {

    /**
     * @param args the command line 
     */
    
    private int id;
    private int userId;
    private String rating;
    private String content;

    public Ratings() {
    }

    public Ratings(int id, int userId, String rating, String content) {
        this.id = id;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Ratings{" + "id=" + id + ", userId=" + userId + ", rating=" + rating + ", content=" + content + '}';
    }
    
  
}
