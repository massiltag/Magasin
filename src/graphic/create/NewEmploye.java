package graphic.create;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import graphic.*;
import main.Main;
import personnel.*;

public class NewEmploye extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1180432963486118206L;
	JPanel main,form,infos,choose,suppl,buttons;
	JLabel nomL,adrL,loginL,passL,pass2L,salaireHL,nbHL,RIBL,ncaissL;
	JTextField nom,adr,login,salaireH,nbH,RIB,ncaiss;
	JPasswordField pass,pass2;
	JButton ok,cancel;
	ButtonGroup g;
	JRadioButton caiss,gperso,gstock,liv,compta;
	GerantPersonnel user;
	
	public NewEmploye(GerantPersonnel user) {
		this.user = user;
		this.setTitle("Nouvel Employé");
		this.setBounds(320,100,370,570);
		
		main = new JPanel(new BorderLayout());
		form = new JPanel(new GridLayout(2,1));
		infos = new JPanel(new GridLayout(9,2,3,10));
		choose = new JPanel(new GridLayout(5,2));
		suppl = new JPanel(new GridLayout(1,2));
		buttons = new JPanel(new GridLayout(1,2,4,0));
		form.setBorder(new EmptyBorder(5,5,5,5));
		buttons.setBorder(new EmptyBorder(5,5,5,5));
		
		nomL = new JLabel("Nom: ");							nom = new JTextField();
		adrL = new JLabel("Adresse: ");						adr = new JTextField();
		loginL = new JLabel("Login: ");						login = new JTextField();	
		passL = new JLabel("Mot de passe: ");				pass = new JPasswordField();	
		pass2L = new JLabel("Confirmer mot de passe: ");	pass2 = new JPasswordField();	
		salaireHL = new JLabel("Salaire Horaire: ");		salaireH = new JTextField();	
		nbHL = new JLabel("Nb. Heures/Sem: ");				nbH = new JTextField();	
		RIBL = new JLabel("RIB: ");							RIB = new JTextField();
		
		ok = new JButton("OK");								ok.addActionListener(this);
		cancel = new JButton("Annuler");					cancel.addActionListener(this);
		
		infos.add(nomL);									infos.add(nom);
		infos.add(adrL);									infos.add(adr);
		infos.add(loginL);									infos.add(login);
		infos.add(passL);									infos.add(pass);
		infos.add(pass2L);									infos.add(pass2);
		infos.add(salaireHL);								infos.add(salaireH);
		infos.add(nbHL);									infos.add(nbH);
		infos.add(RIBL);									infos.add(RIB);
		
		g = new ButtonGroup();
		caiss = new JRadioButton("Caissier");				g.add(caiss);				caiss.addMouseListener(this);
		gperso = new JRadioButton("Gérant Personnel");		g.add(gperso);				gperso.addMouseListener(this);
		gstock = new JRadioButton("Gérant Stock");			g.add(gstock);				gstock.addMouseListener(this);
		liv = new JRadioButton("Livreur");					g.add(liv);					liv.addMouseListener(this);
		compta = new JRadioButton("Comptable");				g.add(compta);				compta.addMouseListener(this);
		
		choose.add(new JLabel("Type"));						choose.add(caiss);
		choose.add(new JLabel(""));							choose.add(gperso);
		choose.add(new JLabel(""));							choose.add(gstock);
		choose.add(new JLabel(""));							choose.add(liv);
		choose.add(new JLabel(""));							choose.add(compta);
		
		ncaissL = new JLabel("Numéro de caisse: "); 		ncaiss = new JTextField("0");
		infos.add(ncaissL);									infos.add(ncaiss);
		ncaissL.setVisible(false);							ncaiss.setVisible(false);
		
		form.add(infos);
		form.add(choose);
		
		buttons.add(ok);									buttons.add(cancel);
		
		main.add(form,BorderLayout.CENTER);
		main.add(buttons,BorderLayout.SOUTH);
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==ok) {
			if (this.check()) {
				if (this.pwdcheck()) {
					if (this.caissecheck()) {
						try {
							create();
							GPersoUI.updateEmployes(this.user);
							GPersoUI.table.setModel(GPersoUI.updateTable(this.user.getArray()));
							this.close();
						} catch (Exception exc) {
							alert("Vérifiez les champs.");
						}
						
					}
				}
			}
		}
		
		if (e.getSource()==cancel) {
			this.close();
		}
	}
	
	public boolean check() {
		if ((nom.getText().equals("")) || (adr.getText().equals("")) || (login.getText().equals("")) || 
			(new String(pass.getPassword()).equals("")) || (new String(pass2.getPassword()).equals("")) ||
			(salaireH.getText().equals("")) || (nbH.getText().equals("")) || (RIB.getText().equals("")) || 
			(Double.parseDouble(salaireH.getText())<=0) || (Integer.parseInt(nbH.getText())<=0)   ) 	
		{
			alert("Un ou plusieurs champs invalides.");
			return false;
		} else return true;
	}
	
	public boolean pwdcheck() {
		if (!(new String(pass.getPassword()).equals(new String(pass2.getPassword())))) {
			alert("Les mots de passe ne correspondent pas");
			return false;
		} else return true;
	}
	
	public boolean caissecheck() {
		boolean dispo = true;
		for (Employe e : Main.employes) {
			if (e.getClass().getSimpleName().equals("Caissier")) {
				if (((Caissier)e).getNCaisse()==Integer.parseInt(ncaiss.getText())) {
					dispo = false;
				}
			}
		}
		if (dispo==false) alert("Caisse déja attribuée.");
		return dispo;
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	public void create() {
		if (caiss.isSelected()) {
			new Caissier(nom.getText(),adr.getText(),login.getText(),new String(pass.getPassword()),Double.parseDouble(salaireH.getText()),
						 RIB.getText(),Integer.parseInt(nbH.getText()),Integer.parseInt(ncaiss.getText()),this.user);
		} else if (gperso.isSelected()) {
			new GerantPersonnel(nom.getText(),adr.getText(),login.getText(),new String(pass.getPassword()),Double.parseDouble(salaireH.getText()),
								RIB.getText(),Integer.parseInt(nbH.getText()));
		} else if (gstock.isSelected()) {
			new GerantStock(nom.getText(),adr.getText(),login.getText(),new String(pass.getPassword()),Double.parseDouble(salaireH.getText()),
							RIB.getText(),Integer.parseInt(nbH.getText()),this.user);
		} else if (liv.isSelected()) {
			new Livreur(nom.getText(),adr.getText(),login.getText(),new String(pass.getPassword()),Double.parseDouble(salaireH.getText()),
						RIB.getText(),Integer.parseInt(nbH.getText()),this.user);
		} else if (compta.isSelected()) {
			new Comptable(nom.getText(),adr.getText(),login.getText(),new String(pass.getPassword()),Double.parseDouble(salaireH.getText()),
						  RIB.getText(),Integer.parseInt(nbH.getText()),this.user);
		} else {
			alert("Séléctionnez le type de l'employé.");
		}
	}
	
	void alert(String s) {
		javax.swing.JOptionPane.showMessageDialog(null,s);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource()==caiss) {
			ncaissL.setVisible(true);
			ncaiss.setVisible(true);
		} else {
			ncaissL.setVisible(false);
			ncaiss.setVisible(false);
		}
	}

	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	
}
