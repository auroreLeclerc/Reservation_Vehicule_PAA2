package reservation.server.usine;

import java.util.logging.Level;

import reservation.MyLogger;

public class Ouvrier {
    private MyLogger logger = new MyLogger(Ouvrier.class.getName());
    private Voiture voiture;
    private int index;

    public Ouvrier(Voiture voiture) {
        this.voiture = voiture;
        this.index = this.voiture.getIndex();
    }
    
    public String prepare() {
        if (voiture.isPrepare()) return "La voiture est déjà préparée !";
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
            return "La voiture n°"+index+" n'a pas pu être préparée";
        }
    }
    
    public String sort() {
        if (voiture.isSorti()) return "La voiture est déjà sortie !";
        voiture.setSorti(true);
        this.logger.log(Level.FINE, "La voiture n°"+index+" est de sortie");
        return "N'oubliez pas de rendre la voiture n°"+index;
    }
    
}
