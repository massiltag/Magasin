package personnel;
import java.util.ArrayList;

import operations.Livraison;

public class Livreur extends Employe {
	private static final long serialVersionUID = -5768106444125430109L;
	double km = 0;		//Kilométrage
	ArrayList<Livraison> livraisons;
	
	public Livreur(String nom, String adresse, String login, String password, double salaireHor, String RIB, int nbH, GerantPersonnel gerant){
		super(nom, adresse, login, password, salaireHor, RIB, nbH, gerant);
		this.livraisons = new ArrayList<Livraison>();
	}
	
	public void addKm(double km) {
		this.km+=km;
	}
	
	public double getKm() {
		double total = 0.0;
		for (Livraison l : this.livraisons) {
			total += l.getDistance();
		}
		return total;
	}
	
	public void addLiv(Livraison liv) {
		this.livraisons.add(liv);
	}
	
	public int getNbLiv() {
		return this.livraisons.size();
	}
	
	public ArrayList<Livraison> getArray() {
		return this.livraisons;
	}
	
	public double salaire() {
		return super.salaire() + this.getKm()*1.20 + this.getNbLiv()*15;
	}	//Calculé avec une prime de 1.20€ par kilomètre parcouru et de 15€ par livraison.
}
