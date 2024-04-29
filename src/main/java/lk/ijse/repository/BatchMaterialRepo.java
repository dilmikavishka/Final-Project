package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.BatchMaterial;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchMaterialRepo {
    public static boolean save(BatchMaterial batchMaterial) throws SQLException {
        String sql = "INSERT INTO material_details  VALUES (?,?)";

        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1, batchMaterial.getMaId());
        pstm.setObject(2, batchMaterial.getBaId());


        return pstm.executeUpdate() > 0;
    }
}
