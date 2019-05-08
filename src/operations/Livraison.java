package operations;
import java.io.Serializable;

import main.Main;
import personnel.Livreur;

public class Livraison implements Serializable {
	private static final long serialVersionUID = -3381251010991403725L;
	int id;
	private double distance;
	private Livreur livreur;
	private Vente vente;
	private boolean etat;
	
	public Livraison(Livreur livreur, Vente vente, double distance) {
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		this.livreur = livreur;
		this.vente = vente;
		this.distance = distance;
		this.etat = false;
		Main.livraisons.add(this);
		if (this.livreur!=null) this.livreur.getArray().add(this);
	}
	
	// GETTERS & SETTERS
	public Livreur getLivreur() {
		return this.livreur;
	}
	
	public Vente getVente() {
		return this.vente;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setLivreur(Livreur l) {
		this.livreur = l;
		l.getArray().add(this);
		l.addKm(this.getDistance());
	}
	
	public void setState(boolean s) {
		this.etat = s;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	// METHODS
	public void remove() {
		this.livreur.getArray().remove(this);
		Main.livraisons.remove(Main.getLivraisonByID(this.getID()));
	}
	
	public boolean getState() {
		return this.etat;
	}
	
	public String toString() {
		return this.getVente().getClient().toString() + ", " + (float) Math.round(this.getDistance() * 100)/100 + " Km.";
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Livraison l : Main.livraisons) {
			if (id==l.getID()) exists = true;
		}
		return exists;
	}
}
