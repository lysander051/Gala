package gestion;

import java.io.Serializable;

public class Etudiant extends Individu  {
    private int anneeFormation;

    public Etudiant(int identifiant,String nom, String prenom, String numTel, String eMail,  int anneeFormation) {
        super( identifiant,nom, prenom, numTel, eMail);
        this.anneeFormation = anneeFormation;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public Type typeIndividu() {
        return Type.ETUDIANT;
    }

    public int getAnneeFormation() {
        return anneeFormation;
    }
}
