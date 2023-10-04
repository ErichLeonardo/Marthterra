package org.Hamm.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.Hamm.model.CaptchaGenerator;

import java.util.HashMap;
import java.util.function.Consumer;

public class CaptchaController {
    @FXML
    private ImageView captchaImageView;

    @FXML
    private TextField captchaTextField;

    private String expectedCaptchaText;
    private Consumer<Boolean> captchaVerifiedListener;

    public void setCaptchaVerifiedListener(Consumer<Boolean> listener) {
        this.captchaVerifiedListener = listener;
    }

    public void initialize() {
        // Lógica para inicializar y mostrar el CAPTCHA en captchaImageView
        Object[] captcha = CaptchaGenerator.generateCaptchaImage();

        Image captchaImage = (Image)captcha[0];
        expectedCaptchaText = (String) captcha[1];
        // Muestra la imagen CAPTCHA en captchaImageView
        captchaImageView.setImage(captchaImage);
    }

    @FXML
    private void verifyCaptcha() {
        // Aquí puedes verificar el texto CAPTCHA ingresado en captchaTextField
        String userResponse = captchaTextField.getText();
        boolean isCaptchaCorrect = userResponse.equals(expectedCaptchaText);

        if (isCaptchaCorrect) {
            // Respuesta correcta, realiza la acción deseada
            showAlert("Correcto", "Eres un humano.");
        } else {
            // Respuesta incorrecta, muestra un mensaje de error
            showAlert("Incorrecto", "Inténtalo de nuevo.");
            captchaTextField.clear();
            initialize(); // Regenera y muestra un nuevo CAPTCHA
        }

        // Notifica al oyente que el CAPTCHA se ha verificado
        if (captchaVerifiedListener != null) {
            captchaVerifiedListener.accept(isCaptchaCorrect);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
