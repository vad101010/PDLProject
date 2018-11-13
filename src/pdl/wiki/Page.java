package pdl.wiki;

import java.util.ArrayList;

public class Page
{
    private String title;
    private ArrayList<String> csvList;
    private Url url

    public Url getUrl()
    {
        return url;
    }

    public String getTitle()
    {
        return title;
    }

    public ArrayList<String> getCsvList()
    {
        return csvList;
    }

    public void setCsvList(ArrayList<String> csvList)
    {
        this.csvList = csvList;
    }
}
