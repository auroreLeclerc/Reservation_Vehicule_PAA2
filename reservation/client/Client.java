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

    public void query(String input, DatagramSocket socket) {
        this.logger.log(Level.FINEST, "Connection au serveur");
        
        int length = input.length();
        byte buffer[] = input.getBytes();
        DatagramPacket dataSent = new DatagramPacket(buffer, length, this.serveur, port);
        
        try {
            socket.send(dataSent);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Le serveur est injoignable", String.valueOf(e));
        }
        DatagramPacket dataReceived = new DatagramPacket(new byte[taille], taille);
        try {
            socket.receive(dataReceived);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Le serveur est muet", String.valueOf(e));
        }
        
        this.logger.log(Level.INFO, new String(dataReceived.getData()));
        this.logger.log(Level.FINE, "From "+dataReceived.getAddress()+":"+dataReceived.getPort());
    }
}