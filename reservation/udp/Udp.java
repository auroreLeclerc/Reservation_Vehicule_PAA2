package reservation.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import reservation.MyLogger;

abstract class Udp {
    protected DatagramSocket socket;
    protected DatagramPacket data;
    protected MyLogger logger = new MyLogger(Sender.class.getName());
    
    public Udp(DatagramSocket socket, int taille, String ip, int port) throws UnknownHostException {
        this.socket = socket;
        this.data = new DatagramPacket(
            new byte[taille],
            taille,
            InetAddress.getByName(ip),
            port
        );
    }
    
    public Udp(DatagramSocket socket, String input, String ip, int port) throws UnknownHostException {
        this.socket = socket;
        this.data = new DatagramPacket(
            input.getBytes(),
            input.length(),
            InetAddress.getByName(ip),
            port
        );
    }

    public Udp(DatagramSocket socket, int taille) {
        this.socket = socket;
        this.data = new DatagramPacket(
            new byte[taille],
            taille
        );
    }
}
