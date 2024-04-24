package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.model.Material;
import lk.ijse.model.Supplier;
import lk.ijse.model.Tm.MatirialTm;
import lk.ijse.model.Tm.SupplierTm;
import lk.ijse.repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MaterialFormController {

    @FXML
    private AnchorPane anpMaterialManage;

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
    private JFXComboBox<String> comSupId;
    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colMatid;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableView<MatirialTm> tblMaterials;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtMatId;

    @FXML
    private TextField txtMatQty;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSupId;

    public void initialize() {
        setCellValueFactory();
        loadAllMaterial();
        getSupId();

    }

    private void getSupId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> payList = SupplierRepo.getId();
            for (String id : payList){
                obList.add(id);
            }
            comSupId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colMatid.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colQty.setCellValueFactory(new PropertyValueFactory("matQty"));
        colSupId.setCellValueFactory(new PropertyValueFactory("supId"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        anpMaterialManage.getChildren().clear();
        anpMaterialManage.getChildren().add(dashboardPane);

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtMatId.setText("");
        txtName.setText("");
        txtDate.setText("");
        txtMatQty.setText("");
        comSupId.setValue(null);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtMatId.getText();

        try {
            boolean isDeleted = MaterialRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Material deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtMatId.getText();
        String name = txtName.getText();
        String date = txtDate.getText();
        double qty = Double.parseDouble(txtMatQty.getText());
        String supId = comSupId.getValue();

        Material material = new Material(id,name,date,qty,supId);

        try {
            boolean isSaved = MaterialRepo.save(material);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Material is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMaterial();
    }

    private void loadAllMaterial() {
        ObservableList<MatirialTm> obList = FXCollections.observableArrayList();
        try {
            List<Material> materialList = MaterialRepo.getAll();
            for( Material material : materialList){
                MatirialTm tm = new MatirialTm(
                        material.getId(),
                        material.getName(),
                        material.getDate(),
                        material.getMatQty(),
                        material.getSupId()
                );
                obList.add(tm);
            }

            tblMaterials.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtMatId.getText();
        String name = txtName.getText();
        String date = txtDate.getText();
        double qty = Double.parseDouble(txtMatQty.getText());
        String supId = comSupId.getValue();

        Material material = new Material(id,name,date,qty,supId);

        try {
            boolean isUpdated = MaterialRepo.update(material);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Material is updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void comSupIdOnAction(ActionEvent event) {
        String supId = comSupId.getValue();

        try {
            Material material = MaterialRepo.searchById(supId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtMatId.getText();

        Material material = MaterialRepo.searchById(id);

        if (material != null) {
            txtMatId.setText(material.getId());
            txtName.setText(material.getName());
            txtDate.setText(material.getDate());
            txtMatQty.setText(String.valueOf(material.getMatQty()));
            txtSupId.setText(material.getSupId());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"Machine is not found !").show();
        }
    }

}
