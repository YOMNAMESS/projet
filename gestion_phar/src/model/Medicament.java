package model;

public class Medicament {
	int id_med;
	String nom_med;
	int stock;
	public Medicament(int id_med,String nom_med,int stock) {
		id_med=this.id_med;
		nom_med=this.nom_med;
		stock=this.stock;
		}
	public int getId_med() {
		return id_med;
	}
	public void setId_med(int id_med) {
		this.id_med = id_med;
	}
	public String getNom_med() {
		return nom_med;
	}
	public void setNom_med(String nom_med) {
		this.nom_med = nom_med;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Medicament [id_med=" + id_med + ", nom_med=" + nom_med + ", stock=" + stock + "]";
	}
	
	

}
