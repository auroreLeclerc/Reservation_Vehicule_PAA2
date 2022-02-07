import java.net.*;
import java.util.Scanner;

public class Client {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    public static void main(String argv[]) throws Exception {
        InetAddress serveur = InetAddress.getByName(argv[0]);
        int length = argv[1].length();
        byte buffer[] = argv[1].getBytes();
        DatagramPacket dataSent = new DatagramPacket(buffer, length, serveur, port);

        System.out.println("Connection au serveur");
        Scanner scan = new Scanner(System.in);
        
        try (DatagramSocket socket = new DatagramSocket()) {
            String message = scan.nextLine();
            socket.send(dataSent);
            DatagramPacket dataReceived = new DatagramPacket(new byte[taille], taille);
            socket.receive(dataReceived);
            System.out.println("Data received : " + new String(dataReceived.getData()));
            System.out.println("From : " + dataReceived.getAddress() + ":" + dataReceived.getPort());
        }
    }
}