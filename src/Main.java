import dbGestion.Connexion;
import dbGestion.Requets;
import view.Login;
import view.adminGestion.Accueil;
public class Main {
    public static void main(String[] args) {
        new Connexion().init();new Requets().verifierEtInsererAdminParDefaut();
        Login.start();}
}

