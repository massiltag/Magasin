package graphic.create;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import acteurs.Client;
import graphic.*;

@SuppressWarnings({ "unused", "serial" })
public class NewClient extends JFrame implements ActionListener {
	JPanel main,form,buttons;
	JLabel nomL,adrL;
	JTextField nom,adr;
	JButton ok,cancel;
	
	public NewClient() {
		this.setTitle("Nouveau Client");
		this.setBounds(400,250,300,140);
		
		main = new JPanel(new BorderLayout());
		form = new JPanel(new GridLayout(2,2,3,10));
		buttons = new JPanel(new GridLayout(1,2,4,0));
		form.setBorder(new EmptyBorder(5,5,5,5));
		buttons.setBorder(new EmptyBorder(5,5,5,5));
		
		nomL = new JLabel("Nom: ");					nom = new JTextField();
		adrL = new JLabel("Adresse: ");				adr = new JTextField();		
		ok = new JButton("OK");						ok.addActionListener(this);
		cancel = new JButton("Annuler");			cancel.addActionListener(this);
		
		form.add(nomL);								form.add(nom);
		form.add(adrL);								form.add(adr);
		buttons.add(ok);							buttons.add(cancel);
		
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
				Client c = new Client(nom.getText(),adr.getText());
				this.close();
			}
		}
		
		if (e.getSource()==cancel) {
			this.close();
		}
	}
	
	public boolean check() {
		if ((nom.getText().equals("")) || (adr.getText().equals(""))) {
			javax.swing.JOptionPane.showMessageDialog(null,"Un ou plusieurs champs invalides.");
			return false;
		} else return true;
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
