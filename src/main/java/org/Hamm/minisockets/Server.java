package org.Hamm.minisockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Server {
    private static MessagePersistence messagePersistence; // Instancia compartida

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        boolean serverListening = false;

        try {
            serverSocket = new ServerSocket(8080);
            serverListening = true;
        } catch (IOException e) {
            System.out.println("Server: No se puede ejecutar el servidor en el puerto 8080");
        }

        if (serverListening) {
            messagePersistence = new MessagePersistence(); // Inicializa la instancia compartida
            System.out.println("Server: Listo para recibir mensajes");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Thread clientThread = new ClientHandler(socket);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    String receivedMessage = in.readLine();
                    if (receivedMessage == null || receivedMessage.equalsIgnoreCase("exit")) {
                        break;
                    }

                    // Obtener la hora actual y formatearla
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedTime = currentTime.format(formatter);

                    // Crear el mensaje con la hora
                    String messageWithTime = "[" + formattedTime + "] Cliente dice: " + receivedMessage;

                    // Guardar el mensaje en el historial
                    messagePersistence.saveMessage("Cliente", messageWithTime);

                    System.out.println(messageWithTime);

                    // Responder al cliente
                    out.println("Servidor responde: Hola desde el servidor");

                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
