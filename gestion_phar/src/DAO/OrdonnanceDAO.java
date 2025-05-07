package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.LigneMedicament;
import model.Medicament;
import model.Ordonnance;


public class OrdonnanceDAO implements IDAOOrdonnance <Ordonnance>{

	@Override
	public void ajouter(Ordonnance ele1,Medicament ele2,LigneMedicament ele3) throws SQLException {
		    Connection cx = SingletonConnection.getInstance(); // Récupération de la connexion

		    try {
		        cx.setAutoCommit(false); // Début de transaction manuelle

		        // Étape 1 : Vérifier si le stock est suffisant
		        String reqStock = "SELECT stock FROM medicament WHERE id = ?";
		        PreparedStatement psStock = cx.prepareStatement(reqStock);
		        psStock.setInt(1, ele2.getId_med()); // suppose que Medicament a une méthode getId()
		        ResultSet rs = psStock.executeQuery();

		        if (rs.next()) {
		            int stockActuel = rs.getInt("stock");
		            int quantiteDemandee =ele3.getQuantite(); // suppose que LigneMedicament a une méthode getQuantite()

		            if (stockActuel >= quantiteDemandee) {
		                // Étape 2 : Insertion de l’ordonnance
		                String reqInsertOrdonnance = "INSERT INTO ordonnance(description) VALUES(?)";
		                PreparedStatement psInsertOrdonnance = cx.prepareStatement(reqInsertOrdonnance, Statement.RETURN_GENERATED_KEYS);
		                psInsertOrdonnance.setString(1, ele1.getDescription());
		                psInsertOrdonnance.executeUpdate();

		                // Récupération de l’ID généré pour l’ordonnance
		                ResultSet generatedKeys = psInsertOrdonnance.getGeneratedKeys();
		                int idOrdonnance = -1;
		                if (generatedKeys.next()) {
		                    idOrdonnance = generatedKeys.getInt(1);
		                }
		                psInsertOrdonnance.close();

		                // Étape 3 : Mise à jour du stock
		                String reqUpdateStock = "UPDATE medicament SET stock = stock - ? WHERE id = ?";
		                PreparedStatement psUpdateStock = cx.prepareStatement(reqUpdateStock);
		                psUpdateStock.setInt(1, quantiteDemandee);
		                psUpdateStock.setInt(2, ele2.getId_med());
		                psUpdateStock.executeUpdate();
		                psUpdateStock.close();

		                // Étape 4 (optionnelle mais logique) : lier ordonnance et médicament dans LigneMedicament
		                String reqInsertLigne = "INSERT INTO ligne_medicament(id_ordonnance, id_medicament, quantite) VALUES (?, ?, ?)";
		                PreparedStatement psInsertLigne = cx.prepareStatement(reqInsertLigne);
		                psInsertLigne.setInt(1, idOrdonnance);
		                psInsertLigne.setInt(2, ele2.getId_med());
		                psInsertLigne.setInt(3, quantiteDemandee);
		                psInsertLigne.executeUpdate();
		                psInsertLigne.close();

		                // Valider la transaction
		                cx.commit();
		                System.out.println("Ordonnance ajoutée");

		            } else {
		                System.out.println("Stock insuffisant : ordonnance non ajoutée");
		                cx.rollback();
		            }
		        }

		        rs.close();
		        psStock.close();
		    } catch (SQLException e) {
		        cx.rollback(); // rollback en cas d'erreur
		        throw e;
		    } finally {
		        cx.setAutoCommit(true); // Rétablir le mode auto-commit
		    }
		}
    @Override
	public void supprimer(Ordonnance ele1, Medicament ele2, LigneMedicament ele3) throws SQLException {
		    Connection cx = SingletonConnection.getInstance();

		    try {
		        cx.setAutoCommit(false); // Début de la transaction

		        int idOrdonnance = ele1. getId_ord();
		        int idMedicament = ele2.getId_med();
		        int quantite = ele3.getQuantite();

		        // Mise à jour du stock : stock += quantité
		        String reqUpdateStock = "UPDATE medicament SET stock = stock + ? WHERE id = ?";
		        PreparedStatement psUpdateStock = cx.prepareStatement(reqUpdateStock);
		        psUpdateStock.setInt(1, quantite);
		        psUpdateStock.setInt(2, idMedicament);
		        psUpdateStock.executeUpdate();
		        psUpdateStock.close();

		        // Supprimer la ligne médicament
		        String reqDeleteLigne = "DELETE FROM ligne_medicament WHERE id_ordonnance = ? AND id_medicament = ?";
		        PreparedStatement psDeleteLigne = cx.prepareStatement(reqDeleteLigne);
		        psDeleteLigne.setInt(1, idOrdonnance);
		        psDeleteLigne.setInt(2, idMedicament);
		        psDeleteLigne.executeUpdate();
		        psDeleteLigne.close();

		        // Supprimer l’ordonnance
		        String reqDeleteOrdonnance = "DELETE FROM ordonnance WHERE id = ?";
		        PreparedStatement psDeleteOrdonnance = cx.prepareStatement(reqDeleteOrdonnance);
		        psDeleteOrdonnance.setInt(1, idOrdonnance);
		        psDeleteOrdonnance.executeUpdate();
		        psDeleteOrdonnance.close();

		        cx.commit(); // Fin de la transaction
		        System.out.println("ordonnance supprimée");

		    } catch (SQLException e) {
		        cx.rollback(); // Annulation en cas d'erreur
		        throw e;
		    }finally {
		        cx.setAutoCommit(true); // Rétablissement de l'auto-commit
		    }}



		    public void enregistrer(List<Ordonnance> liste) throws SQLException {
			    Connection cx = SingletonConnection.getInstance();
			    String req = "INSERT INTO ordonnance(description) VALUES(?)";
			    PreparedStatement ps = cx.prepareStatement(req);

			    for (Ordonnance ord : liste) {
			        ps.setString(1, ord.getDescription());
			        ps.executeUpdate();
			    }

			    ps.close();
			    System.out.println("Toutes les ordonnances ont été enregistrées.");
			}

		

		@Override
		public void fermer() {
		    System.exit(0); // Ferme proprement toute l'application Java
		}}
