package personnel;
import java.io.Serializable;

import main.*;

public abstract class Employe implements Serializable {
	private static final long serialVersionUID = 2178956840636805696L;
	int id;
	private String nom;
	private String adresse;
	private String login;
	private String password;
	private double salaireHor;
	private int nbH;
	private int hSup;
	private String RIB;
	GerantPersonnel gerant;
	
	public Employe(String nom, String adresse, String login, String password, double salaireHor, String RIB, int nbH, GerantPersonnel gerant){
		this.id = 1;
		while (existsID(this.id)) {		// Méthode pour l'attribution automatique de numéros séquentiels d'identification uniques
			this.id++;
		}
		this.nom = nom;
		this.adresse = adresse;
		this.login = login;
		this.password = password;
		this.salaireHor = salaireHor;
		this.RIB = RIB;
		this.nbH = nbH;
		this.gerant = gerant;
		
		if (this.gerant!=null) this.gerant.getArray().add(this);
		Main.employes.add(this);
	}
	
	//SETTERS
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSalaireHor(double salaireHor) {
		this.salaireHor = salaireHor;
	}
	
	public void setNbH(int nbH) {
		this.nbH = nbH;
	}
	
	public void setHSup(int hSup) {
		this.hSup = hSup;
	}
	
	public void setRIB(String RIB) {
		this.RIB = RIB;
	}
	
	public void setGerant(GerantPersonnel gerant) {
		this.gerant = gerant;
	}
	
	// GETTERS
	public int getID() {
		return this.id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getAdresse() {
		return this.adresse;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public double getSalaireHor() {
		return this.salaireHor;
	}
	
	public int getNbH() {
		return this.nbH;
	}
	
	public int getHSup() {
		return this.hSup;
	}
	
	public String getRIB() {
		return this.RIB;
	}
	
	public GerantPersonnel getGerant() {
		return this.gerant;
	}
	
	
	// METHODS
	public String toString() {
		return this.getNom() + ", " + this.getClass().getSimpleName() + ".";
	}
	
	public double salaire(){
		return Math.round((this.getNbH()*this.getSalaireHor()*4 + this.getHSup()*(this.getSalaireHor()+this.getSalaireHor()*0.25))*100) / 100;
	}	//Les heures supplémentaires sont payéees avec un pourcentage de 25% en plus du salaire de base.
	
	public void changeGerant(GerantPersonnel gerant) {
		this.gerant.getArray().remove(this);
		this.gerant = gerant;
		this.gerant.getArray().add(this);
	}
	
	public void remove() {
		Main.employes.remove(this);
		if (this.gerant!=null) this.gerant.getArray().remove(this);
	};
	
	boolean existsID(int id) {
		boolean exists = false;
		for (Employe e : Main.employes) {
			if (id==e.getID()) exists = true;
		}
		return exists;
	}
}
