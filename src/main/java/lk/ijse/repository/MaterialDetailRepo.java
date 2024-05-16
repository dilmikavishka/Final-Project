package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Batch;
import lk.ijse.model.MaterialDetail;
import lk.ijse.model.Order;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDetailRepo {
    public static boolean save(List<MaterialDetail> bcList) throws SQLException {
        for (MaterialDetail md : bcList) {
            boolean isSaved = save(md);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(MaterialDetail md) throws SQLException {
        String sql = "INSERT INTO material_details VALUES(?, ?, ?, 'ACTIVE')";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, md.getBatId());
        pstm.setString(2, md.getMatId());
        pstm.setInt(3, md.getQty());

        return pstm.executeUpdate() > 0;
    }

    public static MaterialDetail searchById(String id) throws SQLException {
        String sql = "SELECT * FROM  material_details WHERE batchId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String batId = resultSet.getString(1);
            String matId = resultSet.getString(2);
            int qty = Integer.parseInt(resultSet.getString(3));


            MaterialDetail md = new MaterialDetail(batId,matId,qty);

            return md;
        }
        return null;
    }

    public static List<MaterialDetail> getAll() throws SQLException {
        String sql = "SELECT * FROM material_details WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MaterialDetail> mdList = new ArrayList<>();

        while (resultSet.next()){
            String batId = resultSet.getString(1);
            String matId = resultSet.getString(2);
            int qty = Integer.parseInt(resultSet.getString(3));

            MaterialDetail md = new MaterialDetail(batId,matId,qty);
            mdList.add(md);
        }
        return mdList;
    }

    public static boolean update(MaterialDetail md) throws SQLException {
        String sql = "UPDATE material_details SET materialId = ? , qty = ?  WHERE batchId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,md.getMatId());
        pstm.setObject(2,md.getQty());
        pstm.setObject(3,md.getBatId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String batId) throws SQLException {
        String sql = "UPDATE material_details SET status = 'DELETE' WHERE batchId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, batId);

        return pstm.executeUpdate() > 0;
    }
}
