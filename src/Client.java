import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Client extends Thread {
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;

    private String cid = null;
    private LocalTime startTime = null;
    private Protocol protocol = null;
    private boolean power = true;

    Client(Socket socket) throws IOException {
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.startTime = LocalTime.now();
        this.protocol = null;
    }

    private String setCIDName(String nickName) {
        this.cid = nickName;
        return "set your nickname";
    }

    private String getClientList() {
        StringBuilder sb = new StringBuilder();
        Main.clients.forEach(item -> {
            sb.append(item.cid+",");
        });
        return sb.toString();
    }

    private String statusMessage(Protocol.statusCode statusCode) {
        switch (statusCode) {
            case Hi:
                return Protocol.resFormat(statusCode, setCIDName(protocol.getCid()));
            case CurrentTime:
                return Protocol.resFormat(statusCode, LocalTime.now().toString().concat(" sec"));
            case ConnectionTime:
                return Protocol.resFormat(statusCode, Long.toString(ChronoUnit.SECONDS.between(startTime, LocalTime.now())).concat(" sec"));
            case ClientList:
                return Protocol.resFormat(statusCode,String.join("/", getClientList()));
            case Quit:
                power = false;
                return Protocol.resFormat(statusCode, "quit");
            case Error:
                return Protocol.resFormat(statusCode, "error");
            case Message:
                return Protocol.resFormat(statusCode, protocol.getMessage());
        }
        throw new AssertionError("Unknown op: " + this);
    }

    public void run() {
        try {
            while (power) {
                String message = dataInputStream.readUTF();
                protocol = Protocol.reqParser(message);
                if (protocol != null) {
                    dataOutputStream.writeUTF(statusMessage(protocol.reqCheck()));
                } else {
                    dataOutputStream.writeUTF(statusMessage(Protocol.statusCode.Error));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.clients.remove(this);
    }
}
