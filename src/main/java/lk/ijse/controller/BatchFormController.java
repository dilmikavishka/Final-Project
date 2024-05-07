package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.*;
import lk.ijse.model.Tm.*;
import lk.ijse.repository.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BatchFormController {

    @FXML
    private AnchorPane anpBatchManage;

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
    private ChoiceBox<String> choiceDescription;

    @FXML
    private TableColumn<?, ?> colAction1;

    @FXML
    private TableColumn<?, ?> colAction2;

    @FXML
    private TableColumn<?, ?> colBatchId;

    @FXML
    private TableColumn<?, ?> colBatchQty;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colHandOverQty;

    @FXML
    private TableColumn<?, ?> colMachineId;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private JFXComboBox<String> comEmployeeId;

    @FXML
    private JFXComboBox<String> comOrderId;

    @FXML
    private TableView<BatchTm> tblBatchManage;

    @FXML
    private TableView<MachineTm> tblMachineDetail;

    @FXML
    private TableView<MatirialTm> tblMaterialDetail;

    @FXML
    private TextField txtBatchColor;

    @FXML
    private TextField txtBatchId;

    @FXML
    private TextField txtBatchQty;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtHandOverQty;

    @FXML
    private TableColumn<?, ?> txtMaterialAction1;

    @FXML
    private TableColumn<?, ?> txtMaterialAction2;

    @FXML
    private TextField txtPrice;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colMatAction;

    public void initialize() {
        loadAllBatch();
        getEmployeeId();
        getOrderId();
        setCellValueFactory();
        loadAllMachineDetail();
        setCellValueFactoryMachine();
        loadAllMaterialDetail();
        setCellValueFactoryMaterial();
        getCurrentBatchIds();


        ObservableList<String> descriptionType = FXCollections.observableArrayList("POLISH","UNPOLISH");
        choiceDescription.setItems(descriptionType);
    }

    private void setCellValueFactoryMaterial() {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatAction.setCellValueFactory(new PropertyValueFactory<>("btnSave"));

    }

    private void loadAllMaterialDetail() {
        ObservableList<MatirialTm> obList = FXCollections.observableArrayList();
        try {
            List<Material> materialList = MaterialRepo.getAll();
            for (Material material : materialList) {
                final Material material1 = material;

                JFXButton btn = new JFXButton("Add");
                btn.setCursor(Cursor.HAND);

                MatirialTm tm = new MatirialTm(
                        material.getId(),
                        material.getName(),
                        material.getDate(),
                        material.getMatQty(),
                        material.getSupId(),
                        btn
                );
                obList.add(tm);
                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Add?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = obList.indexOf(tm);

                        String id = material1.getId();
                        String BaId = txtBatchId.getText();

                        BatchMaterial batchMaterial = new BatchMaterial(id,BaId);

                        try {
                            boolean isSaved = BatchMaterialRepo.save(batchMaterial);
                            if (isSaved){
                                new Alert(Alert.AlertType.CONFIRMATION,"material is saved").show();
                            }
                        } catch (SQLException ex) {
                            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
                        }

                        // Update the existing EmployeeTm object in the obList
                        tm.setBtnSave(new JFXButton("Added"));
                    }
                });
            }

            tblMaterialDetail.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryMachine() {
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("MaId"));
        colAction1.setCellValueFactory(new PropertyValueFactory<>("btnSave"));
    }

    private void loadAllMachineDetail() {
        ObservableList<MachineTm> obList = FXCollections.observableArrayList();
        try {
            List<Machine> machineList = MachineRepo.getAll();
            for (Machine machine : machineList) {
                final Machine machine1 = machine;

                JFXButton btn = new JFXButton("Add");
                btn.setCursor(Cursor.HAND);

                MachineTm tm = new MachineTm(
                        machine.getMaId(),
                        machine.getName(),
                        machine.getDescription(),
                        btn
                );
                obList.add(tm);
                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Add?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = obList.indexOf(tm);

                        String MaId = machine1.getMaId();
                        String BaId = txtBatchId.getText();
                        Date date = Date.valueOf(txtDate.getText());


                        BatchMachine batchMachine = new BatchMachine(MaId,BaId,date);

                        try {
                            boolean isSaved = BatchMachineRepo.save(batchMachine);
                            if (isSaved){
                                new Alert(Alert.AlertType.CONFIRMATION,"machine is saved").show();
                            }
                        } catch (SQLException ex) {
                            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
                        }

                        // Update the existing EmployeeTm object in the obList
                        tm.setBtnSave(new JFXButton("Added"));
                    }
                });
            }

            tblMachineDetail.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("BatchId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Des"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }

    private void getOrderId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> orderList = OrderRepo.getIds();
            for (String id : orderList){
                obList.add(id);
            }
            comOrderId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> employeeList = EmployeeRepo.getIds();
            for (String id : employeeList){
                obList.add(id);
            }
            comEmployeeId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        anpBatchManage.getChildren().clear();
        anpBatchManage.getChildren().add(dashboardPane);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtBatchId.setText("");
        txtBatchColor.setText("");
        choiceDescription.setValue(null);
        txtBatchQty.setText("");
        txtDate.setText("");
        comEmployeeId.setValue(null);
        comOrderId.setValue(null);
        txtPrice.setText("");



    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtBatchId.getText();

        try {
            boolean isDeleted = BatchRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Batch is deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String BatchId = txtBatchId.getText();
        String BatchColor = txtBatchColor.getText();
        String Des = choiceDescription.getValue();

        int qty = Integer.parseInt(txtBatchQty.getText());

        Date date = Date.valueOf(txtDate.getText());
        String EmployeeId = comEmployeeId.getValue();
        String OrderId = comOrderId.getValue();

        double Price = 0;
        if (!txtPrice.getText().isEmpty()) {
            Price = Double.parseDouble(txtPrice.getText());
        }

        Batch batch = new Batch(BatchId,BatchColor,Des,qty,date,EmployeeId,OrderId,Price);

        try {
            boolean isSaved = BatchRepo.save(batch);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Batch is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllBatch();

    }

    private void loadAllBatch() {
        ObservableList<BatchTm> obList = FXCollections.observableArrayList();
        try {
            List<Batch> batchList = BatchRepo.getAll();
            for( Batch batch : batchList){
                BatchTm tm = new BatchTm(
                        batch.getBatchId(),
                        batch.getDes(),
                        batch.getQtyOnHand(),
                        batch.getDate(),
                        batch.getEmployeeId(),
                        batch.getOrderId(),
                        batch.getPrice()
                );
                obList.add(tm);
            }

            tblBatchManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String BatchId = txtBatchId.getText();
        String BatchColor = txtBatchColor.getText();
        String Des = choiceDescription.getValue();

        int qty = 0;
        if (!txtBatchQty.getText().isEmpty()) {
            qty = Integer.parseInt(String.valueOf(qty));
        }

        Date date = Date.valueOf(txtDate.getText());
        String EmployeeId = comEmployeeId.getValue();
        String OrderId = comOrderId.getValue();

        double Price = 0;
        if (!txtPrice.getText().isEmpty()) {
            Price = Double.parseDouble(txtPrice.getText());
        }

        Batch batch = new Batch(BatchId,BatchColor,Des,qty,date,EmployeeId,OrderId,Price);

        try {
            boolean isUpdate = BatchRepo.update(batch);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Batch is Update").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void comEmployeeIdOnAction(ActionEvent event) {
        String id = comEmployeeId.getValue();

        try {
            Batch batch = BatchRepo.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comOrderIdOnAction(ActionEvent event) {
        String id = comOrderId.getValue();

        try {
            Batch batch = BatchRepo.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtBatchId.getText();

        try {
            Batch batch = BatchRepo.searchById(id);
            if (batch != null){
                txtBatchId.setText(batch.getBatchId());
                txtBatchColor.setText(batch.getBatchColor());
                choiceDescription.setValue(batch.getDes());
                txtBatchQty.setText(String.valueOf(batch.getQtyOnHand()));
                txtDate.setText(String.valueOf(batch.getDate()));
                comEmployeeId.setValue(batch.getEmployeeId());
                comOrderId.setValue(batch.getOrderId());
                txtPrice.setText(String.valueOf(batch.getPrice()));

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Batch is not found").show();
        }

    }


    private void getCurrentBatchIds() {
        try {
            String currentId = BatchRepo.getCurrentId();

            String nextBatchId = generateNextBatchId(currentId);
            txtBatchId.setText(nextBatchId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextBatchId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("B");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "B" + String.format("%03d", ++idNum);
        }
        return"B001";
    }



}

