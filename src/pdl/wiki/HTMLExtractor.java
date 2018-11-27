package pdl.wiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Classe permettant la conversion de tables d'une page HTML en format CSV
 */
public class HTMLExtractor implements Extractor {

    /**
     * Constructeur de HTMLExtractor par défaut
     */
    public HTMLExtractor(){}

    /**
     * Méthode qui convertit l'url passé en parametre en document, puis recupère les tableaux
     * correspondant
     *
     * @param purl Url de la page HTML
     * @return Les tableaux sous format Elements
     * @throws IOException
     */
    public Elements getTables(String purl)throws IOException
    {
        Document doc = Jsoup.connect(purl).get();
        Elements table = doc
                .select("table");
        return table;
    }

    /**
     * Convertit les tables d'une page url, validée par PageChecker en une collection de tables au format CSV
     *
     *  url de la page Wikipédia contentant les tables
     * @return une collection de tables au format CSV
     */
    @Override
    public List<String> getCSV(Url purl)
    {
        String url = purl.getLink();
        List<String> csvData = new ArrayList<>();

        try{
            Elements tableElement = getTables(url);
            StringBuilder ligne = new StringBuilder();
            Elements tableHeaderEles = tableElement.select("thead tr th");

            List<Element> listTables = purl.getListTables();

            Iterator<Element> iterator = listTables.iterator();
            while (iterator.hasNext()) {
                ligne = new StringBuilder();
                ligne.append(iterator.next().select());

                if(i != tableHeaderEles.size() -1){
                    ligne.append(";");
                }
            }
            }


            for (int i = 0; i < tableHeaderEles.size(); i++) {
                ligne = new StringBuilder();
                ligne.append(tableHeaderEles.get(i).text());

                if(i != tableHeaderEles.size() -1){
                    ligne.append(";");
                }
            }

            csvData.add(ligne.toString());
            Elements tableRowElements = tableElement.select(":not(thead) tr");

            for (int i = 0; i < tableRowElements.size(); i++) {
                ligne = new StringBuilder();
                Element row = tableRowElements.get(i);
                Elements rowItems = row.select("td");
                for (int j = 0; j < rowItems.size(); j++) {
                    ligne.append(rowItems.get(j).text());

                    if(j != rowItems.size() -1){
                        ligne.append(";");
                    }
                }
                csvData.add(ligne.toString());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return csvData;
    }

    public static void main(String[] args) {
        HTMLExtractor html = new HTMLExtractor();
        Url url = new Url("https://fr.wikipedia.org/wiki/Table_de_multiplication");
        List<String> list = html.getCSV(url);
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
