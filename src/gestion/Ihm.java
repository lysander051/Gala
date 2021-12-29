package gestion;

import java.util.Scanner;

public class Ihm {
    Scanner sc = new Scanner(System.in); //scanner utiliser dans toute la classe

    /**
     * DemandeIdentite()
     * Cette méthode permet de demander si la personne est un étudiant ou un membre du personnel
     * @return 0, 1, 2 en fonction de si la personne est un étudiant ou un personnel
     */

    /*public int demandeIdentite() {
        int id = -1;
        while (identite.hasNext()) {
            System.out.println("inscrivez 0 si vous êtes membre du personnel, 1 si vous êtes étudiants 2 pour quitter");
            id = identite.nextInt();
            if (id == 0) {
                return 0;
            } else if (id == 1) {
                return 1;
            } else if (id == 2) {
                break;
            }
        }
        return id;
    }*/

    public int etudiantOuPersonnel() {
        int type=-1;
        boolean pasBonneReponse = true;
        while (pasBonneReponse) {

            System.out.print("Si vous êtes personnel : 0 \nSi vous êtes etudiant : 1 \nSi vous voulez quitter : q \nVotre choix :");
            if (sc.hasNextInt()) {

                type = sc.nextInt();
                if (type == 0 || type == 1 || type == 2) {
                   break;
                }
            } else {
                if (sc.next().equals("q")) {
                    return -1;
                }
            }

        }
        return type;
    }

        public int demanderNumero(int type){

            int numero=-1;
            int taille;
            boolean pasBonneReponse=true;
            while(pasBonneReponse){
                System.out.print("Veuillez entrer votre numero ou 'q' pour quitter\nVotre choix : ");
                if(sc.hasNextInt()){
                    numero=sc.nextInt();
                    taille=String.valueOf(numero).length();
                    if((type==1 && taille==7) || (type==0 && taille==4)) {
                       break;
                    }
                }
                else {
                    if(sc.next().equals("q")){
                        return -1;
                    }

                }
            }
            return numero;
        }

    public int quitOuInscription(){
        int c=-1;

        boolean pasBonneReponse=true;
        while(pasBonneReponse){
            System.out.print("Pour quitter : q\nPour s'inscrire: 1 \nVotre choix: ");
            if(sc.hasNextInt()){
                c=sc.nextInt();

                if(c==1) {
                    break;
                }
            }
            else {
                if(sc.next().equals("q")) {
                    return -1;
                }

            }
        }
        return c;
    }
    public int choixMenu(boolean sup){
        int c=-1;
        String s="Menu\n1 – Gérer les places du dîner \n2 – Se désinscrire \n3 – Quitter\n";
        if(sup){
            s+="4-Confirmation réservation\n";
        }
        s+="Votre choix :";

        boolean pasBonneReponse=true;
        while(pasBonneReponse){
            System.out.print(s);
            if(sc.hasNextInt()){
                c=sc.nextInt();

                if(c==1 || c==2 ) {
                    break;
                }
                if(c==3){
                    c=-1;
                    break;
                }
                if(sup && c==4){
                    break;
                }
            }
            else {
                sc.next();

            }
        }
        return c;
    }

    public void afficheNbReservationEt(int place){
        System.out.println("Vous avez reservé "+place+" places");
    }

    public void affichageNbPlacePossible(int autorise){
        System.out.println("Vous avez droit à "+autorise+" places");

    }

    public int OuiOuNonPlanTable(){
        int choix=-1;
        boolean pasBonneReponse = true;
        while (pasBonneReponse) {

            System.out.print("Voulez vous consulter le plan des tables?\n 1- Oui \n 2- Non\nVotre choix :");
            if (sc.hasNextInt()) {

                choix = sc.nextInt();
                if (choix == 1 || choix == 2) {
                    pasBonneReponse=false;
                }
            } else {
                if (sc.next().equals("q")) {
                    return -1;
                }
            }

        }
        return choix;
    }

    public int demandeTable(String table,Type t){
        String s="";
        if(t==Type.PERSONNEL){
            s="Choisissez entre les tables 1 - 10";
        }
        else{
            s="Choisissez entre les tables 11 - 25";
        }
        System.out.println(table);
        int choix=-1;
        boolean pasBonneReponse = true;
        while (pasBonneReponse) {

            System.out.print(s+"\nVotre choix :");
            if (sc.hasNextInt()) {

                choix = sc.nextInt();
                if ((t==Type.ETUDIANT && choix>=11 && choix<=25) || (t==Type.PERSONNEL && choix>=1 && choix<=10)){
                    pasBonneReponse=false;
                }
            } else {
                if (sc.next().equals("q")) {
                    return -1;
                }
            }

        }
        return choix;

    }

    public int demandeNbPlace(int autorise){
        int choix=-1;
        boolean pasBonneReponse = true;
        while (pasBonneReponse) {

            System.out.print("Vous avez droit jusqu'à "+autorise+" places "+"\nVotre choix :");
            if (sc.hasNextInt()) {

                choix = sc.nextInt();
                if (choix>0 && choix<=autorise){
                    pasBonneReponse=false;
                }
            } else {
                if (sc.next().equals("q")) {
                    return -1;
                }
            }

        }
        return choix;
    }

    public void afficheSyntheseReservation(String s){
        System.out.println(s);
    }
    public void afficheSyntheseReservation(int nb){
        // a completer
    }
    /**
     * méthode demanderNumero()
     * Cette méthode permet de récupérer le numéro étudiant ou du personnel qui réalise l'inscription
     * @return numero pour comparer avec la liste des Individus pouvant s'inscrire
     */

   /* public int demanderNumero() {
        int numero = 0;
        while (identite.hasNext()) {
            System.out.println("Entrez votre id personnel ou votre numéro étudiant: ");
            numero = identite.nextInt();
            if (Integer.toString(numero).length() == 7 || Integer.toString(numero).length() == 4) {
                return numero;
            }
        }
        return numero;
    }*/

    /**
     * méthode InscrireOuQuitter()
     * Cette méthode permet de s'inscrire ou d'abandonner l'inscription
     * @return 1 ou 2 en fonction de si l'utilisateur veut s'inscrire ou non
     */

    /*public int InscrireOuQuitter() {
        int continuer = 0;
        while (identite.hasNext()) {
            System.out.println("Souhaitez-vous vous inscrire (1) ou quitter l'inscription (0)?");
            continuer = identite.nextInt();
            if (continuer == 1) {
                return 1;
            } else if (continuer == 0) {
                return 0;
            }
        }
        return continuer;
    }*/

    /**
     * méthode afficherMenu()
     * Cette méthode permet d'afficher les places du diner, se désinscrire ou quitter
     * @return 0 pour afficher les places du diner, 1 pour se désinscrire avec confirmation et 2 pour quitter
     */

   /* public int afficherMenu() {
        int menu = -1;
        Scanner sc = new Scanner(System.in);
        while (identite.hasNext()) {
            menu = identite.nextInt();
            if (menu == 0) {
                return 0;
            } else if (menu == 1) {
                if (sc.hasNext()) {
                    System.out.println("Etes-vous sur de vous désinscrire (oui / non?");
                    String validationDesinscription = sc.next();
                    if (validationDesinscription == "oui") {
                        return 1;
                    }
                }
            } else if (menu == 2) {
                return 2;
            }
        }
        return menu;
    }*/
}
