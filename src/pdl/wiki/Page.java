package pdl.wiki;

import java.util.ArrayList;

public class Page
{
    private String title;
    private ArrayList<String> csvList;
    private Url url;

    public Page(Url url, ArrayList<String> csvList)
    {
        this.csvList = csvList;
        title = "à faire";
        this.url = url;
    }

    public Page(Url url)
    {
        this.url = url;
        title = "à faire";
    }

    /**
     * Permet de r�cuperer l'url sur lequel nous travaillons
     * @return retourne l'url sur lequel nous travaillons
     */
    public Url getUrl()
    {
        return url;
        
    }

    /**
     * Permet de r�cuperer le titre de la page sur laquelle nous travaillons
     * @return retourne le titre de la page sur laquelle nous travaillons
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Permet de connaitre la liste des �l�ments contenu dans le fichier csv
     * @return retourne un tableau des diff�rents �l�ments contenu dans le fichier csv
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

    public void addCsvList(String cvsAdd)
    {
        this.csvList.add(cvsAdd);
    }
}
