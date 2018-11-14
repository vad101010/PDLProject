package pdl.wiki;

import java.util.ArrayList;

public class Page
{
    private String title;
    private ArrayList<String> csvList;
    private Url url;

    public Page(Url url, ArrayList<String> csvList)
    {
        this.csvList = csvList;
        title = "à faire";
        this.url = url;
    }

    public Page(Url url)
    {
        this.url = url;
        title = "à faire";
    }

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

    public void addCsvList(String cvsAdd)
    {
        this.csvList.add(cvsAdd);
    }
}
