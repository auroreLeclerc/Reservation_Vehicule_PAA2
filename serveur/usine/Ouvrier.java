package serveur.usine;

public class Ouvrier {
    Voiture voiture;

    Ouvrier(Voiture voiture) {
        this.voiture = voiture;
    }
    
    public void run() {
        sleep(voiture.getTempsPreparation());
        voiture.setDisponible(true);
    }
    
}
