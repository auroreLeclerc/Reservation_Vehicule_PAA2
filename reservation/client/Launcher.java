package reservation.client;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Scanner;

public class Launcher {
    public static void main(String argv[]) throws IOException {
        Scanner scan = new Scanner(System.in);
        Client client = new Client("127.0.0.1");
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(6000);
        
        client.query("tuto", socket);
        while(scan.hasNext()) {
            String message = scan.nextLine();
            client.query(message, socket);
        }
        //scan.close();
    }
}