package gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Gala implements Serializable {
    private final int TARIF1 = 10; //etudiants de dernière année
    private final int TARIF2 = 15; //autres etudiants
    private final int TARIF3 = 20;  //personnels et accompagnants
    private final int TABLE_ETUDIANT = 15;
    private final int TABLE_PERSONNEL = 10;

    private SortedMap<Integer, Individu> individuListe = new TreeMap<>();
    private SortedMap<Integer, Etudiant> etudiantInscrit = new TreeMap<>();
    private SortedMap<Integer, Personnel> personnelInscrit = new TreeMap<>();
    private PriorityQueue<Etudiant> etudiantAttente = new PriorityQueue<>();
    private SortedSet<Personnel> etudiantAccepte = new TreeSet<>();
    private List<Table> tables = new ArrayList<>();

    private LocalDate dateGala;

    public Gala(LocalDate date) {
        dateGala = date;

        //creation des listes initiales pour les etudiants et le personnel
        try {
            File etudiant = new File("./data/etudiants.txt");
            File personnel = new File("./data/personnel.txt");
            Scanner scEtudiant = new Scanner(etudiant);
            Scanner scPersonnel = new Scanner(personnel);

            //creation liste etudiant
            while (scEtudiant.hasNextLine()) {
                if (!scEtudiant.hasNextInt()) {
                    break;
                }
                int num = scEtudiant.nextInt();
                if (!scEtudiant.hasNext()) {
                    break;
                }
                String nom = scEtudiant.next();
                if (!scEtudiant.hasNext()) {
                    break;
                }
                String prenom = scEtudiant.next();
                String telephone = scEtudiant.next();
                if (!scEtudiant.hasNext()) {
                    break;
                }
                String email = scEtudiant.next();
                if (!scEtudiant.hasNextInt()) {
                    break;
                }
                int annee = scEtudiant.nextInt();
                Etudiant e = new Etudiant(num, nom, prenom, telephone, email, annee);
                individuListe.put(num, e);
            }
            scEtudiant.close();

            //creation liste personnel
            while (scPersonnel.hasNextLine()) {
                if (!scPersonnel.hasNextInt()) {
                    break;
                }
                int num = scPersonnel.nextInt();
                if (!scPersonnel.hasNext()) {
                    break;
                }
                String nom = scPersonnel.next();
                if (!scPersonnel.hasNext()) {
                    break;
                }
                String prenom = scPersonnel.next();
                if (!scPersonnel.hasNext()) {
                    break;
                }
                String telephone = scPersonnel.next();
                if (!scPersonnel.hasNext()) {
                    break;
                }
                String email = scPersonnel.next();
                individuListe.put(num, new Personnel(num, nom, prenom, telephone, email));
            }
            scPersonnel.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //creation des table du personnel et des etudiant
        for (int i=0; i < TABLE_PERSONNEL; i++) {
            tables.add(new Table());
        }
        for (int i=0; i < TABLE_ETUDIANT; i++) {
            tables.add(new Table());
        }
    }

    // POUR SAVOIR SI L'INDIVIDU EST BIEN DANS L'ECOLE
    public boolean estPresent(int type, int id) {
        if (individuListe.containsKey(id) && type == individuListe.get(id).getType()) {
            return true;

        } else {
            throw new IllegalArgumentException("Echec de l'indentification");
        }

    }

    // POUR FAIRE L'INSCRIPTION D'UN INDIVIDU DANS LA LISTE DE CE QUI VONT EN SOIREE
    // RETURN TRUE SI IL EST PRESENT DANS L'ECOLE

    public boolean inscription(int id) {
        //TODO verifier si le get ne retourne rien, si cela fonctionne
        if (individuListe.get(id) == null) {
            throw new IllegalArgumentException("ID INEXISTANT");
        }
        switch (individuListe.get(id).getType()) {
            case 0 -> {
                if (estPresent(individuListe.get(id).getType(), id)) {
                    personnelInscrit.put(id, (Personnel) individuListe.get(id));
                    System.out.println(personnelInscrit);
                    return true;
                } else {
                    return false;
                }
            }
            case 1 -> {
                if (estPresent(individuListe.get(id).getType(), id)) {
                    etudiantInscrit.put(id, (Etudiant) individuListe.get(id));
                    System.out.println(etudiantInscrit);
                    return true;
                } else {
                    return false;
                }
            }
            default -> throw new NumberFormatException();
        }
    }

    // POUR SAVOIR SI L'INDIVIDU S'EST DEJA INSCRIT OU PAS

    public boolean estInscrit(int id) {
        if (individuListe.get(id) == null) {
            throw new IllegalArgumentException("ID INEXISTANT");
        }
        return switch (individuListe.get(id).getType()) {
            case 0 -> personnelInscrit.containsKey(id);
            case 1 -> etudiantInscrit.containsKey(id);
            default -> throw new NumberFormatException();
        };
    }

    public int getIndividu(int id) {
        if (individuListe.get(id) == null) {
            throw new IllegalArgumentException("ID INEXISTANT");
        }
        return individuListe.get(id).getType();
    }

    public Individu getPersonne(int id) {
        return individuListe.get(id);
    }

    public int nbPlaceAutoriseIndividu(int id) {
        Individu pers = getPersonne(id);
        int nb = 2;
        if (pers.typeIndividu() == Type.ETUDIANT) {
            if (((Etudiant) pers).getAnneeFormation() == 5) {
                nb = 4;
            }
        }
        return nb;
    }

    public boolean verificationPlaceEtTable(int numTable, int nbPlace) {
        boolean suffisant = false;
        Table t = getTable(numTable);

        if (t.getNumTable() == numTable) {
            suffisant = t.getPlaceLibre() >= nbPlace;
            System.out.println(suffisant);

        }


        return suffisant;

    }

    public Table getTable(int numTable) {
        Table table = null;
        for (Table t : tables) {
            if (t.getNumTable() == numTable) {

                table = t;
                break;
            }
        }
        return table;
    }

    public boolean faireReservation(int numTable, int nbPlace, int id, LocalDate date) {

        boolean accepte = verificationPlaceEtTable(numTable, nbPlace);
        if (accepte) {
            Individu pers = getPersonne(id);
            pers.setReservation(new Reservation(date, numTable, nbPlace));
            Table t = getTable(numTable);
            t.ajoutPersonne(pers, nbPlace);
            System.out.println(pers);
            System.out.println(t.getPlaceLibre());
            return true;
        } else {
            throw new IllegalArgumentException("Il n'y a pas assez de place sur la table " + numTable + " pour votre demande");
        }

    }

    public int getTableAleatoire(int nbPlace, Type t) {
        int debut = -1;
        int fin = -1;
        int numTable=-1;

        switch (t) {
            case ETUDIANT -> {
                debut = 10;
                fin = 25;
                break;
            }
            case PERSONNEL -> {
                debut = 0;
                fin = 10;
                break;
            }
            default -> throw new IllegalArgumentException("Type inexistante");
        }


        for (int i = debut; i < fin; i++) {
            if (tables.get(i).verificationPlaceSuffisant(nbPlace)) {
                numTable=i+1;
                break;
            }

        }
        return numTable;


    }

    //  C4EST RETURN DONC çA NE CONTINUE MEME PAS
    private boolean desinscription(int id) {
        switch (individuListe.get(id).getType()) {
            case 0 -> {
                for (int i = 0; i < 10; i++) {
                    if (tables.get(i).retirerParticipant(id)) {
                        return true;
                    }
                }
                return false;
            }
            case 1 -> {
                for (Individu e : etudiantAttente) {
                    if (e.getId() == id) {
                        etudiantAttente.remove(e);
                        return true;
                    }
                }
                for (Individu e : etudiantAccepte) {
                    if (e.getId() == id) {
                        etudiantAccepte.remove(e);
                        return true;
                    }
                }
                for (int i = 10; i < 25; i++) {
                    if (tables.get(i).retirerParticipant(id)) {
                        return true;
                    }
                }
                return false;
            }
            default -> throw new NumberFormatException();
        }
    }

    // LA LISTE DES PERSONNELS DANS LES TABLES DES PERSONNELS
    public String tablePersonnel() {
        String s = "Table du personnel: \n";
        s += table(0, 10);
        return s;
    }


    //// LA LISTE DES ETUDIANTS DANS LES TABLES DES ETUDIANTS
    public String tableEtudiant() {
        String s = "Table des étudiants: \n";
        s += table(10, 25);
        return s;
    }

    // TO STRING DE LISTE DES INDIVIDUS DANS LES TABLES
    private String table(int debut, int fin) {
        String s = "";
        for (int i = debut; i < fin; i++) {
            s += "Table " + (int) (i + 1) + ": " + tables.get(i) + "\n";
        }
        return s;
    }

    public boolean aDejaReserve(int id){
        boolean reserve=false;
        Individu pers=getPersonne(id);
        if(pers.getReservation()!=null){
            reserve=true;
        }
        else{
            if(pers.typeIndividu()==Type.ETUDIANT){
            }
        }
        return reserve;
    }
}