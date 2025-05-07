package DAO;

import java.sql.SQLException;
import java.util.List;

import model.LigneMedicament;
import model.Medicament;
import model.Ordonnance;

public interface IDAOOrdonnance<T> {
	void ajouter(Ordonnance ele1, Medicament ele2, LigneMedicament ele3) throws SQLException;
	void supprimer(Ordonnance ele1, Medicament ele2, LigneMedicament ele3) throws SQLException;
	void enregistrer(List<Ordonnance> liste) throws SQLException;
	void fermer();
}
