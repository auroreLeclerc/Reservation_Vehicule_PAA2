package reservation.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class Sender extends Udp {
    public Sender(DatagramSocket socket, String input, String ip, int port) throws UnknownHostException {
        super(socket, input, ip, port);
    }

    public Sender(DatagramSocket socket, DatagramPacket data, String toBeSend) {
        super(socket, data, toBeSend);
    }

    public DatagramPacket run() {
        this.logger.log(Level.FINE, "Envoie à "+this.data.getAddress()+":"+this.data.getPort());
        try {
            // this.data.setLength(this.data.getData().length);
            if (this.data.getData().length>1024) this.logger.log(Level.WARNING, "1024 default size bufffer overflow !");
            this.socket.send(this.data);
            return this.data;
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Machine distante injoignable", String.valueOf(e));
            this.data.setData("408 Request Timeout".getBytes());
            return this.data;
        }
    }
}
