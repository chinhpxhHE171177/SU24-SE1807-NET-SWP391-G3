package model;

public class Permission {
   private int id;
   private int roleId;
   private String pageName;
   private boolean canAccess;
   private boolean canAdd;
   private boolean canEdit;
   private boolean canDelete;
   

   public Permission() {

   }

   public Permission(int id, int roleId, String pageName, boolean canAccess, boolean canAdd, boolean canEdit,
		   boolean canDelete) {
	   this.id = id;
	   this.roleId = roleId;
	   this.pageName = pageName;
	   this.canAccess = canAccess;
	   this.canAdd = canAdd;
	   this.canEdit = canEdit;
	   this.canDelete = canDelete;
   }
   
   public Permission(String pageName, boolean canAccess, boolean canAdd, boolean canEdit, boolean canDelete) {
       this.pageName = pageName;
       this.canAccess = canAccess;
       this.canAdd = canAdd;
       this.canEdit = canEdit;
       this.canDelete = canDelete;
   }


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public int getRoleId() {
	return roleId;
}


public void setRoleId(int roleId) {
	this.roleId = roleId;
}


public String getPageName() {
	return pageName;
}


public void setPageName(String pageName) {
	this.pageName = pageName;
}


public boolean isCanAccess() {
	return canAccess;
}


public void setCanAccess(boolean canAccess) {
	this.canAccess = canAccess;
}


public boolean isCanAdd() {
	return canAdd;
}


public void setCanAdd(boolean canAdd) {
	this.canAdd = canAdd;
}


public boolean isCanEdit() {
	return canEdit;
}


public void setCanEdit(boolean canEdit) {
	this.canEdit = canEdit;
}


public boolean isCanDelete() {
	return canDelete;
}


public void setCanDelete(boolean canDelete) {
	this.canDelete = canDelete;
}
   
@Override
public String toString() {
	return "Permission [id=" + id + ", roleId=" + roleId + ", pageName=" + pageName + ", canAccess=" + canAccess
			+ ", canAdd=" + canAdd + ", canEdit=" + canEdit + ", canDelete=" + canDelete + "]";
}
 

   
}
