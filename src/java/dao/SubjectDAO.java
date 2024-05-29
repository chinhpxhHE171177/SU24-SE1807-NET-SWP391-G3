/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Subject;

/**
 *
 * @author Datnt
 */
public class SubjectDAO extends DBContext {
    
    private Connection con;
    PreparedStatement ps;
    ResultSet rs;
    public SubjectDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Subject> GetAllSubjects() {
        String sql = "SELECT * FROM [Subjects]";
        List<Subject> listSubject = new ArrayList();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subject subjects = new Subject();
                subjects.setSubjectID(rs.getInt("SubjectID"));
                subjects.setSubject_Name(rs.getString("Subject_Name"));         
                subjects.setDescription(rs.getString("Description"));
                subjects.setImage(rs.getString("Image"));
                subjects.setStatus(rs.getInt("Status"));
                subjects.setPackageId(rs.getInt("PackageId"));             
                subjects.setCategoryId(rs.getInt("CategoryId"));
                subjects.setCreateById(rs.getInt("created_by"));        
                subjects.setCreateAt(rs.getDate("Created_at").toString());

                listSubject.add(subjects);
            }
            return listSubject;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
       public String GetById(int Id) {
        String sql = "SELECT * FROM [Subjects] WHERE SubjectID = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Id);
            rs = ps.executeQuery();
            if (rs.next()) {
               return rs.getString("Subject_Name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
