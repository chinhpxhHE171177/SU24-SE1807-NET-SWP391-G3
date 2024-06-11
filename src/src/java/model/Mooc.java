package model;

/**
 *
 * @author Admin
 */
public class Mooc {

    /**
     * @param args the command line arguments
     */
    private int id;
    private String name;
    private int subjectId;

    public Mooc() {
    }

    public Mooc(int id, String name, int subjectId) {
        this.id = id;
        this.name = name;
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Mooc{" + "id=" + id + ", name=" + name + ", subjectId=" + subjectId + '}';
    }
}
