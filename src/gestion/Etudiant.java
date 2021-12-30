package gestion;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class Etudiant extends Individu implements Serializable, Comparator<Etudiant> {
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

    @Override
    public int compare(Etudiant o1, Etudiant o2) {
        if(o1.getAnneeFormation()==5 && o2.getAnneeFormation()==5){
            return 0;
        }
        else if(o1.getAnneeFormation()==5 ){
            return -1;
        }
        else if(o2.getAnneeFormation()==5){
            return 1;
        }
        else{
            return -1;
        }
    }


    /*
    NON UTILISE
    @Override
    public int getType() {
        return 1;
    }*/

}
