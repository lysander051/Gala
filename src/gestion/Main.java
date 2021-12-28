package gestion;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Gala g = new Gala(LocalDate.now());
        System.out.println(g.getIndividu(2165150));
        Ihm i = new Ihm();
        Controleur c = new Controleur(g, i, LocalDate.of(10,01,2020));
    }
}
