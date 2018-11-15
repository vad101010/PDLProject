package pdl.wiki;

import java.util.ArrayList;

public class WikiTextExtractor implements Extractor
{
    private ArrayList<String> genCSVList()
    {
        return null;
    }

    @Override
    public ArrayList<String> getCSV(Url purl)
    {
        ArrayList<String> liste = new ArrayList<>();
        liste.add("Dupont est le meilleur");
        liste.add("Dupont est pas le meilleur");
        return liste;
    }
}
