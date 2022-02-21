package reservation.server;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import reservation.MyLogger;
import reservation.server.usine.Ouvrier;
import reservation.udp.Sender;

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
        this.logger.log(Level.FINER, "Server "+this.getName()+" started");
        
        String received = new String(data.getData(), 0, data.getLength());
        this.logger.log(Level.FINEST, data.getAddress()+" requested "+received);

        new Sender(socket, data, this.requestedData(received)).run();

        this.logger.log(Level.FINER, "Server "+this.getName()+" ended");
    }

    private String requestedData(String received) {
        String toBeSend="\n";
        Pattern regex4 = Pattern.compile("prepare \\d*", Pattern.CASE_INSENSITIVE);
        Pattern regex5 = Pattern.compile("sortir \\d*", Pattern.CASE_INSENSITIVE);
        Pattern regex6 = Pattern.compile("rentrer \\d*", Pattern.CASE_INSENSITIVE);
        Pattern regex7 = Pattern.compile("acheter \\d*", Pattern.CASE_INSENSITIVE);

        if (regex7.matcher(received).find()) {
            System.out.println("test");
            // Integer.parseInt(Pattern.compile(".* (\\d*)", Pattern.CASE_INSENSITIVE).matcher(received).group(1));
        }

        Pattern containsNumber = Pattern.compile(".* (\\d*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = containsNumber.matcher(received);
        int index = matcher.find() ? Integer.parseInt(matcher.group(1))-1 : 0;

        if(received.equals("help")) toBeSend+=
            " --- Commandes disponibles --- \n"+
            "help : afficher ce menu \n"+
            "voiture : afficher la liste de voiture \n"+
            "prepare X : prepare la voiture n°X \n"+
            "sortir X : faire un tour d'essai avec la voiture n°X \n"+
            "rentrer X : rentrer la voiture n°X du tour d'essai \n"+
            "acheter X : acheter la voiture n°X (la rendant vendu) \n";
        else if(received.equals("exit")) toBeSend="Bye Bye =) !";
        else if(received.equals("voiture")) {
            for (int i = 0; i < voitures.size(); i++) {
                toBeSend+="Voiture n°"+(i+1)+" "+
                    (voitures.get(i).isVendu() ?
                        "est déjà vendu\n" : 
                        (voitures.get(i).isDisponible() ? 
                            (!voitures.get(i).isSorti() ? 
                                (voitures.get(i).isPrepare() ?
                                    "est" :
                                    "n'est pas"
                                )+" préparée\n" : 
                            "est de sortie\n")
                        : "n'est pas disponible\n")
                    )
                ;
            }
        }
        else if (regex4.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.prepare();
        }
        else if (regex5.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.sortir();
        }
        else if (regex6.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.rentre();
        }
        else if (regex7.matcher(received).find()) {
            Ouvrier ouvrier = new Ouvrier(voitures.get(index));
            toBeSend = ouvrier.vend();
        }
        else toBeSend = "404 Not Found - essayer 'help' pour les commandes disponibles";

        return toBeSend;
    }
}