package gestion;

import java.io.Serializable;
import java.util.Comparator;

public class ComparateurAnnee implements Serializable,Comparator<Etudiant> {

    @Override
    public int compare(Etudiant o1, Etudiant o2) {
        if(o1.getAnneeFormation()==5 || o2.getAnneeFormation()==5)
            return o2.getAnneeFormation()-o1.getAnneeFormation();
        return 0;
    }
}
