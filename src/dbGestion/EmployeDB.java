package dbGestion;

import model.Employer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeDB {
    private final Connection con;

    public EmployeDB(Connection con) {
        this.con = con;
    }

    // Ajouter un nouvel employé
    public boolean insertEmployer(String nom, String prenom, String email) {
        String sql = "INSERT INTO employes(nom, prenom, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Erreur ajout employé : " + e.getMessage());
            return false;
        }
    }

    // Récupérer tous les employés
    public List<Employer> getAllEmployers() {
        List<Employer> employers = new ArrayList<>();
        String sql = "SELECT * FROM employes";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employer emp = new Employer(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
                employers.add(emp);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur récupération employés : " + e.getMessage());
        }

        return employers;
    }

    // Rechercher un employé par email
    public Employer getEmployerByEmail(String email) {
        String sql = "SELECT * FROM employes WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employer(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur recherche employé : " + e.getMessage());
        }

        return null;
    }

    // Vérifier l’existence d’un employé par email
    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM employes WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("❌ Erreur vérification email : " + e.getMessage());
            return false;
        }
    }
}
