package org.Hamm.Controller;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.Hamm.App;
import org.Hamm.model.User;
import org.Hamm.util.JAXBManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private Label roomid;

    @FXML
    private ListView<String> listView;
    @FXML
    private Label userNameLabel; // Agrega un campo Label para mostrar el nombre de usuario

    private User user;
    private Socket socket;
    private BufferedReader serverIn;
    private PrintWriter serverOut;
    public String parameter;

    @FXML
    private void initialize() {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSendButton();
            }
        });
    }


    public void start(String selectedRoom){
        // Inicializa la vista ChatController
        // Puedes realizar configuraciones adicionales aquí si es necesario
        user = JAXBManager.readUser(); // Intenta leer el nombre de usuario desde el archivo XML
        roomid.setText("Room: " + selectedRoom);
        if (user != null) {
            setUserName(user.getName()); // Configura el nombre de usuario en el Label
        } else {
            // Si el archivo no existe o no se pudo leer, puedes manejarlo aquí
            // Por ejemplo, puedes mostrar un mensaje de error o pedir al usuario que ingrese su nombre.
        }

        textArea.setEditable(false);

        // Inicia la conexión con el servidor
        try {
            socket = new Socket("localhost", 8080); // Reemplaza "localhost" con la dirección IP o el nombre del servidor
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOut = new PrintWriter(socket.getOutputStream(), true);
            serverOut.println(this.parameter);
            // Inicia un hilo para recibir mensajes del servidor en tiempo real
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.setDaemon(true);
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");

    }

    @FXML
    private void handleSendButton() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            // Formatea el mensaje como "usuario: mensaje"
            String formattedMessage = user.getName() + ": " + message;

            // Envía el mensaje al servidor
            serverOut.println(formattedMessage);
            serverOut.flush();

            // Borra el mensaje del TextField
            textField.clear();
        }
    }




    // Método para recibir mensajes del servidor en tiempo real
    private void receiveMessages() {
        try {
            while (true) {
                String receivedMessage = serverIn.readLine();
                if (receivedMessage == null) {
                    break;
                }

                // Omitir tus propios mensajes (comparando con tu nombre de usuario)
                if (!receivedMessage.startsWith("[" + user.getName() + ":")) {
                    // Muestra el mensaje del servidor en el TextArea
                    textArea.appendText(receivedMessage + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void meteor() {
        // Obtén la referencia al Stage actual
        Stage stage = (Stage) textArea.getScene().getWindow();

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
            stage.close(); // Cierra la ventana después de la animación
        });

        // Inicia la animación
        parallelTransition.play();
    }



    // Método para establecer el nombre de usuario en el Label
    public void setUserName(String userName) {
        userNameLabel.setText("User: " + userName);
    }
}