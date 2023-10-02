package org.Hamm.minisockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Client ipServer");
        } else {
            String ipServer = args[0];

            try {
                Socket socket = new Socket(ipServer, 8080);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Leer mensajes del usuario y enviarlos al servidor
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("Escribe un mensaje: ");
                    String message = scanner.nextLine();
                    out.println(message);

                    // Recibir respuesta del servidor
                    String response = in.readLine();
                    System.out.println("Servidor dice: " + response);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
