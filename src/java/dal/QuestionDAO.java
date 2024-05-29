/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.QuizQuestion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minh1
 */
public class QuestionDAO extends DBContext{

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<QuizQuestion> getAllQuestion() {
        List<QuizQuestion> list = new ArrayList<>();
        String query = "select * from Questions";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuizQuestion(rs.getInt(1), rs.getString(2), rs.getInt(3)));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public void insertQuestion(String qdetail, String quizid) {
        String query = "INSERT INTO [dbo].[Questions]\n"
                + "           \n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, qdetail);
            ps.setString(2, quizid);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void deleteQuestion(String id) {
        String query = "DELETE FROM [dbo].[Quiz_DB]\n"
                + "      WHERE QuestionID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public int getTotalQuestion() {
        String query = "select count (*) from Questions";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }

        return 0;
    }

    public List<QuizQuestion> pagingQuestion(int index) {
        List<QuizQuestion> list = new ArrayList<>();
        String query = "select * from Questions \n"
                + "order by QuestionID\n"
                + "OFFSET ? Rows fetch next 10 rows only ;";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, (index - 1) * 10);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuizQuestion(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<QuizQuestion> searchByQuestionDetail(String txtSearch) {
        List<QuizQuestion> list = new ArrayList<>();
        String query = "select * from Questions\n"
                + "where QuestionDetail like ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txtSearch + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuizQuestion(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

        } catch (Exception e) {
        }

        return list;
    }
}
