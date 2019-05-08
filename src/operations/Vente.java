package operations;
import main.Main;
import personnel.Caissier;

import java.util.ArrayList;

import acteurs.Client;

public class Vente extends Transaction {
	private static final long serialVersionUID = 2633868357672352640L;
	int reduction;
	int prixTotal;
	Client client;
	Caissier caissier;
	ArrayList<LigneCom> lignes;
	
	public Vente(ArrayList<LigneCom> arr,Caissier caissier, Client client, int reduction) {
		super(arr);
		this.caissier = caissier;
		this.client = client;
		this.reduction = reduction;
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		Main.ventes.add(this);
		if (this.client!=null) this.client.getArray().add(this);
		if (this.caissier!=null) this.caissier.getArray().add(this);
	}

	
	public void setCaissier(Caissier caissier) {
		if (this.caissier!=null) {
			this.caissier.getArray().remove(this);
		}
		this.caissier = caissier;
		this.caissier.getArray().add(this);
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void setReduction(int r) {
		this.reduction = r;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public Caissier getCaissier() {
		return this.caissier;
	}
	
	public ArrayList<LigneCom> getArray(){
		return this.lignes;
	}
	
	public void setArray(ArrayList<LigneCom> a) {
		this.lignes = a;
	}
	
	public int getReduction() {
		return this.reduction;
	}
	
	public void vendre() {
		if (this.lignes!=null) {
			for (LigneCom l : this.lignes) {
				(Main.getProduitByID(l.getProduit().getID())).vendre(l.getQte());
			}
		}
	}

	@Override
	public String toString() {
		return this.getClient() + ", le " + this.getDateString();
	}
	
	public double montant() {
		double total = 0.0;
		for (LigneCom lig : this.lignes){
			total += lig.prixLigne();
		}
		return total-(total*reduction/100);
	}

	public void remove(){
		if (this.lignes!=null) {
			for (LigneCom l : this.lignes) {
				Main.getProduitByID((l.getProduit().getID())).approvisionner(l.getQte());
			}
		}
		if (this.client!=null) this.client.getArray().remove(this);
		if (this.caissier!=null) this.caissier.getArray().remove(this);
		Main.ventes.remove(Main.getVenteByID(this.getID()));
		try {Main.livraisons.remove(Main.getLivraisonByVenteID(this.getID()));} catch (Exception e) {};
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Vente v : Main.ventes) {
			if (id==v.getID()) exists = true;
		}
		return exists;
	}
}
