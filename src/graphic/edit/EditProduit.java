package graphic.edit;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import graphic.create.NewMarque;
import main.Main;
import personnel.GerantStock;
import stock.Depot;
import stock.Marque;
import stock.Produit;

@SuppressWarnings("serial")
public class EditProduit extends JFrame implements ActionListener {
	JPanel main,form,buttons;
	JLabel marqueL,modelL,prixL,stockminL,depotL;
	JTextField model,prix,stockmin;
	JButton ok,cancel;
	JButton newM;
	Produit p;
	GerantStock g;
	public static JComboBox<Marque> marque;
	JComboBox<Depot> depot;
	Object previous;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EditProduit(GerantStock g,Produit p) {
		this.g = g;
		this.p = p;
		this.setTitle("Modifier le Produit");
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
		marq.add(marque,BorderLayout.CENTER);		marq.add(newM,BorderLayout.EAST);			marque.setSelectedItem(Main.getMarqueByID((p.getMarque().getID())));
		modelL = new JLabel("Modèle : ");			model = new JTextField();					model.setText(p.getModele());
		prixL = new JLabel("Prix : ");				prix = new JTextField();					prix.setText(""+p.getPrix());
		stockminL = new JLabel("Stock Min. : ");	stockmin = new JTextField();				stockmin.setText(""+p.getStockMin());
		depotL = new JLabel("Dépôt : ");			
		depot = new JComboBox<Depot>();				depot.setModel(new DefaultComboBoxModel(Main.depots.toArray()));	depot.setSelectedItem(Main.getDepotByID((p.getDepot().getID())));
		
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
		
		previous = depot.getSelectedItem();
		
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
					Main.getProduitByID(this.p.getID()).setMarque(Main.getMarqueByID(((Marque)marque.getSelectedItem()).getID()));
					Main.getProduitByID(this.p.getID()).setModele(model.getText());	
					Main.getProduitByID(this.p.getID()).setPrix(Double.parseDouble(prix.getText()));
					Main.getProduitByID(this.p.getID()).setStockMin(Integer.parseInt(stockmin.getText()));
					if (previous!=depot.getSelectedItem()) {
						Main.getDepotByID(this.p.getDepot().getID()).produits.remove(Main.getProduitByID(this.p.getID()));
						Main.getProduitByID(this.p.getID()).setDepot(Main.getDepotByID(((Depot)depot.getSelectedItem()).getID()));
						Main.getDepotByID(this.p.getDepot().getID()).produits.add(Main.getProduitByID(this.p.getID()));
					}
				} catch (Exception ex) {
					javax.swing.JOptionPane.showMessageDialog(null,"Vérifier les champs.");
					done = false;
				}
				if (done) this.close();
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
		this.setVisible(false);
		this.dispose();
	}
}
