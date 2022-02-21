package reservation.server.usine;

import java.util.logging.Level;

import reservation.MyLogger;

public class Ouvrier {
    private MyLogger logger = new MyLogger(Ouvrier.class.getName());
    private static Voiture voiture;
    private int index;
    private int frontIndex;

    public Ouvrier(Voiture voiture) {
        Ouvrier.voiture = voiture;
        this.index = Ouvrier.voiture.getIndex();
        this.frontIndex = Ouvrier.voiture.getIndex()+1;
    }
    
    public String prepare() {
        if (voiture.isPrepare()) return "406 Not Acceptable - La voiture est déjà préparée !";
        voiture.setDisponible(false);
        this.logger.log(Level.FINE, "Un ouvrier travaille sur la voiture n°"+frontIndex+" pendant "+voiture.getTempsPreparation()+"ms");
        try {
            Thread.sleep(voiture.getTempsPreparation());
            voiture.setPrepare(true);
            voiture.setDisponible(true);
            return "La voiture n°"+frontIndex+" est préparée";
        } catch (InterruptedException e) {
            voiture.setPrepare(false);
            voiture.setDisponible(true);
            this.logger.log(Level.SEVERE, "L'ouvrier s'est coupé un doigt !", String.valueOf(e));
            return "500 Internal Server Error - La voiture n°"+frontIndex+" n'a pas pu être préparée";
        }
    }
    
    public String sortir() {
        if (voiture.isSorti()) return "406 Not Acceptable - La voiture est déjà sortie !";
        voiture.setSorti(true);
        this.logger.log(Level.FINE, "La voiture n°"+frontIndex+" est de sortie");
        return "N'oubliez pas de rendre la voiture n°"+frontIndex;
    }
    
    public String rentre() {
        if (!voiture.isSorti()) return "406 Not Acceptable - La voiture n'est pas de sortie !";
        voiture.setSorti(false);
        this.logger.log(Level.FINE, "La voiture n°"+frontIndex+" rentrée");
        return "Merci d'avoir rendu la voiture n°"+frontIndex+", alors maintenant on l'achète ?";
    }

    public String vend() {
        if (voiture.isVendu()) return "406 Not Acceptable - La voiture est déjà vendue !";
        else if (voiture.isSorti()) return "406 Not Acceptable - La voiture est pour le moment en sortie !";
        voiture.setVendu(true);
        this.logger.log(Level.FINE, "La voiture n°"+frontIndex+" est maintenant vendue");
        return "Merci d'avoir acheté la voiture n°"+frontIndex;
    }
    
}
