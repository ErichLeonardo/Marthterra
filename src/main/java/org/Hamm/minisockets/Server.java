package org.Hamm.minisockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static MessagePersistence messagePersistence; // Instancia compartida
    private static ConcurrentHashMap<Integer, List<PrintWriter>> clientWritersMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ServerSocket serverSocket8080 = null;
        ServerSocket serverSocket5050 = null;
        ServerSocket serverSocket9090 = null;

        try {
            serverSocket8080 = new ServerSocket(8080);
            serverSocket5050 = new ServerSocket(5050);
            serverSocket9090 = new ServerSocket(9090);

            messagePersistence = new MessagePersistence(); // Inicializa la instancia compartida
            System.out.println("Server: Listo para recibir mensajes");

            // Inicia un hilo para cada puerto
            Thread clientHandler8080 = new ClientHandler(serverSocket8080, 8080);
            Thread clientHandler5050 = new ClientHandler(serverSocket5050, 5050);
            Thread clientHandler9090 = new ClientHandler(serverSocket9090, 9090);

            clientHandler8080.start();
            clientHandler5050.start();
            clientHandler9090.start();
        } catch (IOException e) {
            System.out.println("Server: No se puede ejecutar el servidor en uno de los puertos");
        }
    }

    private static class ClientHandler extends Thread {
        private ServerSocket serverSocket;
        private int port;

        public ClientHandler(ServerSocket serverSocket, int port) {
            this.serverSocket = serverSocket;
            this.port = port;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    // Obtén la lista de escritores de cliente para el puerto actual
                    List<PrintWriter> clientWriters = clientWritersMap.computeIfAbsent(port, k -> new CopyOnWriteArrayList<>());

                    clientWriters.add(out); // Agrega el PrintWriter del cliente a la lista

                    Thread clientThread = new ClientConnectionHandler(socket, out, port);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientConnectionHandler extends Thread {
        private Socket socket;
        private PrintWriter clientOut; // Referencia PrintWriter del cliente
        private int port;

        public ClientConnectionHandler(Socket socket, PrintWriter clientOut, int port) {
            this.socket = socket;
            this.clientOut = clientOut;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Pide al cliente su nombre de usuario y guárdalo
                String clientUsername = in.readLine();

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
                    String messageWithTime = "[" + formattedTime + "] " + receivedMessage;

                    // Guardar el mensaje en el historial
                    messagePersistence.saveMessage(port,clientUsername, messageWithTime, formattedTime);

                    System.out.println(messageWithTime);

                    // Reenviar el mensaje a todos los clientes de la misma sala (puerto)
                    List<PrintWriter> clientWriters = clientWritersMap.get(port);
                    if (clientWriters != null) {
                        for (PrintWriter writer : clientWriters) {
                            // Enviar el mensaje con un delimitador
                            writer.println(messageWithTime);
                            writer.flush();
                        }
                    }
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
