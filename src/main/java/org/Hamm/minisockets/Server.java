package org.Hamm.minisockets;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class Server {
    public static void main(String[] args) throws ClassNotFoundException {
        ServerSocket serverSocket = null;
        boolean serverListening=false;

        try {
            serverSocket=new ServerSocket(8080);
            serverListening=true;
        }catch (IOException e) {
            System.out.println("Server: No se puede ejecutar el servidor en el puerto 8080");
        }

        Socket socket=null;
        ObjectInputStream in=null;
        OutputStream out=null;

        if(serverListening) {
            try {
                System.out.println("Server: Listo para recibir peticiones");
                socket=serverSocket.accept();

                String name=LocalTime.now().toString();

                File folder=new File(name);
                if(folder.mkdir()) {
                    in=new ObjectInputStream(socket.getInputStream());
                    do {
                        FileToBeTransfered ftbt = (FileToBeTransfered)in.readObject();

                        out=new BufferedOutputStream(new FileOutputStream(name+"/"+ftbt.getFilename()));
                        out.write(ftbt.getFileData(), 0, ftbt.getFileSize());
                        out.close();
                    }while(socket.isConnected());

                    in.close();
                }else {
                    System.out.println("Server: no se puede crear la carpeta destino");
                }


                socket.close();
                serverSocket.close();
                System.out.println("Server: Cerrando Servidor");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }
    }
}
