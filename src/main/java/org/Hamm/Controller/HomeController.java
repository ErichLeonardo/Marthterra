package org.Hamm.Controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.Hamm.App;
import org.Hamm.model.User;
import org.Hamm.model.UserHistory;
import org.Hamm.util.JAXBManager;


public class HomeController {

    @FXML
    private Button secondaryButton;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private TextField textField;


    @FXML
    private void switchToRoom() throws IOException {
        // Obtén el nombre del usuario desde el campo de texto
        String userName = textField.getText();

        // Verifica si el nombre de usuario no está vacío
        if (!userName.isEmpty()) {
            // Crea un objeto User y establece el nombre
            User user = new User();
            user.setName(userName);

            // Guarda el usuario en su archivo XML individual
            JAXBManager.writeUser(user);

            // Obtiene el historial actual
            UserHistory userHistory = JAXBManager.readUserHistory();

            // Agrega el nuevo usuario al historial
            userHistory.addUser(user);

            // Guarda el historial actualizado en un archivo XML
            JAXBManager.writeUserHistory(userHistory);

            App.setRoot("room1");
        } else {
            // Puedes mostrar un mensaje de error al usuario aquí si el campo está vacío
        }
    }



}