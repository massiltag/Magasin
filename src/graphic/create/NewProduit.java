package graphic.create;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import graphic.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import main.Main;
import personnel.*;
import stock.*;

@SuppressWarnings({ "unused", "serial" })
public class NewProduit extends JDialog implements ActionListener,Observable {
	JPanel main,form,buttons;
	JLabel marqueL,modelL,prixL,stockminL,depotL;
	JTextField model,prix,stockmin;
	JButton ok,cancel;
	GerantStock user;
	JButton newM;
	static JComboBox<Marque> marque;
	JComboBox<Depot> depot;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NewProduit(GerantStock user) {
		this.user = user;
		this.setTitle("Nouveau Produit");
		this.setBounds(300,200,340,265);
		
		main = new JPanel(new BorderLayout());
		form = new JPanel(new GridLayout(5,2,3,10));
		buttons = new JPanel(new GridLayout(1,2,4,0));
		form.setBorder(new EmptyBorder(5,5,5,5));
		buttons.setBorder(new EmptyBorder(5,5,5,5));
		
		JPanel marq = new JPanel(new BorderLayout());
		newM = new JButton("New");					newM.addActionListener(this);
		marqueL = new JLabel("Marque : ");
		marque = new JComboBox<Marque>();			marque.setModel(new DefaultComboBoxModel(Main.marques.toArray()));
		marq.add(marque,BorderLayout.CENTER);		marq.add(newM,BorderLayout.EAST);
		modelL = new JLabel("Modèle : ");			model = new JTextField();
		prixL = new JLabel("Prix : ");				prix = new JTextField();	
		stockminL = new JLabel("Stock Min. : ");	stockmin = new JTextField();	
		depotL = new JLabel("Dépôt : ");			
		depot = new JComboBox<Depot>();				depot.setModel(new DefaultComboBoxModel(this.user.getArray().toArray()));
		
		ok = new JButton("OK");						ok.addActionListener(this);
		cancel = new JButton("Annuler");			cancel.addActionListener(this);
		
		form.add(marqueL);							form.add(marq);
		form.add(modelL);							form.add(model);
		form.add(prixL);							form.add(prix);
		form.add(stockminL);						form.add(stockmin);
		form.add(depotL);							form.add(depot);
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
				boolean done = true;
				try {
					Produit p = new Produit(Main.getMarqueByID(((Marque)marque.getSelectedItem()).getID()),model.getText(),Double.parseDouble(prix.getText()),Integer.parseInt(stockmin.getText()),Main.getDepotByID(((Depot)depot.getSelectedItem()).getID()));
					GStockUI.updateProduits(Main.getDepotByID(((Depot)GStockUI.depots2.getSelectedItem()).getID()));
				} catch (Exception ex) {
					javax.swing.JOptionPane.showMessageDialog(null,"Vérifier les champs.");
					done = false;
				}
				if (done) this.dispose();
			}
		}
		
		if (e.getSource()==newM) {
			new NewMarque();
		}
		
		if (e.getSource()==cancel) {
			this.close();
		}
	}
	

	public boolean check() {
		if ((model.getText().equals("")) || (prix.getText().equals("")) || (stockmin.getText().equals(""))
			|| (Double.parseDouble(prix.getText())<=0) || (Integer.parseInt(stockmin.getText())<=0) ) {
			javax.swing.JOptionPane.showMessageDialog(null,"Un ou plusieurs champs invalides.");
			return false;
		} else return true;
	}
	
	public void close() {
		this.dispose();
	}

	@Override
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}
}
