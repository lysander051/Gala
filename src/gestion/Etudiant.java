package gestion;

import java.io.Serializable;

public class Etudiant extends Individu implements Serializable {
    private int anneeFormation;


    /**
     * Constructeur d'un Etudiant
     * @param identifiant identifiant de l'étudiant
     * @param nom nom de l'étudiant
     * @param prenom prenom de l'étudiant
     * @param numTel numéro téléphone de l'étudiant
     * @param eMail eMail de l'étudiant
     * @param anneeFormation année de formation de l'étudiant
     */
    public Etudiant(int identifiant,String nom, String prenom, String numTel, String eMail,  int anneeFormation) {
        super( identifiant,nom, prenom, numTel, eMail);
        this.anneeFormation = anneeFormation;
    }


    /**
     * Donne le type Etudiant
     * @return Etudiant
     */
    @Override
    public Type typeIndividu() {
        return Type.ETUDIANT;
    }


    /**
     * Donne l'année de formation de l"étudiant
     * @return l'année de formation de l'étudiant
     */
    public int getAnneeFormation() {
        return anneeFormation;
    }
}
