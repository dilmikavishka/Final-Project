package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class MacineDetailsFormController {

    @FXML
    private AnchorPane apnMacchineDetails;

    @FXML
    private TableColumn<?, ?> colBatchId;

    @FXML
    private TableColumn<?, ?> colMachineId;

    @FXML
    private JFXComboBox<?> comBatchId;

    @FXML
    private JFXComboBox<?> comMachineId;

    @FXML
    private TableView<?> tblMachineDetails;

    @FXML
    void comBatchIdOnAction(ActionEvent event) {

    }

    @FXML
    void comMachineIdOnAction(ActionEvent event) {

    }

}
