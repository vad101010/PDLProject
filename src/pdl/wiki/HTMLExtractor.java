package pdl.wiki;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLExtractor implements Extractor
{
    private ArrayList<String> genCSVList;

    @Override
    public ArrayList<String> getCSV(Url purl)
    {
        String url = purl.toString();
        ArrayList<String> csvData = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(url).get();
            Element table = doc
                    .select("div.mainwrapper div.main_background div.main_left")
                    .get(0);
            Elements rows = table .select("tr");
            csvData.add(rows.get(0).select("th").toString());

            for(int j = 1; j < rows.size(); j++){
                csvData.add(rows.get(j).select("td").toString());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return csvData;
    }
}
