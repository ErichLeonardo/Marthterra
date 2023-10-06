package org.Hamm.minisockets;

import java.io.*;

public class MessagePersistence {
    private static final String HISTORY_DIRECTORY = "message_history"; // Directorio para los archivos de historial

    public static synchronized void saveMessage(String sender, String message, String formattedTime, String room) {
        try {
            // Crea el directorio si no existe
            File directory = new File(HISTORY_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Crea el archivo de historial para la sala especificada
            String fileName = HISTORY_DIRECTORY + "/" + room + "_history.txt";
            FileWriter writer = new FileWriter(fileName, true); // Modo de adjuntar al archivo existente

            // Formatear todos los mensajes de la misma manera, incluyendo el mensaje de inicio de sesión
            String formattedMessage = "[" + formattedTime + "] " + sender + ": " + message + "\n";
            writer.write(formattedMessage);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}