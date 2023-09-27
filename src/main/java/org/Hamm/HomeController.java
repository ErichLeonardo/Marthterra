package org.Hamm;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeController {

    @FXML
    private Button primaryButton;

    @FXML
    private Button sendButton;

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("room1");
    }

    @FXML
    private void handleSendButton() {
        // Aquí puedes agregar la lógica para manejar el clic del botón "Send"
        String message = textField.getText();
        // Realiza alguna acción con el mensaje, como enviarlo al chat.
        // También puedes actualizar el TextArea con el mensaje.
        textArea.appendText("You: " + message + "\n");
        // Limpia el campo de texto después de enviar el mensaje.
        textField.clear();
    }
}
