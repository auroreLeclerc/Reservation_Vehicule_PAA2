package serveur;
import java.io.IOException;
import java.net.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server extends java.lang.Thread {
    final static int taille = 1024;
    private DatagramSocket socket;
    private DatagramPacket data;

    Server(DatagramSocket socket, DatagramPacket data) {
        this.socket = socket;
        this.data = data;
    }

    public void run() {
        Logger logger = Logger.getLogger(Server.class.getName());
        ConsoleHandler handler = new ConsoleHandler();
        logger.setLevel(Level.FINE);
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);
        
        logger.log(Level.INFO, "Server started on "+this.socket.getLocalAddress());
        
        String received = new String(data.getData(), 0, data.getLength());

        logger.log(Level.FINE, data.getAddress()+" requested "+received);

        String toBeSend;

        switch (received) {
            case "voitures":
                toBeSend="Listes des voitures et tout";
            break;

            case "test":
                try {
                    sleep(5000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                toBeSend="Slept";
            break;
        
            default:
                toBeSend="404 Not Found";
            break;
        }

        byte newBuffer[] = new byte[taille];
        newBuffer = (toBeSend).getBytes();
        data.setLength(newBuffer.length);
        data.setData(newBuffer);

        try {
            this.socket.send(data);
        } catch (IOException e) {
            logger.log(Level.SEVERE, String.valueOf(e));
        }
    }
}