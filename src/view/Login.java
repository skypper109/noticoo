package view;

import services.LoginService;
import view.adminGestion.Accueil;
import view.employeGestion.EspaceEmploye;

import java.util.Scanner;

public class Login {

    private static final Scanner sc = new Scanner(System.in);
    private static final LoginService loginService = new LoginService();

    public Login(LoginService loginService) {}

    public static void start() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        🟢 BIENVENUE DANS NOTIFCOOL       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        while (true) {
            System.out.println("\n📋 Que voulez-vous faire ?");
            System.out.println("1️⃣  Se connecter");
            System.out.println("2️⃣  Quitter l'application");
            System.out.print("👉 Votre choix : ");

            String choix = sc.nextLine();

            switch (choix) {
                case "1":
                    gererConnexion();
                    break;
                case "2":
                    System.out.println("👋 Merci d’avoir utilisé NotifCool. À bientôt !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private static void gererConnexion() {
        int tentatives = 3;

        while (tentatives > 0) {
            System.out.print("\n📧 Email : ");
            String email = sc.nextLine();

            System.out.print("🔐 Mot de passe : ");
            String password = sc.nextLine();

            if (loginService.verifierIdentifiants(email, password)) {
                System.out.println("✅ Connexion réussie !");
                if (loginService.estEmploye(email)) {
                    new EspaceEmploye().menu(email);
                } else {
                    new Accueil().menu();
                }
                return; // sortir après connexion réussie
            } else {
                tentatives--;
                if (tentatives > 0) {
                    System.out.println("❌ Identifiants incorrects. Il vous reste " + tentatives + " tentative(s).");
                } else {
                    System.out.println("🚫 Trop de tentatives échouées. Fermeture du programme.");
                    System.exit(0);
                }
            }
        }
    }
}
