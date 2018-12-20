package fr.istic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Classe permettant la conversion de tables d'une page HTML en format CSV
 */
public class HTMLExtractor implements Extractor
{

    /**
     * Constructeur de fr.istic.HTMLExtractor par défaut
     */
    public HTMLExtractor()
    {
    }

    /**
     * Méthode qui convertit l'url passé en parametre en document, puis recupère les tableaux
     * correspondant
     *
     * @param purl fr.istic.Url de la page HTML
     * @return Les tableaux sous format Elements
     * @throws IOException
     */
    public Elements getTables(String purl) throws IOException
    {
        Document doc = Jsoup.connect(purl).get();
        Elements table = doc
                .select("table");
        return table;
    }

    /**
     * Convertit les tables d'une page url, validée par fr.istic.PageChecker en une collection de tables au format CSV
     * <p>
     * url de la page Wikipédia contentant les tables
     *
     * @return une collection de tables au format CSV
     */
    @Override
    public List<List<String>> getCSV(Url purl)
    {

        StringBuilder ligne;
        List<List<String>> listeDeList = new ArrayList<>();
        List<Element> listTables = purl.getListTables();
        for (Element e : listTables)
        {
            List<String> csvData = new ArrayList<>();
            //Elements elements = e.select("tr");

            Elements elements1 = e.select(":not(thead) tr");

            for (Element anElements1 : elements1)
            {
                ligne = new StringBuilder();
                Element row = anElements1;
                Elements rowItems = row.select("td");
                for (int j = 0; j < rowItems.size(); j++)
                {
                    ligne.append(rowItems.get(j).text());

                    if (j != rowItems.size() - 1)
                    {
                        ligne.append(";");
                    }
                }
                csvData.add(ligne.toString());
            }
            listeDeList.add(csvData);
        }
        return listeDeList;
    }
}
