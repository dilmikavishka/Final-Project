package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Employee;
import lk.ijse.model.Payment;
import lk.ijse.model.Tm.EmployeeTm;
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


    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();


    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        anpEmplloyeeManage.getChildren().clear();
        anpEmplloyeeManage.getChildren().add(dashboardPane);
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
            boolean isDeleted = EmployeeRepo.delete(id);
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
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();




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
            boolean isSaved = EmployeeRepo.update(employee);
            if (isSaved){
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

}
