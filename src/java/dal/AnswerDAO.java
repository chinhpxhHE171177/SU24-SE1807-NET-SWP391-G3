/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Answer;

public class AnswerDAO extends DBContext {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean addNew(Answer t) throws SQLException, ClassNotFoundException {
        try {
            String sql = "INSERT INTO AnswersQuestion (QuestionID, AnswerContent, IsCorrect)"
                    + " VALUES (?, ?, ?)";
            int check = 0;
            con = new DBContext().connection;
            ps = con.prepareStatement(sql);
            ps.setLong(1, t.getQuestionID());
            ps.setString(2, t.getAnswerContent());
            ps.setBoolean(3, t.getIsCorrect());
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
