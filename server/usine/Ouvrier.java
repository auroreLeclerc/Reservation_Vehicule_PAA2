package server.usine;

public class Ouvrier {
    Voiture voiture;

    public Ouvrier(Voiture voiture) {
        this.voiture = voiture;
    }
    
    public void prepare() throws InterruptedException {
        voiture.setDisponible(false);
        Thread.sleep(voiture.getTempsPreparation());
        voiture.setPrepare(true);
        voiture.setDisponible(true);
    }
    
}
