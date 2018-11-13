package pdl.wiki;

import java.util.ArrayList;

public class Page
{
    private String title;
    private ArrayList<String> csvList;
    private Url url;

    /**
     * Permet de récuperer l'url sur lequel nous travaillons
     * @return retourne l'url sur lequel nous travaillons
     */
    public Url getUrl()
    {
        return url;
    }

    /**
     * Permet de récuperer le titre de la page sur laquelle nous travaillons
     * @return retourne le titre de la page sur laquelle nous travaillons
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Permet de connaitre la liste des éléments contenu dans le fichier csv
     * @return retourne un tableau des différents éléments contenu dans le fichier csv
     */
    public ArrayList<String> getCsvList()
    {
        return csvList;
    }

    /**
     * Changer le contenu du fichier csv
     * @param Un tableau de valeurs a rajouter au fichier csv  
     */
    public void setCsvList(ArrayList<String> csvList)
    {
        this.csvList = csvList;
    }
    
}
