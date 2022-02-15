package client;
import java.io.IOException;
import java.net.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    private InetAddress serveur;

    Client(String ip) throws UnknownHostException {
        this.serveur = InetAddress.getByName(ip);
    }

    public void query(String input) throws IOException {
        Logger logger = Logger.getLogger(Client.class.getName());
        ConsoleHandler handler = new ConsoleHandler();
        logger.setLevel(Level.FINE);
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);
        logger.log(Level.FINEST, "Connection au serveur");
        
        try (DatagramSocket socket = new DatagramSocket()) {
            //String message = scan.nextLine();
            int length = input.length();
            byte buffer[] = input.getBytes();
            DatagramPacket dataSent = new DatagramPacket(buffer, length, this.serveur, port);
            
            socket.send(dataSent);
            DatagramPacket dataReceived = new DatagramPacket(new byte[taille], taille);
            socket.receive(dataReceived);
            
            logger.log(Level.INFO, new String(dataReceived.getData()));
            logger.log(Level.FINE, "From "+dataReceived.getAddress()+":"+dataReceived.getPort());
        }
    }
}