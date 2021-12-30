package gestion;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;

public class Personnel extends Individu implements Serializable {
    /**
     * Constructeur d'un personnel
     * @param identifiant identifiant du personnel
     * @param nom nom du personnel
     * @param prenom prenom du personnel
     * @param numTel numero telephone du personnel
     * @param eMail eMail du personnel
     */
    public Personnel(int identifiant,String nom, String prenom, String numTel, String eMail) {
        super( identifiant,nom, prenom, numTel, eMail);
    }


    /**
     * Donne le type Personnel
     * @return le type Personnel
     */
    @Override
    public Type typeIndividu() {
        return Type.PERSONNEL;
    }

   /*
   NON UTILISE
   @Override
    public int getType() {
        return 0;
    }*/
}
