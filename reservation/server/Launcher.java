package reservation.server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import reservation.server.usine.Voiture;

public class Launcher {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    public static void main(String argv[]) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        ArrayList<reservation.server.usine.Voiture> voitures = new ArrayList<reservation.server.usine.Voiture>();
        
        for (int index = 0; index < 15; index++) {
            voitures.add(new Voiture());
        }
        while(true) {
            socket.receive(data);
            Server server = new Server(socket, data, voitures);
            server.start();
        }
    }
}