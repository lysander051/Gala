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

    public boolean identification() {
        int numero=0;
        // demande si etudiant ou personnel et retourne 1 pour etudiant et 0 pour personnel
        int type = ihm.etudiantOuPersonnel();
        if(type==-1){
            //exit
            return false;
        }
        Boolean present=false;
        while(!present) {
            // demande numero etudiant
            try {
                numero = ihm.demanderNumero(type);
                // verifie si dans la listeEtudiant si identite=1 et listePersonnel si identite=0 le numero est dedans
                if (numero == -1) {
                    //exit
                    return false;
                }
                present = gala.estPresent(type, numero);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }


        }

        idIndividu=numero;

        return present;
    }


    public void inscription(){
           if(!gala.estInscrit(idIndividu)){
               int qi=ihm.quitOuInscription();
               if(qi==-1){
                   // quitter

               }
               else{
                   gala.inscription(idIndividu);
               }
           }

    }




    public void choisirMenu(){
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

            if(choix==1){
                table = ihm.demandeTable(gala.tablePersonnel(),Type.PERSONNEL);

            }

            if(choix==2){
                table= gala.getTableAleatoire(place,Type.PERSONNEL);
            }

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




   /* public finall() {
        boolean sIdentifier=identification();
        if(!sIdentifier) {
            // PROGRAMME S'ARRETE
        }
        else {

            ihm.inscriptionOuQuitter();

        }

    }*/




}
