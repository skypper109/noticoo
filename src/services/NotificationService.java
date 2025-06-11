package services;

import dbGestion.Requets;
import interfaces.Observe;
import interfaces.SujetNotification;
import model.Abonnement;
import model.Employer;
import model.Notification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationService implements SujetNotification {

    public List<Observe> abonneList;
    public Requets requets = new Requets();
    public NotificationService(){
        this.abonneList = new ArrayList<>();
    }

    @Override
    public void abonner(String email) {

        Employer employe =requets.getEmployeByEmail(email);
        boolean yes = false;
        List<Abonnement> abonnementList = requets.getAbonne();
        if (requets.getAbonne()!=null){
            for (Abonnement abonne:abonnementList){
                if (abonne.getIdEmploye()== employe.getId() && abonne.getStatut().equalsIgnoreCase("abonne")){
                    System.out.println("Cet Employé est deja abonné !!!");
                    yes = true;
                } else if (abonne.getIdEmploye() == employe.getId() && abonne.getStatut().equalsIgnoreCase("desabonne")) {
                    requets.reabonnement(employe.getId(),email);
                    System.out.println("Employer "+employe.getNom()+" "+employe.getPrenom()+" est réabonné avec succès !!!");
                    return;
                }
            }
            if (!yes){
                requets.abonne(employe.getId(),email);
                System.out.println("Employer "+employe.getNom()+" "+employe.getPrenom()+" est abonné avec succès !!!");
            }
        }else {
            requets.abonne(employe.getId(),email);
            System.out.println("Employer "+employe.getNom()+" "+employe.getPrenom()+" est abonné avec succès !!!");
        }
    }

    @Override
    public void desabonner(String email) {
        Employer employe = requets.getEmployeByEmail(email);
        requets.desabonnement(employe.getId(),email);
        System.out.println("Employer "+employe.getNom()+" "+employe.getPrenom()+" est desabonné avec succès !!!");
    }

    @Override
    public void notifier(String message, LocalDate date) {
        requets.insertNotification(message, date);
    }

    @Override
    public boolean estAbonner(String email) {
        return requets.estAbonne(email);
    }
    //Pour l'affichage des notifications :

    public void afficherNotifications() {
        List<Notification> notifications = requets.getNotifications();

        if (notifications == null || notifications.isEmpty()) {
            System.out.println("❌ Aucune notification à afficher.");
            return;
        }

        System.out.println("\n📬 HISTORIQUE DES NOTIFICATIONS 📬");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("| %-5s | %-40s | %-10s |\n", "ID", "Message", "Date");
        System.out.println("------------------------------------------------------------------");
        int i = 1;
        for (Notification notif : notifications) {
            System.out.printf("| %-5d | %-40s | %-10s |\n",
                    i,
                    couperTexte(notif.getMessage(), 40),
                    notif.getDate().toString()
            );
            i++;
        }

        System.out.println("------------------------------------------------------------------");
    }
    // Méthode utilitaire pour ne pas déborder la console
    private String couperTexte(String texte, int max) {
        if (texte.length() <= max) return texte;
        return texte.substring(0, max - 3) + "...";
    }

    public List<Notification> notificationsPour(String email){
        return  requets.getNotificationsPour(email);
    }


}
