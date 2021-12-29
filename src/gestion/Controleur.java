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
            if(fichGala.exists()){
                this.gala = new Gala(date);
                stockage.enregistrer(gala);
            }
            else {
                this.gala = (Gala)stockage.charger();
            }
            this.ihm = new Ihm();
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
        if(type==null){
            System.exit(0);
        }
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
        System.out.println("tonga ato amin'ny choisir menu");
        if(menu==-1){
            // quitter
        }
        if(menu==1){
            System.out.println("tonga ato amin'ny gere place");
            gererPlace();
            // gerer place
        }
        if(menu==2){
            // se desinscrire
        }
        if(menu==3){
            //pour les etudiants qui viennent d'etre accepte
        }
        if(menu==4){
            // confirmation reservation
        }
    }

    public void gererPlace(){
        Individu pers=gala.getPersonne(idIndividu);
        if(gala.aDejaReserve(idIndividu)){
            switch (pers.typeIndividu()){
                case PERSONNEL -> {
                    ihm.afficheSyntheseReservation(pers.getReservation().toString());
                    break;
                }
                case ETUDIANT -> {
                    if(pers.getReservation()==null){
                        // a completer
                    }
                    else{
                        ihm.afficheSyntheseReservation(pers.getReservation().toString());

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
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        // quitter l'application
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
                table = ihm.demandeTable(gala.tablePersonnel(),Type.PERSONNEL);
            if(choix==2)
                table= gala.getTableAleatoire(place,Type.PERSONNEL);
            gala.faireReservation(table,place,idIndividu,LocalDate.now());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            choisirMenu();
        }
    }

    public void gererPlacePersonnel(){
        int choix=ihm.OuiOuNonPlanTable();
        try {
            int table=0;
            if(choix==1){
                table = ihm.demandeTable(gala.tablePersonnel(),Type.PERSONNEL);
            }
            int place = ihm.demandeNbPlace(gala.nbPlaceAutoriseIndividu(idIndividu));
            if(choix==2){
                table= gala.getTableAleatoire(place,Type.PERSONNEL);
            }
            gala.faireReservation(table,place,idIndividu,LocalDate.now());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            choisirMenu();
        }
    }
}
