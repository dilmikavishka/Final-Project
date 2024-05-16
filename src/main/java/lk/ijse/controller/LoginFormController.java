package lk.ijse.controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
//import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import javafx.util.Duration;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
  /*  @FXML
    private Label lblButton1;

    @FXML
    private Label lblButton2;

    @FXML
    private Label lblButton3;

    @FXML
    private Label lblButton4;*/

    @FXML
    private AnchorPane anpMain;

    @FXML
    private AnchorPane anp1;

    @FXML
    private AnchorPane anp2;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Hyperlink linkRegister;
    public AnchorPane rootNode;

    @FXML
    private Hyperlink linkFrogetPassword;


    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;


    @FXML
    void linkFrogetPasswordOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/SendOtpForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();
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
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }
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

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtUserName)) return false;
        if (!Regex.setTextColor(TextFeild.QTY,txtPassword)) return false;
        return true;
    }

    private void navigateToTheMain() throws IOException {
     AnchorPane rootNote = FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"));
     Scene scene = new Scene(rootNote);
     Stage stage = (Stage) this.anp1.getScene().getWindow();
     stage.setScene(scene);
     stage.centerOnScreen();
     stage.setTitle("Main form");
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

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtUserName);
    }

    @FXML
    void txtUserNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.QTY,txtPassword);
    }


}
