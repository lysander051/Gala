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


}
