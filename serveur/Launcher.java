package serveur;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Launcher {
    final static int port = 8532;
    public static void main(String argv[]) throws SocketException {
        Server server = new Server(new DatagramSocket(port));
        server.run();
    }
}