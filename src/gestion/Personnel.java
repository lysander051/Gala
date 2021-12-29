package gestion;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;

public class Personnel extends Individu implements Serializable {
    public Personnel(int identifiant,String nom, String prenom, String numTel, String eMail) {
        super( identifiant,nom, prenom, numTel, eMail);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public Type typeIndividu() {
        return Type.PERSONNEL;
    }
}
