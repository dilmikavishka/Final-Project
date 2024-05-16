package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;
import lk.ijse.model.Payment;
import lk.ijse.model.Tm.EmployeeTm;
import lk.ijse.repository.BatchRepo;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.PaymentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private AnchorPane anpEmplloyeeManage;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<EmployeeTm> tblEmployeeManage;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtTel;

    @FXML
    private JFXButton btnEmpList;


    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
        getCurrentEmployeeIds();

    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtEmpName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
        txtSalary.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();

        try {
            boolean isDeleted = false;
            if (isValied()) {
                isDeleted = EmployeeRepo.delete(id);
            }else {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee is deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String id = txtEmployeeId.getText();
        String name = txtEmpName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = 0;
        if (!txtSalary.getText().isEmpty()) {
            salary = Double.parseDouble(txtSalary.getText());
        }

        Employee employee = new Employee(id,name,address,tel,salary);

        try {
            boolean isSave = false;
            if (isValied()) {
                isSave = EmployeeRepo.save(employee);
            }else {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if (isSave){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee is Save").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();


    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.NAME,txtEmpName)) return false;
        if (!Regex.setTextColor(TextFeild.ID,txtEmployeeId)) return false;
        if (!Regex.setTextColor(TextFeild.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(TextFeild.CONTACT,txtTel)) return false;
        return true;
    }

    private void loadAllEmployee() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList){
                EmployeeTm tm = new EmployeeTm(
                      employee.getId(),
                      employee.getName(),
                      employee.getAddress(),
                      employee.getTel(),
                      employee.getSalary()

                );
                obList.add(tm);
            }
            tblEmployeeManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtEmpName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = 0;
        if (!txtSalary.getText().isEmpty()) {
            salary = Double.parseDouble(txtSalary.getText());
        }

        Employee employee = new Employee(id,name,address,tel,salary);

        try {
            boolean isUpdate = false;
            if (isValied()) {
                isUpdate = EmployeeRepo.update(employee);
            }else {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee is update").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();

        try {
            Employee employee = EmployeeRepo.searchById(id);
            if (employee != null){
                txtEmployeeId.setText(employee.getId());
                txtEmpName.setText(employee.getName());
                txtAddress.setText(employee.getAddress());
                txtTel.setText(employee.getTel());
                txtSalary.setText(String.valueOf(employee.getSalary()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"payment is not found").show();
        }

    }

    private void getCurrentEmployeeIds() {
        try {
            String currentId = EmployeeRepo.getCurrentId();

            String nextEmpId = generateNextEmployeeId(currentId);
            txtEmployeeId.setText(nextEmpId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextEmployeeId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("E");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "E" + String.format("%03d", ++idNum);
        }
        return"E001";
    }


    @FXML
    void btnEmpListOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmployeeIdOnActionReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtEmployeeId);
    }

    public void txtEmpNameOnKeyReleased(KeyEvent keyEvent) {
      Regex.setTextColor(TextFeild.NAME,txtEmpName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.ADDRESS,txtAddress);
    }

    public void txtTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.CONTACT,txtTel);
    }

    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.SALARY,txtSalary);
    }
}
