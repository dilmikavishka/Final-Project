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
import lk.ijse.model.Customer;
import lk.ijse.model.Machine;
import lk.ijse.model.Tm.MachineTm;
import lk.ijse.repository.CustomerRepo;
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
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        apnMachineManage.getChildren().clear();
        apnMachineManage.getChildren().add(dashboardPane);
    }

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
            boolean isDeleted = MachineRepo.delete(MaId);
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
    }

    private void setCellValueFactory() {
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("MaId"));
        colMachineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String MaId = txtMachineId.getText();
        String name = txtMachineName.getText();
        String description = txtStatus.getText();

        Machine machine = new Machine(MaId,name,description);

        try {
            boolean isSaved = MachineRepo.save(machine);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Machine is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMachine();
    }

    private void loadAllMachine() {
        ObservableList<MachineTm> obList = FXCollections.observableArrayList();
        try {
            List<Machine> machineList = MachineRepo.getAll();
            for(Machine machine : machineList){
                MachineTm tm = new MachineTm(
                        machine.getMaId(),
                        machine.getName(),
                        machine.getDescription()

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
            boolean isUpdate = MachineRepo.update(machine);
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

}
