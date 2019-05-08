package graphic;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import acteurs.Client;
import main.Main;
import graphic.create.NewClient;
import graphic.edit.ChangePassword;
import graphic.edit.EditClient;
import personnel.*;

@SuppressWarnings({ "serial" })
public class GClientUI extends JFrame implements ActionListener {		// Interface g�rant personnel
	GerantClientele user;
	JTabbedPane main;
	JLabel idL,nomL,adrL,loginL,passL,salaireHorL,nbHL,salaireL,RIBL;
	JTextField id,nom,adr,login,pass,salaireHor,nbH,salaire,RIB;
	JPanel affichage,edit,stats,buttons,logoffp,empInfo,statButtons;
	public static JTable table;
	static JComboBox<Client> clients;
	JButton clinew,cliedit,clidel,logoff,modifyID,ret;
	
	
	public GClientUI(GerantClientele user) {
		this.user = user;
		this.setTitle("Gestion de la Client�le | [" + this.user.getNom() + "]@Magasin");
		this.setBounds(100,100,1000,300);
		
		main = new JTabbedPane();
		
		affichage = new JPanel(new BorderLayout());
		table = new JTable();
		table.setModel(updateTable(Main.clients));
		table.setEnabled(false);
		affichage.add(new JLabel("Clients du magasin :"),BorderLayout.NORTH);
		affichage.add(new JScrollPane(table),BorderLayout.CENTER);
		
		edit = new JPanel(new BorderLayout());
		clients = new JComboBox<Client>();
		updateClients();
		clinew = new JButton("Ajouter");			clinew.addActionListener(this);
		cliedit = new JButton("Modifier");			cliedit.addActionListener(this);
		clidel = new JButton("Supprimer");			clidel.addActionListener(this);
		buttons = new JPanel(new GridLayout(1,3));
		buttons.add(clinew);
		buttons.add(cliedit);
		buttons.add(clidel);
		edit.add(new JLabel("Edition des employ�s g�r�s : "), BorderLayout.NORTH);
		JPanel choose = new JPanel(new GridLayout(6,1));
		choose.add(clients);
		edit.add(choose,BorderLayout.CENTER);
		edit.add(buttons,BorderLayout.SOUTH);
		
		
		
		stats = new JPanel(new BorderLayout());
		empInfo = new JPanel(new GridLayout(9,2));
		statButtons = new JPanel(new GridLayout(1,2));
		
		idL = new JLabel("ID Employ�: ");					id = new JTextField();				id.setEditable(false);
		nomL = new JLabel("Nom: ");							nom = new JTextField();				nom.setEditable(false);
		adrL = new JLabel("Adresse: ");						adr = new JTextField();				adr.setEditable(false);
		loginL = new JLabel("Login: ");						login = new JTextField();			login.setEditable(false);
		passL = new JLabel("Mot de passe: ");				pass = new JTextField();			pass.setEditable(false);
		salaireHorL = new JLabel("Salaire Horaire: ");		salaireHor = new JTextField();		salaireHor.setEditable(false);
		nbHL = new JLabel("Nombre d'heures par semaine: ");	nbH = new JTextField();				nbH.setEditable(false);
		salaireL = new JLabel("Salaire : ");				salaire = new JTextField();			salaire.setEditable(false);
		RIBL = new JLabel("RIB: ");							RIB = new JTextField();				RIB.setEditable(false);
		
		modifyID = new JButton("Modifier Identifiants");		modifyID.addActionListener(this);
		ret = new JButton("Retour � la gestion");				ret.addActionListener(this);
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
		
		statButtons.add(modifyID);
		statButtons.add(ret);
		
		stats.add(empInfo, BorderLayout.CENTER);
		stats.add(statButtons, BorderLayout.SOUTH);
		
		
		logoff = new JButton("D�connexion");		logoff.addActionListener(this);
		logoffp = new JPanel(new BorderLayout());
		logoffp.setBackground(new Color(40, 65, 104));
		JLabel bye = new JLabel("Au revoir, " + this.user.getNom());
		bye.setForeground(Color.WHITE);
		logoffp.add(bye);
		logoffp.add(logoff,BorderLayout.SOUTH);
		logoffp.setBorder(new EmptyBorder(80,80,80,80));
		
		
		main.addTab("Affichage", null, affichage, "Afficher les employ�s que vous g�rez");
		main.addTab("Edition", null, edit, "Modifier les information relatives � vos employ�s");
		main.addTab("Stats", null, stats, "Afficher les informations vous concernant");
		main.addTab("D�connexion", null, logoffp, "Vous d�connecter");
		
		//Demande de sauvegarde en quittant la fen�tre
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	JDialog.setDefaultLookAndFeelDecorated(true);
			    int response = JOptionPane.showConfirmDialog(null, "Voulez-vous enregistrer les modifications ?", "Enregistrement",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.NO_OPTION) {
			    	//javax.swing.JOptionPane.showMessageDialog(null, "Aucune modification ne sera enregistr�e.");
			    } else if (response == JOptionPane.YES_OPTION) {
			    	Main.save();
			    	javax.swing.JOptionPane.showMessageDialog(null, "Les modifications ont �t� enregistr�es.");
			    } else if (response == JOptionPane.CLOSED_OPTION) {
			    	//javax.swing.JOptionPane.showMessageDialog(null, "Aucune modification ne sera enregistr�e.");
			    }
		    }
		});
		
		
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==clinew) {
			NewClient n = new NewClient();
			n.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosed(WindowEvent e) {
			    	updateClients();
					table.setModel(updateTable(Main.clients));
			    }
			});
		}
		if (e.getSource()==cliedit){
			//TODO new EditEmploye(this.user,(Employe)clients.getSelectedItem());
			EditClient n = new EditClient(Main.getClientByID((((Client)clients.getSelectedItem()).getID())));
			n.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosed(WindowEvent e) {
			    	updateClients();
					table.setModel(updateTable(Main.clients));
			    }
			});
		}
		if (e.getSource()==clidel) {
			if (clients.getSelectedItem()!=null) Main.clients.remove(Main.getClientByID(((Client) clients.getSelectedItem()).getID()));
			updateClients();
			table.setModel(updateTable(Main.clients));
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
		if (e.getSource()==ret) {
			main.setSelectedIndex(0);
		}
		if (e.getSource()==logoff) {
			this.dispose();
			new Login();
		}
	}
	
	public static DefaultTableModel updateTable(ArrayList<Client> clients){
		String [] headers = {"Nom","Adresse"};
		DefaultTableModel m = new DefaultTableModel(headers,0);
		for (Client c : clients) {
			Object[] data = {c.getNom(),c.getAdresse()};
			m.addRow(data);
		}
		return m;
	}
	
	public void getStats() {
		id.setText("E0" + user.getID());
		nom.setText(user.getNom());
		adr.setText(user.getAdresse());
		login.setText(user.getLogin());
		pass.setText(user.getPassword());
		salaireHor.setText(user.getSalaireHor()+"�");
		nbH.setText(user.getNbH()+" h");
		salaire.setText(user.salaire()+"�");
		RIB.setText(user.getRIB());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateClients() {
		clients.setModel(new DefaultComboBoxModel(Main.clients.toArray()));
	}
}
