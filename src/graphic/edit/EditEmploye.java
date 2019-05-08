package graphic.edit;
import java.awt.*;  
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import graphic.GPersoUI;
import main.Main;
import personnel.Caissier;
import personnel.Employe;
import personnel.GerantPersonnel;

@SuppressWarnings("serial")
public class EditEmploye extends JFrame implements ActionListener, MouseListener {
	JPanel main,infos,choose,suppl,buttons,pwdshow;
	JLabel nomL,adrL,loginL,passL,pass2L,salaireHL,nbHL,RIBL,ncaissL,typeL;
	JTextField nom,adr,login,salaireH,nbH,RIB,ncaiss,type;
	JPasswordField pass,pass2;
	JButton ok,cancel,show;
	Employe e;
	GerantPersonnel user;
	
	public EditEmploye(GerantPersonnel g, Employe e) {
		this.user = g;
		this.e = e;
		this.setTitle("Edition Employé");
		this.setBounds(320,100,370,470);
		
		main = new JPanel(new BorderLayout());
		infos = new JPanel(new GridLayout(10,2,3,10));
		choose = new JPanel(new GridLayout(5,2));
		suppl = new JPanel(new GridLayout(1,2));
		buttons = new JPanel(new GridLayout(1,2,4,0));
		buttons.setBorder(new EmptyBorder(5,5,5,5));
		pwdshow = new JPanel(new BorderLayout());
		
		nomL = new JLabel("Nom: ");							nom = new JTextField(e.getNom());
		adrL = new JLabel("Adresse: ");						adr = new JTextField(e.getAdresse());
		loginL = new JLabel("Login: ");						login = new JTextField(e.getLogin());	
		passL = new JLabel("Mot de passe: ");				pass = new JPasswordField(e.getPassword());	
		show = new JButton("Show"); show.addMouseListener(this); pwdshow.add(pass); pwdshow.add(show,BorderLayout.EAST); show.setFocusable(false);
		pass2L = new JLabel("Confirmer mot de passe: ");	pass2 = new JPasswordField(e.getPassword());	
		salaireHL = new JLabel("Salaire Horaire: ");		salaireH = new JTextField(""+e.getSalaireHor());	
		nbHL = new JLabel("Nb. Heures/Sem: ");				nbH = new JTextField(""+e.getNbH());	
		RIBL = new JLabel("RIB: ");							RIB = new JTextField(e.getRIB());
		typeL = new JLabel("Type: ");						type = new JTextField(e.getClass().getSimpleName());	type.setEditable(false);					
		
		ok = new JButton("OK");								ok.addActionListener(this);
		cancel = new JButton("Annuler");					cancel.addActionListener(this);
		
		infos.add(nomL);									infos.add(nom);
		infos.add(adrL);									infos.add(adr);
		infos.add(loginL);									infos.add(login);
		infos.add(passL);									infos.add(pwdshow);
		infos.add(pass2L);									infos.add(pass2);
		infos.add(salaireHL);								infos.add(salaireH);
		infos.add(nbHL);									infos.add(nbH);
		infos.add(RIBL);									infos.add(RIB);
		infos.add(typeL);									infos.add(type);
		
		if (e.getClass().getSimpleName().equals("Caissier")) {
			ncaissL = new JLabel("Numéro de caisse: "); 	ncaiss = new JTextField(((Caissier)e).getNCaisse()+"");
			infos.add(ncaissL);								infos.add(ncaiss);
		}
		
		buttons.add(ok);									buttons.add(cancel);
		
		main.add(infos,BorderLayout.CENTER);
		main.add(buttons,BorderLayout.SOUTH);
		main.setBorder(new EmptyBorder(5,5,5,5));
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource()==ok) {
			if (this.check()) {
				if (this.pwdcheck()) {
					if (this.caissecheck()) {
						try {
							update(this.e);
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
		
		if (evt.getSource()==cancel) {
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
		if (!(e.getClass().getSimpleName().equals("Caissier"))) return true;
		boolean dispo = true;
		for (Employe emp : Main.employes) {
			if (emp.getClass().getSimpleName().equals("Caissier")) {
				if ((((Caissier)emp).getNCaisse() == Integer.parseInt(ncaiss.getText())) && (emp.getID() != e.getID())) {
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
	
	public void update(Employe e) {
		e.setNom(nom.getText());
		e.setAdresse(adr.getText());
		e.setLogin(login.getText());
		e.setPassword(new String(pass.getPassword()));
		e.setSalaireHor(Double.parseDouble(salaireH.getText()));
		e.setNbH(Integer.parseInt(nbH.getText()));
		e.setRIB(RIB.getText());
		if (e.getClass().getSimpleName().equals("Caissier")) ((Caissier)e).setNCaisse(Integer.parseInt(ncaiss.getText()));
	}
	
	void alert(String s) {
		javax.swing.JOptionPane.showMessageDialog(null,s);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		pass.setEchoChar((char) 0);
	}
	public void mouseReleased(MouseEvent e){
		pass.setEchoChar('•');
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
}
