package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Material;
import lk.ijse.model.MaterialDetail;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialRepo {
    public static boolean save(Material material) throws SQLException {
        String sql = "INSERT INTO material VALUES (?,?,?,?,?,?,'ACTIVE')";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,material.getId());
        pstm.setObject(2,material.getName());
        pstm.setObject(3,material.getDate());
        pstm.setObject(4,material.getMatQty());
        pstm.setObject(5,material.getSupId());
        pstm.setObject(6,material.getPrice());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Material material) throws SQLException {
        String sql = "UPDATE material SET name = ?, date = ?, materialQty = ?, supplierId = ?,price = ? WHERE materialId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,material.getName());
        pstm.setObject(2,material.getDate());
        pstm.setObject(3,material.getMatQty());
        pstm.setObject(4,material.getSupId());
        pstm.setObject(5,material.getPrice());
        pstm.setObject(6,material.getId());


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
            Date date = Date.valueOf(resultSet.getString(3));
            int qty = Integer.parseInt(resultSet.getString(4));
            String supId = resultSet.getString(5);
            double price = Double.parseDouble(resultSet.getString(6));


            Material material = new Material(matId,name,date,qty,supId,price);
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
            Date date = Date.valueOf(resultSet.getString(3));
            int matQty = Integer.parseInt(resultSet.getString(4));
            String supId = resultSet.getString(5);
            double price = Double.parseDouble(resultSet.getString(6));


            Material material = new Material(id,name,date,matQty,supId,price);
            materialList.add(material);
        }
        return materialList;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT materialId FROM material ORDER BY materialId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String id = resultSet.getString(1);
            return id;
        }
        return null;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT materialId FROM material";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> matList = new ArrayList<>();
        while (resultSet.next()) {
            matList.add(resultSet.getString(1));
        }
        return matList;
    }

    public static boolean update(List<MaterialDetail> bcList) throws SQLException {
        for (MaterialDetail md : bcList) {
            boolean isUpdateQty = updateQty(md.getBatId(),md.getQty(), md.getMatId());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;

    }

    private static boolean updateQty(String batId, int qty, String matId) throws SQLException {
        String sql = "UPDATE material SET materialQty = materialQty - ? WHERE materialId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, matId);



        return pstm.executeUpdate() > 0;
    }
}

