import java.io.*;
import java.net.*;
import java.util.*;
public class Main {
    ServerSocket serverSocket = null;
    ArrayList<Client> clients = new ArrayList<Client>();

    public static void main(String[] args) {
        Main server = new Main();
        try{
            server.serverSocket = new ServerSocket(55555);
            while(true){

                Socket socket = server.serverSocket.accept();
                Client  c = new Client(socket);
                server.clients.add(c);
                c.start();
            }

        }catch (SocketException e){

        }
        catch (IOException e){

        }
    }
}
