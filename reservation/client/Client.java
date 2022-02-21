package reservation.client;
import java.net.*;
import java.util.logging.Level;

import reservation.MyLogger;
import reservation.udp.Receiver;
import reservation.udp.Sender;

public class Client {
    private DatagramSocket socket;
    private int size;
    private String input;
    private String ip;
    private int port;
    private MyLogger logger = new MyLogger(Client.class.getName());

    Client(DatagramSocket socket, int size, String input, String ip, int port) {
        this.socket = socket;
        this.size = size;
        this.input = input;
        this.ip = ip;
        this.port = port;
        
    }

    public void run() throws UnknownHostException {
        new Sender(this.socket, this.input, this.ip, this.port).run();
        DatagramPacket dataReceived = new Receiver(this.socket, this.size, this.ip, this.port).run();
        
        this.logger.log(Level.SEVERE, new String(dataReceived.getData()));
    }
}