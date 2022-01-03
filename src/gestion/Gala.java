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
    private Map<Etudiant, Integer> demandeEtudiant = new HashMap<>();
    private PriorityQueue<Etudiant> etudiantAttente = new PriorityQueue<>(new ComparateurAnnee());
    private SortedSet<Etudiant> etudiantAccepte = new TreeSet<>();
    private List<Table> tables = new ArrayList<>();
    private LocalDate dateGala;


    /**
     * Constructeur de Gala
     * Crée une map  des individus de l'école avec leur identifiants( étudiant et personnel) par lecture des fichiers des étudiants et des personnels
     * Crée la liste des tables associées aux étudiants et aux personnels
     * @param date date du Gala
     */
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
        for (int i = 0; i < TABLE_PERSONNEL; i++) {
            tables.add(new Table());
        }
        for (int i = 0; i < TABLE_ETUDIANT; i++) {
            tables.add(new Table());
        }
    }


    /**
     * Changer la date de gala
     * @param dateGala nouvelle date d'un gala
     */
    public void setDateGala(LocalDate dateGala) {
        this.dateGala = dateGala;
    }

    /**
     * Donne l'individu correspondant à l'id
     * @param id le numero d'identification de l'individu
     * @return l'individu dont le numéro d'indentification est passé en paramètre
     */
    public Individu getPersonne(int id) {
        return individuListe.get(id);
    }

    /**
     * Verifie si l'individu est bien présent dans l'école
     * @param type le type de l'individu ( Etudiant ou Personnel)
     * @param id le numéro d'identification de l'individu
     * @return true si l'individu est présent dans l'école false sinon
     */
    public boolean estPresent(Type type, int id) {
        return individuListe.containsKey(id) && type == individuListe.get(id).typeIndividu();
    }


    /**
     * Verifie si l'individu s'est déjà inscrit en verifiant s'il est déjà present dans la liste des inscrits
     * @param id le numéro d'identification de l'individu
     * @return true si l'individu s'est déjà inscrit false sinon
     */
    public boolean estInscrit(int id) {
        if (individuListe.get(id) == null) {
            throw new IllegalArgumentException("ID INEXISTANT");
        }
        return switch (individuListe.get(id).typeIndividu()) {
            case PERSONNEL -> personnelInscrit.containsKey(id);
            case ETUDIANT -> etudiantInscrit.containsKey(id);
        };
    }


    /**
     * Inscrit l'individu dans la liste des inscrits pour le gala si il est bien présent dans l'école
     * @param id le numéro d'identification de l'individu
     * @return true si l'individu peux s'inscrire et false sinon
     */
    public boolean inscription(int id) {
        if (individuListe.get(id) == null) {
            throw new IllegalArgumentException("ID INEXISTANT");
        }
        switch (individuListe.get(id).typeIndividu()) {
            case PERSONNEL -> {
                if (estPresent(individuListe.get(id).typeIndividu(), id)) {
                    personnelInscrit.put(id, (Personnel) individuListe.get(id));
                    return true;
                } else {
                    return false;
                }
            }
            case ETUDIANT -> {
                if (estPresent(individuListe.get(id).typeIndividu(), id)) {
                    etudiantInscrit.put(id, (Etudiant) individuListe.get(id));
                    return true;
                } else {
                    return false;
                }
            }
            default -> throw new IllegalArgumentException("id non inexistant");
        }
    }


    /**
     * Pour savoir si l'individu dont l'id est passé en paramètre a déjà fait une demande ou réservé
     * @param id le numéro d'identification de l'individu
     * @return true si l'individu a déja réservé ou a déja fait une demande de réservation, false sinon
     */
    public boolean aDejaReserve(int id) {
        Individu i = getPersonne(id);
        switch (i.typeIndividu()) {
            case PERSONNEL -> {
                return i.getNbReservation() != 0;
            }
            case ETUDIANT -> {
                return demandeEtudiant.containsKey((Etudiant) i);
            }
            default -> throw new IllegalArgumentException("id inexistant");
        }
    }


    /**
     * Renvoie le nombre de place autorisé à l'individu correspondant
     * @param id le numero d'identification de l'individu
     * @return le nombre de place autorisé à l'individu
     */
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


    /**
     * Donne un numéro de table aléatoire correspondant au type de l'individu et qui a assez de place pour le nombre de place demandé
     * @param nbPlace le nombre de place demandé
     * @param t le type de l'individu
     * @return  un numéro de table qui a assez de place pour le nombre de place souhaité
     */
    public int getTableAleatoire(int nbPlace, Type t) {
        int debut ;
        int fin ;
        int numTable = -1;
        switch (t) {
            case ETUDIANT -> {
                debut = 10;
                fin = 25;

            }
            case PERSONNEL -> {
                debut = 0;
                fin = 10;

            }
            default -> throw new IllegalArgumentException("Type inexistante");
        }
        for (int i = debut; i < fin; i++) {
            if (tables.get(i).verificationPlaceSuffisant(nbPlace)) {
                numTable = i + 1;
                break;
            }
        }
        return numTable;
    }


    /**
     * Donne le plan de table du personnel: les tables de 1 à 10
     * @return Un texte sur le plan des tables du personnel
     */
    public String tablePersonnel() {
        return "\n-----------------------------------------------------\n" +
                "Table du personnel: \n" + table(0, 10);
    }


    /**
     *  Donne le plan de table des étudiants: les tables de 11 à 25
     * @return  Un texte sur le plan des tables des étudiants
     */
    public String tableEtudiant() {
        return "\n-----------------------------------------------------\n" +
                "Table des étudiants: \n"+ table(10, 25);
    }


    /**
     * @param debut le numero de table debut inclus ( numero de table -1 )
     * @param fin le numero de table fin exclus( numero de table -1
     * @return un texte sur le plan des tables de tout les tables entre le numéro debut et fin
     */
    private String table(int debut, int fin) {
        String s = "";
        for (int i = debut; i < fin; i++) {
            s += "Table " +(i + 1) + ": " + tables.get(i) + "\n";
        }
        return s;
    }


    /**
     * Verifie si la table dont le numéro est passé en paramètre a assez de place pour le nombre de place demandé
     * @param numTable le numéro de la table
     * @param nbPlace le nombre de place demandé
     * @return true si la table correspondant a assez de place pour le nombre de place demandé ,false sinon
     */
    public boolean verificationPlaceEtTable(int numTable, int nbPlace) {
        Table t = tables.get(numTable - 1);
       return t.getPlaceLibre() >= nbPlace;
    }


    /**
     * Renvoie le tarif correspondant à l'individu dont l'id est passé en paramètre
     * Si c'est un personnel, le tarif c'est le TARIF3 si c'est un étudiant de dernière année le tarif est le TARIF1 et les autres étudiants ont comme tarif le TARIF2
     * @param id le numéro d'identification de l'individu
     * @return le tarif correspondant à l'individu
     */
    public int tarifPrincipale(int id) {
        Individu i = getPersonne(id);
        Type t = i.typeIndividu();
        int tarif;
        switch (t) {
            case PERSONNEL ->
                tarif = TARIF3;
            case ETUDIANT -> {
                Etudiant e = (Etudiant) i;
                if (e.getAnneeFormation() == 5) {
                    tarif = TARIF1;
                } else {
                    tarif = TARIF2;
                }

            }
            default -> throw new IllegalArgumentException("Id inexistant");
        }
        return tarif;
    }


    /**
     * Enregistre la réservation de l'individu dont l'id est passé en paramètre
     * Si il y a assez de place dans la table choisie, on enregistre la réservation et on ajoute l'individu à la liste des participants sur la table correspondante
     * @param numTable le numéro de la table
     * @param nbPlace le nombre de place demandé
     * @param id le numéro d'indentification de la personne
     * @param date la date de réservation
     * @throw IllegalArgumentException si la table choisi n'a pas assez de place pour le nombre de place souhaité
     * @return true si la reservation c'est bien passée
     */
    public boolean faireReservation(int numTable, int nbPlace, int id, LocalDate date) {
        boolean accepte = verificationPlaceEtTable(numTable, nbPlace);
        if (accepte) {
            Individu pers = getPersonne(id);
            pers.setReservation(new Reservation(date, numTable, nbPlace, tarifPrincipale(id), TARIF3));
            Table t = tables.get(numTable - 1);
            t.ajoutPersonne(pers, nbPlace);
            return true;
        } else {
            throw new IllegalArgumentException("Il n'y a pas assez de place sur la table " + numTable + " pour votre demande");
        }
    }

    /**
     * Donne le montant de la réservation de l'individu dont l'id est passé en paramètre
     * @param id le numéro d'identification de l'individu
     * @return le montant de la réservation de l'individu
     */
    public double montantReservationGala(int id) {
        return getPersonne(id).getMontantReservation();
    }


    /**
     * On enregistre l'étudiant dans la liste d'attente
     * On ajoute l'étudiant dans la liste d'attente
     * On enregistre la demande de l'étudiant avec le nombre de place souhaité
     * @param num le numéro d'identifiant de l'étudiant
     * @param  nbPlace le nombre de place souhaité par l'étudiant
     */
    public void enregistrementListeAttente(int num,int nbPlace) {
        Etudiant e = (Etudiant) getPersonne(num);
        etudiantAttente.add(e);
        demandeEtudiant.put(e, nbPlace);
    }


    /**
     * Donne le nombre de place reservé aux étudiants à la soirée
     * * @return le nombre total des étudiants et de ses accompagnants pouvant venir à la soirée
     */
    public int getNbTotalEtudiant() {
        // 8 est le nombre de place de chaque table
        return TABLE_ETUDIANT * 8;

    }


    /**
     * Donne le nombre des étudiants dont la réservation ou la demande de réservation est acceptée
     * @return le nombre de place qui ont déjà été distribué et ceux dont la demande ont déjà été accepté
     */
    public int getNbEtudiantAccepte() {
        int nb = 0;
        for (Etudiant e : etudiantAccepte) {
            if(e.getNbReservation()!=0)
            {
                nb += e.getNbReservation();
            }
            else{
                nb+=demandeEtudiant.get(e);
            }
        }
        return nb;
    }


    /**
     * Donne le nombre de place qu'on puisse distribué pour les étudiants
     * @return le nombre de place restant qui peut être distribué pour les étudiants
     */
    public int getNbPlaceAcceptationRestant() {
        return getNbTotalEtudiant() - getNbEtudiantAccepte();
    }


    /**
     * Met à jour les demandes de réservations acceptées
     * Si les places sont suffisants, on accepte la demande de réservation de l'étudiant qui est au sommet de la queue
     * Si le nombre de place demandé par l'étudiant au sommet est supérieur au nombre de place restant, on regarde la demande de l'étudiant suivant
     * Quand on accepte la demande de réservation de l'étudiant : on l'ajoute dans la liste des étudiants acceptés et on attend sa confirmation
     */
    public void updateReservation() {
        Set<Etudiant> rajouter = new HashSet<>();
        int restant=getNbPlaceAcceptationRestant() ;
        int nbPlace;
        while (restant> 0) {
            Etudiant sommet= etudiantAttente.poll();
            if (sommet == null) {
                break;
            }
            nbPlace = demandeEtudiant.get(sommet);
            if (restant >= nbPlace) {
                etudiantAccepte.add(sommet);
            } else {
                rajouter.add(sommet);
                // je dois chercher la personne qui a besoin de nb de place qui reste
            }
            restant=getNbPlaceAcceptationRestant();
        }
        if (!rajouter.isEmpty()) {
            etudiantAttente.addAll(rajouter);
        }
    }


    /**
     * Donne le nombre de place que l'étudiant a demandé lors de sa demande de réservation
     * @param id le numéro d'identification de l'individu
     * @return le nombre de place demandé par l'étudiant lors de sa demande
     */
    public int getNbDemande(int id) {
        Etudiant e=(Etudiant)getPersonne(id);
        return demandeEtudiant.get(e);
    }


    /**
     * Pour savoir si l'individu dont l'id est passé en paramètre est en attente de confirmation de sa réservation
     * Si c'est un étudiant dont la demande vient d'être acceptée,il est en attente de confirmation ; tous les autres ne sont pas concernés
     * @param id le numéro d'indentification de l'individu
     * @return true si la demande de l'étudiant a été accepté et on attend la confirmation de sa réservation, false sinon
     */
    public boolean attenteConfirmation(int id) {
        Individu i = getPersonne(id);
        Type t = i.typeIndividu();
        switch (t) {
            case PERSONNEL -> {
                return false;
            }
            case ETUDIANT -> {
                return i.getNbReservation() == 0 && etudiantAccepte.contains((Etudiant) i);
            }
            default -> throw new IllegalArgumentException("Id non existant");
        }
    }


    /**
     * Annule la réservation faite par l'individu dont l'identifiant est passé en paramètre
     * Il est retiré de la liste des participants sur la table de sa réservation et sa reservation est annulée
     * @param id le numéro d'indentification de l'individu
     * @return true si la réservation est bien annulé, false sinon
     */
    public boolean annulationReservation(int id) {
        Individu ind = getPersonne(id);
        if (ind.getNbReservation() != 0) {
            int numTable = ind.getNumTableReservation();
            tables.get(numTable - 1).retirerParticipant(id, ind.getNbReservation());
            ind.annulerReservationInd();
            return true;
        }
        return false;
    }


    /**
     * Desinscrit l'individu dont le numéro est en paramètre
     * Si la date où il souhaite se désinscrire est avant les 10 derniers jours avant le Gala, on le désinscrit
     * Si c'est un personnel, on le supprime de la liste des personnels inscrits
     * Si c'est un étudiant, on le supprime de la liste des étudiants acceptés,de la demande de réservation, de la liste d'attente et des étudiants inscrits
     * @param id  le numéro d'indentification de l'individu
     * @param date la date où l'individu souhaite se désinscrire
     * @throw IllegalArgumentException si il souhaite se désinscrire dans les 10 jours avant le Gala
     * @return true si la désinscription s'est bien passé
     */
    public boolean desinscription(int id, LocalDate date) {
        System.out.println(dateGala);
        if (ChronoUnit.DAYS.between(date, dateGala) >= 10) {
            Individu ind = getPersonne(id);
            annulationReservation(id);
            switch (ind.typeIndividu()) {
                case PERSONNEL ->
                    personnelInscrit.remove(id);
                case ETUDIANT -> {
                    etudiantAccepte.remove((Etudiant)ind);
                    demandeEtudiant.remove((Etudiant)ind);
                    etudiantAttente.remove((Etudiant)ind);
                    etudiantInscrit.remove(id);
                }
                default -> throw new NumberFormatException();
            }
            return true;
        } else {
            throw new IllegalArgumentException("Vous ne pouvez plus vous desinscrire");
        }
    }


    /**
     * Un texte pour avoir l'état du gala
     * @return un texte de l'état du gala
     */
    @Override
    public String toString() {
        return "Gala du " + dateGala +
                "\n Liste des individus dans l'école : \n"
               + individuListe +
                "\n Liste des etudiants inscrits : \n" + etudiantInscrit +
                "\n Liste des personnels inscrits : \n" + personnelInscrit +
                "\n Liste des étudiants qui ont fait des demandes de reservations : \n" + demandeEtudiant +
                "\n Liste d'attente des étudiants : \n "+ etudiantAttente +
                "\n Liste des étudiants acceptés : \n " + etudiantAccepte +
                "\n Liste des tables \n" + tables+
                "\n------------------------------\n";
    }

}