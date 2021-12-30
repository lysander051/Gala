package gestion;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;

public class Etudiant extends Individu implements Serializable {
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

    public int compare(Etudiant e1, Etudiant e2) {
        //return (int) ChronoUnit.DAYS.between(e1.getDateReservation(), e2.getDateReservation());
       if(e1.anneeFormation==5 && e2.anneeFormation==5){
           return (int) ChronoUnit.DAYS.between(e1.getDateReservation(), e2.getDateReservation());
       }
       else if(e1.anneeFormation==5 ){
           return -1;
       }
       else if(e2.anneeFormation==5){
           return 1;
       }
       else{
           return (int) ChronoUnit.DAYS.between(e1.getDateReservation(), e2.getDateReservation());
       }
    }
}
