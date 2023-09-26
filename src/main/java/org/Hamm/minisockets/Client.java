package org.Hamm.minisockets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        if(args.length!=2) {
            System.out.println("Usage: java Client ipServer folderToBeTransfered");
        }else {
            String ipServer=args[0];
            String folderToBeTransfered=args[1];

            Socket socket=null;
            InputStream in=null;
            ObjectOutputStream out=null;

            try {
                socket=new Socket(ipServer,8080);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }

            try {
                File folder=new File(folderToBeTransfered);
                if(folder.exists() && folder.isDirectory()) {
                    out=new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("Client: creando stream de salida");

                    String[] files = folder.list();
                    System.out.println("Client: Manejando "+files.length+" archivos");
                    for(String file : files) {
                        File f=new File(folderToBeTransfered+"/"+file);
                        if(f.exists() && f.isFile()) {
                            FileInputStream ff=new FileInputStream(f);
                            byte[] data=ff.readAllBytes();
                            FileToBeTransfered ftbt = new FileToBeTransfered(f.getName(), data.length);
                            ftbt.setFileData(data);
                            out.writeObject(ftbt);
                            out.flush();
                            System.out.println("Client: archivo transferido con éxito");
                        }
                    }



                }else {
                    System.out.println("Client: La carpeta no existe");
                    return;
                }

                out.close();
                //in.close();
                socket.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
