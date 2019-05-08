package main;
import acteurs.*;
import operations.*;
import personnel.*;
import stock.*;
import graphic.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Main {
	public static ArrayList<Employe> employes;
	public static ArrayList<Achat> achats;
	public static ArrayList<Vente> ventes;
	public static ArrayList<Livraison> livraisons;
	public static ArrayList<Depot> depots;
	public static ArrayList<Marque> marques;
	public static ArrayList<Produit> produits;
	public static ArrayList<Client> clients;
	public static ArrayList<Fournisseur> fournisseurs;
	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;

	public static void main(String[] args) {
		// TODO Auto-generated method stub					
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		load();
		new Login();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Depot getDepotByID(int id) {
		Depot res = null;
		for (Depot d : Main.depots) {
			if (d.getID()==id) res = d;
		}
		return res;
	}
	
	public static Employe getEmployeByID(int id) {
		Employe res = null;
		for (Employe a : Main.employes) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	public static Achat getAchatByID(int id) {
		Achat res = null;
		for (Achat a : Main.achats) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	public static Vente getVenteByID(int id) {
		Vente res = null;
		for (Vente a : Main.ventes) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	public static Livraison getLivraisonByID(int id) {
		Livraison res = null;
		for (Livraison a : Main.livraisons) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	public static Livraison getLivraisonByVenteID(int id) {
		Livraison res = null;
		for (Livraison a : Main.livraisons) {
			if (a.getVente().getID()==id) res = a;
		}
		return res;
	}
	
	public static Marque getMarqueByID(int id) {
		Marque res = null;
		for (Marque a : Main.marques) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	public static Produit getProduitByID(int id) {
		Produit res = null;
		for (Produit a : Main.produits) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	public static Client getClientByID(int id) {
		Client res = null;
		for (Client a : Main.clients) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	public static Fournisseur getFournisseurByID(int id) {
		Fournisseur res = null;
		for (Fournisseur a : Main.fournisseurs) {
			if (a.getID()==id) res = a;
		}
		return res;
	}
	
	
	static void serialize(Object o,String path) {
		try {
			File out = new File(path);
			oos = new ObjectOutputStream(new FileOutputStream(out));
			oos.writeObject(o);
		} catch (Exception e) {
			System.out.println("Read Error");
		}
	}
	
	static Object loadfile(String path) {
		try {
			File in = new File(path);
			ois = new ObjectInputStream(new FileInputStream(in));
			return ois.readObject();
		} catch (Exception e) {
			System.out.println("Read Error");
			return 0;
		}
	}
	
	public static void save() {
		serialize(employes,"save/employes.save");
		serialize(achats,"save/achats.save");
		serialize(ventes,"save/ventes.save");
		serialize(livraisons,"save/livraisons.save");
		serialize(depots,"save/depots.save");
		serialize(marques,"save/marques.save");
		serialize(produits,"save/produits.save");
		serialize(clients,"save/clients.save");
		serialize(fournisseurs,"save/fournisseurs.save");
	}
	
	@SuppressWarnings("unchecked")
	public static void load() {
		employes = (ArrayList<Employe>) loadfile("save/employes.save");
		clients = (ArrayList<Client>) loadfile("save/clients.save");
		fournisseurs = (ArrayList<Fournisseur>) loadfile("save/fournisseurs.save");
		achats = (ArrayList<Achat>) loadfile("save/achats.save");
		ventes = (ArrayList<Vente>) loadfile("save/ventes.save");
		livraisons = (ArrayList<Livraison>) loadfile("save/livraisons.save");
		depots = (ArrayList<Depot>) loadfile("save/depots.save");
		marques = (ArrayList<Marque>) loadfile("save/marques.save");
		produits = (ArrayList<Produit>) loadfile("save/produits.save");
	}
}
