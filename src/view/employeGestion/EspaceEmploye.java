package view.employeGestion;

import dbGestion.Requets;
import model.Employer;
import model.Notification;
import services.NotificationService;

import java.util.List;
import java.util.Scanner;

public class EspaceEmploye {
    public Requets requets = new Requets();
    static Scanner sc = new Scanner(System.in);
    private NotificationService service = new NotificationService();

    private List<Employer> tousLesEmployes = requets.getAllEmploye();

    public EspaceEmploye(){}


    //Pour l'espace employés :
    public void menu(String email) {

        // Vérifier si l’employé existe
        Employer employeConnecte = null;
        for (Employer emp : tousLesEmployes) {
            if (emp.getEmail().equalsIgnoreCase(email)) {
                employeConnecte = emp;
                break;
            }
        }

        if (employeConnecte == null) {
            System.out.println("❌ Email non reconnu. Veuillez contacter l’administrateur.");
            pause();
            return;
        }

        int choix;
        do {
            System.out.println("\n===============================");
            System.out.println("👤 ESPACE DE " + employeConnecte.getPrenom().toUpperCase());
            System.out.println("===============================\n");
            System.out.println("1️⃣  Voir mes notifications");
            System.out.println("2️⃣  Me désabonner");
            System.out.println("3️⃣  Me réabonner");
            System.out.println("0️⃣  Quitter l’espace");
            System.out.print("👉 Votre choix : ");

            while (!sc.hasNextInt()) {
                System.out.print("❌ Entrez un chiffre valide : ");
                sc.next();
            }
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.println("\n📨 Vos notifications :");
                    List<Notification> notifs = service.notificationsPour(email);
                    if (notifs.isEmpty()) {
                        System.out.println("📭 Aucune notification reçue.");
                    } else {
                        for (Notification n : notifs) {
                            System.out.println("📅 " + n.getDate() + " - 📝 " + n.getMessage());
                        }
                    }
                    pause();
                }

                case 2 -> {
                    if (!service.estAbonner(email)) {
                        System.out.println("⚠️ Vous n'êtes pas encore abonné.");
                    } else {
                        service.desabonner(email);
                        System.out.println("❌ Vous avez été désabonné avec succès.");
                    }
                    pause();
                }

                case 3 -> {
                    if (service.estAbonner(email)) {
                        System.out.println("✅ Vous êtes déjà abonné.");
                    } else {
                        service.abonner(email);
                        System.out.println("🔁 Vous avez été réabonné avec succès.");
                    }
                    pause();
                }

                case 0 -> {
                    System.out.println("👋 Déconnexion de l’espace employé.");
                    pause();
                }

                default -> {
                    System.out.println("❗ Choix invalide. Réessayez.");
                    pause();
                }
            }

        } while (choix != 0);
    }

    private void pause(){
        System.out.print("Appuyez sur enter pour continuer .....");
        sc.nextLine();
    }
}
