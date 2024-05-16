package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Batch;
import lk.ijse.model.Material;
import lk.ijse.model.MaterialDetail;
import lk.ijse.model.Tm.MaterialDetailTm;
import lk.ijse.repository.BatchRepo;
import lk.ijse.repository.MaterialDetailRepo;
import lk.ijse.repository.MaterialRepo;


import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MaterialDetailFormController {

    @FXML
    private AnchorPane anpMaterialsDetails;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colBatchId;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private JFXComboBox<String> comBatchId;

    @FXML
    private JFXComboBox<String> comMaterialId;

    @FXML
    private TextField txtQty;

    @FXML
    private TableView<MaterialDetailTm> tblMaterialsDetails;

    public void initialize() {
        setCellValueFactory();
        loadAllMaterialDetail();

    }

    private void setCellValueFactory() {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batId"));
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("matId"));
    }

    private void loadAllMaterialDetail() {
        ObservableList<MaterialDetailTm> obList = FXCollections.observableArrayList();
        try {
            List<MaterialDetail> mdList = MaterialDetailRepo.getAll();
            for( MaterialDetail md : mdList){
                MaterialDetailTm tm = new MaterialDetailTm(
                        md.getBatId(),
                        md.getMatId(),
                        md.getQty()

                );
                obList.add(tm);
            }

            tblMaterialsDetails.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void comBatchIdOnAction(ActionEvent event) {
        String id = comBatchId.getValue();

        try {
            Batch batch = BatchRepo.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comMaterialIdOnAction(ActionEvent event) {
        String id = comMaterialId.getValue();

        try {
            Material material = MaterialRepo.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}


/*@FXML
void filterCustomerCon(KeyEvent event) {
    ObservableList<String > filterCon = FXCollections.observableArrayList();
    String enteredText = comCustTel.getEditor().getText();

    try {
        List<String> conList = CustomerRepo.getCon();

        for (String con : conList){
            if (con.contains(enteredText)){
                filterCon.add(con);
            }
        }
        comCustTel.setItems(filterCon);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

comCustTel.setEditable(true);

@FXML
    void comCustTelOnMouseClicked(MouseEvent event) {
        comCustTel.getSelectionModel().clearSelection();
    }meka dann anthimata mouse


*/