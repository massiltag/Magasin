package personnel;
public class GerantClientele extends Employe {
	private static final long serialVersionUID = 4441190545271870803L;
	
	public GerantClientele(String nom, String adresse, String login, String password, double salaireHor, String RIB, int nbH, GerantPersonnel gerant){
		super(nom, adresse, login, password, salaireHor, RIB, nbH, gerant);
	}
	
	public double salaire() {
		return super.salaire();
	}
}
