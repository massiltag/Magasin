package personnel;
import java.util.ArrayList;

import operations.Vente;

public class Caissier extends Employe {
	private static final long serialVersionUID = 126084436942599216L;
	int ncaisse;			//Numero de caisse
	private ArrayList<Vente> ventes;
	
	public Caissier(String nom, String adresse, String login, String password, double salaireHor, String RIB, int nbH, int ncaisse, GerantPersonnel gerant){
		super(nom, adresse, login, password, salaireHor, RIB, nbH, gerant);
		this.ncaisse = ncaisse;
		ventes = new ArrayList<Vente>();
	}
	
	public String toString() {
		return super.toString() + " Chargé de caisse " + this.getNCaisse() + ".\n";
	}
	
	public void setNCaisse(int ncaisse) {
		this.ncaisse = ncaisse;
	}
	
	public int getNCaisse() {
		return this.ncaisse;
	}
	
	public ArrayList<Vente> getArray(){
		return this.ventes;
	}
	
	public int getNBVentes() {
		return this.ventes.size();
	}
	
	public double salaire() {
		return super.salaire() + this.getNBVentes()*0.40;
	}	//Calcule avec une prime de 40 centimes par vente.
	
}