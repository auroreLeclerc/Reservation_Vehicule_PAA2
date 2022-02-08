package serveur.usine;

public class Voiture {
    private boolean disponible = true;
    private boolean sorti = false;
    private boolean vendu = false;
    private Integer tempsPreparation;

    Voiture() {
        this.tempsPreparation = (int) Math.random() * (100 - 0 + 1) + 0 ; 
    }

    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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
