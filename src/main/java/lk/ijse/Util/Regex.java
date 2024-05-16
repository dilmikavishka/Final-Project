package lk.ijse.Util;

import com.jfoenix.controls.JFXTextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextFeild textFeild,String text){
        String filed = "";

        switch (textFeild){
            case NAME :
                filed = "^[A-z|\\s]{3,}$";
                break;

            case ID:
                filed =  "^([A-Z][0-9]{3})$";
                break;

            case ADDRESS:
                filed = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;

            case SALARY:
                filed = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;

            case CONTACT:
                filed =  "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";

            case QTY:
                filed = "^\\d+$";
        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextFeild location,javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location,textField.getText())){
            textField.setStyle("-fx-border-color: green;");
            textField.setStyle("-fx-border-color: green;");

            return true;

        }else {
            textField.setStyle("-fx-border-color: red;");
            textField.setStyle("-fx-border-color: red;");

            return false;
        }
    }
}
