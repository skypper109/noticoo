package model;

public class Abonnement {
    private int id;
    private int idEmploye;
    private String statut;

    public Abonnement(int id, int idEmploye, String statut) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.statut = statut;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
