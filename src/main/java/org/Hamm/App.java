package org.Hamm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.Hamm.Controller.CaptchaController;
import org.Hamm.Controller.ChatController;
import org.Hamm.Controller.ChatController2;
import org.Hamm.Controller.ChatController3;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Inicializa la ventana principal sin mostrarla
        primaryStage.initStyle(StageStyle.UNDECORATED);
        scene = new Scene(loadFXML("home"), 640, 348);

        // Agrega el evento de presionar el ratón para arrastrar la ventana
        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Agrega el evento de arrastrar la ventana
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // Carga la imagen del cursor personalizado con fondo transparente
        Image customCursor = new Image(getClass().getResourceAsStream("/org/Hamm/sol.png"));

        scene.setOnMouseEntered(event -> {
            ImageCursor imageCursor = new ImageCursor(customCursor);
            scene.setCursor(imageCursor);
        });

        primaryStage.setScene(scene);

        // Abre la ventana de Captcha
        openCaptchaWindow(primaryStage);

    }

    private void openCaptchaWindow(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/Hamm/Captcha.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage captchaStage = new Stage();
            captchaStage.setTitle("Captcha Test");
            captchaStage.setScene(scene);
            captchaStage.initStyle(StageStyle.UNDECORATED);

            CaptchaController captchaController = loader.getController();
            captchaController.initialize();

            captchaController.setCaptchaVerifiedListener(isCaptchaCorrect -> {
                if (isCaptchaCorrect) {
                    // Si el captcha se verifica correctamente, muestra la ventana principal
                    primaryStage.show();

                    // Cierra la ventana de Captcha
                    captchaStage.close();
                }
            });

            captchaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setRoot(String fxml, String parameter, String selectedRoom) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);

        // Obtén el controlador correspondiente según la sala seleccionada
        Object controller = fxmlLoader.getController();

        if (controller instanceof ChatController) {
            ChatController chatController = (ChatController) controller;
            chatController.parameter = parameter;
            chatController.start(selectedRoom);
        } else if (controller instanceof ChatController2) {
            ChatController2 chatController2 = (ChatController2) controller;
            chatController2.parameter = parameter;
            chatController2.start(selectedRoom);
        } else if (controller instanceof ChatController3) {
            ChatController3 chatController3 = (ChatController3) controller;
            chatController3.parameter = parameter;
            chatController3.start(selectedRoom);

            // Ajusta el tamaño de la ventana para ChatController3
            Stage stage = (Stage) scene.getWindow();
            stage.setWidth(800.0);
            stage.setHeight(445.0);
        }
    }


    public static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);

        if (fxml.equals("home")) {
            // Ajusta el tamaño de la ventana de la aplicación cuando vuelves a "home"
            Stage stage = (Stage) scene.getWindow();
            stage.setWidth(640);  // Establece el ancho deseado
            stage.setHeight(348); // Establece el alto deseado
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
