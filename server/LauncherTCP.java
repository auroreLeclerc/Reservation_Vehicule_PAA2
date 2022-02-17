package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LauncherTCP {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    public static void main(String argv[]) throws IOException {
        ServerSocket socket = new ServerSocket(8080);
        while(true) {
            Socket connectionSocket = socket.accept();
            ServerTCP server = new ServerTCP(connectionSocket);
            server.start();
        }
    }
}