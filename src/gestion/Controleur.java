package gestion;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Controleur {
    private Gala gala;
    private Ihm ihm;
    private int idIndividu;
    private ServiceStockage stockage;


    /**
     * Constructeur du controleur avec la date du gala, initialise Ihm et Gala ou lit le fichier de sauvegarde
     * Affiche l'état du gala
     * Renvoie la méthode d'identification pour l'individu
     * @param date la date du gala
     */
    public Controleur(LocalDate date) {
        try {
            stockage=new ServiceStockage();
            File fichGala = new File("./gala.ser");
            this.ihm = new Ihm();
            if(fichGala.length()==0){
                this.gala = new Gala(date);
                stockage.enregistrer(gala);
            }
            else {
                this.gala = (Gala)stockage.charger();
                miseAJourEtudiant(LocalDate.now(),date);
                ihm.etatGala(gala.toString());
            }
        }
        catch (Exception e){
            ihm.afficheErreur(e.getMessage());
        }
        this.identification();
    }


    /**
     * Mettre à jour la liste des étudiants dont la demande de réservation a été acceptée si on est dans le mois avant la date du gala
     * @param dateNow date à laquelle l'individu se connecte
     * @param dateGala date du Gala
     */
    private void miseAJourEtudiant(LocalDate dateNow,LocalDate dateGala){
        if(ChronoUnit.MONTHS.between(dateNow,dateGala)<=1){
            gala.updateReservation();
        }
    }


    /**
     * Partie d'identification de l'individu
     * Demande le type et le numéro d'identifation
     * Verifie si l'identification est bon
     * Renvoie la méthode d'inscription
     */
    private void identification() {
        int id=0;
        Type type = ihm.etudiantOuPersonnel();
        if(type==null)
            System.exit(0);
        while(true) {
            try{
                id = ihm.demanderNumero();
            }catch(NumberFormatException e){
                System.out.println("Le numéro ne comporte que des chiffres");
            }
            if (gala.estPresent(type, id))
                break;
            System.out.println("le numéro d'identification n'éxiste pas");
        }
        idIndividu=id;
        this.inscription();
    }


    /**
     * Partie inscription de l'individu
     * Verifie si l'individu n'est pas encore inscrit et demande s'il veut s'inscrire
     * Renvoie la méthode menu
     */
    private void inscription(){
        if(!gala.estInscrit(idIndividu)){
            boolean qi=ihm.quitOuInscription();
            if(!qi){
                System.exit(0);
            }
            gala.inscription(idIndividu);
        }
        this.Menu();
    }

    /**
     * Partie du menu
     * Affiche les sous-menus disponible à chaque situation de l'individu
     * Demande le choix de l'individu
     * renvoie vers le choix de l'individu
     */
    private void Menu(){
        int menu= ihm.choixMenu(gala.attenteConfirmation(idIndividu));
        if(menu==1){
            // gerer place
            gererPlace();
        }
        if(menu==2){
            // se desinscrire
            try{
                gala.desinscription(idIndividu,LocalDate.of(2021,11,30));
                stockage.enregistrer(gala);
                System.exit(0);
            }
            catch (Exception e){
                ihm.afficheErreur(e.getMessage());
            }
        }
        if(menu==-1){
            //quitter
            try{
                stockage.enregistrer(gala);
                System.exit(0);
            }
            catch (Exception e){
                ihm.afficheErreur(e.getMessage());
            }
        }
        if(menu==4){
            // confirmer la reservation de l'etudiant
            confirmationEtudiant();
        }
    }


    /**
     * Partie gerer la place au diner
     * La gestion des places diffère selon le type de l'individu : personnel ou étudiant
     * Si une réservation ou une demande de réservation a déja été effectué, une synthèse est affichée
     * On enregistre toutes les modifications faites et on quitte l'application
     */
   private void gererPlace(){
        Individu pers=gala.getPersonne(idIndividu);
        if(gala.aDejaReserve(idIndividu)){
            switch (pers.typeIndividu()){
                case PERSONNEL ->
                    ihm.afficheSyntheseReservation(pers.getNom(),pers.getNbReservation(),pers.getNumTableReservation());

                case ETUDIANT -> {
                    if(pers.getNbReservation()==0){
                        ihm.afficheSyntheseReservation(pers.getNom(), gala.getNbDemande(idIndividu));
                    }
                    else{
                        ihm.afficheSyntheseReservation(pers.getNom(),pers.getNbReservation(),pers.getNumTableReservation());
                    }
                }
                default -> throw new IllegalArgumentException("Type inexistant");
            }
        }
        else {
            ihm.affichageNbPlacePossible((gala.nbPlaceAutoriseIndividu(idIndividu)));
            switch (pers.typeIndividu()) {
                case PERSONNEL -> {
                    gererPlacePersonnel();
                    return;
                }
                case ETUDIANT -> {
                    gererPlaceEtudiant();
                    return;
                }
                default -> throw new IllegalArgumentException("Type inexistant");
            }
        }
        try{
            stockage.enregistrer(gala);
            // quitter l'application
        }
        catch (Exception e){
            ihm.afficheErreur(e.getMessage());
        }
    }


    /**
     * Gestion des places des personnels
     * Demande si le personnel souhaite visionner le plan de table
     * Si oui, demande le numéro de la table, le nombre de place souhaité
     * si non, attribut une table aléatoire par rapport au nombre de place souhaité
     * Renvoie au sous-menu s'il n'y a pas assez de place sur la table choisie
     * Sinon, la réservation est acceptée et on enregistre les modifications effectuées
     * On quitte l'application
     */
    private void gererPlacePersonnel(){
        boolean ouiPlanTable=ihm.OuiOuNonPlanTable();
        try {
            int table=0;
            if(ouiPlanTable){
                table = ihm.demandeTable(gala.tablePersonnel(),Type.PERSONNEL);
            }
            int place = ihm.demandeNbPlace(gala.nbPlaceAutoriseIndividu(idIndividu));
            if(!ouiPlanTable){
                table= gala.getTableAleatoire(place,Type.PERSONNEL);
            }
            gala.faireReservation(table,place,idIndividu,LocalDate.now());
            ihm.afficheMontant(gala.montantReservationGala(idIndividu),place );
        } catch (Exception e) {
            ihm.afficheErreur(e.getMessage());
            Menu();
        }
    }


    /**
     * Gestion des places des étudiants
     * Insertion de l'étudiant dans la liste d'attente des demandes de réservations avec le nombre de places souhaité
     */
    private void gererPlaceEtudiant(){
        int place = ihm.demandeNbPlace(gala.nbPlaceAutoriseIndividu(idIndividu));
        gala.enregistrementListeAttente(idIndividu,place);
    }


    /**
     * Confirmation de la réservation de l'étudiant
     * Affiche le nombre de place qu'il souhaite réservé et finalise la réservation en demandant ou pas le numéro de la table souhaité
     * Affiche le montant de la reservation si celle si est accepté et les modifications sont enregistrées
     * On quitte l'application
     */
    private void confirmationEtudiant(){
        int place=gala.getNbDemande(idIndividu);
        ihm.afficheNbReservationEt(place);
        boolean ouiPlanTable=ihm.OuiOuNonPlanTable();
        try {
            int table=0;
            if(ouiPlanTable) {
                table = ihm.demandeTable(gala.tableEtudiant(), Type.ETUDIANT);
            }
            if(!ouiPlanTable){
                table= gala.getTableAleatoire(place,Type.ETUDIANT);
            }
            gala.faireReservation(table,place,idIndividu,LocalDate.now());
            ihm.afficheMontant(gala.montantReservationGala(idIndividu),place );
            stockage.enregistrer(gala);
            // quitter l'application
        } catch (Exception e) {
            ihm.afficheErreur(e.getMessage());
            Menu();
        }
    }
}
