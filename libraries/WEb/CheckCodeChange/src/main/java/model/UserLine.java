package model;

public class UserLine {
	
	private int userId;
	private int lineId;
	public UserLine() {
		super();
	}
	public UserLine(int userId, int lineId) {
		super();
		this.userId = userId;
		this.lineId = lineId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLineId() {
		return lineId;
	}
	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	
}
