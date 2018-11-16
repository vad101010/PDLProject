package pdl.wiki;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WikiTextExtractor implements Extractor
{
    private ArrayList<String> genCSVList;

    @Override
    public ArrayList<String> getCSV(Url purl)
    {
        ArrayList<String> liste = new ArrayList<>();
        String wikitext = getWikitextFromApi(purl);
        if (!wikitext.equals("")) {

        }
        /*liste.add("Dupont est le meilleur");
        liste.add("Dupont est pas le meilleur");*/
        return liste;
    }

    private String getWikitextFromApi(Url pUrl) {
        String wt = "";
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wt;
    }
}
