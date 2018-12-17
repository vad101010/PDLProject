package pdl.wiki;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Normalizer;
import java.util.List;

/**
 * Classe qui récupère le titre de la page et le nettoie
 */
public class Page
{
    private String title;
    private List<List<String>> csvList;
    private Url url;

    public Page(Url url, List<List<String>> csvList)
    {
        this.url = url;
        this.csvList = csvList;
        // Récupération du titre
        this.title = purifyTitle();
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
    public List<List<String>> getCsvList()
    {
        return csvList;
    }

    /**
     * Permet de récupérer le titre de la page dans un format condensé
     * @return le titre de la page sans accent et espace
     */
    public String getTitleWithoutSpace() {
        String titleWithoutSpace = "";
        for (String mot : title.split("\\s")) {
            String temp = mot.replaceAll("'", "");
            titleWithoutSpace += temp.substring(0, 1).toUpperCase() + temp.substring(1);
        }
        // On remplace les accents par leurs lettres respectives
        titleWithoutSpace = Normalizer.normalize(titleWithoutSpace, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return  titleWithoutSpace;
    }

    /**
     * Permet de clarifier le titre des tableaux
     * @return le titre sans les paramètres de l'url
     */
    private String purifyTitle() {
        String[] tabUrl = new String[0];
        String purifiedTitle = "";
        try {
            // Décodage de l'url pour obtenir la version unicode
            tabUrl = URLDecoder.decode(url.getLink(), "UTF-8").split("/");
            // Remplacement des underscore par des espaces
            purifiedTitle = tabUrl[tabUrl.length - 1].replaceAll("_", " ");
            // Suppresion des paramètres si présents
            if (purifiedTitle.contains("?")) {
                purifiedTitle = purifiedTitle.split("\\?")[0];
            }
            // Si aucun paramètre n'existait on supprime le potentiel lien interne
            if (purifiedTitle.contains("#")) {
                purifiedTitle = purifiedTitle.split("#")[0];
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return purifiedTitle;
    }

}
