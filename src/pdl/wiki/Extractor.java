package pdl.wiki;

import java.util.List;

public interface Extractor
{
    /**
     * Renvoi la liste des csv d'une url wikipédia
     * @param purl url wikipédia
     * @return une liste de tableaux contenant des liste de lignes du CSV
     */
    List<List<String>> getCSV(Url purl);
}
