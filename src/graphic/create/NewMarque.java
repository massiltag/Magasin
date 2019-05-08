package graphic.create;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import graphic.*;
import graphic.edit.EditProduit;
import main.Main;
import stock.Marque;

@SuppressWarnings({ "unused", "serial" })
public class NewMarque extends JFrame implements ActionListener {
	JPanel main,form,buttons;
	JLabel nomL;
	JTextField nom;
	JButton ok,cancel;
	
	public NewMarque() {
		this.setTitle("Nouvelle Marque");
		this.setBounds(400,250,300,100);
		
		main = new JPanel(new BorderLayout());
		form = new JPanel(new GridLayout(1,2,3,10));
		buttons = new JPanel(new GridLayout(1,2,4,0));
		form.setBorder(new EmptyBorder(5,5,5,5));
		buttons.setBorder(new EmptyBorder(5,5,5,5));
		
		nomL = new JLabel("Nom: ");					nom = new JTextField();
		ok = new JButton("OK");						ok.addActionListener(this);
		cancel = new JButton("Annuler");			cancel.addActionListener(this);
		
		form.add(nomL);								form.add(nom);
		buttons.add(ok);							buttons.add(cancel);
		
		main.add(form,BorderLayout.CENTER);
		main.add(buttons,BorderLayout.SOUTH);
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==ok) {
			if (this.check()) {
				Marque m = new Marque(nom.getText());
				try {
					NewProduit.marque.setModel(new DefaultComboBoxModel(Main.marques.toArray()));
				} catch (Exception exc) {
					EditProduit.marque.setModel(new DefaultComboBoxModel(Main.marques.toArray()));
				}
				this.close();
			}
		}
		
		if (e.getSource()==cancel) {
			this.close();
		}
	}
	
	public boolean check() {
		if (nom.getText().equals("")) {
			javax.swing.JOptionPane.showMessageDialog(null,"Champ invalide.");
			return false;
		} else return true;
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
