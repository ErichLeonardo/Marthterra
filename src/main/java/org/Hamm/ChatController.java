package org.Hamm;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML
    private Button primaryButton;
    @FXML
    private Button sendButton;
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void handleSendButton() {
        // Aquí puedes agregar el código que se ejecutará cuando se haga clic en el botón "Send".
        // Por ejemplo, enviar el contenido del campo de texto al chat.
        String message = textField.getText();
        // Realiza alguna acción con el mensaje, como enviarlo al chat.
    }

}
