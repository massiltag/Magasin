package stock;
import java.io.Serializable;

import main.Main;

public class Produit implements Serializable {
	private static final long serialVersionUID = -6341684865234699296L;
	private int id;
	private Marque marque;
	private String modele;
	private double prix;
	private int qteStock;
	private int stock_min;
	private Depot depot;		
	
	public Produit(Marque marque, String modele, double prix, int stock_min, Depot depot){
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		this.marque = marque;
		this.modele = modele;
		this.prix = prix;
		this.qteStock = 0;
		this.stock_min = stock_min;
		this.depot = depot;
		
		this.marque.getArray().add(this);
		this.depot.produits.add(this);
		Main.produits.add(this);
	}
	
	// SETTERS
	public void setID(int id) {
		this.id = id;
	}
	
	public void setMarque(Marque marque) {
		this.marque.getArray().remove(this);
		this.marque = marque;
		this.marque.getArray().add(this);
	}
	
	public void setModele(String modele) {
		this.modele = modele;
	}
	
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	public void setQteStock(int qte) {
		this.qteStock = qte;
	}
	
	public void setStockMin(int stock_min) {
		this.stock_min = stock_min;
	}
	
	public void setDepot(Depot depot) {
		this.depot = depot;
	}
	
	// GETTERS
	public int getID() {
		return this.id;
	}
	
	public Marque getMarque() {
		return this.marque;
	}
	
	public String getModele() {
		return this.modele;
	}
	
	public double getPrix() {
		return this.prix;
	}
	
	public int getStockMin() {
		return this.stock_min;
	}
	
	public int getQteStock() {
		return this.qteStock;
	}
	
	public Depot getDepot() {
		return this.depot;
	}
	
	public String getGerant() {
		return this.depot.getGerant();		//Un produit est présent dans un seul dépot, son stock est donc géré par un seul gérant.	
	}
	
	// METHODS
	public String toString() {
		return this.getMarque() + " " + this.getModele() + " ";
	}
	
	public void approvisionner(int qte) {
		this.qteStock += qte;
	}
	
	public void vendre(int qte) {
		this.qteStock -= qte;
	}
	
	public void move(Depot d) {
		Main.getDepotByID(this.depot.getID()).produits.remove(Main.getProduitByID(this.getID()));
		this.depot = d;
		d.add(Main.getProduitByID(this.getID()));
	}
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Produit p : Main.produits) {
			if (id==p.getID()) exists = true;
		}
		return exists;
	}
	
	public void remove() {
		this.marque.getArray().remove(this);
		this.depot.getArray().remove(this);
		Main.produits.remove(Main.getProduitByID(this.getID()));
	}
	
}
