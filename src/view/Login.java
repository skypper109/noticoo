package view;

import services.LoginService;
import view.adminGestion.Accueil;
import view.employeGestion.EspaceEmploye;

import java.util.Scanner;

public class Login {

    private static LoginService loginService = new LoginService();
    private static final Scanner sc = new Scanner(System.in);

    public Login(LoginService loginService) {}

    /**
     */
    public static void start() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         👨‍💼  LOGIN USER           ║");
        System.out.println("╚══════════════════════════════════╝");

        System.out.print("📧 Email : ");
        String email = sc.nextLine();

        System.out.print("🔐 Mot de passe : ");
        String password = sc.nextLine();

        if (loginService.verifierIdentifiants(email, password)) {
            if (loginService.estEmploye(email)){
               new EspaceEmploye().menu(email);
            }else {
                new Accueil().menu();
            }
            //On doit verifier maintenant qui est connecté :
        } else {
            System.out.println("❌ Email ou mot de passe incorrect.");
        }
    }
}

