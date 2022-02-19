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
        try {
            this.logger.log(Level.FINER, "En attente de connection");
            this.socket.receive(this.data);
            
            String received = new String(data.getData(), 0, data.getLength());
            this.logger.log(Level.FINE, "naaaaaaaaaaaaaaaaaaaaaa");

            return this.data;
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Machine distante muette", String.valueOf(e));
            this.data.setData("408 Request Timeout".getBytes());
            return this.data;
        }
    }
}
