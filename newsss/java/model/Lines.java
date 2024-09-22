package model;

public class Lines {
    private String linecode;
    private String linename;

    public Lines() {
    }

    public Lines(String linecode, String linename) {
        this.linecode = linecode;
        this.linename = linename;
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    @Override
    public String toString() {
        return "Lines{" +
                "linecode='" + linecode + '\'' +
                ", linename='" + linename + '\'' +
                '}';
    }
}
