package reservation.client;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Scanner;
import java.util.logging.Level;

import reservation.MyLogger;
import reservation.udp.Request;

public class Launcher {
    private final static int PORT = 8532;
    private final static String IP = "127.0.0.1";
    private final static int SIZE = 1024;
    public static void main(String argv[]) throws IOException {
        MyLogger logger = new MyLogger(Launcher.class.getName());
        Scanner scanner = new Scanner(System.in);
        DatagramSocket socket = new DatagramSocket();
        // socket.setSoTimeout(6000);
        
        logger.log(Level.INFO, new Request(socket, SIZE, "help", IP, PORT).run());
        while(scanner.hasNext()) {
            String input = scanner.nextLine();
            new Client(socket, SIZE, input, IP, PORT).run();
            if(input.equals("exit")) scanner.close();
        }
    }
}