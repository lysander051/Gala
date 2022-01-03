package gestion;

import java.io.Serializable;
import java.util.Comparator;

public class ComparateurAnnee implements Serializable,Comparator<Etudiant> {

    /**
     * @param o1 l'étudiant qu'on compare
     * @param o2 l'étudiant avec lequel on compare
     * @return 0 si ils sont tous les deux en 5ème années, -1 si o1 est en 5ème année, 1 si o2 est en 5ème années et -1 si aucun des deux n'est en 5ème années
     */
    @Override
    public int compare(Etudiant o1, Etudiant o2) {
        if (o1.getAnneeFormation() == 5 && o2.getAnneeFormation() == 5) {
            return 0;
        } else if (o1.getAnneeFormation() == 5) {
            return -1;
        } else if (o2.getAnneeFormation() == 5) {
            return 1;
        } else {
            return -1;
        }
    }
}
