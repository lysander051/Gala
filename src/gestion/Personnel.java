package gestion;

public class Personnel extends Individu{
    public Personnel(String nom, String prenom, String numTel, String eMail, int identifiant) {
        super( identifiant,nom, prenom, numTel, eMail);
    }
}
