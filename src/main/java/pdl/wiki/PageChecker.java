package pdl.wiki;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class PageChecker
{

    /**
     * La méthode orchestre la vérification en 2 étapes du lien fourni
     *
     * @param pUrl, c'est le lien de l'url qui est vérifiée
     * @return -1 si le lien fourni n'est pas exploitable ou le nombre de tableaux présents sur la page (0 ou +)
     */
    public static List<Element> urlCheck(String pUrl)
    {
        // On ajoute le https s'il n'est pas présent dans l'url
        if (pUrl.startsWith("http") && !pUrl.startsWith("https"))
        {
            pUrl = "https" + pUrl.substring(4);
        }
        else if (!pUrl.contains("://"))
        {
            pUrl = "https://" + pUrl;
        }
        if (pageExist(pUrl))
        {
            return pageContainsTable(pUrl);
        }
        return null;
    }

    /**
     * @param pUrl
     * @return true si la page est un lien existant de Wikipédia, false si la page n'appartient pas au domaine Wikipédia
     * ou est injoignable
     */
    private static boolean pageExist(String pUrl)
    {
        // On vérifie d'abord que le lien renvoi vers une page existante
        Connection.Response response = null;
        try
        {
            response = Jsoup.connect(pUrl).execute();
            if (response.statusCode() == 200)
            {
                // Si la librairie a réussi à se connecter on teste ensuite si la page provient de wikipedia
                return pUrl.split("//")[1].split("/")[0].contains("wikipedia");
            }
        }
        catch (IOException e)
        {
            System.out.println("Une erreur est survenue pendant la connexion à la page (" + e.getCause() + ")");
        }
        return false;
    }

    /**
     * La méthode est appelée uniquement si le lien a passé la 1ère vérification et compte le nombre de tableaux existants sur la page
     *
     * @param pUrl
     * @return le nombre de tableaux présents sur la page
     */
    private static List<Element> pageContainsTable(String pUrl)
    {
        List<Element> colTable = new ArrayList<>();
        try
        {
            Document page = Jsoup.connect(pUrl).get();
            // Récupération des tableaux de Wikipedia via le selecteur css sur la classe wikitable propre aux tableaux
            Elements tables = page.select(".wikitable");
            for (Element table : tables)
            {
                // On compte le nombre de cases fusionnées afin d'en ignorer les tableaux parents
                int nbExtendedCells = table.select("td[colspan]").size() + table.select("td[rowspan]").size() + table.select("th[colspan]").size() + table.select("th[rowspan]").size();
                if (nbExtendedCells == 0)
                {
                    colTable.add(table);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return colTable;
    }
}