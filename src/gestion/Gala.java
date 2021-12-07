package gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Gala {
    private final int TARIF1 = 10;
    private final int TARIF2 = 15;
    private final int TARIF3 = 20;
    private final int TABLE_ETUDIANT = 15;
    private final int TABLE_PERSONNEL = 10;

    private SortedSet<Etudiant> etudiantsListe = new TreeSet<>();
    private SortedSet<Personnel> personnelsListe = new TreeSet<>();
    private SortedSet<Etudiant> etudiantInscrit = new TreeSet<>();
    private SortedSet<Personnel>personnelInscrit = new TreeSet<>();
    private PriorityQueue<Etudiant> listeAttente = new PriorityQueue<>();
    private SortedSet<Personnel> etudiantAccepte = new TreeSet<>();
    private SortedSet<Table> etudiantTable = new TreeSet<>();
    private SortedSet<Table> personnelTable = new TreeSet<>();

    private LocalDate dateGala;

    public Gala(LocalDate date){
        dateGala = date;
        File etudiant = new File("data/etudiants.txt");
        File personnel = new File("data/personnel.txt");

        //creation des listes initiales pour les etudiants et le personnel
        try {
            Scanner scEtudiant = new Scanner(etudiant);
            Scanner scPersonnel = new Scanner(personnel);

            //creation liste etudiant
            while (scEtudiant.hasNextLine()) {
                etudiantsListe.add(new Etudiant(
                        Integer.parseInt(scEtudiant.next())      //numero etudiant
                        ,scEtudiant.next()                       //nom
                        ,scEtudiant.next()                       //prenom
                        ,scEtudiant.next()                       //numero de telephone
                        ,scEtudiant.next()                       //addresse email
                        ,Integer.parseInt(scEtudiant.next())));  //annee d'etude
            }
            scEtudiant.close();

            //creation liste personnel
            while (scPersonnel.hasNextLine()) {
                personnelsListe.add(new Personnel(
                        Integer.parseInt(scPersonnel.next())      //numero personnel
                        ,scPersonnel.next()                       //nom
                        ,scPersonnel.next()                       //prenom
                        ,scPersonnel.next()                       //numero de telephone
                        ,scPersonnel.next()));                    //addresse email

            }
            scPersonnel.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //creation des table du personnel et des etudiant
        for (int i = 0; i < TABLE_PERSONNEL; i++) {
            personnelTable.add(new Table());
        }
        for (int i = 0; i < TABLE_ETUDIANT; i++) {
            personnelTable.add(new Table());
        }
    }
}