package acteurs;
import java.io.Serializable;

public abstract class Acteur implements Serializable {
	private static final long serialVersionUID = 5991241533228634465L;
	private String nom;
	private String adresse;

	public Acteur(String nom, String adresse){
		this.nom = nom;
		this.adresse = adresse;
	}
	
	// SETTERS
	public void setNom(String nom){
		this.nom = nom;
	}
	
	public void setAdresse(String adresse){
		this.adresse = adresse;
	}
	
	// GETTERS
	public String getNom(){
		return this.nom;
	}
	
	public String getAdresse(){
		return this.adresse;
	}
}