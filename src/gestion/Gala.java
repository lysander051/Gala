package gestion;

import java.time.LocalDate;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;

public class Gala {
    private final int TARIF1 = 10;
    private final int TARIF2 = 15;
    private final int TARIF3 = 20;
    private final int TABLE_ETUDIANT = 15;
    private final int TABLE_PERSONNEL = 10;

    private SortedSet<Individu> individus = new TreeSet<>();
    private SortedSet<Etudiant> etudiantInscrit = new TreeSet<>();
    private SortedSet<Personnel>personnelInscrit = new TreeSet<>();
    private PriorityQueue<Etudiant> listeAttente = new PriorityQueue<>();
    private SortedSet<Personnel> etudiantAccepte = new TreeSet<>();
    private SortedSet<Table> table = new TreeSet<>();

    private LocalDate dateGala;

    public Gala(LocalDate date){
        dateGala = date;

    }
}