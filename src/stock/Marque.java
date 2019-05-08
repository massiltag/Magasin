package stock;
import java.io.Serializable;
import java.util.ArrayList;
import main.Main;

public class Marque implements Serializable {
	private static final long serialVersionUID = -6397173015759003469L;
	int id;
	private String nom;
	ArrayList<Produit> produits;
	
	public Marque(String nom) {
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		this.nom = nom;
		produits = new ArrayList<Produit>();
		Main.marques.add(this);
	}
	
	public String toString() {
		return this.nom;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void add(Produit p) {
		this.produits.add(p);
	}
	
	public ArrayList<Produit> getArray() {
		return this.produits;
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Marque m : Main.marques) {
			if (id==m.getID()) exists = true;
		}
		return exists;
	}
}
