package reservation.server.usine;

import java.util.Random;

public class Voiture {
    private boolean disponible = true;
    private boolean prepare = false;
    private boolean sorti = false;
    private boolean vendu = false;
    private Integer tempsPreparation;

    public Voiture() {
        Random random = new Random();
        int min = 250;
        int max = 5000;        
        this.tempsPreparation = (int) random.nextInt((max-min)+1) + min; 
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isPrepare() {
        return prepare;
    }

    public void setPrepare(boolean prepare) {
        this.prepare = prepare;
    }

    public boolean isSorti() {
        return sorti;
    }

    public void setSorti(boolean sorti) {
        this.sorti = sorti;
    }

    public boolean isVendu() {
        return vendu;
    }

    public void setVendu(boolean vendu) {
        this.vendu = vendu;
    }

    public Integer getTempsPreparation() {
        return tempsPreparation;
    }

    public void setTempsPreparation(Integer tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }
}
