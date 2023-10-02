package org.Hamm.minisockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Platform; // Necesario para actualizar la GUI desde hilos distintos
import javafx.scene.control.TextArea; // Reemplaza con la clase adecuada si es diferente

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private TextArea textArea; // Referencia al TextArea de la vista ChatController
    private String user; // Nombre de usuario del cliente

    public Client(String ipServer, TextArea textArea, String user) {
        this.textArea = textArea;
        this.user = user;

        try {
            socket = new Socket(ipServer, 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Envia el nombre de usuario al servidor
            out.println(user);

            // Crear un hilo para recibir y mostrar mensajes del servidor
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        if (message == null) {
                            break; // Terminar el hilo si el servidor cierra la conexión
                        }

                        // Actualizar el TextArea en la GUI con el mensaje recibido
                        Platform.runLater(() -> textArea.appendText(message + "\n"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message); // Enviar el mensaje al servidor
        }
    }
}
