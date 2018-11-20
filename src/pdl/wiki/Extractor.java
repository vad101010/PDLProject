package pdl.wiki;

import java.util.List;

/**
 * Interface de la liste des tableaux CSV
 */
public interface Extractor
{
    List<String> getCSV(Url purl);
}
