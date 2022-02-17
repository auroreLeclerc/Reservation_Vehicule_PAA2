package reservation.server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;

import reservation.MyLogger;
import reservation.server.usine.Ouvrier;

class Server extends java.lang.Thread {
    private final int taille;
    private DatagramSocket socket;
    private DatagramPacket data;
    private static ArrayList<reservation.server.usine.Voiture> voitures;
    private MyLogger logger = new MyLogger(Server.class.getName());

    Server(DatagramSocket socket, DatagramPacket data, ArrayList<reservation.server.usine.Voiture> voitures) {
        this.socket = socket;
        this.data = data;
        this.taille = data.getLength();
        Server.voitures = voitures;
    }

    public void run() {        
        this.logger.log(Level.INFO, "Server "+this.getName()+" started on "+this.socket.getLocalSocketAddress());
        
        String received = new String(data.getData(), 0, data.getLength());

        this.logger.log(Level.FINE, data.getAddress()+" requested "+received);

        String toBeSend="\n";

        if(received.equals("voiture")) {
            for (int i = 0; i < voitures.size(); i++) {
                toBeSend+="Voiture n°"+i+" est "+(voitures.get(i).isDisponible() ? "est" : "n'est pas")+" disponible\n";
            }
        }
        else if (Character.isDigit(received.charAt(0))) {
            int index = Integer.parseInt(received);
            this.logger.log(Level.FINE, "Un ouvrier travaille sur voiture n°"+index+" pendant "+voitures.get(index).getTempsPreparation()+"secondes");
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            try {
                ouvrier.prepare();
            } catch (InterruptedException e) {
                voitures.get(index).setPrepare(false);
                voitures.get(index).setDisponible(true);
                this.logger.log(Level.SEVERE, "L'ouvrier s'est coupé un doigt !", String.valueOf(e));
            }
            toBeSend="La voiture n°"+index+" est préparée";
        }
        else toBeSend="404 Not Found";

        byte newBuffer[] = new byte[taille];
        newBuffer = (toBeSend).getBytes();
        data.setLength(newBuffer.length);
        data.setData(newBuffer);

        try {
            this.socket.send(data);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, String.valueOf(e));
        }
        this.logger.log(Level.INFO, "Server "+this.getName()+" ended");
    }
}