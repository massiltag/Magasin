package graphic;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import graphic.edit.ChangePassword;
import main.Main;
import operations.Livraison;
import personnel.Livreur;

@SuppressWarnings("serial")
public class LivreurUI extends JFrame implements ActionListener {
	Livreur user;
	JTabbedPane main;
	JPanel livrerp,stats,logoffp,empInfo,statButtons;
	JLabel idL,nomL,adrL,loginL,passL,salaireHorL,nbHL,salaireL,RIBL,kmsL,lvsL;
	JTextField id,nom,adr,login,pass,salaireHor,nbH,salaire,RIB,kms,lvs;
	JTable attente,livrees;
	static JComboBox<Livraison> enAttente;
	JButton livrer,logoff,modifyID,ret;
	JScrollPane attentep,livreesp;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LivreurUI(Livreur user) {
		this.user = user;
		this.setTitle("Livraison | [" + this.user.getNom() + "]@Magasin");
		this.setBounds(100,100,1000,300);
		main = new JTabbedPane();
		
		JTable attente = new JTable(getFalseLivraisons());
		JTable livrees = new JTable(getTrueLivraisons());
		
		
		livrerp = new JPanel(new GridLayout(5,1));
		enAttente = new JComboBox<Livraison>(new DefaultComboBoxModel(getUncompletedDeliveries().toArray()));
		livrer = new JButton("Livrer !");		livrer.addActionListener(this);
		livrerp.add(enAttente);
		livrerp.add(new JLabel());livrerp.add(new JLabel());livrerp.add(new JLabel());
		livrerp.add(livrer);
		livrerp.setBorder(new EmptyBorder(30,30,30,30));
		
		attentep = new JScrollPane(attente);
		livreesp = new JScrollPane(livrees);
		
		
		
		stats = new JPanel(new BorderLayout());
		empInfo = new JPanel(new GridLayout(11,2));
		statButtons = new JPanel(new GridLayout(1,2));
		idL = new JLabel("ID Employé: ");					id = new JTextField();				id.setEditable(false);
		nomL = new JLabel("Nom: ");							nom = new JTextField();				nom.setEditable(false);
		adrL = new JLabel("Adresse: ");						adr = new JTextField();				adr.setEditable(false);
		loginL = new JLabel("Login: ");						login = new JTextField();			login.setEditable(false);
		passL = new JLabel("Mot de passe: ");				pass = new JTextField();			pass.setEditable(false);
		salaireHorL = new JLabel("Salaire Horaire: ");		salaireHor = new JTextField();		salaireHor.setEditable(false);
		nbHL = new JLabel("Nombre d'heures par semaine: ");	nbH = new JTextField();				nbH.setEditable(false);
		kmsL = new JLabel("Kilométrage: ");					kms = new JTextField();				kms.setEditable(false);
		lvsL = new JLabel("Nombre de livraisons: ");		lvs = new JTextField();				lvs.setEditable(false);
		salaireL = new JLabel("Salaire : ");				salaire = new JTextField();			salaire.setEditable(false);
		RIBL = new JLabel("RIB: ");							RIB = new JTextField();				RIB.setEditable(false);
		modifyID = new JButton("Modifier Identifiants");	modifyID.addActionListener(this);
		ret = new JButton("Retour à la livraison");			ret.addActionListener(this);
		getStats();
		empInfo.add(idL);			empInfo.add(id);
		empInfo.add(nomL);			empInfo.add(nom);
		empInfo.add(adrL);			empInfo.add(adr);
		empInfo.add(loginL);		empInfo.add(login);
		empInfo.add(passL);			empInfo.add(pass);
		empInfo.add(salaireHorL);	empInfo.add(salaireHor);
		empInfo.add(nbHL);			empInfo.add(nbH);
		empInfo.add(kmsL);			empInfo.add(kms);
		empInfo.add(lvsL);			empInfo.add(lvs);
		empInfo.add(salaireL);		empInfo.add(salaire);
		empInfo.add(RIBL);			empInfo.add(RIB);
		statButtons.add(modifyID);
		statButtons.add(ret);
		stats.add(empInfo, BorderLayout.CENTER);
		stats.add(statButtons, BorderLayout.SOUTH);
		
		
		// Logoff
        logoff = new JButton("Déconnexion");		logoff.addActionListener(this);
		logoffp = new JPanel(new BorderLayout());
		logoffp.setBackground(new Color(40, 65, 104));
		JLabel bye = new JLabel("Au revoir, "+this.user.getNom());
		bye.setForeground(Color.WHITE);
		logoffp.add(bye);
		logoffp.add(logoff,BorderLayout.SOUTH);
		logoffp.setBorder(new EmptyBorder(80,80,80,80));
		
		main.addTab("En attente",null, attentep, "Livraisons en attente");
		main.addTab("Livrées",null, livreesp, "Livraisons effectuées");
		main.addTab("Livrer !",null, livrerp, "Effectuer des livraisons");
		main.addTab("Stats",null, stats, "Afficher vos informations");
		main.addTab("Déconnexion",null, logoffp, "Déconnectez-vous");
		
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
	}
	
	public void actionPerformed(ActionEvent e) {
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
		if (e.getSource()==livrer) {
			try {
				((Livraison)(enAttente.getSelectedItem())).setState(true);
				((Livraison)(enAttente.getSelectedItem())).setLivreur(this.user);
			} catch (NullPointerException ex) {};
			this.restart();
		}
		if(e.getSource()==ret) {
			this.main.setSelectedIndex(2);
		}
		if (e.getSource()==logoff) {
			this.dispose();
			new Login();
		}
	}
	
	public DefaultTableModel getFalseLivraisons(){		// Livraisons en attente
		String [] headers = {"Client","Adresse","Distance"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Livraison l : Main.livraisons) {
			if (l.getState()==false) {
				Object[] data = {l.getVente().getClient().getNom(),l.getVente().getClient().getAdresse(),(float) Math.round(l.getDistance() * 100)/100+" Km"};
				m.addRow(data);
			}
		}
		return m;
	}
	
	public DefaultTableModel getTrueLivraisons(){		// Livraisons effectuées
		String [] headers = {"Client","Adresse","Distance","Livreur"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Livraison l : Main.livraisons) {
			if (l.getState()==true) {
				Object[] data = {l.getVente().getClient().getNom(),l.getVente().getClient().getAdresse(),(float) Math.round(l.getDistance() * 100)/100+" Km",l.getLivreur().getNom()};
				m.addRow(data);
			}
		}
		return m;
	}
	
	public static ArrayList<Livraison> getUncompletedDeliveries(){
		ArrayList<Livraison> arr = new ArrayList<Livraison>();
		for (Livraison l : Main.livraisons) {
			if (!(l.getState())) arr.add(l);
		}
		return arr;
	}
	
	public void restart() {
		this.dispose();
		new LivreurUI(this.user).main.setSelectedIndex(2);
	}
	
	public void getStats() {
		id.setText("E0" + user.getID());
		nom.setText(user.getNom());
		adr.setText(user.getAdresse());
		login.setText(user.getLogin());
		pass.setText(user.getPassword());
		salaireHor.setText(user.getSalaireHor()+" €");
		nbH.setText(user.getNbH()+" h");
		kms.setText((float) Math.round(user.getKm()*100)/100+" Km");
		lvs.setText(user.getNbLiv()+" Livraisons");
		salaire.setText((float) Math.round(user.salaire()*100)/100+" €");
		RIB.setText(user.getRIB());
	}
}
