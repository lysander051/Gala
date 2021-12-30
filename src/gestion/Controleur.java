package gestion;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Controleur {
    private Gala gala;
    private Ihm ihm;
    private int idIndividu;
    private ServiceStockage stockage;

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
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.identification();
    }

    // affiche Etat de gala et son contenu
    public void identification() {
        int id=0;
        Type type = ihm.etudiantOuPersonnel();
        while(true) {
            id = ihm.demanderNumero();
            if (gala.estPresent(type, id))
                break;
            System.out.println("le numéro d'identification n'éxiste pas");
        }
        idIndividu=id;
        this.inscription();
    }

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

    public void Menu(){
        int menu= ihm.choixMenu(gala.attenteConfirmation(idIndividu));


        if(menu==1){

            gererPlace();
            // gerer place
        }
        if(menu==2){
            // se desinscrire
        }
        if(menu==-1){
            //quitter
            try{
                stockage.enregistrer(gala);
                System.exit(0);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        if(menu==4){
            confirmationEtudiant();
        }
    }

   public void gererPlace(){
        Individu pers=gala.getPersonne(idIndividu);
        if(gala.aDejaReserve(idIndividu)){
            switch (pers.typeIndividu()){
                case PERSONNEL -> {
                    ihm.afficheSyntheseReservation(pers.getNom(),pers.getNbReservation(),pers.getNumTableReservation());
                    break;
                }
                case ETUDIANT -> {
                    if(pers.getNumTableReservation()==0){

                        ihm.afficheSyntheseReservation(pers.getNom(),pers.getNbReservation());
                    }
                    else{
                        ihm.afficheSyntheseReservation(pers.getNom(),pers.getNbReservation(),pers.getNumTableReservation());

                    }
                    break;
                }
                default -> throw new IllegalArgumentException("Type inexistant");
            }
        }
        else {
            ihm.affichageNbPlacePossible((gala.nbPlaceAutoriseIndividu(idIndividu)));
            switch (pers.typeIndividu()) {
                case PERSONNEL -> {
                    gererPlacePersonnel();
                    break;
                }
                case ETUDIANT -> {
                    gererPlaceEtudiant();
                    break;
                }
                default -> throw new IllegalArgumentException("Type inexistant");
            }

        }
        try{
            stockage.enregistrer(gala);
            // quitter l'application


        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }


    }

    public void miseAJourEtudiant(LocalDate dateNow,LocalDate dateGala){

        if(ChronoUnit.MONTHS.between(dateNow,dateGala)<=1){
            gala.updateReservation();
        }
    }

    public void gererPlaceEtudiant(){
        int place = ihm.demandeNbPlace(gala.nbPlaceAutoriseIndividu(idIndividu));
        gala.enregistrementListeAttente(idIndividu,LocalDate.now(),place);

    }

    public void confirmationEtudiant(){
        int place=gala.getPersonne(idIndividu).getNbReservation();
        ihm.afficheNbReservationEt(place);
        int choix=ihm.OuiOuNonPlanTable();
        try {
            int table=0;
            if(choix==1)
                System.out.println(gala.tableEtudiant());
                table = ihm.demandeTable(gala.tableEtudiant(),Type.ETUDIANT);
            if(choix==2)
                table= gala.getTableAleatoire(place,Type.ETUDIANT);
            gala.faireReservation(table,place,idIndividu,LocalDate.now());
            ihm.afficheMontant(gala.montantReservationGala(idIndividu),place );
            stockage.enregistrer(gala);
            // quitter l'application

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Menu();
        }
    }

    public void gererPlacePersonnel(){
        int choix=ihm.OuiOuNonPlanTable();
        try {
            int table=0;
            if(choix==1){

                System.out.println(gala.tablePersonnel());
                table = ihm.demandeTable(gala.tablePersonnel(),Type.PERSONNEL);

            }
            int place = ihm.demandeNbPlace(gala.nbPlaceAutoriseIndividu(idIndividu));
            if(choix==2){
                table= gala.getTableAleatoire(place,Type.PERSONNEL);
            }
            gala.faireReservation(table,place,idIndividu,LocalDate.now());
            ihm.afficheMontant(gala.montantReservationGala(idIndividu),place );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Menu();
        }
    }
}
