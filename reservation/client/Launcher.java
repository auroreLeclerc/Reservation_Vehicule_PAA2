package reservation.client;
import java.io.IOException;
import java.util.Scanner;

public class Launcher {
    public static void main(String argv[]) throws IOException {
        Scanner scan = new Scanner(System.in);
        Client client = new Client("127.0.0.1");
        
        while(true) {
            String message = scan.nextLine();
            client.query(message);
        }
        //scan.close();
    }
}