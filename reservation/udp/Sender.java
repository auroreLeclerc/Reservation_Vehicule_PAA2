package reservation.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class Sender extends Udp {
    public Sender(DatagramSocket socket, int taille, String ip, int port) throws UnknownHostException {
        super(socket, taille, ip, port);
    }

    public DatagramPacket run() {
        try {
            this.socket.send(this.data);
            return this.data;
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Machine distante injoignable",String.valueOf(e));
            this.data.setData("408 Request Timeout".getBytes());
            return this.data;
        }
    }
}
