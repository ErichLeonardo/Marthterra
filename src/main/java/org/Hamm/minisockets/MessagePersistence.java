package org.Hamm.minisockets;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

public class MessagePersistence {
    private static ConcurrentHashMap<Integer, String> portToFileMap = new ConcurrentHashMap<>();

    public static synchronized void saveMessage(int port, String sender, String message, String formattedTime) {
        try {
            String filename = getOrCreateFilenameForPort(port);
            FileWriter writer = new FileWriter(filename, true); // Modo de adjuntar al archivo existente

            // Formatear todos los mensajes de la misma manera, incluyendo el mensaje de inicio de sesión
            String formattedMessage = "[" + formattedTime + "] " + sender + ": " + message + "\n";
            writer.write(formattedMessage);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getOrCreateFilenameForPort(int port) {
        return portToFileMap.computeIfAbsent(port, k -> "message_history_" + port + ".txt");
    }
}
