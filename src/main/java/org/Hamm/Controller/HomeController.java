package org.Hamm.Controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.Hamm.App;

public class HomeController {

    @FXML
    private Button secondaryButton;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private TextField textField;


    @FXML
    private void switchToRoom() throws IOException {
        App.setRoot("room1");
    }

}