package model;

public class Process {
    private int processId;
    private String processname;

    public Process() {
    }

    public Process(int processId, String processname) {
        this.processId = processId;
        this.processname = processname;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    @Override
    public String toString() {
        return "Process{" +
                "processId=" + processId +
                ", processname='" + processname + '\'' +
                '}';
    }
}
