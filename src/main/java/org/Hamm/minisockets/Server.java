package org.Hamm.minisockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Server {
    private static MessagePersistence messagePersistence; // Instancia compartida
    private static Map<String, List<PrintWriter>> roomUsers = new HashMap<>(); // Mapa para almacenar usuarios en cada sala

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
                    //System.out.println("Alguien llega");
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    // Lee el nombre de usuario del cliente
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String clientUsername = in.readLine();

                    // Lee la sala a la que el cliente desea unirse
                    String selectedRoom = in.readLine();

                    // Verifica si la sala ya existe, de lo contrario, créala
                    roomUsers.putIfAbsent(selectedRoom, new ArrayList<>());

                    List<PrintWriter> roomWriters = roomUsers.get(selectedRoom);
                    roomWriters.add(out); // Agrega el PrintWriter del cliente a la lista de la sala seleccionada

                    Thread clientThread = new ClientHandler(socket, out, clientUsername, selectedRoom);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter clientOut; // Referencia PrintWriter del cliente
        private String clientUsername;
        private String selectedRoom;

        public ClientHandler(Socket socket, PrintWriter clientOut, String clientUsername, String selectedRoom) {
            this.socket = socket;
            this.clientOut = clientOut;
            this.clientUsername = clientUsername;
            this.selectedRoom = selectedRoom;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    String receivedMessage = in.readLine();

                    if (receivedMessage == null || receivedMessage.equalsIgnoreCase("exit")) {
                        break;
                    }

                    // Obtener la hora actual y formatearla
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedTime = currentTime.format(formatter);

                    // Formar el mensaje con la hora y el contenido del mensaje
                    String messageWithTime = "[" + formattedTime + "] " + clientUsername + ": " + receivedMessage;

                    // Guardar el mensaje en el historial de la sala correspondiente
                    messagePersistence.saveMessage(clientUsername, receivedMessage, formattedTime, selectedRoom);

                    System.out.println("[" + formattedTime + "] " + clientUsername + ": " + receivedMessage);

                    // Reenviar el mensaje a todos los clientes en la misma sala
                    List<PrintWriter> roomWriters = roomUsers.get(selectedRoom);
                    for (PrintWriter writer : roomWriters) {
                        writer.println("[" + formattedTime + "] " + clientUsername + ": " + receivedMessage);
                        writer.flush();
                    }
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
