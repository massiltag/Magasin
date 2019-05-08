package graphic;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import main.Main;
import operations.*;
import personnel.*;
import stock.*;
import acteurs.*;

@SuppressWarnings({ "unused", "serial" })
public class OverviewUI extends JFrame implements ActionListener {
	JTabbedPane main;
	JPanel logoffp;
	JTable employes,produits,clients,ventes,fournisseurs,achats,depots;
	JButton logoff;
	
	public OverviewUI() {
		this.setTitle("Vue d'ensemble du magasin");
		this.setBounds(100,100,1000,300);
		main = new JTabbedPane();
		employes = new JTable(getEmployes());				employes.setEnabled(false);
		produits = new JTable(getProduits());				produits.setEnabled(false);
		clients = new JTable(getClients());					clients.setEnabled(false);
		ventes = new JTable(getVentes());					ventes.setEnabled(false);
		fournisseurs = new JTable(getFournisseurs());		fournisseurs.setEnabled(false);
		achats = new JTable(getAchats());					achats.setEnabled(false);
		depots = new JTable(getDepots());					depots.setEnabled(false);
		
		employes.getColumnModel().getColumn(0).setPreferredWidth(10);
		employes.getColumnModel().getColumn(1).setPreferredWidth(120);
		employes.getColumnModel().getColumn(7).setPreferredWidth(60);
		
		produits.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		produits.getColumnModel().getColumn(0).setPreferredWidth(30);
		produits.getColumnModel().getColumn(1).setPreferredWidth(162);
		produits.getColumnModel().getColumn(2).setPreferredWidth(162);
		produits.getColumnModel().getColumn(3).setPreferredWidth(80);
		produits.getColumnModel().getColumn(4).setPreferredWidth(80);
		produits.getColumnModel().getColumn(5).setPreferredWidth(80);
		produits.getColumnModel().getColumn(6).setPreferredWidth(388);
		
		clients.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		clients.getColumnModel().getColumn(0).setPreferredWidth(30);
		clients.getColumnModel().getColumn(1).setPreferredWidth(475);
		clients.getColumnModel().getColumn(2).setPreferredWidth(475);
		
		ventes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ventes.getColumnModel().getColumn(0).setPreferredWidth(140);
		ventes.getColumnModel().getColumn(1).setPreferredWidth(140);
		ventes.getColumnModel().getColumn(2).setPreferredWidth(550);
		ventes.getColumnModel().getColumn(3).setPreferredWidth(70);
		ventes.getColumnModel().getColumn(4).setPreferredWidth(80);
		
		achats.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		achats.getColumnModel().getColumn(0).setPreferredWidth(150);
		achats.getColumnModel().getColumn(1).setPreferredWidth(682);
		achats.getColumnModel().getColumn(2).setPreferredWidth(150);
		
		main.addTab("Employés", null, new JScrollPane(employes), "Afficher tous les employés du magasin");
		main.addTab("Produits", null, new JScrollPane(produits), "Visualiser tous les produits en stock");
		main.addTab("Ventes", null, new JScrollPane(ventes), "Vue d'ensemble sur les ventes effectuées");
		main.addTab("Achats", null, new JScrollPane(achats), "Vue d'ensemble sur les achats effectués");
		main.addTab("Clients", null, new JScrollPane(clients), "Visualiser la liste des clients");
		main.addTab("Fournisseurs", null, new JScrollPane(fournisseurs), "Visualiser la liste des fournisseurs");
		main.addTab("Dépôts", null, new JScrollPane(depots), "Visualiser la liste des dépôts");
		
		logoff = new JButton("Déconnexion");		logoff.addActionListener(this);
		logoffp = new JPanel(new BorderLayout());
		logoffp.setBackground(new Color(40, 65, 104));
		JLabel bye = new JLabel("Au revoir, Administrateur");
		bye.setForeground(Color.WHITE);
		logoffp.add(bye);
		logoffp.add(logoff,BorderLayout.SOUTH);
		logoffp.setBorder(new EmptyBorder(80,80,80,80));
		
		main.addTab("Déconnexion", null, logoffp, "Déconnectez-vous");
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		new Login();
	}
	
	
	DefaultTableModel getEmployes(){
		String [] headers = {"ID","Nom","Rôle","Adresse","Login","Salaire Horaire","Nb. Heures/sem","Salaire","RIB","Responsable","Infos comp."};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Employe e : Main.employes) {
			Object[] data = {e.getID(),e.getNom(),e.getClass().getSimpleName(),e.getAdresse(),e.getLogin(),e.getSalaireHor()+" €",e.getNbH(),(e.salaire())+" €",e.getRIB(),null,null};
			if (e.getGerant()!=null) data[9] = e.getGerant().getNom(); else data[9] = " - ";
			String type = e.getClass().getName();
			switch(type) {
				case "personnel.Caissier":
					data[10] = "Caisse " + ((Caissier)e).getNCaisse();
					break;
				case "personnel.GerantStock":
					data[10] = "Dépôts " + ((GerantStock)e).getArray().toString();
					break;
				case "personnel.Livreur":
					data[10] = ((Livreur)e).getNbLiv() + " Livraisons";
					break;
				default : 
					data[10] = "Aucune";
			}
			m.addRow(data);
		}
		return m;
	}
	
	public static DefaultTableModel getProduits(){
		String [] headers = {"ID","Marque","Modèle","Prix","Qte en stock","Stock min.","Dépôt"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Produit p : Main.produits) {
			Object[] data = {p.getID(),p.getMarque(),p.getModele(),p.getPrix()+" €",p.getQteStock(),p.getStockMin(),p.getDepot().toString()};
			m.addRow(data);
		}
		return m;
	}
	
	public static DefaultTableModel getClients(){
		String [] headers = {"ID","Nom","Adresse"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Client c : Main.clients) {
			Object[] data = {c.getID(),c.getNom(),c.getAdresse()};
			m.addRow(data);
		}
		return m;
	}
	
	public static DefaultTableModel getVentes(){
		String [] headers = {"Client","Caissier","Contenu","Réduction","Montant"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Vente v : Main.ventes) {
			Object[] data = {v.getClient().getNom(),v.getCaissier().getNom(),v.getArray(),v.getReduction()+"%",v.montant()+" €"};
			m.addRow(data);
		}
		return m;
	}
	
	public static DefaultTableModel getFournisseurs(){
		String [] headers = {"Nom","Adresse"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Fournisseur f : Main.fournisseurs) {
			Object[] data = {f.getNom(),f.getAdresse()};
			m.addRow(data);
		}
		return m;
	}
	
	public static DefaultTableModel getAchats(){
		String [] headers = {"Fournisseur","Contenu","Montant"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Achat a : Main.achats) {
			Object[] data = {a.getFournisseur(),a.getArray(),a.montant()+" €"};
			m.addRow(data);
		}
		return m;
	}
	
	public static DefaultTableModel getDepots(){
		String [] headers = {"Nom","Adresse","Gérant","Nb. produits"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Depot d : Main.depots) {
			Object[] data = {d.getNom(),d.getAdresse(),d.getGerant(),d.getArray().size()};
			m.addRow(data);
		}
		return m;
	}
	
}
