package graphic;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import java.util.ArrayList;
import java.util.Random;

import main.Main;
import operations.*;
import personnel.*;
import acteurs.*;
import stock.*;
import graphic.create.NewClient;
import graphic.edit.ChangePassword;



@SuppressWarnings({ "unused", "serial" })
public class CaissierUI extends JFrame implements ActionListener, MouseListener {
	Caissier user;
	JMenuBar mbar;
	JMenu vente,edit,stats,logoff;
	JPanel main,saisie,okcancel,list,listheader,client_add;
	JTable info;
	JLabel clientL,produitL,qteL,reducL,livraisonL,headerL,totalL;
	JTextField qte,reduc;
	static JComboBox<Client> client;
	static JComboBox<Produit> produit;
	JComboBox<Vente> ventes;
	JButton ok,cancel,valider,newclient,show;
	JRadioButton livyes,livno;
	ButtonGroup liv;
	
	JPanel empInfo,statButtons;
	JLabel idL,nCaissL,nomL,adrL,loginL,passL,salaireHorL,nbHL,salaireL,RIBL,gerantL;
	JTextField id,nCaiss,nom,adr,login,pass,salaireHor,nbH,salaire,RIB,gerant;
	JButton modifyID,ret,del;
	
	LigneCom line;
	ArrayList<LigneCom> commande = new ArrayList<LigneCom>();
	Vente v = new Vente(commande, this.user, null, 0);
	
	public CaissierUI(Caissier user) {
		this.user = user;
		this.setTitle("Caisse - [" + user.getNom() + "]@CAISS0" + user.getNCaisse());
		this.setBounds(100,100,1000,350);
		
		mbar = new JMenuBar();
		vente = new JMenu("Vente");							vente.addMouseListener(this);		mbar.add(vente);
		edit = new JMenu("Edition");						edit.addMouseListener(this);		mbar.add(edit);	
		stats = new JMenu("Stats");							stats.addMouseListener(this);		mbar.add(stats);
		logoff = new JMenu("Déconnexion");					logoff.addMouseListener(this);		mbar.add(logoff);
		
		Main.ventes.remove(v); 			//v temporaire
		this.user.getArray().remove(v);	//v temporaire
		
		
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
		
		
		this.setJMenuBar(mbar);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==ok) {
			if (check()){
				v.setClient(Main.getClientByID(((Client)client.getSelectedItem()).getID()));
				client.setEnabled(false);
				headerL.setText("Aperçu de la commande" + " | Client: " + client.getSelectedItem());
				line = new LigneCom(Main.getProduitByID(((Produit)produit.getSelectedItem()).getID()),Integer.parseInt(qte.getText()));
				commande.add(line);
				v.setArray(commande);
				v.setReduction(Integer.parseInt((reduc.getText())));
				totalL.setText("Total : " + v.montant());
				String[] t = {"Produit","Quantité","Prix"};
				info.setModel(updateTable(commande));
			}
		}
		if (e.getSource()==cancel) {
			this.reset();
			commande = new ArrayList<LigneCom>();
			if (v!=null) v.setArray(commande);
			String[] t = {"Produit","Quantité","Prix"};
			info = new JTable(new Object[50][50],t);
			list.add(info,BorderLayout.CENTER);
		}
		if (e.getSource()==valider) {
			if (v.getArray()==null) {
				alert("Vente vide.");
			} else if (!checkreduc()){
				alert("Attention, vérifier réduction");
			} else {
				Vente rec = new Vente(null,null,null,0);
				rec.setArray(v.getArray());
				rec.setCaissier((Caissier)Main.getEmployeByID(user.getID()));
				rec.setClient(Main.getClientByID(((Client)client.getSelectedItem()).getID()));
				rec.setReduction(Integer.parseInt(reduc.getText()));
				rec.vendre();
				if (livyes.isSelected()) new Livraison(null,Main.getVenteByID(rec.getID()),150*(new Random().nextDouble()));
				this.reset();
				commande = new ArrayList<LigneCom>();
				alert("Vente enregistrée ! Montant total : " + rec.montant());
				v.setArray(commande);
				v.setClient(null);
				v.setReduction(0);
			}
		}
		if (e.getSource()==newclient) {
			NewClient n = new NewClient();
			n.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosed(WindowEvent e) {
			    	updateClient();
			    }
			});
		}
		if (e.getSource()==del) {
			if (ventes.getSelectedItem()!=null) ((Vente) ventes.getSelectedItem()).remove();
			updateVentes();
		}
		if (e.getSource()==show) {
			try {
				String[] t = {"Produit","Quantité","Prix"};
				ArrayList<LigneCom> com = new ArrayList<LigneCom>();
				com = ((Vente)ventes.getSelectedItem()).getArray();
				info.setModel(updateTable(com));
				list.add(info,BorderLayout.CENTER);
				totalL.setText("Total : " + ((Vente)ventes.getSelectedItem()).montant() + "€ | Avec réduction de : " + ((Vente)ventes.getSelectedItem()).getReduction() + "%");
				this.repaint();
			} catch (Exception exc) {
				alert("Séléction invalide.");
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
	}
	
	
	public void mousePressed(MouseEvent e){	
		if ((e.getSource()==vente)||(e.getSource()==ret)) {
			this.setContentPane(new JPanel());
			vente.removeMouseListener(this);
			stats.addMouseListener(this);
			edit.addMouseListener(this);
			
			main = new JPanel(new GridLayout(1,2));
			saisie = new JPanel(new GridLayout(6,2,10,20));
			okcancel = new JPanel(new GridLayout(2,1,0,1));
			list = new JPanel(new BorderLayout());
			listheader = new JPanel(new BorderLayout());
			client_add = new JPanel(new BorderLayout());
			
			clientL = new JLabel("Client");						client = new JComboBox<Client>();			clientL.setLabelFor(client);		
			produitL = new JLabel("Produit");					produit = new JComboBox<Produit>();			produitL.setLabelFor(produit);
			qteL = new JLabel("Quantité");						qte = new JTextField();						qteL.setLabelFor(qte);
			reducL = new JLabel("Réduction (sur total) %");		reduc = new JTextField("0");				reducL.setLabelFor(reduc);
			
			ok = new JButton("Ajouter");						ok.addActionListener(this);			okcancel.add(ok);
			cancel = new JButton("Annuler");					cancel.addActionListener(this);		okcancel.add(cancel);
			valider = new JButton("Valider la commande");		valider.addActionListener(this);	
			newclient = new JButton("New");						newclient.addActionListener(this);
			
			updateClient();
			updateProduit();
			
			liv = new ButtonGroup();
			livyes = new JRadioButton("Oui");
			livno = new JRadioButton("Non");
			livraisonL = new JLabel("Livraison");
			JPanel livyesno = new JPanel(new GridLayout(1,2));
			liv.add(livyes);		livno.setSelected(true);
			liv.add(livno);
			livyesno.add(livyes);
			livyesno.add(livno);
			
			saisie.add(clientL);
			client_add.add(client,BorderLayout.CENTER);
			client_add.add(newclient,BorderLayout.EAST);
			saisie.add(client_add);
			saisie.add(produitL);
			saisie.add(produit);
			saisie.add(qteL);
			saisie.add(qte);
			saisie.add(reducL);
			saisie.add(reduc);
			saisie.add(livraisonL);
			saisie.add(livyesno);
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
			
			main.add(saisie);
			main.add(list);
			
			this.setContentPane(main);
			this.setVisible(true);
		}
		
		if (e.getSource()==edit) {
			stats.addMouseListener(this);
			vente.addMouseListener(this);
			edit.removeMouseListener(this);
			this.setContentPane(new JPanel());
			
			main = new JPanel(new GridLayout(1,2));
			saisie = new JPanel(new GridLayout(5,1));
			okcancel = new JPanel(new GridLayout(1,3,0,1));
			list = new JPanel(new BorderLayout());
			listheader = new JPanel(new BorderLayout());
			
			show = new JButton("Afficher");						show.addActionListener(this);		okcancel.add(show);
			del = new JButton("Supprimer");						del.addActionListener(this);		okcancel.add(del);
			ret = new JButton("Retour à la vente");				ret.addMouseListener(this);			okcancel.add(ret);
			
			ventes = new JComboBox<Vente>();
			updateVentes();
			
			saisie.add(new JLabel("Mes ventes"));
			saisie.add(ventes);
			saisie.add(new JLabel());
			saisie.add(new JLabel());
			saisie.add(okcancel);
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
			list.add(info,BorderLayout.CENTER);
			list.add(totalL,BorderLayout.SOUTH);
			
			main.add(saisie);
			main.add(list);
			
			this.setContentPane(main);
			this.setVisible(true);
		}
		
		if (e.getSource()==stats) {
			stats.removeMouseListener(this);
			vente.addMouseListener(this);
			edit.addMouseListener(this);
			this.setContentPane(new JPanel());
			main = new JPanel(new BorderLayout());
			empInfo = new JPanel(new GridLayout(11,2));
			statButtons = new JPanel(new GridLayout(1,2));
			
			idL = new JLabel("ID Employé: ");					id = new JTextField();				id.setEditable(false);
			nCaissL = new JLabel("N° de Caisse: ");				nCaiss = new JTextField();			nCaiss.setEditable(false);
			nomL = new JLabel("Nom: ");							nom = new JTextField();				nom.setEditable(false);
			adrL = new JLabel("Adresse: ");						adr = new JTextField();				adr.setEditable(false);
			loginL = new JLabel("Login: ");						login = new JTextField();			login.setEditable(false);
			passL = new JLabel("Mot de passe: ");				pass = new JTextField();			pass.setEditable(false);
			salaireHorL = new JLabel("Salaire Horaire: ");		salaireHor = new JTextField();		salaireHor.setEditable(false);
			nbHL = new JLabel("Nombre d'heures par semaine: ");	nbH = new JTextField();				nbH.setEditable(false);
			salaireL = new JLabel("Salaire : ");				salaire = new JTextField();			salaire.setEditable(false);
			RIBL = new JLabel("RIB: ");							RIB = new JTextField();				RIB.setEditable(false);
			gerantL = new JLabel("Votre gérant: ");				gerant = new JTextField();			gerant.setEditable(false);
			
			modifyID = new JButton("Modifier Identifiants");		modifyID.addActionListener(this);
			ret = new JButton("Revenir à la vente");				ret.addMouseListener(this);
			getStats();
			
			empInfo.add(idL);			empInfo.add(id);
			empInfo.add(nCaissL);		empInfo.add(nCaiss);
			empInfo.add(nomL);			empInfo.add(nom);
			empInfo.add(adrL);			empInfo.add(adr);
			empInfo.add(loginL);		empInfo.add(login);
			empInfo.add(passL);			empInfo.add(pass);
			empInfo.add(salaireHorL);	empInfo.add(salaireHor);
			empInfo.add(nbHL);			empInfo.add(nbH);
			empInfo.add(salaireL);		empInfo.add(salaire);
			empInfo.add(RIBL);			empInfo.add(RIB);
			empInfo.add(gerantL);		empInfo.add(gerant);
			
			statButtons.add(modifyID);
			statButtons.add(ret);
			
			main.add(empInfo, BorderLayout.CENTER);
			main.add(statButtons, BorderLayout.SOUTH);
			this.setContentPane(main);
			this.setVisible(true);
		}
		
		if (e.getSource()==logoff) {
			this.dispose();
			new Login();
		}
	}
	
	public void reset() {
		client.setEnabled(true);
		qte.setText("");
		reduc.setText("0");
	}
	
	public boolean check() {
		if ((qte.getText().equals("")) || (reduc.getText().equals("")) || (Integer.parseInt(qte.getText())<=0) || !checkreduc()) {
			javax.swing.JOptionPane.showMessageDialog(null,"Un ou plusieurs champs invalides.");
			return false;
		} else if (contains(commande,((Produit)produit.getSelectedItem()).getID())) {
			javax.swing.JOptionPane.showMessageDialog(null,"Ligne dupliquée.");
			return false;
		} else if (Integer.parseInt(qte.getText())>(Main.getProduitByID(((Produit)produit.getSelectedItem()).getID()).getQteStock())){
			javax.swing.JOptionPane.showMessageDialog(null,"Quantité insuffisante\n" + "En stock : " + (Main.getProduitByID(((Produit)produit.getSelectedItem()).getID()).getQteStock()));
			return false;
		} else return true;
	}
	
	public boolean checkreduc() {
		return ((Integer.parseInt(reduc.getText())>=0) && (Integer.parseInt(reduc.getText())<=100));
	}
	
	private boolean contains(ArrayList<LigneCom> lignes, int id) {
		boolean result = false;
		for (LigneCom l : lignes) {
			if (l.getProduit().getID()==id) result = true;
		}
		return result;
	}
	
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	public void alert(String s) {
		javax.swing.JOptionPane.showMessageDialog(null,s); 
	}
	
	public void getStats() {
		id.setText("E0" + user.getID());
		nCaiss.setText("C0" + user.getNCaisse());
		nom.setText(user.getNom());
		adr.setText(user.getAdresse());
		login.setText(user.getLogin());
		pass.setText(user.getPassword());
		salaireHor.setText(user.getSalaireHor()+"€");
		nbH.setText(user.getNbH()+" h");
		salaire.setText(user.salaire()+"€");
		RIB.setText(user.getRIB());
		gerant.setText(user.getGerant().toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void updateClient() {
		client.setModel(new DefaultComboBoxModel(Main.clients.toArray()));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void updateProduit() {
		produit.setModel(new DefaultComboBoxModel(Main.produits.toArray()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateVentes() {
		ventes.setModel(new DefaultComboBoxModel(this.user.getArray().toArray()));
	}
	
	DefaultTableModel updateTable(ArrayList<LigneCom> commande){
		String [] headers = {"Produit","Quantité","Prix"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (LigneCom l : commande) {
			Object[] data = {l.getProduit(),l.getQte(),l.prixLigne()};
			m.addRow(data);
		}
		return m;
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}
