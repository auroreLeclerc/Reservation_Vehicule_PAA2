package server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.usine.Ouvrier;

class Server extends java.lang.Thread {
    final static int taille = 1024;
    private DatagramSocket socket;
    private DatagramPacket data;
    private ArrayList<server.usine.Voiture> voitures;

    Server(DatagramSocket socket, DatagramPacket data, ArrayList<server.usine.Voiture> voitures) {
        this.socket = socket;
        this.data = data;
        this.voitures = voitures;
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

        String toBeSend="\n";

        if(received.equals("voiture")) {
            for (int i = 0; i < voitures.size(); i++) {
                toBeSend+="Voiture n°"+i+" est "+(voitures.get(i).isDisponible() ? "est" : "n'est pas")+" disponible\n";
            }
        }
        else if (Character.isDigit(received.charAt(0))) {
            int index = Integer.parseInt(received);
            logger.log(Level.FINE, "Un ouvrier travaille sur voiture n°"+index+" pendant "+voitures.get(index).getTempsPreparation()+"secondes");
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            try {
                ouvrier.prepare();
            } catch (InterruptedException e) {
                voitures.get(index).setPrepare(false);
                voitures.get(index).setDisponible(true);
                logger.log(Level.SEVERE, "L'ouvrier s'est coupé un doigt !", String.valueOf(e));
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
            logger.log(Level.SEVERE, String.valueOf(e));
        }
    }
}