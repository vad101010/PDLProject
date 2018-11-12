package pdl.wiki;

import java.util.ArrayList;

public interface Extractor
{
    ArrayList<String> getCSV(Url purl);
}
