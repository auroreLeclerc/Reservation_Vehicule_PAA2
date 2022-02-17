package reservation.client;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;

import reservation.MyLogger;

public class Client {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    private InetAddress serveur;
    private MyLogger logger = new MyLogger(Client.class.getName());

    Client(String ip) throws UnknownHostException {
        this.serveur = InetAddress.getByName(ip);
    }

    public void query(String input) throws IOException {
        this.logger.log(Level.FINEST, "Connection au serveur");
        
        try (DatagramSocket socket = new DatagramSocket()) {
            int length = input.length();
            byte buffer[] = input.getBytes();
            DatagramPacket dataSent = new DatagramPacket(buffer, length, this.serveur, port);
            
            socket.send(dataSent);
            DatagramPacket dataReceived = new DatagramPacket(new byte[taille], taille);
            socket.receive(dataReceived);
            
            this.logger.log(Level.INFO, new String(dataReceived.getData()));
            this.logger.log(Level.FINE, "From "+dataReceived.getAddress()+":"+dataReceived.getPort());
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, String.valueOf(e));
        }
    }
}