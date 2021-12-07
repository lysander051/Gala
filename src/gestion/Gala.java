package gestion;

import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Gala {
    private SortedSet<Etudiant> etudiant = new TreeSet<>();
    private SortedSet<Personnel> personnel = new TreeSet<>();

    private PriorityQueue<Etudiant> listeAttente = new PriorityQueue<>();

    private SortedSet<Etudiant> etudiantAccepte = new TreeSet<>();
    private SortedSet<Personnel> personnelInscrit = new TreeSet<>();

    private SortedSet<Table> table = new TreeSet<>();
}