package graphic;
import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import graphic.*;
import graphic.create.NewFournisseur;
import graphic.create.NewProduit;
import graphic.edit.ChangePassword;
import graphic.edit.EditProduit;
import personnel.*;
import operations.*;
import stock.*;
import acteurs.*;
import main.Main;

@SuppressWarnings({ "unused", "serial" })
public class GStockUI extends JFrame implements ActionListener {
	JTabbedPane main;
	JPanel affichage,affichage_1,edit,achat,stats,logoffp,buttons,empInfo,statButtons,saisie,okcancel,list,listheader,fourn_add;
	JLabel idL,nomL,adrL,loginL,passL,salaireHorL,nbHL,salaireL,RIBL,depsL,fournL,produitL,qteL,headerL,totalL;
	JTextField id,nom,adr,login,pass,salaireHor,nbH,salaire,RIB,deps,qte;
	JButton prodnew,prodedit,proddel,logoff,modifyID,check,ret,ok,cancel,valider,newfourn;
	static JTable table,info;
	public static JComboBox<Depot> depots,depots2;
	public static JComboBox<Fournisseur> fourn;
	static JComboBox<Produit> produits,produit2;
	GerantStock user;
	ArrayList<LigneCom> commande = new ArrayList<LigneCom>();;
	static Achat a = new Achat(null,null);
	
	public GStockUI(GerantStock user) {
		this.user = user;
		this.setTitle("Gestion du Stock | [" + this.user.getNom() + "]@Magasin");
		this.setBounds(100,100,1000,300);
		
		main = new JTabbedPane();
		
		
		// PANEL AFFICHAGE
		check = new JButton("Vérifier rupture");		check.addActionListener(this);
		affichage = new JPanel(new BorderLayout());
		affichage.setBorder(new EmptyBorder(5,5,5,5));
		depots = new JComboBox<Depot>();
		depots.addActionListener(this);
		updateDepots(depots,this.user);
		table = new JTable();
		try {
			table.setModel(updateTable(Main.produits,(Depot)depots.getSelectedItem()));
		} catch (Exception e) {
			// Prevent NullPointerException on JComboBox when <GerantStock> has no <Depot> in its array.
		};
		table.setEnabled(false);
		affichage.add(new JLabel("Les employés que je gère :"),BorderLayout.NORTH);
		affichage_1 = new JPanel(new BorderLayout());
		affichage_1.setBorder(new EmptyBorder(3,5,10,5));
		affichage_1.add(new JLabel("Dépôt : "),BorderLayout.WEST);
		affichage_1.add(depots,BorderLayout.CENTER);
		affichage_1.add(check,BorderLayout.EAST);
		affichage.add(affichage_1,BorderLayout.NORTH);
		affichage.add(new JScrollPane(table),BorderLayout.CENTER);
		
		
		// PANEL EDIT
		edit = new JPanel(new BorderLayout());
		edit.setBorder(new EmptyBorder(5,5,5,5));
		depots2 = new JComboBox<Depot>();
		depots2.addActionListener(this);
		updateDepots(depots2,this.user);
		produits = new JComboBox<Produit>();
		try {
			updateProduits((Main.getDepotByID(((Depot)depots2.getSelectedItem()).getID())));
		} catch (NullPointerException e) {}
		prodnew = new JButton("Ajouter");			prodnew.addActionListener(this);
		prodedit = new JButton("Modifier");			prodedit.addActionListener(this);
		proddel = new JButton("Supprimer");			proddel.addActionListener(this);
		buttons = new JPanel(new GridLayout(1,3));
		buttons.add(prodnew);
		buttons.add(prodedit);
		buttons.add(proddel);
		edit.add(new JLabel("Edition des Produits gérés, choisir le dépot puis le produit : "), BorderLayout.NORTH);
		JPanel choose = new JPanel(new GridLayout(6,1,7,7));
		choose.add(depots2);
		choose.add(produits);
		edit.add(choose,BorderLayout.CENTER);
		edit.add(buttons,BorderLayout.SOUTH);
		
		
		// PANEL STATS
		stats = new JPanel(new BorderLayout());
		empInfo = new JPanel(new GridLayout(10,2));
		statButtons = new JPanel(new GridLayout(1,2));
		idL = new JLabel("ID Employé: ");					id = new JTextField();				id.setEditable(false);
		nomL = new JLabel("Nom: ");							nom = new JTextField();				nom.setEditable(false);
		adrL = new JLabel("Adresse: ");						adr = new JTextField();				adr.setEditable(false);
		loginL = new JLabel("Login: ");						login = new JTextField();			login.setEditable(false);
		passL = new JLabel("Mot de passe: ");				pass = new JTextField();			pass.setEditable(false);
		salaireHorL = new JLabel("Salaire Horaire: ");		salaireHor = new JTextField();		salaireHor.setEditable(false);
		nbHL = new JLabel("Nombre d'heures par semaine: ");	nbH = new JTextField();				nbH.setEditable(false);
		salaireL = new JLabel("Salaire : ");				salaire = new JTextField();			salaire.setEditable(false);
		RIBL = new JLabel("RIB: ");							RIB = new JTextField();				RIB.setEditable(false);
		depsL = new JLabel("Dépots gérés:");				deps = new JTextField();			deps.setEditable(false);
		modifyID = new JButton("Modifier Identifiants");		modifyID.addActionListener(this);
		ret = new JButton("Retour à la gestion");				ret.addActionListener(this);
		
		getStats();
		empInfo.add(idL);			empInfo.add(id);
		empInfo.add(nomL);			empInfo.add(nom);
		empInfo.add(adrL);			empInfo.add(adr);
		empInfo.add(loginL);		empInfo.add(login);
		empInfo.add(passL);			empInfo.add(pass);
		empInfo.add(salaireHorL);	empInfo.add(salaireHor);
		empInfo.add(nbHL);			empInfo.add(nbH);
		empInfo.add(salaireL);		empInfo.add(salaire);
		empInfo.add(RIBL);			empInfo.add(RIB);
		empInfo.add(depsL);			empInfo.add(deps);
		statButtons.add(modifyID);
		statButtons.add(ret);
		stats.add(empInfo, BorderLayout.CENTER);
		stats.add(statButtons, BorderLayout.SOUTH);
		
		
		// PANEL ACHAT
		achat = new JPanel(new GridLayout(1,2));
		saisie = new JPanel(new GridLayout(5,2,10,20));
		okcancel = new JPanel(new GridLayout(2,1,0,1));
		list = new JPanel(new BorderLayout());
		listheader = new JPanel(new BorderLayout());
		fourn_add = new JPanel(new BorderLayout());
		
		Main.achats.remove(a);// a temporaire
		
		fournL = new JLabel("Fournisseur");					fourn = new JComboBox<Fournisseur>();		fournL.setLabelFor(fourn);		
		produitL = new JLabel("Produit");					produit2 = new JComboBox<Produit>();		produitL.setLabelFor(produit2);
		qteL = new JLabel("Quantité");						qte = new JTextField();						qteL.setLabelFor(qte);
		
		ok = new JButton("Ajouter");						ok.addActionListener(this);			okcancel.add(ok);
		cancel = new JButton("Annuler");					cancel.addActionListener(this);		okcancel.add(cancel);
		valider = new JButton("Valider la commande");		valider.addActionListener(this);	
		newfourn = new JButton("New");						newfourn.addActionListener(this);
		
		updateFournisseurs();
		updateProduits();
		
		saisie.add(fournL);
		fourn_add.add(fourn,BorderLayout.CENTER);
		fourn_add.add(newfourn,BorderLayout.EAST);
		saisie.add(fourn_add);
		saisie.add(produitL);
		saisie.add(produit2);
		saisie.add(qteL);
		saisie.add(qte);
		saisie.add(new JLabel());
		saisie.add(new JLabel());
		saisie.add(okcancel);
		saisie.add(valider);
		saisie.setBorder(new EmptyBorder(5,5,5,5));
		
		String [] headers = {"Produit","Quantité","Prix"};
		info = new JTable();
		info.setModel(new DefaultTableModel(headers,20));
		info.setEnabled(false);
		headerL = new JLabel("Aperçu de la commande");
		listheader.add(headerL,BorderLayout.NORTH);
		listheader.add(info.getTableHeader(),BorderLayout.SOUTH);
		totalL = new JLabel("Total : ");
		list.add(listheader,BorderLayout.NORTH);
		list.add(new JScrollPane(info),BorderLayout.CENTER);
		list.add(totalL,BorderLayout.SOUTH);
		
		achat.add(saisie);
		achat.add(list);
		
		
		// PANEL LOGOFF
		logoff = new JButton("Déconnexion");		logoff.addActionListener(this);
		logoffp = new JPanel(new BorderLayout());
		logoffp.setBackground(new Color(40, 65, 104));
		JLabel bye = new JLabel("Au revoir, " + this.user.getNom());
		bye.setForeground(Color.WHITE);
		logoffp.add(bye);
		logoffp.add(logoff,BorderLayout.SOUTH);
		logoffp.setBorder(new EmptyBorder(80,80,80,80));
		
		
		// MISE EN PLACE
		main.addTab("Affichage", null, affichage, "Afficher les dépots et les produits que vous gérez");
		main.addTab("Edition", null, edit, "Modifier les information relatives à vos produits");
		main.addTab("Achat", null, achat, "Réapprovisionner le stock dans vos dépots");
		main.addTab("Stats", null, stats, "Afficher les informations vous concernant");
		main.addTab("Déconnexion", null, logoffp, "Vous déconnecter");

		
		//Demande de sauvegarde en quittant la fenêtre
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	JDialog.setDefaultLookAndFeelDecorated(true);
			    int response = JOptionPane.showConfirmDialog(null, "Voulez-vous enregistrer les modifications ?", "Enregistrement",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.NO_OPTION) {
			    	//javax.swing.JOptionPane.showMessageDialog(null, "Aucune modification ne sera enregistrée.");
			    } else if (response == JOptionPane.YES_OPTION) {
			    	Main.save();
			    	javax.swing.JOptionPane.showMessageDialog(null, "Les modifications ont été enregistrées.");
			    } else if (response == JOptionPane.CLOSED_OPTION) {
			    	//javax.swing.JOptionPane.showMessageDialog(null, "Aucune modification ne sera enregistrée.");
			    }
		    }
		});
		
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		stock_check();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==depots) {
			table.setModel(updateTable(Main.produits,(Depot)depots.getSelectedItem()));
		}
		if (e.getSource()==check) {
			if (stock_check()) {} else {alert("Aucun produit en rupture de stock.");}
		}
		if (e.getSource()==depots2) {
			updateProduits((Main.getDepotByID(((Depot)depots2.getSelectedItem()).getID())));
		}
		if (e.getSource()==prodnew) {
			JDialog.setDefaultLookAndFeelDecorated(true);
			JDialog d = new NewProduit(this.user);
			d.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosed(WindowEvent e) {
			    	updateProduits();
			    	table.setModel(updateTable(Main.produits,(Depot)depots.getSelectedItem()));
			    }
			});
		}
		if (e.getSource()==prodedit) {
			try {
				//JFrame edt = new EditProduit(this.user,Main.getProduitByID(((Produit)produits.getSelectedItem()).getID())); 
				JFrame edt = new EditProduit(this.user,((Produit)produits.getSelectedItem())); 
				edt.addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosed(WindowEvent e) {
				    	table.setModel(updateTable(Main.produits,(Depot)depots.getSelectedItem()));
				    	updateProduits((Depot)depots2.getSelectedItem());
				    }
				});
			} catch (Exception exc) {};
		}
		if (e.getSource()==proddel) {
			try { ((Produit)produits.getSelectedItem()).remove(); } catch (Exception exc) {};
			updateProduits((Main.getDepotByID(((Depot)depots2.getSelectedItem()).getID())));
			try {
				table.setModel(updateTable(Main.produits,(Depot)depots.getSelectedItem()));
			} catch (Exception exc) {};
		}
		if (e.getSource()==newfourn) {
			new NewFournisseur();
		}
		if (e.getSource()==ok) {
			if (check()){
				fourn.setEnabled(false);
				headerL.setText("Aperçu de la commande" + " | Fournisseur : " + fourn.getSelectedItem());
				LigneCom line = new LigneCom(Main.getProduitByID(((Produit)produit2.getSelectedItem()).getID()),Integer.parseInt(qte.getText()));
				commande.add(line);
				a.setArray(commande);
				totalL.setText("Total : " + a.montant());
				String[] t = {"Produit","Quantité","Prix"};
				info.setModel(updateCommande(commande));
			}
		}
		if (e.getSource()==cancel) {
			this.reset();
			commande = new ArrayList<LigneCom>();
			a.setArray(commande);
			String[] t = {"Produit","Quantité","Prix"};
			info = new JTable(new Object[50][50],t);
			list.add(info,BorderLayout.CENTER);
		}
		if (e.getSource()==valider) {
			if (a.getArray()==null) {
				alert("Vente vide.");
			} else {
				Achat rec = new Achat(commande,Main.getFournisseurByID(((Fournisseur)fourn.getSelectedItem()).getID()));
				rec.acheter();
				table.setModel(updateTable(Main.produits,(Depot)depots.getSelectedItem()));
				this.reset();
				commande = new ArrayList<LigneCom>();
				alert("Achat enregistré ! Montant total : " + rec.montant());
				a.setFournisseur(null);
				a.setArray(commande);
			}
		}
		if (e.getSource()==modifyID) {
			JDialog.setDefaultLookAndFeelDecorated(true);
			JDialog d = new ChangePassword(this.user);
			d.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosed(WindowEvent e) {
			    	getStats();
			    }
			});
		}
		if (e.getSource()==logoff) {
			this.dispose();
			new Login();
		}
	}
	
	public static DefaultTableModel updateTable(ArrayList<Produit> produits,Depot d){
		String [] headers = {"ID","Marque","Modèle","Prix","Qte en stock","Stock min."};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Produit p : produits) {
			if (p.getDepot().getID()==d.getID()) {
				Object[] data = {p.getID(),p.getMarque(),p.getModele(),p.getPrix(),p.getQteStock(),p.getStockMin()};
				m.addRow(data);
			}
		}
		return m;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateDepots(JComboBox<Depot> box,GerantStock user) {
		box.setModel(new DefaultComboBoxModel(user.getArray().toArray()));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })	// Pour le panel Edition, un seul dépôt
	public static void updateProduits(Depot dep) {
		ArrayList<Produit> arr = new ArrayList<Produit>();
		for (Produit p : Main.produits) {
			if (p.getDepot().getID()==dep.getID()) {
				arr.add(p);
			}
		}
		produits.setModel(new DefaultComboBoxModel(arr.toArray()));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })	// Pour le panel Achat, tous dépôts gérés par user
	public void updateProduits() {
		produit2.setModel(new DefaultComboBoxModel(allDepProds().toArray()));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateFournisseurs() {
		fourn.setModel(new DefaultComboBoxModel(Main.fournisseurs.toArray()));
	}
	
	DefaultTableModel updateCommande(ArrayList<LigneCom> commande){
		String [] headers = {"Produit","Quantité","Prix"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (LigneCom l : commande) {
			Object[] data = {l.getProduit(),l.getQte(),l.prixLigne()};
			m.addRow(data);
		}
		return m;
	}
	
	ArrayList<Produit> allDepProds() {
		ArrayList<Produit> arr = new ArrayList<Produit>();
		for (Depot d : this.user.getArray()) {
			for (Produit p : Main.produits) {
				if (p.getDepot().getID()==d.getID()) arr.add(p);
			}
		}
		return arr;
	}
	
	public boolean check() {
		if ((qte.getText().equals("")) || (Integer.parseInt(qte.getText())<=0)) {
			javax.swing.JOptionPane.showMessageDialog(null,"Un ou plusieurs champs invalides.");
			return false;
		} else return true;
	}
	
	private boolean stock_check() {
		JDialog.setDefaultLookAndFeelDecorated(true);
		boolean rupture = false;
		String s = "Vous avez des produits en rupture de stock dans vos dépôts\n";
		for (Produit p : allDepProds()) {
			if (Main.getProduitByID(p.getID()).getQteStock()<Main.getProduitByID(p.getID()).getStockMin()) {
				rupture = true;
				s+= Main.getProduitByID(p.getID()).toString() + ", Dépôt " + Main.getProduitByID(p.getID()).getDepot().getNom() + ", " + Main.getProduitByID(p.getID()).getQteStock() + " en stock.\n";
			}
		}
		if (rupture) JOptionPane.showMessageDialog(this,s,"Attention !",JOptionPane.WARNING_MESSAGE);
		return rupture;
	}
	
	public void reset() {
		fourn.setEnabled(true);
		qte.setText("");
	}
	
	public void getStats() {
		id.setText("E0" + user.getID());
		nom.setText(user.getNom());
		adr.setText(user.getAdresse());
		login.setText(user.getLogin());
		pass.setText(user.getPassword());
		salaireHor.setText(user.getSalaireHor()+"€");
		nbH.setText(user.getNbH()+" h");
		salaire.setText(user.salaire()+"€");
		RIB.setText(user.getRIB());
		deps.setText(this.user.getArray().toString());
	}
	
	void alert(String s) {
		javax.swing.JOptionPane.showMessageDialog(null,s);
	}
}
