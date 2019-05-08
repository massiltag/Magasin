package graphic;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import main.Main;
import operations.*;
import personnel.*;
import acteurs.*;
import graphic.edit.ChangePassword;
import stock.*;

@SuppressWarnings("serial")
public class ComptableUI extends JFrame implements ActionListener {
	Comptable user;
	JTabbedPane main;
	JPanel general,client,produit,logoffp,stats,empInfo,statButtons;
	JLabel idL,nomL,adrL,loginL,passL,salaireHorL,nbHL,salaireL,RIBL;
	JTextField id,nom,adr,login,pass,salaireHor,nbH,salaire,RIB;
	JButton logoff,ret,modifyID;
	ChartPanel produits,caissiers,clients,fournisseurs,depots;
	JFreeChart prod,caiss,cli,fourn,dep;
	
	public ComptableUI(Comptable user) {
		this.user = user;
		this.setTitle("Comptabilité & Statistiques | [" + user.getNom() + "]@Magasin");
		this.setBounds(90,90,800,500);
		main = new JTabbedPane();
		general = new JPanel(new GridLayout(8,2,10,10));
		client = new JPanel(new BorderLayout());
		produit = new JPanel(new BorderLayout());
		
		
		CategoryDataset dataset = createProduitsGraph();
        prod = ChartFactory.createBarChart("Nombre de ventes par produit","Produit","Nombre de ventes",dataset, PlotOrientation.VERTICAL,false, true, false);
        CategoryPlot cplot = (CategoryPlot)prod.getPlot();
        ((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
        BarRenderer r = (BarRenderer)prod.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, new GradientPaint(5,5,Color.BLUE,5,10,new Color(41,0,66),true));
        
        produits = new ChartPanel(prod);
        produits.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        produits.setBackground(Color.white);
        
        
        DefaultPieDataset dataset2 = createCaissierDataset();
        caiss = ChartFactory.createPieChart("Pourcentages de vente par caissier",dataset2,false, true, false);
        caissiers = new ChartPanel(caiss);
        caissiers.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        caissiers.setBackground(Color.white);
        
        DefaultPieDataset dataset3 = createClientDataset();
        cli = ChartFactory.createPieChart("Pourcentages de vente par client (€)",dataset3,false, true, false);
        clients = new ChartPanel(cli);
        clients.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        clients.setBackground(Color.white);
        
        DefaultPieDataset dataset4 = createFournisseurDataset();
        fourn = ChartFactory.createPieChart("Pourcentages d'achat par fournisseur (€)",dataset4,false, true, false);
        fournisseurs = new ChartPanel(fourn);
        fournisseurs.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        fournisseurs.setBackground(Color.white);
        
        DefaultPieDataset dataset5 = createDepotDataset();
        dep = ChartFactory.createPieChart("Répartition des produits sur les dépôts",dataset5,false, true, false);
        depots = new ChartPanel(dep);
        depots.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        depots.setBackground(Color.white);        
        
        // Logoff
        logoff = new JButton("Déconnexion");		logoff.addActionListener(this);
		logoffp = new JPanel(new BorderLayout());
		logoffp.setBackground(new Color(40, 65, 104));
		JLabel bye = new JLabel("Au revoir, "+this.user.getNom());
		bye.setForeground(Color.WHITE);
		logoffp.add(bye);
		logoffp.add(logoff,BorderLayout.SOUTH);
		logoffp.setBorder(new EmptyBorder(80,80,80,80));
		
		
		// Stats
		stats = new JPanel(new BorderLayout());
		empInfo = new JPanel(new GridLayout(9,2));
		statButtons = new JPanel(new GridLayout(1,2));
		idL = new JLabel("ID Employé: ");					id = new JTextField();				id.setEditable(false);
		nomL = new JLabel("Nom: ");							nom = new JTextField();				nom.setEditable(false);
		adrL = new JLabel("Adresse: ");						adr = new JTextField();				adr.setEditable(false);
		loginL = new JLabel("Login: ");						login = new JTextField();			login.setEditable(false);
		passL = new JLabel("Mot de passe: ");				pass = new JTextField();			pass.setEditable(false);
		salaireHorL = new JLabel("Salaire Horaire: ");		salaireHor = new JTextField();		salaireHor.setEditable(false);
		nbHL = new JLabel("Nombre d'heures par semaine: ");	nbH = new JTextField();				nbH.setEditable(false);
		salaireL = new JLabel("Salaire : ");				salaire = new JTextField();			salaire.setEditable(false);
		RIBL = new JLabel("RIB: ");							RIB = new JTextField();				RIB.setEditable(false);
		modifyID = new JButton("Modifier Identifiants");	modifyID.addActionListener(this);
		ret = new JButton("Retour aux statistiques");		ret.addActionListener(this);
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
		
		
		// Général
		general.add(new JLabel("Informations sur les bénéfices"));
		general.add(new JLabel(""));
		general.add(new JLabel(""));
		general.add(new JLabel(""));
		general.add(new JLabel("Total en achats :"));
		JTextField ach = new JTextField(getMontantTotalAchats()+" €");
		ach.setEditable(false);
		general.add(ach);
		general.add(new JLabel("Total en ventes :"));
		JTextField vent = new JTextField(getMontantTotalVentes()+" €");
		vent.setEditable(false);
		general.add(vent);
		general.add(new JLabel("Bénéfice Total :"));
		double ben_value = getMontantTotalVentes() - getMontantTotalAchats();
		JTextField ben = new JTextField(ben_value+" €");
		ben.setEditable(false);
		general.add(ben);
		general.add(new JLabel("Remarque : "));
		if (ben_value<0) general.add(new JLabel("Négatif.")); else general.add(new JLabel("Positif"));
		general.setBorder(new EmptyBorder(50,50,50,50));
		
		
		main.addTab("Par Produit",null, produits, "Afficher le nombre de ventes par produit");
        main.addTab("Par Client",null, clients,"Afficher le montant total des ventes par client");
		main.addTab("Par Caissier",null, caissiers,"Afficher le pourcentage de ventes effectuées par chaque caissier");
		main.addTab("Par Fournisseur",null, fournisseurs,"Afficher le pourcentage de ventes effectuées par chaque caissier");
		main.addTab("Par Dépôt",null, depots,"Afficher la répartition des produits sur les dépôts");
		main.addTab("Général",null, general,"Afficher les informations sur les bénéfices");
		main.addTab("Mon statut", null, logoffp, "Afficher vos informations");
		main.addTab("Déconnexion", null, logoffp, "Déconnectez-vous");
		
		
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
		this.setResizable(true);
		this.setVisible(true);
	}
	

    private CategoryDataset createProduitsGraph() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Produit p : Main.produits) {
        	dataset.setValue(getNbVentes(p), "Nombre de ventes", p.toString());
        }
        return dataset;
    }
    
    
    private DefaultPieDataset createCaissierDataset() {
    	double total = Main.ventes.size();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Employe e : Main.employes) {
        	if (e.getClass().getSimpleName().equals("Caissier")) {
        		double pourcentage = ((double) getNbVentes((Caissier) e)/total)*100;
        		dataset.setValue(e.getNom()+" "+(int)(Math.round(pourcentage * 100))/100.0+"%", pourcentage);
        	}
        }
        return dataset;
    }
    
    
    private DefaultPieDataset createClientDataset() {
    	double total = getMontantTotalVentes();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Client c : Main.clients) {
        	double pourcentage = (getMontantVentes(c)/total)*100;
        	dataset.setValue(c.getNom()+" "+getMontantVentes(c)+"€ | " + format(pourcentage) + "%", pourcentage);
        }
        return dataset;
    }
    
    private DefaultPieDataset createFournisseurDataset() {
    	double total = getMontantTotalAchats();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Fournisseur f : Main.fournisseurs) {
        	double pourcentage = (getMontantAchats(f)/total)*100;
        	dataset.setValue(f.getNom()+" "+getMontantAchats(f)+"€ | " + format(pourcentage) + "%", pourcentage);
        }
        return dataset;
    }
    
    private DefaultPieDataset createDepotDataset() {
    	double total = Main.produits.size();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Depot d : Main.depots) {
        	double pourcentage = ((double)d.getArray().size()/total)*100;
        	dataset.setValue(d.getNom()+", "+d.getArray().size()+" Produits", pourcentage);
        }
        return dataset;
    }
    
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==logoff) {
			this.dispose();
			new Login();
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
		if(e.getSource()==ret) {
			this.main.setSelectedIndex(2);
		}
	}
	
	
	private int getNbVentes(Caissier c) {
		int n=0;
		for (Vente v : Main.ventes) {
			if (v.getCaissier().getID()==c.getID()) n++;
		}
		return n;
	}
	
	private double getMontantVentes(Client c) {
		double n=0;
		for (Vente v : Main.ventes) {
			if (v.getClient().getID()==c.getID()) n+=v.montant();
		}
		return n;
	}
	
	private double getMontantAchats(Fournisseur f) {
		double n=0;
		for (Achat a : f.getArray()) {
			n+=a.montant();
		}
		return n;
	}
	
	private double getMontantTotalVentes() {
		double t = 0;
		for (Vente v : Main.ventes) {
			t+=v.montant();
		}
		return t;
	}
	
	private double getMontantTotalAchats() {
		double t = 0;
		for (Achat a : Main.achats) {
			t+=a.montant();
		}
		return t;
	}
	
	private int getNbVentes(Produit p) {
		int n=0;
		for (Vente v : Main.ventes) {
			for (LigneCom l : v.getArray()) {
				if (l.getProduit().getID()==p.getID()) n += l.getQte();
			}
		}
		return n;
	}
	
	public void getStats() {
		id.setText("E0" + user.getID());
		nom.setText(user.getNom());
		adr.setText(user.getAdresse());
		login.setText(user.getLogin());
		pass.setText(user.getPassword());
		salaireHor.setText(user.getSalaireHor()+" €");
		nbH.setText(user.getNbH()+" h");
		salaire.setText((float) Math.round(user.salaire()*100)/100+" €");
		RIB.setText(user.getRIB());
	}
	
	public String format(double n) {
		DecimalFormat df = new DecimalFormat("###.##");
		return new String(df.format(n));
	}
}
