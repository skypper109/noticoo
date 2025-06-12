package dbGestion;

import model.Abonnement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbonnementDB {
    private final Connection con;

    public AbonnementDB(Connection con) {
        this.con = con;
    }

    //  Abonner un employé
    public void abonner(int idEmploye) {
        String sql = "INSERT INTO abonnements(idEmploye, statut) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idEmploye);
            stmt.setString(2, "abonne");
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'abonnement : " + e.getMessage());
        }
    }

    //  Désabonner un employé
    public void desabonner(int idEmploye) {
        String sql = "UPDATE abonnements SET statut='desabonne' WHERE idEmploye=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idEmploye);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors du désabonnement : " + e.getMessage());
        }
    }

    //  Réabonner un employé
    public void reabonner(int idEmploye) {
        String sql = "UPDATE abonnements SET statut='abonne' WHERE idEmploye=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idEmploye);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors du réabonnement : " + e.getMessage());
        }
    }

    // Vérifier si un employé est abonné
    public boolean estAbonne(int idEmploye) {
        String sql = "SELECT * FROM abonnements WHERE idEmploye=? AND statut='abonne'";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idEmploye);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("❌ Erreur vérification abonnement : " + e.getMessage());
            return false;
        }
    }

    //  Obtenir tous les abonnements
    public List<Abonnement> getAll() {
        String sql = "SELECT * FROM abonnements";
        List<Abonnement> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Abonnement abn = new Abonnement(
                        rs.getInt("id"),
                        rs.getInt("idEmploye"),
                        rs.getString("statut")
                );
                list.add(abn);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur récupération des abonnements : " + e.getMessage());
        }
        return list;
    }
}
