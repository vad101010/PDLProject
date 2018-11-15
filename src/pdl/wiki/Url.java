package pdl.wiki;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Permet de récupérer un lien, de tester sa validité et d'ajouter le nombre de tableau présent à une base
 * retourne le lien,sa validité et le nombre de tableau
 */
public class Url
{
    private String link;
    private boolean valid;
    private int tableCount;
    private List<Element> listTables;

    public Url(String link)
    {
        this.link = link;
        this.listTables = PageChecker.urlCheck(link);
        this.tableCount = (listTables != null ? listTables.size() : -1);
        this.valid = tableCount > -1;
    }

    /**
     * Permet de récupérer le lien url
     *
     * @return retourne le lien
     */
    public String getLink()
    {
        return link;
    }

    /**
     * Permet de tester si le lien est valide
     *
     * @return retourne vrai si le lien est valide, faux sinon
     */
    public boolean isValid()
    {
        return valid;
    }

    /**
     *
     * @return une liste des Element tables trouvés sur la page
     */
    public List<Element> getListTables() {
        return listTables;
    }

    /**
     * Permet de récupérer le nombre de tableau qu'il y a dans la page
     *
     * @return retourne le nombre de tableau de la page
     */
    public int getTableCount()
    {
        return tableCount;
    }

    @Override
    public String toString()
    {
        if (!valid)
        {
            return link + "(Lien invalide !)";
        }
        return link + "(" + tableCount + ((tableCount) > 1 ? " tableaux trouvés)" : " tableau trouvé)");
    }
}