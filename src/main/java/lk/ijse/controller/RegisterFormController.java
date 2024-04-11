package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private ImageView anp1;

    @FXML
    private AnchorPane anp2;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();

        try {
            boolean isSaved = saveUser(userId, name, password);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private boolean saveUser(String userId, String name, String password) throws SQLException {
        String sql = "INSERT INTO user VALUES(?, ?, ?)";

       /* DbConnection dbConnection = DbConnection.getInstance();
        Connection connection = dbConnection.getConnection();*/

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        pstm.setObject(2, name);
        pstm.setObject(3, password);

        return pstm.executeUpdate() > 0;
    }

}
