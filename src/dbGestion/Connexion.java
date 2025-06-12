package dbGestion;

import java.sql.*;

public class Connexion {
    public static String  urlDB = "jdbc:sqlite:notifDB.sqlite"; //Le nom de la base de donnée

    //Methode qui va gerer la connexion
    public static Connection connect(){
       try { //On essaye pour pas couper le programme en cas d'erreur
          return DriverManager.getConnection(urlDB); //L'instruction qui fait la connexion à la base de donnée reponse de type
       } catch (SQLException e) {//Exception capter et stocker dans e pour pas arreter le programme
           System.out.println("Erreur lors de la connexion a la base de donnée !!!");
       }
        return null;
    }

    //La methode pour la creation des tables dans la base de donnée
    public static void init(){
        String deleteTable4 = """
                DROP TABLE IF EXISTS employes;
                """;
        String deleteTable2 = """
                DROP TABLE IF EXISTS notifications;
                """;
        String deleteTable3 = """
                DROP TABLE IF EXISTS administrateurs;
                """;
        String deleteTable1 = """
                DROP TABLE IF EXISTS abonnements;
                """;
        String deleteTable5 = """
                DROP TABLE IF EXISTS envoieNotification;
                """;
        //La creation des requetes pour la creation des tables dans la base de donnée
        String tableAdmin = """
                CREATE TABLE IF NOT EXISTS administrateurs(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nomComplet TEXT NOT NULL,
                email TEXT NOT NULL
                );
                """;
        String tableEmploye = """
                CREATE TABLE IF NOT EXISTS employes(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT NOT NULL,
                prenom TEXT NOT NULL,
                email TEXT UNIQUE
                );
                """;
        String tableUser = """
                CREATE TABLE IF NOT EXISTS users(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT NOT NULL,
                password TEXT NOT NULL,
                role TEXT CHECK(role IN ('Admin','Employe'))
                );
                """;
        String tableAbonnement = """
                CREATE TABLE IF NOT EXISTS abonnements(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                idEmploye INTEGER,
                statut TEXTE CHECK( statut IN ('abonne','desabonne')),
                FOREIGN KEY (idEmploye) REFERENCES employes(id)
                );
                """;
        String tableNotification = """
                CREATE TABLE IF NOT EXISTS notifications(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                message TEXT NOT NULL,
                date DATE
                );
                """;
        String tableRelation = """
                CREATE TABLE IF NOT EXISTS envoieNotification(
                id integer primary key autoincrement,
                idAbonnement integer,
                idNotification,
                foreign key (idAbonnement) references abonnements(id),
                foreign key (idNotification) references notifications(id)
                );
                """;
        try(Connection con = connect()/*ICI c'est l'instruction pour etablir une connexion a la base de donnée*/ ;
            Statement stt = con.createStatement()/* ICI c'est pour faire executer bcp de commande sql*/
            ) {
            /*
            stt.execute(deleteTable1);
            stt.execute(deleteTable3);
            stt.execute(deleteTable4);
            stt.execute(deleteTable2);
            stt.execute(deleteTable5);
            stt.execute(tableAdmin);
            */

            //Pour l'execution de sql commande pour la creation des tables

            stt.execute(tableUser);
            stt.execute(tableEmploye);
            stt.execute(tableAbonnement);
            stt.execute(tableRelation);
            stt.execute(tableNotification);

            //System.out.println("Tables Créer avec succès !!!");
        }catch (SQLException e){
            System.out.println("Erreur lors de la creation des tables !!!"+e.getMessage());
        }
    }
}
