package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.Material;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialRepo {
    public static boolean save(Material material) throws SQLException {
        String sql = "INSERT INTO material VALUES (?,?,?,?,?,'ACTIVE')";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,material.getId());
        pstm.setObject(2,material.getName());
        pstm.setObject(3,material.getDate());
        pstm.setObject(4,material.getMatQty());
        pstm.setObject(5,material.getSupId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Material material) throws SQLException {
        String sql = "UPDATE material SET name = ?, date = ?, materialQty = ?, supplierId = ? WHERE materialId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,material.getName());
        pstm.setObject(2,material.getDate());
        pstm.setObject(3,material.getMatQty());
        pstm.setObject(4,material.getSupId());
        pstm.setObject(5,material.getId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "UPDATE material SET status = 'DELETE' WHERE materialId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Material searchById(String id) throws SQLException {
        String sql = "SELECT * FROM  material WHERE materialId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String matId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            double qty = Double.parseDouble(resultSet.getString(4));
            String supId = resultSet.getString(5);

            Material material = new Material(matId,name,date,qty,supId);
            return material;
        }
        return null;
    }

    public static List<Material> getAll() throws SQLException {
        String sql = "SELECT * FROM  material WHERE status = 'ACTIVE' ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Material> materialList = new ArrayList<>();

        while(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            double matQty = Double.parseDouble(resultSet.getString(4));
            String supId = resultSet.getString(5);


            Material material = new Material(id,name,date,matQty,supId);
            materialList.add(material);
        }
        return materialList;
    }
}

