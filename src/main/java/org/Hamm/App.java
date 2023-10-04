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


    public static void setRoot(String fxml,String parameter) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        scene.setRoot(fxmlLoader.load());
        ChatController c = fxmlLoader.getController();
        c.parameter = parameter;
        c.start();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
