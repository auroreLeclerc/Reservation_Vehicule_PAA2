package client;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class LauncherTCP {
    public static void main(String argv[]) throws IOException {
        Scanner scan = new Scanner(System.in);
        Socket socket = new Socket("127.0.0.1", 8080);
        ClientTCP client = new ClientTCP(socket);

        while(true) {
            String message = scan.nextLine();
            client.query(message);
        }
        //scan.close();
    }
}