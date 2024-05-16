package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.BatchCost;
import lk.ijse.model.MaterialDetail;

import java.sql.Connection;
import java.sql.SQLException;

public class BatchCostRepo {
    public static boolean placeCost(BatchCost bc) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isMaterialUpdate = MaterialRepo.update(bc.getBcList());
            System.out.println("ddd");
            if (isMaterialUpdate) {
                boolean isMaterialDetailSave = MaterialDetailRepo.save(bc.getBcList());
                System.out.println("sss");
                if (isMaterialDetailSave) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
