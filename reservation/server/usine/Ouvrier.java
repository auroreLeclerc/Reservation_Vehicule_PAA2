package reservation.server.usine;

import java.util.logging.Level;

import reservation.MyLogger;

public class Ouvrier {
    private MyLogger logger = new MyLogger(Ouvrier.class.getName());
    private static Voiture voiture;
    private int index;

    public Ouvrier(Voiture voiture) {
        Ouvrier.voiture = voiture;
        this.index = Ouvrier.voiture.getIndex()+1;
    }
    
    public String preparer() {
        if (voiture.isVendu()) return "406 Not Acceptable - La voiture est déjà vendue !";
        if (voiture.isPrepare()) return "406 Not Acceptable - La voiture est déjà préparée !";
        voiture.setDisponible(false);
        this.logger.log(Level.FINE, "Un ouvrier travaille sur la voiture n°"+index+" pendant "+voiture.getTempsPreparation()+"ms");
        try {
            Thread.sleep(voiture.getTempsPreparation());
            voiture.setPrepare(true);
            voiture.setDisponible(true);
            return "La voiture n°"+index+" est préparée";
        } catch (InterruptedException e) {
            voiture.setPrepare(false);
            voiture.setDisponible(true);
            this.logger.log(Level.SEVERE, "L'ouvrier s'est coupé un doigt !", String.valueOf(e));
            return "500 Internal Server Error - La voiture n°"+index+" n'a pas pu être préparée";
        }
    }
    
    public String sortir() {
        if (voiture.isVendu()) return "406 Not Acceptable - La voiture est déjà vendue !";
        if (!voiture.isPrepare()) return "406 Not Acceptable - La voiture n'est même pas préparée !";
        if (voiture.isSorti()) return "406 Not Acceptable - La voiture est déjà sortie !";
        voiture.setSorti(true);
        this.logger.log(Level.FINE, "La voiture n°"+index+" est de sortie");
        return "N'oubliez pas de rendre la voiture n°"+index;
    }
    
    public String rentrer() {
        if (voiture.isVendu()) return "406 Not Acceptable - La voiture est déjà vendue !";
        if (!voiture.isPrepare()) return "406 Not Acceptable - La voiture n'est même pas préparée !";
        if (!voiture.isSorti()) return "406 Not Acceptable - La voiture n'est pas de sortie !";
        voiture.setSorti(false);
        this.logger.log(Level.FINE, "La voiture n°"+index+" rentrée");
        return "Merci d'avoir rendu la voiture n°"+index+", alors maintenant on l'achète ?";
    }

    public String vendre() {
        if (voiture.isVendu()) return "406 Not Acceptable - La voiture est déjà vendue !";
        if (!voiture.isPrepare()) return "406 Not Acceptable - La voiture n'est même pas préparée !";
        if (voiture.isSorti()) return "406 Not Acceptable - La voiture est pour le moment en sortie !";
        voiture.setVendu(true);
        this.logger.log(Level.FINE, "La voiture n°"+index+" est maintenant vendue");
        return "Merci d'avoir acheté la voiture n°"+index;
    }
    
}
