package acteurs;
import main.Main;
import operations.Vente;

import java.util.ArrayList;

public class Client extends Acteur{
	private static final long serialVersionUID = -8660420842272880797L;
	int id;
	ArrayList<Vente> achats;
	
	public Client(String nom, String adresse){
		super(nom,adresse);
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		this.achats = new ArrayList<Vente>();
		Main.clients.add(this);
	}
	
	public int getID(){
		return this.id;
	}
	
	public String toString() {
		return this.getNom() + ", " + this.getAdresse() + ", Client.\n";
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Client c : Main.clients) {
			if (id==c.getID()) exists = true;
		}
		return exists;
	}
	
	public ArrayList<Vente> getArray(){
		return this.achats;
	}
}