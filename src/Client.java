import java.io.*;
import java.net.*;

public class Client extends Thread{
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    Client(Socket socket) throws IOException {
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
    }
    public void run(){
        try {
            dataOutputStream.writeUTF("Welcome");
            while(true){

                String message = dataInputStream.readUTF();
                System.out.println(message);
                dataOutputStream.writeUTF(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
