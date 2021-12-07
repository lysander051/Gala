package gestion;

public class Etudiant extends Individu{
    private int anneeFormation;

    public Etudiant(String nom, String prenom, String numTel, String eMail, int identifiant, int anneeFormation) {
        super( identifiant,nom, prenom, numTel, eMail);
        this.anneeFormation = anneeFormation;
    }


}
