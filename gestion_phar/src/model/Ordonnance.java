package model;

public class Ordonnance {
	int id_ord;
	String description;
	public Ordonnance(int id_ord,String description) {
		id_ord=this.id_ord;
		description=this.description;
	}
	public int getId_ord() {
		return id_ord;
	}
	public void setId_ord(int id_ord) {
		this.id_ord = id_ord;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Ordonnance [id_ord=" + id_ord + ", description=" + description + "]";
	}
	
	

}
