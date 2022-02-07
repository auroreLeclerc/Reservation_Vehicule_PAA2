import java.io.IOException;
import java.net.*;

class Server extends java.lang.Thread {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    private DatagramSocket socket;

    public void run() {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Server started on "+socket.getLocalAddress());
        while(true) {
            DatagramPacket data = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String received = new String(data.getData(), 0, data.getLength());

            System.out.println(data.getAddress()+" requested "+received);

            String toBeSend;

            switch (received) {
                case "voitures":
                    toBeSend="Listes des voitures et tout";
                break;
            
                default:
                    toBeSend="404 Not Found";
                break;
            }

            byte newBuffer[] = new byte[taille];
            newBuffer=(toBeSend).getBytes();
            data.setLength(newBuffer.length);
            data.setData(newBuffer);
            try {
                socket.send(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}