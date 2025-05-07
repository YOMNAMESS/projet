package model;

public class Client {
	int id_cl ;
	String nom_cli;
	String prenom;
	double credit;
	public Client(int id_cl,String nom_cli,String prenom,double credit) {
		id_cl=this.id_cl;
		nom_cli=this.nom_cli;
		prenom=this.prenom;
		credit=this.credit;
		
	}
	public int getId_cl() {
		return id_cl;
	}
	public void setId_cl(int id_cl) {
		this.id_cl = id_cl;
	}
	public String getNom_cli() {
		return nom_cli;
	}
	public void setNom_cli(String nom_cli) {
		this.nom_cli = nom_cli;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	@Override
	public String toString() {
		return "Client [id_cl=" + id_cl + ", nom_cli=" + nom_cli + ", prenom=" + prenom + ", credit=" + credit + "]";
	}
	

}
