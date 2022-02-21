package reservation.server;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;

import reservation.server.usine.Voiture;
import reservation.udp.Receiver;

public class Launcher {
    private final static int PORT = 8532;
    private final static String IP = "127.0.0.1";
    private final static int TAILLE = 1024;
    public static void main(String argv[]) throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket(PORT);
        ArrayList<reservation.server.usine.Voiture> voitures = new ArrayList<reservation.server.usine.Voiture>();
        
        for (int index = 0; index < 15; index++) {
            voitures.add(new Voiture(index));
        }
        
        while(!socket.isClosed()) {
            Server server = new Server(socket, new Receiver(socket, TAILLE, IP, PORT).run(), voitures);
            server.start();
            server.join();
        }
    }
}