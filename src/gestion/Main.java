package gestion;

import java.sql.SQLOutput;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
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
