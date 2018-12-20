package pdl.wiki;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant la conversion de tables d'une page HTML en format CSV
 */
public class HTMLExtractor implements Extractor
{
    /**
     * Convertit les tables d'une page url, validée par PageChecker en une collection de tables au format CSV
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

//            csvData.add(getTableHeader(e));
            // Suppression des tags <sup> et <sub>, étant trop souvent des liens
            e.getElementsByTag("sup").remove();
            e.getElementsByTag("sub").remove();

            Elements elements1 = e.select("tr");
            for (Element anElements1 : elements1)
            {
                ligne = new StringBuilder();
//                Elements rowItems = anElements1.select("td");
                Elements rowItems = anElements1.children();
                for (int j = 0; j < rowItems.size(); j++)
                {
                    String cellContent = rowItems.get(j).text().replaceAll(";", ",");
                    ligne.append(cellContent);
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

    private String getTableHeader(Element e)
    {
        String csvHeader = "";
        Elements header = e.select("thead tr td");
        for (Element colHeader : header)
        {
            csvHeader += colHeader.text();
        }
        return csvHeader;
    }

}
