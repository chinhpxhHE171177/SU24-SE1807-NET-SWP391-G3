package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Duc
 */
public class Registrations {

    /**
     * @param args the command line arguments
     */
    private int registerID;
    private int userID;
    private int subjectID;
    private int packageID;
    private BigDecimal totalCost;
    private int status;
    private Date validFrom;
    private Date validTo;
    private Timestamp createdAt;
    private String FullName;
    private String PackageName;
    private String SubjectName;
    private float listPrice;

    @Override
    public String toString() {
        return "Registrations{" + "registerID=" + registerID + ", userID=" + userID + ", subjectID=" + subjectID + ", packageID=" + packageID + ", totalCost=" + totalCost + ", status=" + status + ", validFrom=" + validFrom + ", validTo=" + validTo + ", createdAt=" + createdAt + ", FullName=" + FullName + ", PackageName=" + PackageName + ", SubjectName=" + SubjectName + ", listPrice=" + listPrice + '}';
    }

    public Registrations(String SubjectName) {
        this.SubjectName = SubjectName;
    }

    public Registrations(int userID, int status, Date validFrom, Date validTo, Timestamp createdAt, String FullName, String PackageName, String SubjectName, float listPrice) {
        this.userID = userID;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.FullName = FullName;
        this.PackageName = PackageName;
        this.SubjectName = SubjectName;
        this.listPrice = listPrice;
    }
    

    public Registrations(int userID, BigDecimal totalCost, int status, Date validFrom, Date validTo, Timestamp createdAt, String FullName, String PackageName, String SubjectName) {
        this.userID = userID;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.FullName = FullName;
        this.PackageName = PackageName;
        this.SubjectName = SubjectName;
    }
    
    public Registrations(int registerID, int userID, int subjectID, int packageID, BigDecimal totalCost, int status, Date validFrom, Date validTo, Timestamp createdAt, String FullName, String PackageName, String SubjectName, float listPrice) {
        this.registerID = registerID;
        this.userID = userID;
        this.subjectID = subjectID;
        this.packageID = packageID;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.FullName = FullName;
        this.PackageName = PackageName;
        this.SubjectName = SubjectName;
        this.listPrice = listPrice;
    }

    public Registrations(int registerID, int userID, int subjectID, int packageID, BigDecimal totalCost, int status, Date validFrom, Date validTo, Timestamp createdAt) {
        this.registerID = registerID;
        this.userID = userID;
        this.subjectID = subjectID;
        this.packageID = packageID;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName = SubjectName;
    }

    public float getListPrice() {
        return listPrice;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }

    public Registrations() {
    }

    public int getRegisterID() {
        return registerID;
    }

    public void setRegisterID(int registerID) {
        this.registerID = registerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

}
