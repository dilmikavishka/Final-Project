package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM  employee WHERE status = 'ACTIVE' ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();

        while(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            double salary = Double.parseDouble(resultSet.getString(5));


            Employee employee = new Employee(id,name,address,tel,salary);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,'ACTIVE')";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,employee.getId());
        pstm.setObject(2,employee.getName());
        pstm.setObject(3,employee.getAddress());
        pstm.setObject(4,employee.getTel());
        pstm.setObject(5,employee.getSalary());


        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "UPDATE employee SET status = 'DELETE' WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET employeeName = ?,address = ?,employeeCon_Number = ?,employeeSalary = ? WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,employee.getName());
        pstm.setObject(2,employee.getAddress());
        pstm.setObject(3,employee.getTel());
        pstm.setObject(4,employee.getSalary());
        pstm.setObject(5,employee.getId());


        return pstm.executeUpdate() > 0;
    }

    public static Employee searchById(String id) throws SQLException {
        String sql = "SELECT * FROM  employee WHERE employeeId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String EmpId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            double salary = Double.parseDouble(resultSet.getString(5));

            Employee employee = new Employee(EmpId,name,address,tel,salary);
            return employee;
        }
        return null;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT employeeId FROM employee";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<String> employeeList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            employeeList.add(id);
        }
        return employeeList;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT employeeId FROM employee ORDER BY employeeId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String id = resultSet.getString(1);
            return id;
        }
        return null;
    }
}

