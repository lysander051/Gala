package gestion;

import java.time.LocalDate;

public class Controleur {
    private Gala gala;
    private Ihm ihm;

    public Controleur(Gala gala, Ihm ihm, LocalDate date) {
        this.gala = new Gala(date);
        this.ihm = new Ihm();

    }

    // affiche Etat de gala et son contenu

    public boolean identification() {
        Boolean present=false;
        while(!present) {
            // demande si etudiant ou personnel et retourne 0 pour etudiant et 1 pour personnel
            int identite = ihm.demanderIdentite();
            // demande numero etudiant
            int numero = ihm.demandeIdentification();
            // verifie si dans la listeEtudiant si identite=0 et listePersonnel si identite=1 le numero est dedans
            present=gala.verificationIdentification(identite, numero);
        }
        return present;
    }

    /*public inscription(){

    }*/


}
