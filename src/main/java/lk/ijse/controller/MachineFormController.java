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
import lk.ijse.model.Machine;
import lk.ijse.model.Tm.MachineTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.MachineRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MachineFormController {

    @FXML
    private AnchorPane apnMachineManage;

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
    private TableColumn<?, ?> colMachineId;

    @FXML
    private TableColumn<?, ?> colMachineName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<MachineTm> tblMachineManage;

    @FXML
    private TextField txtMachineId;

    @FXML
    private TextField txtMachineName;

    @FXML
    private TextField txtStatus;


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtMachineId.setText("");
        txtMachineName.setText("");
        txtStatus.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String MaId = txtMachineId.getText();

        try {
            boolean isDeleted = false;
            if (isValied()) {
                isDeleted = MachineRepo.delete(MaId);
            }else {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Machine deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMachine();
    }

    public void initialize() {
       setCellValueFactory();
       loadAllMachine();
       getCurrentMachineIds();
    }

    private void setCellValueFactory() {
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("MaId"));
        colMachineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String MaId = txtMachineId.getText();
        String name = txtMachineName.getText();
        String description = txtStatus.getText();

        Machine machine = new Machine(MaId,name,description);

        try {
            boolean isSave = false;
            if (isValied()) {
                isSave = MachineRepo.update(machine);
            }else {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if (isSave){
                new Alert(Alert.AlertType.CONFIRMATION,"Machine is Save!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMachine();
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtMachineId)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtMachineName)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtStatus)) return false;
        return true;
    }

    private void loadAllMachine() {
        ObservableList<MachineTm> obList = FXCollections.observableArrayList();
        try {
            List<Machine> machineList = MachineRepo.getAll();
            for(Machine machine : machineList){
                MachineTm tm = new MachineTm(
                        machine.getMaId(),
                        machine.getName(),
                        machine.getDescription(),
                        new JFXButton()

                );
                obList.add(tm);
            }

            tblMachineManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String MaId = txtMachineId.getText();
        String name = txtMachineName.getText();
        String description = txtStatus.getText();

        Machine machine = new Machine(MaId,name,description);

        try {
            boolean isUpdate = false;
            if (isValied()) {
                isUpdate = MachineRepo.update(machine);
            }else {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Machine is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMachine();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String MaId = txtMachineId.getText();

        Machine machine = MachineRepo.searchById(MaId);

        if (machine != null) {
            txtMachineId.setText(machine.getMaId());
            txtMachineName.setText(machine.getName());
            txtStatus.setText(machine.getDescription());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"Machine is not found !").show();
        }
    }

    private void getCurrentMachineIds() {
        try {
            String currentId = MachineRepo.getCurrentId();

            String nextMachineId = generateNextMachineId(currentId);
            txtMachineId.setText(nextMachineId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextMachineId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("M");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "M" + String.format("%03d", ++idNum);
        }
        return"M001";
    }

    @FXML
    void txtMachineIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtMachineId);
    }

    @FXML
    void txtStatusOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.NAME,txtStatus);
    }

    public void txtMachineNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.NAME,txtMachineName);
    }
}
