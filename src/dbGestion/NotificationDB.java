package dbGestion;

import model.Notification;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationDB {
    private final Connection con;

    public NotificationDB(Connection con) {
        this.con = con;
    }

    //  Insérer une notification et retourner son ID généré
    public int insertNotification(String message, LocalDate date) {
        String sql = "INSERT INTO notifications(message, date) VALUES(?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, message);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // ID généré
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur insertion notification : " + e.getMessage());
        }
        return -1;
    }

    //  Lier une notification à un abonnement
    public void lierNotificationAAbonnement(int idAbonnement, int idNotification) {
        String sql = "INSERT INTO envoieNotification(idAbonnement, idNotification) VALUES(?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAbonnement);
            stmt.setInt(2, idNotification);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur d’association notification/abonnement : " + e.getMessage());
        }
    }

    //  Récupérer toutes les notifications
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new Notification(
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur récupération notifications : " + e.getMessage());
        }
        return notifications;
    }

    //  Récupérer les notifications pour un employé (par email)
    public List<Notification> getNotificationsParEmail(String email) {
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
                notifications.add(new Notification(
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur récupération notifications pour " + email + " : " + e.getMessage());
        }

        return notifications;
    }
}
