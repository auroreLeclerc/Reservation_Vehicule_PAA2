package reservation.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class Receiver extends Udp {
    public Receiver(DatagramSocket socket, int taille, String ip, int port) throws UnknownHostException {
        super(socket, taille, ip, port);
    }

    public Receiver(DatagramSocket socket, int taille) {
        super(socket, taille);
    }

    public DatagramPacket run() {
        this.logger.log(Level.FINE, "En attente de connection sur "+this.socket.getLocalSocketAddress());
        try {
            this.socket.receive(this.data);
            this.logger.log(Level.FINEST, "From "+this.data.getAddress()+":"+this.data.getPort());
            logger.log(Level.INFO, new String(this.data.getData(), 0, this.data.getLength())); // Faut l'enlever et le mettre dans Client mais ça bug
            return this.data;
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Listening Timeout", String.valueOf(e));
            this.data.setData("408 Request Timeout".getBytes());
            return this.data;
        }
    }
}
