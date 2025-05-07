package model;

public class LigneMedicament {
	double quantite;

	public LigneMedicament(double quantite) {
		quantite=this.quantite;
		
	}

	public double getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return "LigneMedicament [quantite=" + quantite + "]";
	}
	
	

}
