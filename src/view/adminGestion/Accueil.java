package view.adminGestion;

import dbGestion.Requets;
import model.Employer;
import model.Notification;
import services.NotificationService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class Accueil {
    public Requets requets = new Requets();
    static Scanner sc = new Scanner(System.in);
    private NotificationService service = new NotificationService();

    private List<Employer> tousLesEmployes = requets.getAllEmploye();
    public Accueil(){

    }
    public void menu(){
        int choix = -1;
        do {
            System.out.println("\n=======================================");
            System.out.println("      📨 SYSTÈME DE NOTIFICATION - NOTICOO");
            System.out.println("=======================================\n");
            System.out.println("1️⃣  Ajouter des employés");
            System.out.println("2️⃣  Afficher les employés");
            System.out.println("3️⃣  Abonner un employé");
            System.out.println("4️⃣  Désabonner un employé");
            System.out.println("5️⃣  Envoyer un message");
            System.out.println("6️⃣  Vérifier un abonnement");
            System.out.println("7️⃣  Voir les notifications envoyées");
            System.out.println("0️⃣  Quitter\n");
            System.out.print("👉 Choisissez une option : ");

            while (!sc.hasNextInt()) {
                System.out.print("❌ Entrez un chiffre valide : ");
                sc.next();
            }
            choix = sc.nextInt();
            sc.nextLine(); // consomme retour ligne

            switch (choix) {
                case 1 -> {
                    System.out.print("👥 Combien d’employés voulez-vous ajouter ? ");
                    int nombre = sc.nextInt();
                    sc.nextLine();
                    for (int i = 0; i < nombre; i++) {
                        System.out.println("\n🆕 Employé #" + (i + 1));
                        System.out.print("Nom : ");
                        String nom = sc.nextLine();
                        System.out.print("Prénom : ");
                        String prenom = sc.nextLine();
                        System.out.print("Email : ");
                        String email = sc.nextLine();
                        requets.insertEmploye(nom, prenom, email);
                        System.out.println("✅ Employé ajouté avec succès !");
                    }
                    pause();
                }

                case 2 -> {
                    System.out.println("\n📋 Liste des employés enregistrés :");
                    if (tousLesEmployes.isEmpty()) {
                        System.out.println("⚠️ Aucun employé trouvé.");
                    } else {
                        for (int i = 0; i < tousLesEmployes.size(); i++) {
                            Employer e = tousLesEmployes.get(i);
                            System.out.printf("%d. 👤 %s %s (%s)\n", i + 1, e.getPrenom(), e.getNom(), e.getEmail());
                        }
                    }
                    pause();
                }

                case 3 -> {
                    System.out.print("📧 Email de l’employé à abonner : ");
                    String email = sc.nextLine();
                    boolean trouve = false;
                    for (Employer emp : tousLesEmployes) {
                        if (emp.getEmail().equalsIgnoreCase(email)) {
                            service.abonner(email);
                            trouve = true;
                            break;
                        }
                    }
                    if (!trouve) System.out.println("❌ Employé non trouvé.");
                    pause();
                }

                case 4 -> {
                    System.out.print("📧 Email de l’employé à désabonner : ");
                    String email = sc.nextLine();
                    boolean trouve = false;
                    for (Employer emp : tousLesEmployes) {
                        if (emp.getEmail().equalsIgnoreCase(email)) {
                            service.desabonner(email);
                            trouve = true;
                            break;
                        }
                    }
                    if (!trouve) System.out.println("❌ Employé non trouvé.");
                    pause();
                }

                case 5 -> {
                    System.out.print("✉️  Saisir le message à envoyer : ");
                    String message = sc.nextLine();
                    service.notifier(message, LocalDate.now());
                    pause();
                }

                case 6 -> {
                    System.out.print("📧 Email de l’employé à vérifier : ");
                    String email = sc.nextLine();
                    boolean existe = false;
                    for (Employer emp : tousLesEmployes) {
                        if (emp.getEmail().equalsIgnoreCase(email)) {
                            existe = true;
                            System.out.println(service.estAbonner(email)
                                    ? "✅ L’employé est abonné."
                                    : "❌ L’employé n’est pas abonné.");
                            break;
                        }
                    }
                    if (!existe) System.out.println("❌ Employé non trouvé.");
                    pause();
                }

                case 7 -> {
                    service.afficherNotifications();
                    pause();
                }

                case 0 -> {
                    System.out.println("👋 Merci d’avoir utilisé NOTICOO. À bientôt !");
                    pause();
                }

                default -> {
                    System.out.println("❗ Option invalide. Réessayez.");
                }
            }

        } while (choix != 0);

    }


    private void pause(){
        System.out.print("Appuyez sur enter pour continuer .....");
        sc.nextLine();
    }


}
