package reservation.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import reservation.MyLogger;

abstract class Udp {
    protected DatagramSocket socket;
    protected DatagramPacket data;
    protected int size;
    protected String input;
    protected MyLogger logger = new MyLogger(Sender.class.getName());

    //reservation.udp.Request
    public Udp(DatagramSocket socket, int size, String input, String ip, int port) throws UnknownHostException {
        this.socket = socket;
        this.data = new DatagramPacket(
            input.getBytes(),
            input.length(),
            InetAddress.getByName(ip),
            port
        );
        this.size = size;
        this.input = input;
    }
    
    //reservation.udp.Receiver
    public Udp(DatagramSocket socket, int size, String ip, int port) throws UnknownHostException {
        this.socket = socket;
        this.data = new DatagramPacket(
            new byte[size],
            size,
            InetAddress.getByName(ip),
            port
        );
    }
    
    public Udp(DatagramSocket socket, int size) {
        this.socket = socket;
        this.data = new DatagramPacket(
            new byte[size],
            size
        );
        this.size = size;
    }

    //reservation.udp.Sender
    public Udp(DatagramSocket socket, String input, String ip, int port) throws UnknownHostException {
        this.socket = socket;
        this.data = new DatagramPacket(
            input.getBytes(),
            input.length(),
            InetAddress.getByName(ip),
            port
        );
        this.input = input;
    }
    
    public Udp(DatagramSocket socket, DatagramPacket data, String input) {
        this.socket = socket;
        this.data = data;
        this.data.setData(input.getBytes());
        this.input = input;
    }
}
