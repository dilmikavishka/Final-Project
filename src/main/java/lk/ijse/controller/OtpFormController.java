package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OtpFormController {

    @FXML
    private AnchorPane anpOtp1;

    @FXML
    private AnchorPane anpOtp2;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnConfirm;

    @FXML
    private Label lblOtp;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField txt1;

    @FXML
    private TextField txt2;

    @FXML
    private TextField txt3;

    @FXML
    private TextField txt4;

    public void initialize(){
        lblOtp.setText(String.valueOf(SendOtpFormController.OTP));
        Platform.runLater(() -> txt1.requestFocus());
        txt1.setOnKeyReleased(event -> moveFocus(txt1, txt2));
        txt2.setOnKeyReleased(event -> moveFocus(txt2, txt3));
        txt3.setOnKeyReleased(event -> moveFocus(txt3, txt4));
    }

    private void moveFocus(TextField currentTextField, TextField nextTextField) {
        if (!currentTextField.getText().isEmpty()) {
            nextTextField.requestFocus();
        }
    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane loginPane = FXMLLoader.load(this.getClass().getResource("/view/SendOtpForm.fxml"));

        Scene scene = new Scene(loginPane);

        Stage stage = (Stage) anpOtp1.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws IOException {
        String otp1 = txt1.getText();
        String otp2 = txt2.getText();
        String otp3 = txt3.getText();
        String otp4 = txt4.getText();

        String setOtp = otp1 + otp2 + otp3 + otp4;

        if (lblOtp.getText().equalsIgnoreCase(setOtp)){
            lblStatus.setStyle("-fx-fill: green");
            lblStatus.setText("Correct OTP");
            Parent root = FXMLLoader.load(getClass().getResource("/view/EnterPasswordForm.fxml"));
            Scene scene = btnConfirm.getScene();
            root.translateXProperty().set(scene.getWidth());

            AnchorPane parentContainer = (AnchorPane) scene.getRoot();
            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(anpOtp1);
            });
            timeline.play();
        } else {
            lblStatus.setStyle("-fx-fill: red");
            lblStatus.setText("Invalid OTP");
        }
    }

}
