package org.Hamm.minisockets;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessagePersistence {
    private static final String FILENAME = "message_history.txt"; // Nombre del archivo de historial

    public static synchronized void saveMessage(String sender, String message, String formattedTime) {
        try {
            FileWriter writer = new FileWriter(FILENAME, true); // Modo de adjuntar al archivo existente

            // Guardar el mensaje en el archivo con el nuevo formato
            if (sender.startsWith("LOGIN:")) {
                // Si el mensaje es un inicio de sesión, guárdalo sin el prefijo "LOGIN:"
                writer.write("[" + formattedTime + "] " + sender.replace("LOGIN:", "") + "\n");
            } else {
                // Si no es un inicio de sesión, guárdalo con el formato de mensaje regular
                writer.write("[" + formattedTime + "] " + sender + ": " + message + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




