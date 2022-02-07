import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    private static Scanner scan;
    private InetAddress serveur;

    Client(String ip) throws UnknownHostException {
        this.serveur = InetAddress.getByName(ip);
    }

    public void query(String input) throws IOException {
        System.out.println("Connection au serveur");
        //scan = new Scanner(System.in);
        try (DatagramSocket socket = new DatagramSocket()) {
            //String message = scan.nextLine();
            int length = input.length();
            byte buffer[] = input.getBytes();
            DatagramPacket dataSent = new DatagramPacket(buffer, length, this.serveur, port);
            
            socket.send(dataSent);
            DatagramPacket dataReceived = new DatagramPacket(new byte[taille], taille);
            socket.receive(dataReceived);
            
            System.out.println("Data received : " + new String(dataReceived.getData()));
            System.out.println("From : "+dataReceived.getAddress()+":"+dataReceived.getPort());
        }
    }
}