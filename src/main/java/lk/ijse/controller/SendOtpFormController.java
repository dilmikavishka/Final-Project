package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Random;

public class SendOtpFormController {

    @FXML
    private AnchorPane anp1;

    @FXML
    private AnchorPane anp2;

    @FXML
    private JFXButton btnSendOpt;

    public static int OTP;

    @FXML
    void btnSendOptOnAction(ActionEvent event) throws IOException, MessagingException {
        int otp = new Random().nextInt(9000) + 1000;
        JavaMail.sendMail("lasithasumudu.ls@gmail.com",otp);
        OTP=otp;
        System.out.println(">>>"+otp);

        Parent root = FXMLLoader.load(getClass().getResource("/view/OtpForm.fxml"));
        Scene scene = btnSendOpt.getScene();
        root.translateXProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(anp1);
        });
        timeline.play();
    }





}
