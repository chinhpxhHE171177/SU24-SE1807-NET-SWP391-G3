package model;

import java.sql.Timestamp;

public class Code {
	
	private int id;
	private String code;
	private String name;
	private int lineId;
	private String lineName;
	private String image;
	private Timestamp createdDate;
	public Code() {
		super();
	}
	public Code(int id, String code, String name, int lineId, String lineName, String image, Timestamp createdDate) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.lineId = lineId;
		this.lineName = lineName;
		this.image = image;
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLineId() {
		return lineId;
	}
	public void setLineId(int lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "Code [id=" + id + ", code=" + code + ", name=" + name + ", lineId=" + lineId + ", lineName=" + lineName
				+ ", image=" + image + ", createdDate=" + createdDate + "]";
	}
	
	

}
