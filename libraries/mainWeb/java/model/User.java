package model;

import java.sql.Timestamp;

public class User {
	
	private int id;
	private String code;
	private String name;
	private String gender;
	private String pasword;
	private int roleId;
	private String roleName;
	private int lineId;
	private String lineIds;
	private String lineName;
	private Timestamp createdAt;
	
	public User() {
		super();
	}

	public User(int id, String code, String name, String gender, String pasword, int roleId, String roleName,
			String lineName, Timestamp createdAt) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.gender = gender;
		this.pasword = pasword;
		this.roleId = roleId;
		this.roleName = roleName;
		this.lineName = lineName;
		this.createdAt = createdAt;
	}

	public User(int id, String code, String name, String gender, String pasword, int roleId, Timestamp createdAt) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.gender = gender;
		this.pasword = pasword;
		this.roleId = roleId;
		this.createdAt = createdAt;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getLineIds() {
	    return lineIds;
	}

	public void setLineIds(String lineIds) {
	    this.lineIds = lineIds;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", code=" + code + ", name=" + name + ", gender=" + gender + ", pasword=" + pasword
				+ ", roleId=" + roleId + ", roleName=" + roleName + ", lineId=" + lineId + ", lineName=" + lineName
				+ ", createdAt=" + createdAt + "]";
	}
}
