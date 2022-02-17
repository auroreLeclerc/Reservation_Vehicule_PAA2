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

    @Override
    public void run() {        
        this.logger.log(Level.INFO, "Server "+this.getName()+" started on "+this.socket.getLocalSocketAddress());
        
        String received = new String(data.getData(), 0, data.getLength());

        this.logger.log(Level.FINE, data.getAddress()+" requested "+received);

        String toBeSend="\n";

        if(received.equals("tuto")) toBeSend+=
            "Commandes disponibles : \n"+
            "tuto : afficher ce menu \n"+
            "voiture : afficher la liste de voiture \n"+
            "1 : numéro d'un voiture -> prépare la voiture ou prend la voiture pour un tour \n"+
            "1V : vend la voiture \n"
        ;
        else if(received.equals("voiture")) {
            for (int i = 0; i < voitures.size(); i++) {
                toBeSend+="Voiture n°"+i+" "+
                    (voitures.get(i).isDisponible() ? 
                        ((voitures.get(i).isPrepare() ? "est" : "n'est pas")+" préparée\n") :
                        "n'est pas disponible"
                    )
                ;
            }
        }
        else if (Character.isDigit(received.charAt(0))&&!Character.isDigit(received.charAt(received.length()))) {
            int index = Integer.parseInt(received);
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            if (!voitures.get(index).isPrepare()) toBeSend = ouvrier.prepare();
            else if (!voitures.get(index).isSorti()) toBeSend = ouvrier.sort();
            else if (!voitures.get(index).isVendu()) toBeSend="La voiture n°"+index+" a déjà été vendu";
            else toBeSend="La voiture n°"+index+" est en préparation";
        }
        else if (Character.isDigit(received.charAt(0))&&Character.isDigit(received.charAt(received.length()))) {

        }
        else toBeSend="404 Not Found";

        byte newBuffer[] = new byte[this.taille];
        newBuffer = (toBeSend).getBytes();
        //data.setLength(this.taille);
        data.setData(newBuffer);

        try {
            this.socket.send(data);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, String.valueOf(e));
        }
        this.logger.log(Level.INFO, "Server "+this.getName()+" ended");
    }
}