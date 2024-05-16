package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.model.*;
import lk.ijse.model.Tm.BatchCostTm;
import lk.ijse.model.Tm.PlaceOrderTm;
import lk.ijse.repository.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lk.ijse.repository.PlaceOrderRepo.calculateNetTotal;

public class BatchCostFormController {

    @FXML
    private AnchorPane anpBatchCost;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnPlaceCost;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colBatchId;

    @FXML
    private TableColumn<?, ?> colColor;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colMatId;

    @FXML
    private TableColumn<?, ?> colMatName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private ComboBox<String> comBatchId;

    @FXML
    private ComboBox<String> comMaterialId;

    @FXML
    private Label lblColor;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblMatName;

    @FXML
    private Label lblMatQtyOnHand;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<BatchCostTm> tblBatchCost;

    @FXML
    private TextField txtQty;
    private ObservableList<BatchCostTm> obList = FXCollections.observableArrayList();


    public void initialize() {
        setDate();
        getBatchIds();
        getMaterialId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batId"));
        colMatId.setCellValueFactory(new PropertyValueFactory<>("matId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

    }

    private void getMaterialId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> matIdList = MaterialRepo.getIds();

            for (String id : matIdList) {
                obList.add(id);
            }
            comMaterialId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getBatchIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> batIdList = BatchRepo.getIds();

            for (String id : batIdList) {
                obList.add(id);
            }
            comBatchId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comBatchIdOnAction(ActionEvent event) {
        String batId = comBatchId.getValue();

        try {
            Batch batch = BatchRepo.searchById(batId);
            if(batch != null) {
                lblColor.setText(batch.getBatchColor());
                lblDescription.setText(batch.getDes());
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String batId = comBatchId.getValue();
        String matId = comMaterialId.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(lblPrice.getText());
        double total = qty * price;

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> types = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (types.orElse(no) == yes) {
                int selectedIndex = tblBatchCost.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblBatchCost.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblBatchCost.getItems().size(); i++) {
            if (matId.equals(colMatId.getCellData(i))) {

                BatchCostTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * price;

                tm.setQty(qty);
                tm.setTotal(total);

                tblBatchCost.refresh();

                calculateNetTotal();
                return;
            }
        }

        BatchCostTm tm = new BatchCostTm(batId,matId,qty,price,total, btnRemove);
        obList.add(tm);

        tblBatchCost.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.QTY,txtQty)) return false;
        return true;
    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblBatchCost.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceCostOnAction(ActionEvent event) {

        List<MaterialDetail> bcList = new ArrayList<>();

        for (int i = 0; i < tblBatchCost.getItems().size(); i++) {
            BatchCostTm tm = obList.get(i);

            MaterialDetail md = new MaterialDetail(

                    tm.getBatId(),
                    tm.getMatId(),
                    tm.getQty()
            );

            bcList.add(md);
        }

        BatchCost bc = new BatchCost(bcList);

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        try {
            boolean isPlaced = BatchCostRepo.placeCost(bc);
            System.out.println("pppp");
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                obList.clear();
                tblBatchCost.setItems(obList);
                calculateNetTotal();

            }else{
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void comMaterialIdOnAction(ActionEvent event) {
        String matId = comMaterialId.getValue();

        try {
            Material material = MaterialRepo.searchById(matId);
            if(material != null) {
                lblMatName.setText(material.getName());
                lblPrice.setText(String.valueOf(material.getPrice()));
                lblMatQtyOnHand.setText(String.valueOf(material.getMatQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }


    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.QTY,txtQty);
    }

}
