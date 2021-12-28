package gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Gala {
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
                if (!scEtudiant.hasNext()) {
                    break;
                }
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

    public boolean estPresent(int type, int id) {
        return individuListe.containsKey(id) && type == individuListe.get(id).getType();
    }

    public boolean inscription(int id) {
        //TODO verifier si le get ne retourne rien, si cela fonctionne

        switch (individuListe.get(id).getType()) {
            case 0 -> {
                if (estPresent(individuListe.get(id).getType(), id)) {
                    personnelInscrit.put(id, (Personnel) individuListe.get(id));
                    return true;
                } else {
                    return false;
                }
            }
            case 1 -> {
                if (estPresent(individuListe.get(id).getType(), id)) {
                    etudiantInscrit.put(id, etudiantInscrit.get(id));
                    return true;
                } else {
                    return false;
                }
            }
            default -> throw new NumberFormatException();
        }
    }

    public boolean estInscrit(int id) {
        return switch (individuListe.get(id).getType()) {
            case 0 -> personnelInscrit.containsKey(id);
            case 1 -> etudiantInscrit.containsKey(id);
            default -> throw new NumberFormatException();
        };
    }

    public int getIndividu(int id) {
        return individuListe.get(id).getType();
    }

    private boolean desinscription(int id){
        switch (individuListe.get(id).getType()) {
            case 0 -> {
                for (int i = 0; i < 10; i++) {
                    if (tables.get(i).retirerParticipant(id)){
                        return true;
                    }
                }
                return false;
            }
            case 1 -> {
                for (Individu e: etudiantAttente) {
                if(e.getId() == id){
                    etudiantAttente.remove(e);
                    return true;
                }
            }
                for (Individu e: etudiantAccepte) {
                    if(e.getId() == id){
                        etudiantAccepte.remove(e);
                        return true;
                    }
                }
                for (int i = 10; i < 25; i++) {
                    if (tables.get(i).retirerParticipant(id)){
                        return true;
                    }
                }
                return false;
            }
            default -> throw new NumberFormatException();
        }
    }

    public String tablePersonnel(){
        String s = "Table du personnel: \n";
        s+= table(0,10);
        return s;
    }

    public String tableEtudiant(){
        String s = "Table des étudiants: \n";
        s+= table(10, 25);
        return s;
    }

    private String table(int debut, int fin){
        String s = "";
        for (int i = debut; i < fin; i++){
            s+= "Table " + i+1 + ": " + tables.get(i) + "\n";
        }
        return s;
    }
}