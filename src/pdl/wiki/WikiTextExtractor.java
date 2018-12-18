package pdl.wiki;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.sweble.wikitext.parser.WikitextParser;
import org.sweble.wikitext.parser.nodes.WtNode;
import org.sweble.wikitext.parser.utils.SimpleParserConfig;
import xtc.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Extrait les tableaux des pages HTML en format Wikitext
 *
 * @return la liste des tableaux CSV créés
 */
public class WikiTextExtractor implements Extractor
{
    @Override
    public List<List<String>> getCSV(Url purl)
    {
        ArrayList<String> liste = new ArrayList<>();
        String wikitext = getWikitextFromApi(purl);
        if (!wikitext.equals(""))
        {
            WikitextParser parser = new WikitextParser(new SimpleParserConfig());
            try
            {
                System.out.println("AVANT parseArticle()");
                WtNode test = parser.parseArticle(wikitext, "test");
                System.out.println("APRES parseArticle()");
                liste.add("ça marche");
            }
            catch (IOException | ParseException e)
            {
                e.printStackTrace();
            }
        }
        List<List<String>> listDeList = new ArrayList<>();
        listDeList.add(liste);
        return listDeList;
    }

    /**
     * Récupère la page HTML à partir de l'URL
     *
     * @return le wikitext de la page en String
     */
    private String getWikitextFromApi(Url pUrl)
    {
        String wt = "";
        try
        {
            URL apiUrl = new URL("https://" + pUrl.getLang() + ".wikipedia.org/w/api.php?action=parse&format=json&prop=wikitext&page=" + pUrl.getPageName());
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream is = connection.getInputStream();
            StringWriter sw = new StringWriter();
            IOUtils.copy(is, sw, "UTF-8");
            String jsonString = sw.toString();
            JSONObject apiResult = new JSONObject(jsonString);
            wt = apiResult.getJSONObject("parse").getJSONObject("wikitext").toMap().get("*").toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return wt;
    }
}
