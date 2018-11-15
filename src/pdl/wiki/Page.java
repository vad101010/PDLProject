package pdl.wiki;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Normalizer;
import java.util.ArrayList;

public class Page
{
    private String title;
    private String titleWithoutSpace;
    private ArrayList<String> csvList;
    private Url url;

    public Page(Url url, ArrayList<String> csvList)
    {
        this.csvList = csvList;
        // Récupération du titre
        String tabUrl[] = new String[0];
        try {
            tabUrl = URLDecoder.decode(url.getLink(), "UTF-8").split("/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.title = tabUrl[tabUrl.length - 1].replaceAll("_", " ");
        this.titleWithoutSpace = "";
        for (String mot : title.split("\\s")) {
            String temp = mot.replaceAll("'", "");
            titleWithoutSpace += temp.substring(0, 1).toUpperCase() + temp.substring(1);
        }
        // On remplace les accents par leurs lettres respectives
        this.titleWithoutSpace = Normalizer.normalize(titleWithoutSpace, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        this.url = url;
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
     * Permet de récupérer le titre de la page dans un format condensé
     * @return le titre de la page sans accent et espace
     */
    public String getTitleWithoutSpace() {
        return titleWithoutSpace;
    }

    /**
     * Permet de connaitre la liste des �l�ments contenu dans le fichier csv
     * @return retourne un tableau des diff�rents �l�ments contenu dans le fichier csv
     */
    public ArrayList<String> getCsvList()
    {
        return csvList;
    }

}
