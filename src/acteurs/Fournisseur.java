package acteurs;

import java.util.ArrayList;

import main.Main;
import operations.Achat;

public class Fournisseur extends Acteur {
	private static final long serialVersionUID = -8017498046336152682L;
	int id;
	ArrayList<Achat> achats;		// Contient tous nos achats chez ce fournisseur
  
	public Fournisseur(String nom, String adresse){
		super(nom,adresse);
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
    	this.achats = new ArrayList<Achat>();
    	Main.fournisseurs.add(this);
	}
	
	public String toString() {
		return this.getNom() + ", " + this.getAdresse();
	}
	
	public int getID(){
		return this.id;
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Fournisseur f : Main.fournisseurs) {
			if (id==f.getID()) exists = true;
		}
		return exists;
	}
	
	public void remove() {
		Main.fournisseurs.remove(this);
	}
	
	public ArrayList<Achat> getArray(){
		return this.achats;
	}
}