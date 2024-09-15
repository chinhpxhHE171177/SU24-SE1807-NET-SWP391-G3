package model;

import java.util.List;

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
    private boolean status;
    private String subjectName;
    private int numOfLes;

    private List<LesMooc> lessons;

    public Mooc() {
    }

    public Mooc(int id, String name, int subjectId) {
        this.id = id;
        this.name = name;
        this.subjectId = subjectId;
    }

    public Mooc(int id, String name, int subjectId, boolean status) {
        this.id = id;
        this.name = name;
        this.subjectId = subjectId;
        this.status = status;
    }

    public Mooc(int id, String name, int subjectId, boolean status, String subjectName, int numOfLes) {
        this.id = id;
        this.name = name;
        this.subjectId = subjectId;
        this.status = status;
        this.subjectName = subjectName;
        this.numOfLes = numOfLes;
    }

    public Mooc(String name, int subjectId, boolean status) {
        this.name = name;
        this.subjectId = subjectId;
        this.status = status;
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

    public List<LesMooc> getLessons() {
        return lessons;
    }

    public void setLessons(List<LesMooc> lessons) {
        this.lessons = lessons;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNumOfLes() {
        return numOfLes;
    }

    public void setNumOfLes(int numOfLes) {
        this.numOfLes = numOfLes;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Mooc{" + "id=" + id + ", name=" + name + ", subjectId=" + subjectId + ", status=" + status + ", subjectName=" + subjectName + ", numOfLes=" + numOfLes + ", lessons=" + lessons + '}';
    }
}
