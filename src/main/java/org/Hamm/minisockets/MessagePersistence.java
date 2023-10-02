package org.Hamm.minisockets;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessagePersistence {
    private static final String FILENAME = "message_history.txt"; // Nombre del archivo de historial

    public static synchronized void saveMessage(String sender, String message) {
        try {
            // Obtener la hora actual y formatearla
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            FileWriter writer = new FileWriter(FILENAME, true); // Modo de adjuntar al archivo existente

            // Guardar el mensaje en el archivo
            writer.write("[" + formattedTime + "] " + sender + ": " + message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
