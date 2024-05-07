package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineRepo {
    public static List<Machine> getAll() throws SQLException {
        String sql = "SELECT * FROM Machine WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Machine> machineList = new ArrayList<>();

        while (resultSet.next()){
            String MaId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);

            Machine machine = new Machine(MaId,name,description);
            machineList.add(machine);
        }
        return machineList;
    }

    public static boolean save(Machine machine) throws SQLException {
        String sql = "INSERT INTO Machine VALUES(?,?,?,'ACTIVE')";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,machine.getMaId());
        pstm.setObject(2,machine.getName());
        pstm.setObject(3,machine.getDescription());


        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String maId) throws SQLException {
        String sql = "UPDATE Machine SET status = 'DELETE' WHERE machineId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, maId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Machine machine) throws SQLException {
        String sql = "UPDATE Machine SET machineName = ?,description = ?   WHERE machineId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,machine.getName());
        pstm.setObject(2,machine.getDescription());
        pstm.setObject(3,machine.getMaId());


        return pstm.executeUpdate() > 0;
    }

    public static Machine searchById(String maId) throws SQLException {
        String sql = "SELECT * FROM Machine WHERE machineId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, maId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String MaId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);

            Machine machine = new Machine(MaId,name,description);

            return machine;
        }

        return null;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT machineId FROM Machine ORDER BY machineId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String MaId = resultSet.getString(1);
            return MaId;
        }
        return null;
    }
}
