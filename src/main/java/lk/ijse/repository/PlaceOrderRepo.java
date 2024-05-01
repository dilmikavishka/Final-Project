package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            System.out.println("oooooooooo");
            if (isOrderSaved) {
                boolean isQtyUpdated = BatchRepo.update(po.getOdList());
                System.out.println("bbbbbbbb");
                if (isQtyUpdated){
                    boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                    System.out.println("odddddddddd");
                    if (isOrderDetailSaved){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}