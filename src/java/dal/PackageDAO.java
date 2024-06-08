package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Packages;

/**
 *
 * @author Admin
 */
public class PackageDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public List<Packages> getAllPackage() {
        List<Packages> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Packages";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Packages p = new Packages(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getInt(6),
                        rs.getString(7));
                list.add(p);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public void insert(Packages p) {

        String sql = "INSERT INTO [dbo].[Packages]\n"
                + "           ([package_name]\n"
                + "           ,[description]\n"
                + "           ,[listPrice]\n"
                + "           ,[salePrice]\n"
                + "           ,[duration]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?)";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, p.getName());
            pst.setString(2, p.getDescription());
            pst.setDouble(3, p.getPrice());
            pst.setDouble(4, p.getSalePrice());
            pst.setInt(5, p.getDuration());
            pst.setString(6, p.getStatus());
            pst.executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public Packages deletePackage(int id) {

        String sql = "DELETE Packages WHERE PackageID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
        }
        return null;
    }

    public Packages getPackageById(int id) {

        String sql = "SELECT * FROM Packages WHERE PackageID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new Packages(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getInt(6),
                        rs.getString(7));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public void update(Packages p) {

        String sql = "UPDATE [dbo].[Packages]\n"
                + "   SET [package_name] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[listPrice] = ?\n"
                + "      ,[salePrice] = ?\n"
                + "      ,[duration] = ?\n"
                + "      ,[status] = ?\n"
                + " WHERE PackageID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, p.getName());
            pst.setString(2, p.getDescription());
            pst.setDouble(3, p.getPrice());
            pst.setDouble(4, p.getSalePrice());
            pst.setInt(5, p.getDuration());
            pst.setString(6, p.getStatus());
            pst.setInt(7, p.getId());
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void updateStatus(int id, String status) {

        String sql = "UPDATE Packages SET Packages.status = ? WHERE PackageID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static void main(String args[]) {
        // TODO code application logic here
        PackageDAO pdao = new PackageDAO();
        Packages p = new Packages();

        p = pdao.getPackageById(1);
        System.out.println(p);
        //pdao.deletePackage(5);
//        List<Packages> listp = pdao.getAllPackage();
//        for (Packages packages : listp) {
//            System.out.println(packages);
//        }
    }
}
