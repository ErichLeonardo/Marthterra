package org.Hamm.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
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
            showAlert("Correct", "You are a human.");
        } else {
            // Respuesta incorrecta, muestra un mensaje de error
            showAlert("Incorrect", "Try again.");
            captchaTextField.clear();
            initialize(); // Regenerate and display a new CAPTCHA
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

        // Cargar el archivo CSS de estilos
        String cssPath = getClass().getResource("/org/Hamm/styles.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssPath);

        // Aplicar estilos al cuadro de diálogo
        alert.getDialogPane().getStyleClass().add("dialog-pane");

        // Ajustar el tamaño del cuadro de diálogo según el contenido
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();
    }
}
