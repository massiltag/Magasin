package stock;
import java.io.Serializable;
import java.util.ArrayList;

import main.Main;
import personnel.GerantStock;

public class Depot implements Serializable {
	private static final long serialVersionUID = 1819644187678120454L;
	static int nbDep = 0;
	private int id;
	private String nom;
	private String adresse;
	public ArrayList<Produit> produits;
	private GerantStock gerant;
	
	public Depot(String nom, String adresse, GerantStock gerant) {
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		this.nom = nom;
		this.adresse = adresse;
		this.produits = new ArrayList<Produit>();
		this.gerant = gerant;
		
		if (this.gerant!=null) this.gerant.getArray().add(this);
		Main.depots.add(this);
	}
	
	//SETTERS
	public void setID(int id) {
		this.id = id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public void setGerant(GerantStock gerant) {
		if (!(this.gerant == null)) this.gerant.getArray().remove(this);
		this.gerant = gerant;
		this.gerant.getArray().add(this);
	}
	
	// GETTERS
	public int getID() {
		return this.id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getAdresse() {
		return this.adresse;
	}
	
	public String getGerant() {
		return this.gerant.toString();
	}
	
	public ArrayList<Produit> getArray() {
		return this.produits;
	}
	
	// METHODS
	public void add(Produit p) {
		this.produits.add(p);
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Depot d : Main.depots) {
			if (id==d.getID()) exists = true;
		}
		return exists;
	}
	
	public String toString() {
		return "Dépot " + this.getNom() + ", Adresse : " + this.getAdresse() + ".\n";
	}
	
}
