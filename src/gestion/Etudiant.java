package gestion;

public class Etudiant extends Individu{
    private int anneeFormation;

    public Etudiant(String nom, String prenom, String numTel, String eMail, int identifiant, Reservation reservation, int anneeFormation) {
        super(nom, prenom, numTel, eMail, identifiant, reservation);
        this.anneeFormation = anneeFormation;
    }


}
