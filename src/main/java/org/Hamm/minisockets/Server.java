package org.Hamm.minisockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static MessagePersistence messagePersistence; // Instancia compartida
    private static List<PrintWriter> clientWriters = new ArrayList<>(); // Almacena las referencias PrintWriter de los clientes

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
                    clientWriters.add(out); // Agrega el PrintWriter del cliente a la lista

                    Thread clientThread = new ClientHandler(socket, out);
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

        public ClientHandler(Socket socket, PrintWriter clientOut) {
            this.socket = socket;
            this.clientOut = clientOut;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Pide al cliente su nombre de usuario y guárdalo
                //System.out.println("Esperando nombre de usuario");
                String clientUsername = in.readLine();
                //System.out.println("Listo para comunicar con "+clientUsername);
                while (true) {
                    String receivedMessage;
                    receivedMessage= in.readLine();
                    //while((receivedMessage= in.readLine())==null);

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
                    messagePersistence.saveMessage(clientUsername, messageWithTime, formattedTime);

                    System.out.println(messageWithTime);

                    // Reenviar el mensaje a todos los clientes (incluido el remitente original)
                    for (PrintWriter writer : clientWriters) {
                        // Enviar el mensaje con un delimitador
                        writer.println(messageWithTime);
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
