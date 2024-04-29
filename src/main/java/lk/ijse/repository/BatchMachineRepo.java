package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.BatchMachine;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchMachineRepo {

    public static boolean save(BatchMachine batchMachine) throws SQLException {
        String sql = "INSERT INTO machine_details  VALUES (?,?,?)";

        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1, batchMachine.getMaId());
        pstm.setObject(2, batchMachine.getBaId());
        pstm.setObject(3, batchMachine.getDate());

        return pstm.executeUpdate() > 0;
    }
}
