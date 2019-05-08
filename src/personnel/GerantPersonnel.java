package personnel;
import java.util.ArrayList;

public class GerantPersonnel extends Employe {
	private static final long serialVersionUID = -8933299247448117508L;
	ArrayList<Employe> personnel;
	
	public GerantPersonnel(String nom, String adresse, String login, String password, double salaireHor, String RIB, int nbH){
		super(nom, adresse, login, password, salaireHor, RIB, nbH, null);
		this.gerant = null;
		this.personnel = new ArrayList<Employe>();
	}
	
	public ArrayList<Employe> getArray() {
		return this.personnel;
	}
	
	public int getNbPersonnel() {
		return this.getArray().size();
	}
	
	public double salaire() {
		return super.salaire() + this.getNbPersonnel()*0.50;
	} //Calculé avec une prime de 40 centimes par employé géré
	
}
