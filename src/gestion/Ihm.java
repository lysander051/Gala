package gestion;

import java.util.Scanner;

public class Ihm {
    Scanner identite = new Scanner(System.in); //scanner utiliser dans toute la classe

    /**
     * DemandeIdentite()
     * Cette méthode permet de demander si la personne est un étudiant ou un membre du personnel
     * @return 0, 1, 2 en fonction de si la personne est un étudiant ou un personnel
     */

    public int getIdentite() {
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
    }

    /**
     * méthode demanderNumero()
     * Cette méthode permet de récupérer le numéro étudiant ou du personnel qui réalise l'inscription
     * @return numero pour comparer avec la liste des Individus pouvant s'inscrire
     */

    public int getNumero() {
        int numero = 0;
        while (identite.hasNext()) {
            System.out.println("Entrez votre id personnel ou votre numéro étudiant: ");
            numero = identite.nextInt();
            if (Integer.toString(numero).length() == 7 || Integer.toString(numero).length() == 4) {
                return numero;
            }
        }
        return numero;
    }

    /**
     * méthode InscrireOuQuitter()
     * Cette méthode permet de s'inscrire ou d'abandonner l'inscription
     * @return 1 ou 2 en fonction de si l'utilisateur veut s'inscrire ou non
     */

    public int inscrireOuQuitter() {
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
    }

    /**
     * méthode afficherMenu()
     * Cette méthode permet d'afficher les places du diner, se désinscrire ou quitter
     * @return 0 pour afficher les places du diner, 1 pour se désinscrire avec confirmation et 2 pour quitter
     */

    public int afficherMenu() {
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
    }
}
