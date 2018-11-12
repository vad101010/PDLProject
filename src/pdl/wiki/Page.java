package pdl.wiki;

import java.util.ArrayList;

public class Page
{
    private String title;
    private ArrayList<String> csvList;

    public Url getUrl()
    {
        return null;
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
