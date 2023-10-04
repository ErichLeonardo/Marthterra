package org.Hamm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.Hamm.Controller.CaptchaController;

import java.io.IOException;

public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Crea una nueva ventana y muestra el captcha
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/Hamm/captcha.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Captcha Test");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);

            CaptchaController captchaController = loader.getController();
            captchaController.initialize();

            // Establece el oyente para verificar el captcha
            captchaController.setCaptchaVerifiedListener(isCaptchaCorrect -> {
                if (isCaptchaCorrect) {
                    try {
                        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/org/Hamm/home.fxml"));
                        Parent root2 = loader2.load();
                        Scene scene2 = new Scene(root2);
                        Stage stage2 = new Stage();
                        stage2.setTitle("Otra Ventana");
                        stage2.setScene(scene2);
                        stage2.show();
                        stage2.initStyle(StageStyle.UNDECORATED);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
