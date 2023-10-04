package org.Hamm.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class CaptchaGenerator {
    private static final int WIDTH = 300; // Ancho de la imagen CAPTCHA (aumentado)
    private static final int HEIGHT = 100; // Alto de la imagen CAPTCHA (aumentado)
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    /**
     * Este método genera una imagen CAPTCHA y el texto CAPTCHA correspondiente.
     *
     * @return Un objeto de tipo Object[] que contiene la imagen CAPTCHA y el texto CAPTCHA.
     */
    public static Object[] generateCaptchaImage() {
        // Creamos un objeto para almacenar la imagen CAPTCHA y el texto CAPTCHA
        Object[] result = new Object[2];

        // Creamos una nueva imagen WritableImage con las dimensiones especificadas
        WritableImage image = new WritableImage(WIDTH, HEIGHT);

        // Rellenamos el fondo de la imagen con un color claro (blanco)
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                image.getPixelWriter().setColor(x, y, Color.WHITE);
            }
        }

        // Generamos el texto CAPTCHA aleatorio
        String captchaText = generateRandomCaptchaText();

        // Agregamos el texto CAPTCHA a la imagen
        addCaptchaText(image, captchaText);

        // Agregamos un poco de ruido aleatorio a la imagen
        addRandomNoise(image);

        // Almacenamos la imagen y el texto CAPTCHA en el objeto result
        result[0] = image;
        result[1] = captchaText;

        // Devolvemos el objeto result que contiene la imagen y el texto CAPTCHA
        return result;
    }


    private static String generateRandomCaptchaText() {
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS.length());
            captcha.append(CHARACTERS.charAt(index));
        }
        return captcha.toString();
    }

    private static void addCaptchaText(WritableImage image, String captchaText) {
        // Crea un nuevo Canvas y obtén su GraphicsContext
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Configura el texto
        Text text = new Text(captchaText);
        text.setFont(Font.font("Arial", 50)); // Aumenta el tamaño de la fuente
        text.setFill(Color.BLACK);

        // Coloca el texto en el centro de la imagen
        double textX = (WIDTH - text.getBoundsInLocal().getWidth()) / 2;
        double textY = (HEIGHT + text.getBoundsInLocal().getHeight()) / 2;

        // Dibuja el texto en el GraphicsContext
        gc.setFont(text.getFont());
        gc.setFill(text.getFill());
        gc.fillText(captchaText, textX, textY);

        // Copia los píxeles del Canvas a la imagen
        PixelReader reader = canvas.snapshot(null, null).getPixelReader();
        PixelWriter writer = image.getPixelWriter();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                writer.setColor(x, y, reader.getColor(x, y));
            }
        }
    }


    private static void addRandomNoise(WritableImage image) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (random.nextDouble() < 0.02) { // Reducimos el ruido al 2%
                    // Agrega ruido aleatorio
                    image.getPixelWriter().setColor(x, y, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                }
            }
        }
    }
}
