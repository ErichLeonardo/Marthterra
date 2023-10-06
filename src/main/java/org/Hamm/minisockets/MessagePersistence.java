package org.Hamm.minisockets;

import java.io.*;

public class MessagePersistence {
    private static final String FILENAME = "message_history.txt"; // Nombre del archivo de historial

    public static synchronized void saveMessage(String sender, String message, String formattedTime) {
        try {
            FileWriter writer = new FileWriter(FILENAME, true); // Modo de adjuntar al archivo existente

            // Formatear todos los mensajes de la misma manera, incluyendo el mensaje de inicio de sesión
            String formattedMessage = "[" + formattedTime + "] " + sender + ": " + message + "\n";
            writer.write(formattedMessage);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}