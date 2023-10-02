package org.Hamm.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.Hamm.model.User;
import org.Hamm.util.JAXBManager;

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
    private ListView<String> listView;
    @FXML
    private Label userNameLabel; // Agrega un campo Label para mostrar el nombre de usuario

    @FXML
    private void initialize() {
        // Inicializa la vista ChatController
        // Puedes realizar configuraciones adicionales aquí si es necesario
        User user = JAXBManager.readUser(); // Intenta leer el nombre de usuario desde el archivo XML
        if (user != null) {
            setUserName(user.getName()); // Configura el nombre de usuario en el Label
        } else {
            // Si el archivo no existe o no se pudo leer, puedes manejarlo aquí
            // Por ejemplo, puedes mostrar un mensaje de error o pedir al usuario que ingrese su nombre.
        }

    }


    @FXML
    private void switchToHome() {
        // Implementa el código para cambiar a la vista Home si es necesario
    }

    @FXML
    private void handleSendButton() {
        // Implementa el código para enviar mensajes si es necesario
    }

    // Método para establecer el nombre de usuario en el Label
    public void setUserName(String userName) {
        userNameLabel.setText("User: " + userName);
    }
}
