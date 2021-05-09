import java.io.*;
import java.net.*;
import java.util.*;
public class Main {
    static ArrayList<Client> clients = new ArrayList<Client>();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(55555);
            while(true){
                Socket socket = serverSocket.accept();
                Client  c = new Client(socket);
                clients.add(c);
                c.start();
            }
        }catch (SocketException e){
            clients.clear();
            serverSocket.close();
        }

    }
}
