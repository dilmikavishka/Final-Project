package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OredrDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {

    public static boolean save(List<OredrDetail> odList) throws SQLException {
        for (OredrDetail od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OredrDetail od) throws SQLException {
        String sql = "INSERT INTO order_details (batchId, orderId, Qty) VALUES (?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getBaId());
        pstm.setString(2, od.getOId());
        pstm.setString(3, String.valueOf(od.getQty()));

        return pstm.executeUpdate() > 0;
    }
    }

