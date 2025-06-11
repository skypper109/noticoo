package model;

import dbGestion.Connexion;
import interfaces.Observe;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Employer implements Observe {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private int id ;
    //Pour la liste qui va contenir les notifications des employers :
    public List<String> notificationRecu;

    public Employer(int id, String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.id = id;
        this.notificationRecu = new ArrayList<>();
    }

    //Pour les getters et setters

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public void envoieNotification(String message, Employer expediteur) {
        notificationRecu.add(message);
        System.out.println("📩 Notification pour " + getNom() + " : " + message);
    }


    //Pour l'affichage des notifications
    public void afficherNotification(){
        if (notificationRecu.isEmpty()){
            System.out.println("Aucune notification pour " + nom);
        }
        else{
            for(String message : notificationRecu){
                System.out.println("- "+ message);
            }
        }

    }



}
