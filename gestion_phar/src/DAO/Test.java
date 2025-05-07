package DAO;
import java.sql.*;
import java.util.List;

import javax.swing.JOptionPane;
public class Test {
	public static void main(String[]args) throws ClassNotFoundException {
		Connection Con=SingletonConnection.getInstance();
		System.out.println("connection stablish");
        List<String> medicaments = List.of("Paracetamol", "Aspirine", "Ibuprofene");
        try {
            // Connexion à la base de données (à adapter à ton environnement)
        
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/projet", "root", "java2");

            // Fenêtre de saisie du nom du médicament à rechercher
            String nomRecherche = JOptionPane.showInputDialog(
                null, "Entrez le nom du médicament à rechercher :");

            // Vérifie que l'utilisateur a bien entré un nom (ni null ni vide)
            if (nomRecherche != null && !nomRecherche.isBlank()) {
                // Appel de la méthode pour effectuer la recherche
            	MedicamentDAO.rechercher(conn, nomRecherche.trim());
            }

            // Fermeture de la connexion à la base de données
            conn.close();

        } catch (SQLException e) {
            // En cas d'erreur de connexion, affichage dans une boîte de dialogue
            JOptionPane.showMessageDialog(
                null, "Erreur de connexion : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }}}


