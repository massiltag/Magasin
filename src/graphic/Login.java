package graphic;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.Main;
import personnel.*;

@SuppressWarnings({ "unused", "serial" })
public class Login extends JFrame implements ActionListener {
	JLabel l1,l2;
	JTextField login;
	JPasswordField password;
	JButton ok,cancel;
	JPanel fields,buttons,main;
	
	public Login() {
		this.setTitle("Connexion Magasin");
		this.setBounds(300,300,450,150);
		
		main = new JPanel(new GridLayout(2,1));
		
		l1 = new JLabel("Login: ");
		l2 = new JLabel("Password: ");
		login = new JTextField();
		password = new JPasswordField(10);
		password.addActionListener(this);
		l1.setLabelFor(login);
		l2.setLabelFor(password);
		
		fields = new JPanel(new GridLayout(2,2));
		fields.add(l1);
		fields.add(login);
		fields.add(l2);
		fields.add(password);
		fields.setSize(new Dimension(200,100));
		
		ok = new JButton("OK");
		cancel = new JButton("Quitter");
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		buttons = new JPanel(new GridLayout(1,2,20,20));
		buttons.add(ok);
		buttons.add(cancel);
		buttons.setBorder(new EmptyBorder(10,30,10,30));
		
		main.add(fields);
		main.add(buttons);
		main.setBorder(new EmptyBorder(5,5,5,5));
		
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == ok) || (e.getSource()==password)) {
			String log = login.getText();
			String pwd = new String(password.getPassword());
			if ((log.equals("root") && (pwd.equals("toor")))) {
				new OverviewUI();
				this.close();
			} else {
				boolean found = false;
				for (Employe emp : Main.employes) {
					if ((log.equals(emp.getLogin())) && (pwd.equals(emp.getPassword()))) {
						found = true;
						String type = emp.getClass().getName();
						switch(type) {
							case "personnel.Caissier":
								new CaissierUI((Caissier) emp);
								this.close();
								break;
							case "personnel.GerantPersonnel":
								new GPersoUI((GerantPersonnel) emp);
								this.close();
								break;
							case "personnel.GerantStock":
								new GStockUI((GerantStock) emp);
								this.close();
								break;
							case "personnel.GerantClientele":
								new GClientUI((GerantClientele) emp);
								this.close();
								break;
							case "personnel.Comptable":
								new ComptableUI((Comptable) emp);
								this.close();
								break;
							case "personnel.Livreur":
								new LivreurUI((Livreur) emp);
								this.close();
								break;
						}
					}
				}
				if (found==false) {
					javax.swing.JOptionPane.showMessageDialog(null,"Login ou mot de passe incorrect"); 
				}
			}
		}
		
		if (e.getSource() == cancel) {
			this.closesave();
		}
	}
	
	void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	void closesave() {
		this.dispose();
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
}
