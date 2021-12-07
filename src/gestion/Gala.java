package gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

public class Gala {
    private final int TARIF1 = 10;
    private final int TARIF2 = 15;
    private final int TARIF3 = 20;
    private final int TABLE_ETUDIANT = 15;
    private final int TABLE_PERSONNEL = 10;

    private SortedMap<Integer, Etudiant> etudiantListe = new TreeMap<>();
    private SortedMap<Integer, Personnel> personnelListe = new TreeMap<>();
    private SortedMap<Integer, Etudiant> etudiantInscrit = new TreeMap<>();
    private SortedMap<Integer, Personnel>personnelInscrit = new TreeMap<>();
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
                int num = Integer.parseInt(scEtudiant.next());
                String nom = scEtudiant.next();
                String prenom = scEtudiant.next();
                String telephone = scEtudiant.next();
                String email = scEtudiant.next();
                int annee = Integer.parseInt(scEtudiant.next());

                etudiantListe.put(num, new Etudiant(num, nom, prenom, telephone, email, annee));
            }
            scEtudiant.close();

            //creation liste personnel
            while (scPersonnel.hasNextLine()) {
                int num = Integer.parseInt(scPersonnel.next());
                String nom = scPersonnel.next();
                String prenom = scPersonnel.next();
                String telephone = scPersonnel.next();
                String email = scPersonnel.next();

                personnelListe.put(num, new Personnel(num, nom, prenom, telephone, email));
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

    public boolean estPresent(int type, int id){
        return switch (type) {
            case 0 -> personnelListe.containsKey(id);
            case 1 -> etudiantListe.containsKey(id);
            default -> throw new NumberFormatException();
        };
    }

    public boolean inscription(int type, int id){
        //TODO verifier si le get ne retourne rien, si cela fonctionne
        switch (type) {
            case 0 -> {
                if(estPresent(type, id)) {
                    personnelInscrit.put(id, personnelListe.get(id));
                    return true;
                }
                else {return false;}
            }
            case 1 -> {
                if(estPresent(type, id)){
                    etudiantInscrit.put(id, etudiantInscrit.get(id));
                    return true;
                }
                else {return false;}
            }
            default -> throw new NumberFormatException();
        }
    }

    public boolean estInscrit(int type, int id){
        return switch (type) {
            case 0 -> personnelInscrit.containsKey(id);
            case 1 -> etudiantInscrit.containsKey(id);
            default -> throw new NumberFormatException();
        };
    }
}