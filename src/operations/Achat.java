package operations;
import acteurs.*;
import main.Main;

import java.util.ArrayList;

public class Achat extends Transaction {
	private static final long serialVersionUID = 8714194005142678952L;
	int prixTotal;
	Fournisseur fournisseur;
	
	public Achat(ArrayList<LigneCom> arr, Fournisseur fournisseur) {
		super(arr);
		this.fournisseur = fournisseur;
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		Main.achats.add(this);
		if (this.fournisseur!=null) this.fournisseur.getArray().add(this);
	}

	public String toString() {
		return this.fournisseur.getNom() + ", le " + this.getDateString();
	}
	
	public double montant() {
		double total = 0.0;
		for (LigneCom lig : this.lignes){
			total += lig.prixLigne();
		}
		return total;
	}
	
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	public Fournisseur getFournisseur() {
		return this.fournisseur;
	}
	
	public void remove(){
		this.fournisseur.getArray().remove(this);
		Main.achats.remove(this);
	}
	
	public void setArray(ArrayList<LigneCom> a) {
		this.lignes = a;
	}
	
	public ArrayList<LigneCom> getArray(){
		return this.lignes;
	}
	
	public void acheter() {
		if (this.lignes!=null) {
			for (LigneCom l : this.lignes) {
				(Main.getProduitByID(l.getProduit().getID())).approvisionner(l.getQte());
			}
		}
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Achat a : Main.achats) {
			if (id==a.getID()) exists = true;
		}
		return exists;
	}
}
