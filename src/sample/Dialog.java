package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class Dialog {

    public static void infoBox(String infoMessage, String titleBar) {
        infoBox(infoMessage, titleBar, null);
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static boolean confirmBox(String infoMessage, String titleBar){
        return confirmBox(infoMessage, titleBar, null);
    }

    public static boolean confirmBox(String infoMessage, String titleBar, String headerMessage){

        Alert response = new Alert(Alert.AlertType.CONFIRMATION);
        response.setTitle(titleBar);
        response.setHeaderText(headerMessage);
        response.setContentText(infoMessage);


        ButtonType yes = new ButtonType("Так");
        ButtonType no = new ButtonType("Ні");

        response.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = response.showAndWait();


        if (result.get() == yes) return true;

        return false;
    }

    //Діалогове вікно з великим полем вводу
    public static void largeInput(){
        // Create the custom dialog.
        javafx.scene.control.Dialog<String> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Ліцензія");
        dialog.setHeaderText(null);

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 1, 5));

        TextArea inText = new TextArea();
        inText.setWrapText(true);
        inText.setPromptText("Введіть код ліцензування");
        gridPane.add(inText, 0, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the TextArea field by default.
        //Platform.runLater(() -> inText.requestFocus());

        // Get the result to a String when the ok button is clicked.
        dialog.setResultConverter(ok -> {
            if (ok == ButtonType.OK) {
                return inText.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        //result.ifPresent(code -> System.out.println("Result: " + code));
        result.ifPresent(code -> {
            SaveData.licLimit = code;
            try {
                SaveData.serialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}