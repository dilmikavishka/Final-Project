package lk.ijse.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFielValid(TextField textField, String text) {
        String filed = "";

        switch (textField) {
            case NAME:
                filed = "^[A-z|\\s]{3,}$";
                break;

            case ADDRESS:
                filed = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;

            case SALARY:
                filed = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;

            case CONTACT:
                filed = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null) {
            if (text.trim().isEmpty()) {
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return true;
    }

    public static boolean setTextColor(TextField location,javafx.scene.control.TextField textField){
        if (Regex.isTextFielValid(location,textField.getText())){
            textField.setStyle("-fx-background-color: Green");
            textField.setStyle("-fx-background-color: Green");
            return true;
        }else {
            textField.setStyle("-fx-background-color: Red");
            textField.setStyle("-fx-background-color: Red");
            return false;
        }
    }
}
