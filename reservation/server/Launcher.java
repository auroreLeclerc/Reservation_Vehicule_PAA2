package reservation.server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;

import reservation.MyLogger;
import reservation.server.usine.Voiture;

public class Launcher {
    private final static int port = 8532;
    private final static int taille = 1024;
    private final static byte buffer[] = new byte[taille];
    public static void main(String argv[]) throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket(port);
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        DatagramPacket data = new DatagramPacket(buffer, buffer.length, ip, port);
        ArrayList<reservation.server.usine.Voiture> voitures = new ArrayList<reservation.server.usine.Voiture>();
        MyLogger logger = new MyLogger(Server.class.getName());
        
        for (int index = 0; index < 15; index++) {
            voitures.add(new Voiture(index));
        }
        
        while(!socket.isClosed()) {
            logger.log(Level.FINER, "En attente de connection");
            socket.receive(data);
            Server server = new Server(socket, data, voitures);
            server.start();
            //server.join();
        }
    }
}