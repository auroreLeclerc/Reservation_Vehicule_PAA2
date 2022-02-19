package reservation.server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import reservation.MyLogger;
import reservation.server.usine.Ouvrier;

class Server extends java.lang.Thread {
    private DatagramSocket socket;
    private DatagramPacket data;
    private static ArrayList<reservation.server.usine.Voiture> voitures;
    private MyLogger logger = new MyLogger(Server.class.getName());

    Server(DatagramSocket socket, DatagramPacket data, ArrayList<reservation.server.usine.Voiture> voitures) {
        this.socket = socket;
        this.data = data;
        Server.voitures = voitures;
    }

    @Override
    public void run() {        
        this.logger.log(Level.INFO, "Server "+this.getName()+" started on "+this.socket.getLocalSocketAddress());
        
        String received = new String(data.getData(), 0, data.getLength());

        this.logger.log(Level.FINE, data.getAddress()+" requested "+received);

        String toBeSend = this.regex(received);

        data.setData(toBeSend.getBytes());

        try {
            this.socket.send(data);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, String.valueOf(e));
        }
        this.logger.log(Level.INFO, "Server "+this.getName()+" ended");
    }

    private String regex(String received) {
        String toBeSend="\n";       
        Pattern regex1 = Pattern.compile("tuto", Pattern.CASE_INSENSITIVE);
        Pattern regex2 = Pattern.compile("exit", Pattern.CASE_INSENSITIVE);
        Pattern regex3 = Pattern.compile("voiture", Pattern.CASE_INSENSITIVE);
        Pattern regex4 = Pattern.compile("prepare \\d*", Pattern.CASE_INSENSITIVE);
        Pattern regex5 = Pattern.compile("sort \\d*", Pattern.CASE_INSENSITIVE);
        Pattern regex6 = Pattern.compile("rentre \\d*", Pattern.CASE_INSENSITIVE);
        Pattern regex7 = Pattern.compile("achete \\d*", Pattern.CASE_INSENSITIVE);

        Pattern containsNumber = Pattern.compile(".* (\\d*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = containsNumber.matcher(received);
        int index = matcher.find() ? Integer.parseInt(matcher.group(1)): 0;

        if(regex1.matcher(received).find()) toBeSend+=
            " --- Commandes disponibles --- \n"+
            "tuto : afficher ce menu \n"+
            "voiture : afficher la liste de voiture \n"+
            "prepare X : prepare la voiture n°X \n"+
            "sort X : faire un tour d'essaie avec la voiture n°X \n"+
            "rentre X : rentrer la voiture n°X du tour d'essaie \n"+
            "achete X : acheter la voiture n°X (la rendant vendu) \n";
        else if(regex2.matcher(received).find()) toBeSend="Bye Bye =) !";
        else if(regex3.matcher(received).find()) {
            for (int i = 0; i < voitures.size(); i++) {
                toBeSend+="Voiture n°"+i+" "+
                    (voitures.get(i).isVendu() ?
                        (voitures.get(i).isDisponible() ? 
                            (
                                voitures.get(i).isPrepare() ? "est" : "n'est pas"
                            )+" préparée\n"
                        : "n'est pas disponible\n") 
                    : "est déjà vendu\n")
                ;
            }
        }
        else if (regex4.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.prepare();
        }
        else if (regex5.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.sort();
        }
        else if (regex6.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.rentre();
        }
        else if (regex7.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.vend();
        }
        else toBeSend = "404 Not Found - essayer 'tuto' pour les commandes disponibles";

        return toBeSend;
    }
}