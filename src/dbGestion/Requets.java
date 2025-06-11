package dbGestion;

import model.Abonnement;
import model.Employer;
import model.Notification;
import services.EmailService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Requets {
    Connection con = new Connexion().connect();
    public void verifierEtInsererAdminParDefaut() {
        String sqlCheck = "SELECT COUNT(*) AS total FROM administrateurs";
        String sqlInsert = "INSERT INTO administrateurs(nomComplet, email) VALUES ( ?, ?)";
        String sqlInsert2 = "INSERT INTO users(email,password,role) VALUES ( ?, ?, ?)";

        try (
                PreparedStatement checkStmt = con.prepareStatement(sqlCheck);
                ResultSet rs = checkStmt.executeQuery()
        ) {
            if (rs.next()) {
                int total = rs.getInt("total");

                if (total == 0) {
                    try (
                            PreparedStatement insertStmt = con.prepareStatement(sqlInsert);
                            PreparedStatement users = con.prepareStatement(sqlInsert2)
                    ) {
                        insertStmt.setString(1, "Administrateur");
                        insertStmt.setString(2, "admin@noticoo.com"); //  à chiffrer idéalement

                        insertStmt.executeUpdate();


                        users.setString(1,"admin@noticoo.com");
                        users.setString(2,"Admin1234");
                        users.setString(3,"Admin");
                        users.executeUpdate();

                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la vérification des administrateurs : " + e.getMessage());
        }
    }


    /* Debut des requets pour la gestion des Employés */


    //Pour l'authentification :
    public boolean verifierLog(String email,String motDePasse){
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?"; // assure-toi que la colonne existe

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // retourne vrai si un employé correspond
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la connexion : " + e.getMessage());
            return false;
        }
    }

    //Pour l'authentification si c'est un employé :
    public boolean verifierEmp(String email){
        String sql = "SELECT * FROM employes WHERE email = ?"; // assure-toi que la colonne existe

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // retourne vrai si un employé correspond
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la connexion : " + e.getMessage());
            return false;
        }
    }

    public void insertEmploye(String nom, String prenom,String email){
        String sql = "INSERT INTO employes(nom,prenom,email) VALUES(?,?,?)";
        String sql2 = "INSERT INTO users(email,password,role) VALUES(?,?,?)";
        System.out.println("Nom : "+nom+" \nPrenom : "+prenom+"\nEmail : "+email);
        try(PreparedStatement prst = con.prepareStatement(sql);PreparedStatement users=con.prepareStatement(sql2)) {
            prst.setString(1,nom);
            prst.setString(2,prenom);
            prst.setString(3,email);
            prst.executeUpdate();
            users.setString(1,email);
            users.setString(2,"Employe1234");
            users.setString(3,"Employe");
            users.executeUpdate();
            EmailService.envoyerEmail(email,"Inscription dans le systeme NotifCool","Bienvenue dans le systeme de notification. Vous etes inscrit avec succès.\n Vous pouvez vous connectez avec votre email et comme mot de passe Employe1234.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la table employee !"+e.getMessage());
        }
    }
    //Pour la recuperation l'ensemble des employés :
    public List<Employer> getAllEmploye(){
        String sql = "SELECT * FROM employes";
        try(PreparedStatement pt = con.prepareStatement(sql)) {
            var employe = pt.executeQuery();
            List<Employer> employers = new ArrayList<>();
            while (employe.next()){
                Employer emp = new Employer(
                        employe.getInt("id"),
                        employe.getString("nom"),
                        employe.getString("prenom"),
                        employe.getString("email")
                );
                employers.add(emp);
            }
            return employers;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation des employés "+e.getMessage());
        }
        return null;
    }

    //Pour la recuperation d'un employé en fonction de l'email:
    public Employer getEmployeByEmail(String email){
        String sql = "SELECT * FROM employes WHERE email=?";
        try(PreparedStatement pt = con.prepareStatement(sql)) {
            pt.setString(1,email);
            var employe = pt.executeQuery();
            if (employe.next()){
                Employer emp = new Employer(
                        employe.getInt("id"),
                        employe.getString("nom"),
                        employe.getString("prenom"),
                        employe.getString("email")
                );
                return emp;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation de l'employé avec email : "+email+" erreur du a : "+e.getMessage());
        }
        return null;
    }

    //Pour l'abonnement d'un employé :
    public List<Abonnement> getAbonne(){
        String sql = "SELECT * FROM abonnements";
        try(PreparedStatement pt = con.prepareStatement(sql)) {
           var response = pt.executeQuery();
            List<Abonnement> abonnes = new ArrayList<>();
           while (response.next()){
               Abonnement abn = new Abonnement(
                       response.getInt("id"),
                       response.getInt("idEmploye"),
                       response.getString("statut")
               );
               abonnes.add(abn);
           }
           return abonnes;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation de l'employé : "+e.getMessage());
        }
        return null;
    }
    //Pour l'abonnement d'un employé :
    public void abonne(int id,String email){
        String sql = "INSERT INTO abonnements(idEmploye,statut) VALUES(?,?)";
        try(PreparedStatement pt = con.prepareStatement(sql)) {
            pt.setInt(1,id);
            pt.setString(2,"abonne");
            pt.executeUpdate();
            EmailService.envoyerEmail(email,"Abonnement","Vous etes abonnés avec succès !!!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation de l'employé avec id : "+id+" erreur du a : "+e.getMessage());
        }
    }
    //Pour le desabonnement d'un employé :
    public void desabonnement(int id,String email){
        String sql = "UPDATE abonnements SET statut='desabonne' WHERE idEmploye=?";
        try(PreparedStatement pt = con.prepareStatement(sql)) {
            pt.setInt(1,id);
            pt.executeUpdate();
            EmailService.envoyerEmail(email,"Désabonnement","Vous etes désabonnés avec succès !!!");
       } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation de l'employé avec id : "+id+" erreur du a : "+e.getMessage());
        }
    }
    //Pour le Reabonnement d'un employé :
    public void reabonnement(int id,String email){
        String sql = "UPDATE abonnements SET statut='abonne' WHERE idEmploye=?";
        try(PreparedStatement pt = con.prepareStatement(sql)) {
            pt.setInt(1,id);
            pt.executeUpdate();
            EmailService.envoyerEmail(email,"Réabonnement","Vous etes résabonnés avec succès !!!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation de l'employé avec id : "+id+" erreur du a : "+e.getMessage());
        }
    }

    //Pour la recuperation des abonnés :
    public List<Employer> getEmployesAbonne(){
        String sqlAbonne = "SELECT * FROM employes e,abonnements a where e.id = a.idEmploye and a.statut ='abonne'";
        try(PreparedStatement ptr = con.prepareStatement(sqlAbonne)){
            var employes = ptr.executeQuery();
            List<Employer> employer = new ArrayList<>();
            if (employes.wasNull()){
                return null;
            }
            while (employes.next()){
                Employer emplo = new Employer(
                    employes.getInt("id"),
                    employes.getString("nom"),
                    employes.getString("prenom"),
                    employes.getString("email")
                );
                employer.add(emplo);
            }
            return employer;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation !!! "+e.getMessage());
        }
        return null;
    }

    /* Fin des requets pour la gestion des employés */


    /* Debut de la partie notification */

    public void insertNotification(String message, LocalDate date){
        String sqlInsertNotif = "INSERT INTO notifications(message, date) VALUES(?, ?)";
        String sqlSelectAbonnes = "SELECT * FROM abonnements WHERE statut = 'abonne'";
        String sqlInsertEnvoi = "INSERT INTO envoieNotification(idAbonnement, idNotification) VALUES(?, ?)";
        String sqlSelectEmploye = "SELECT * FROM employes WHERE id = ?";

        try (
                PreparedStatement selectAbonnes = con.prepareStatement(sqlSelectAbonnes);
                PreparedStatement insertNotif = con.prepareStatement(sqlInsertNotif, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement insertEnvoi = con.prepareStatement(sqlInsertEnvoi);
                PreparedStatement selectEmploye = con.prepareStatement(sqlSelectEmploye)
        ) {
            var abonnes = selectAbonnes.executeQuery();

            if (!abonnes.isBeforeFirst()) {
                System.out.println("❌ Aucun abonné pour l’instant.");
                return;
            }

            insertNotif.setString(1, message);
            insertNotif.setDate(2, java.sql.Date.valueOf(date));
            insertNotif.executeUpdate();

            var generatedKeys = insertNotif.getGeneratedKeys();
            if (!generatedKeys.next()) {
                System.out.println("❌ Impossible de récupérer l'ID de la notification.");
                return;
            }

            int idNotification = generatedKeys.getInt(1);

            while (abonnes.next()) {
                int idAb = abonnes.getInt("id");
                int idEmploye = abonnes.getInt("idEmploye");

                // Insertion dans envoieNotification
                insertEnvoi.setInt(1, idAb);
                insertEnvoi.setInt(2, idNotification);
                insertEnvoi.executeUpdate();

                // Récupération de l’email et du nom
                selectEmploye.setInt(1, idEmploye);
                var rsEmp = selectEmploye.executeQuery();

                if (rsEmp.next()) {
                    String email = rsEmp.getString("email");
                    String nom = rsEmp.getString("prenom") + " " + rsEmp.getString("nom");
                    EmailService.envoyerEmail(email, "📢 Notification", "Bonjour " + nom + ",\n\n" + message);
                }
            }

            System.out.println("✅ Notification envoyée à tous les abonnés.");

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }


    public void insertNotificationAbonnement(String message, Date date){
        String sqlInsert = "INSERT INTO notifications(message,date,idAbonnement) VALUES(?,?,?)";
        String sqlSelect = "SELECT * FROM abonnements WHERE statut ='abonne'";
        try(PreparedStatement ptr1 = con.prepareStatement(sqlSelect);PreparedStatement ptr2 = con.prepareStatement(sqlInsert)) {
            var abonnes = ptr1.executeQuery();
            if (abonnes.next()){
                int idAb = abonnes.getInt("id");
                ptr2.setString(1,message);
                ptr2.setDate(2, (java.sql.Date) date);
                ptr2.setInt(3,idAb);
                ptr2.executeUpdate();
                //ICI on va configurer l'envoie de message aux employés :

            }
        }catch (SQLException e){
            System.out.println("Erreur : "+e.getMessage());
        }

    }

    //Pour afficher les notifications en fonction des employés :
    public List<Notification> getNotifications(){
        String sqlCommande="SELECT * FROM notifications";
        try(PreparedStatement ptr= con.prepareStatement(sqlCommande)) {
            var listNot = ptr.executeQuery();
            List<Notification> listNotification = new ArrayList<>();
            while (listNot.next()){
                Notification notif = new Notification(
                        listNot.getInt("id"),
                        listNot.getString("message"),
                        listNot.getDate("date")
                );
                listNotification.add(notif);
            }
            return listNotification;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recuperation des Notifications : "+e.getMessage());
        }
        return null;
    }

    //Pour les notifications lies a un seul employés :
    public List<Notification> getNotificationsPour(String email) {
        List<Notification> notifications = new ArrayList<>();

        String sql = """
        SELECT n.id, n.message, n.date
        FROM notifications n
        INNER JOIN envoieNotification en ON n.id = en.idNotification
        INNER JOIN abonnements ab ON en.idAbonnement = ab.id
        INNER JOIN employes e ON ab.idEmploye = e.id
        WHERE e.email = ? AND ab.statut = 'abonne'
    """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notif = new Notification(
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getDate("date")
                );
                notifications.add(notif);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des notifications : " + e.getMessage());
        }

        return notifications;
    }


    public boolean estAbonne(String email){
        String reqSel = "SELECT * FROM employes where email=?";
        String verif = "SELECT * FROM abonnements where idEmploye=? and statut='abonne'";
        try(
                PreparedStatement ptReqSel1 = con.prepareStatement(reqSel);
                PreparedStatement ptVerif = con.prepareStatement(verif);
        ){
            ptReqSel1.setString(1,email);
            var empl = ptReqSel1.executeQuery();
            int id = empl.getInt("id");
            ptVerif.setInt(1,id);
            var ver = ptVerif.executeQuery();
            return ver.next();
        }catch (SQLException e){
            System.out.println("Erreur lors de verification : "+e.getMessage());
        }
        return false;
    }

    /* Fin de la partie notification */
}
