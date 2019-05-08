package personnel;
import java.io.Serializable;

public class Comptable extends Employe implements Serializable {
	private static final long serialVersionUID = -3931889863318639549L;
	
	public Comptable(String nom, String adresse, String login, String password, double salaireHor, String RIB, int nbH, GerantPersonnel gerant){
		super(nom, adresse, login, password, salaireHor, RIB, nbH, gerant);
	}
	
	
}
