package reservation.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class Request extends Udp {
    public Request(DatagramSocket socket, int size, String input, String ip, int port) throws UnknownHostException {
        super(socket, size, input, ip, port);
    }

    public String run() {
        new Sender(this.socket, this.data, this.input).run();
        DatagramPacket dataReceived = new Receiver(socket, this.size).run(); //buffer à taille fixe
        return new String(dataReceived.getData(), 0, dataReceived.getLength());
    }
}
