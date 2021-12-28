package gestion;

import java.sql.SQLOutput;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //Gala gala=new Gala(LocalDate.now());
        //System.out.println(gala.tableEtudiant());
       // Ihm ihm=new Ihm();
        //System.out.println(ihm.etudiantOuPersonnel());
       // Controleur controleur=new Controleur(LocalDate.now());
       /* for(int i=0;i<10;i++){
            System.out.println("i= "+i);
            controleur.identification();
            //System.out.println(gala.inscription	(6835		));
            // System.out.println(gala.estInscrit	(1235		));
            controleur.inscription();
            //controleur.inscription();
            controleur.choisirMenu();
            //controleur.gererPlacePersonnel();
        }*/

        Controleur c1=new Controleur(LocalDate.now());
        c1.identification();
        c1.inscription();
        c1.choisirMenu();
        Controleur c2=new Controleur(LocalDate.now());
        c2.identification();
        c2.inscription();
        c2.choisirMenu();


    }
}
