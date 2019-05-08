package personnel;
import java.util.ArrayList;

import stock.Depot;

public class GerantStock extends Employe {	// Travaille dans un ou plusieurs dépots, y gère le stock de produits.
	private static final long serialVersionUID = -3881542678554530170L;
	ArrayList<Depot> depots;	// Liste des dépots gérés.
	
	public GerantStock(String nom, String adresse, String login, String password, double salaireHor, String RIB, int nbH, GerantPersonnel gerant){
		super(nom, adresse, login, password, salaireHor, RIB, nbH, gerant);
		this.depots = new ArrayList<Depot>();
	}
	
	public ArrayList<Depot> getArray() {
		return this.depots;
	}
	
	public int getNbDepots() {
		return this.getArray().size();		
	}
	
	public double salaire() {
		return super.salaire() + this.getNbDepots()*500;
	} //Calculé avec une prime de 500€ par dêpot géré.
	
	
}
