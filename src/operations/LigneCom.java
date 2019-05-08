package operations;
import java.io.Serializable;

import stock.Produit;

public class LigneCom implements Serializable {		// Cette classe décrit une ligne de commande, que ce soit un achat ou une vente.
	private static final long serialVersionUID = -3264255030365536977L;
	Produit produit;
	int qte;
	
	public LigneCom(Produit produit,int qte) {
		this.produit = produit;
		this.qte = qte;
	}
	
	public Produit getProduit() {
		return this.produit;
	}
	
	public int getQte() {
		return this.qte;
	}
	
	public double prixLigne() {
		return this.produit.getPrix()*this.qte;
	}
	
	public String toString() {
		return this.getProduit().toString() + " x" + this.getQte() + ". ";
	}
}
