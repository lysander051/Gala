package gestion;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Gala g = new Gala(LocalDate.now());
        Ihm i = new Ihm();
        Controleur c = new Controleur(g, i, LocalDate.of(2022, 1, 10));
    }
}
