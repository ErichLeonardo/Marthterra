package org.Hamm.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.Hamm.App;
import org.Hamm.model.Room;
import org.Hamm.model.User;
import org.Hamm.model.UserHistory;
import org.Hamm.util.JAXBManager;

public class HomeController implements Initializable {

    @FXML
    private Button secondaryButton;
    @FXML
    private ChoiceBox<String> rooms;
    @FXML
    private TextField textField;

    @Override
    public void initialize(URL location, ResourceBundle resourses) {
        ObservableList<String> optionsRooms = FXCollections.observableArrayList(
                "1",
                "2",
                "3"
        );
        rooms.setItems(optionsRooms);
    }

    @FXML
    private void meteor() {
        // Obtén la referencia al Stage actual
        Stage stage = (Stage) secondaryButton.getScene().getWindow();

        // Crea una transición de desplazamiento hacia abajo
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), stage.getScene().getRoot());
        translateTransition.setByY(500); // Desplaza hacia abajo en 500 píxeles

        // Crea una transición de atenuación (fade out)
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeTransition.setFromValue(1.0); // Valor de opacidad inicial
        fadeTransition.setToValue(0.0);   // Valor de opacidad final

        // Combina ambas transiciones en una secuencia
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        // Define lo que sucede después de que termine la animación
        parallelTransition.setOnFinished(event -> {
            stage.close();
        });

        // Inicia la animación
        parallelTransition.play();
    }

    @FXML
    private void switchToRoom() throws IOException {
        // Obtén el nombre del usuario desde el campo de texto
        String userName = textField.getText();
        String selectedRoom = rooms.getValue(); // Obtén la sala seleccionada

        // Verifica si el nombre de usuario no está vacío
        if (!userName.isEmpty() && selectedRoom != null) {
            // Crea un objeto User y establece el nombre
            User user = new User();
            user.setName(userName);

            // Crear un objeto Room y configurarlo con la sala seleccionada
            Room room = new Room();
            room.setName(rooms.getValue()); // Obtén el nombre de la sala seleccionada

            // Guarda el usuario en su archivo XML individual
            JAXBManager.writeUser(user);

            // Obtiene el historial actual
            UserHistory userHistory = JAXBManager.readUserHistory();

            // Agrega el nuevo usuario al historial
            userHistory.addUser(user);

            // Guarda el historial actualizado en un archivo XML
            JAXBManager.writeUserHistory(userHistory);

            // Inicia la conexión con el servidor (si es necesario)

            // Ahora, abre la vista de sala correspondiente
            String roomFXML = "room" + selectedRoom; // Nombre del archivo FXML de la sala
            String parameter = "LOGIN:" + userName; // Parámetro a pasar a la vista de sala

            App.setRoot(roomFXML, parameter, selectedRoom);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            if (userName.isEmpty()) {
                alert.setContentText("Please enter your nickname.");
            } else {
                alert.setContentText("Please select a room before continuing.");
            }
            alert.showAndWait();
        }
    }

}