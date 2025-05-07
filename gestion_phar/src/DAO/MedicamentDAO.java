package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Medicament;

public class MedicamentDAO implements IDAOClientMedicament <Medicament>{

	@Override
	public List<Medicament> getAll() throws SQLException {
		List<Medicament> medicaments = new ArrayList<>();
		Connection cx=SingletonConnection.getInstance();
		PreparedStatement ps=cx.prepareStatement("select * from medicament");
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			Medicament c= new Medicament(1, "Doliprane", 120);
			c.setId_med(rs.getInt("id_med"));
			c.setNom_med(rs.getString("nom_med"));
			c.setStock(rs.getInt("stock"));
			medicaments.add(c);
			}
		ps.close();
		return medicaments;
		
	}


    // Méthode pour rechercher un médicament dans la base de données
    public static void rechercher(Connection conn, String nomMedicament) {
        boolean trouve = false; // Sert à savoir si le médicament a été trouvé

        // Requête SQL : chercher le médicament par nom, sans tenir compte de la casse
        String sql = "SELECT * FROM medicaments WHERE LOWER(nom_med) = LOWER(?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // On injecte le nom du médicament dans la requête
            stmt.setString(1, nomMedicament);
            ResultSet rs = stmt.executeQuery(); // Exécution de la requête

            if (rs.next()) {
                // Si un résultat est trouvé, on récupère les données
                trouve = true;

                int id = rs.getInt("id_med");
                String nom = rs.getString("nom_med");
                int stock = rs.getInt("stock");

                // Message formaté à afficher dans une boîte de dialogue
                String message = "ID : " + id + "\nNom : " + nom + "\nStock : " + stock;

                // Affichage du médicament trouvé via une boîte de dialogue Swing
                JOptionPane.showMessageDialog(
                    null, message, "Consultation Médicament", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            // En cas d'erreur SQL, on affiche un message d'erreur graphique
            JOptionPane.showMessageDialog(
                null, "Erreur SQL : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si aucun résultat n’a été trouvé
        if (!trouve) {
            JOptionPane.showMessageDialog(
                null,
                "Le médicament '" + nomMedicament + "' ne se trouve pas dans la base de données.",
                "Résultat",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}

	
	









}
	 
