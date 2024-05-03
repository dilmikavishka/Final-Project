package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private Label lblButton1;

    @FXML
    private Label lblButton2;

    @FXML
    private Label lblButton3;

    @FXML
    private Label lblButton4;

    @FXML
    private AnchorPane anp1;

    @FXML
    private AnchorPane anp2;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Hyperlink linkRegister;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane anpWelcome;

    private String Button1Text = "A BEAUTIFUL  ";
    private String Button2Text = " DESING FINISH ";
    private String Button3Text = "USING";
    private String Button4Text = "SUPERIOR MATERIALS .";

    private int currentIndex = 0;
    private int currentIndex1 = 0;
    private int currentIndex2 = 0;
    private int currentIndex3 = 0;


    public void initialize() throws IOException {


        // Create a Timeline to update the label text gradually
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (currentIndex <= Button1Text.length()) {
                lblButton1.setText(Button1Text.substring(0, currentIndex));
                currentIndex++;
            }

            if (currentIndex1 <= Button2Text.length()){
                lblButton2.setText(Button2Text.substring(0,currentIndex1));
                currentIndex1++;
            }

            if (currentIndex2 <= Button3Text.length()){
                lblButton3.setText(Button3Text.substring(0,currentIndex2));
                currentIndex2++;
            }

            if (currentIndex3 <= Button4Text.length()){
                lblButton4.setText(Button4Text.substring(0,currentIndex3));
                currentIndex3++;
            }

        }));
        timeline.setCycleCount(Button1Text.length() + 1);
        timeline.setCycleCount(Button2Text.length() + 2);
        timeline.setCycleCount(Button3Text.length() + 3);
        timeline.setCycleCount(Button4Text.length() + 4);
        timeline.play();

    }



    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String userId = txtUserName.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT userId, password FROM user WHERE userId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if(pw.equals(dbPw)) {
                navigateToTheMain();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
    }

    private void navigateToTheMain() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) anp1.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("main Form");
    }

    @FXML
    void linkRegisterOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/RegisterForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();

    }

}
