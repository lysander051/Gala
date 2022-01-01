package gestion;

import java.time.LocalDate;
import java.util.Scanner;

public class Ihm {
    Scanner sc = new Scanner(System.in);


    /**
     * Montre à l'utilisateur l'état du gala
     * @param s Un texte de l'etat de gala
     */
    public void etatGala(String s){
        System.out.println("ETAT DU GALA \n"+s);
    }


    /**
     * On demande à l'utilisateur si c'est un étudiant ou un personnel
     * @return le type de l'individu
     */
    public Type etudiantOuPersonnel() {
        System.out.print("êtes vous du personnel ou un employé? \n" +
                "[0] Personnel \n" +
                "[1] Etudiant \n" +
                "[q] quitter \n" +
                "Choix:");
        while (sc.hasNext()) {
            String choix = sc.next();
            if (choix.equals("0"))
                return Type.PERSONNEL;
            if (choix.equals("1"))
                return Type.ETUDIANT;
            if (choix.equals("q"))
                return null;
            System.out.println("probléme d'identification");
        }
        return null;
    }



    /**
     * Demande le numéro d'indentification de l'utilisateur
     * @return le numero de l'utilisateur
     */
    public int demanderNumero(){
        System.out.print("\n-----------------------------------------------------\n" +
                "Identification:\n" +
                "votre identifiant\n" +
                "[q] pour quitter\n" +
                "Identifiant: ");
        while(sc.hasNext()){
            String choix = sc.next();
            if(choix.equals("q"))
                return 0;
            return Integer.parseInt(choix);
        }
        return 0;
    }


    /**
     * Demande à l'utilisateur s'il veut s'inscrire ou bien quitter
     * @return true si l'utilisatuer veut s'inscrire false sinon
     */
    public boolean quitOuInscription(){
        System.out.print("\n-----------------------------------------------------\n" +
                "Inscription:\n" +
                "[1] S'inscrire\n" +
                "[q] Quitter\n" +
                "Inscription: ");
        while(sc.hasNext()){
            String res = sc.next();
            if(res.equals("1")){
                return true;
            }
            if(res.equals("q"))
                return false;
            System.out.println("problème de saisie");
        }
        return false;
    }

    /**
     * Affiche le menu correspondant à l'utilisateur
     * S'il est en attente de confirmation, un sous menu pour confirmer sa demande est ajouté
     * L'utilisateur choisit son sous-menu
     * @param sup boolean qui marque si l'utilisateur est en attente de confirmation ou pas
     * @return le choix du menu
     */
    public String choixMenu(boolean sup){
        System.out.print("\n-----------------------------------------------------\n" +
                "Menu principal:\n" +
                "[1] Se désinscrire\n" +
                "[2] Gérer les places du dîner\n");
        if(sup){
            System.out.println("[3] Confirmation réservation");
        }
        System.out.println("[q] Quitter\n" +
                "Quel menu: ");
        while(sc.hasNext()){
            String choix=sc.next();
            if(choix.equals("1") || choix.equals("2") || choix.equals("q"))
                return choix;
            if(sup && choix.equals("3"))
                return choix;
            System.out.println("problème de saisie");
        }
        return "q";
    }


    /**
     * Affiche la synthèse de la réservation effectué par l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param nbPlace le nombre de place réservé
     * @param numTable le numéro de la table réservé
     */
    public void afficheSyntheseReservation(String nom, int nbPlace, int numTable){
        String s=nom+" a effectué une reservation de "+nbPlace+" place(s) à la table n°"+numTable;
        System.out.println(s);
    }


    /**
     * Affiche la synthèse de la demande effectué par l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param nbPlace le nombre de place que l'utilisateur souhaite
     */
    public void afficheSyntheseReservation(String nom, int nbPlace){
        String s=nom+" a effectué une reservation de "+nbPlace+" place(s) ";
        System.out.println(s);
    }


    /**
     * Demande à l'utilisateur s'il veut visionner le plan de table
     * @return true si l'utilisateur veut visionner le plan de table, false sinon
     */
    public boolean OuiOuNonPlanTable(){
        System.out.print("\n-----------------------------------------------------\n" +
                "Voulez vous consulter le plan des tables?\n" +
                "[1] Oui \n" +
                "[2] Non\n" +
                "Votre choix: ");
        while(sc.hasNext()){
            String res = sc.next();
            if(res.equals("1")){
                return true;
            }
            if(res.equals("2")) {
                return false;
            }
            System.out.print("Problème de saisie");
        }
        return false;
    }


    /**
     * Demande le numéro de la table de l'utilisateur
     * @param table le plan de table correspondant au type de l'utilisateur
     * @param t le type de l'utilisateur
     * @return le numéro de la table choisi par l'utilisateur
     */
    public int demandeTable(String table,Type t){
        System.out.print(table);
        if(t==Type.PERSONNEL){
            System.out.println("Choisissez entre les tables [1-10]\n");
        }
        else{
            System.out.println("Choisissez entre les tables [11-25]\n");
        }
        System.out.print("Votre choix: ");
        while (sc.hasNext()) {
            int choix=sc.nextInt();
                if ((t==Type.ETUDIANT && choix>=11 && choix<=25) || (t==Type.PERSONNEL && choix>=1 && choix<=10)){
                    return choix;
                }
            System.out.print("Problème de saisie");
        }
        return -1;
    }



    /**
     * Demande à l'utilisateur de nombre de place qu'il souhaite réserver
     * @param autorise le nombre de place autorisé pour l'utilisateur
     * @return le nombre de place que l'utilisateur souhaite
     */
    public int demandeNbPlace(int autorise){
        int choix=-1;
        System.out.print("\n-----------------------------------------------------\n" +
                "Vous avez droit jusqu'à " + autorise + " places " + "\n" +
                "Nombre de place: ");
        while (sc.hasNext()) {
            String res=sc.next();
                if(res.matches("[0-9]")) {
                    choix = Integer.parseInt(res);
                    if (choix > 0 && choix <= autorise) {
                        break;
                    }
                }
            System.out.print("Problème de saisie");
            }
        return choix;
    }


    /**
     * Affiche à l'utilisateur le montant de sa réservation
     * @param montant le montant de la réservation
     * @param nbPlace le nombre de place réservé
     */
    public void afficheMontant(double montant,int nbPlace){
        String s="Le montant à payer pour "+nbPlace+" place(s): "+montant;
        System.out.println(s);
    }


    /**
     * Affiche le nombre de place que l'utilisateur a choisi
     * @param place le nombre de place que l'utilisateur a réservé
     */
    public void afficheNbReservationEt(int place){
        System.out.println("Vous avez reservé "+place+" places");
    }


    /**
     * Affiche le message d'erreur
     * @param s le message d'erreur
     */
    public void afficheErreur(String s){
        System.out.println(s);
    }
}
