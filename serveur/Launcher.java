package serveur;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Launcher {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    public static void main(String argv[]) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        while(true) {
            socket.receive(data);
            Server server = new Server(socket, data);
            server.start();
        }
    }
}