package dbGestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {
    private final Connection con;

    public UserDB(Connection con) {
        this.con = con;
    }

    // Ajouter un utilisateur (email, mot de passe, rôle)
    public boolean insertUser(String email, String password, String role) {
        String sql = "INSERT INTO users(email, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, role);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout utilisateur : " + e.getMessage());
            return false;
        }
    }

    // Vérifier l'authentification
    public boolean verifierConnexion(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("❌ Erreur d'authentification : " + e.getMessage());
            return false;
        }
    }

    // Récupérer le rôle d’un utilisateur
    public String getUserRole(String email) {
        String sql = "SELECT role FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération du rôle : " + e.getMessage());
        }
        return null;
    }

    // Vérifier si un utilisateur existe
    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la vérification de l'utilisateur : " + e.getMessage());
            return false;
        }
    }
}
