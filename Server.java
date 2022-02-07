import java.io.*;
import java.net.*;
import java.util.Calendar;

class Server {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    public static void main(String argv[]) throws Exception {
        DatagramSocket socket = new DatagramSocket(port);
        while(true) {
            DatagramPacket data = new DatagramPacket(buffer, buffer.length);
            socket.receive(data);
            System.out.println(data.getAddress());
            byte newBuffer[] = new byte[taille];
            Calendar calendar = Calendar.getInstance();
            String received = new String(data.getData(), 0, data.getLength());
            newBuffer=(
                "RECEIVED FROM "+data.getAddress()+" : "+received
                +"\nTime is "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)
            ).getBytes();
            data.setLength(newBuffer.length);
            data.setData(newBuffer);
            socket.send(data);
            /*test*/
        }
    }
}