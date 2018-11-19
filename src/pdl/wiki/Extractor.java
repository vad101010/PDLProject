package pdl.wiki;

import java.util.List;

public interface Extractor
{
    List<String> getCSV(Url purl);
}
