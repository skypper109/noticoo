package model;

public class Administrateur {
    private int id;
    private String nomComplet;
    private String email;
    public Administrateur(String nomComplet,String email){
        this.email = email;
        this.nomComplet = nomComplet;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
