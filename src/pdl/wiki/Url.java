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
     * Permet de modifier le lien
     *
     * @param link est une chaîne de caractère correspondant au lien
     */
    public void setLink(String link)
    {
        this.link = link;
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
     * Permet de définir le lien
     *
     * @param valid est un booléen lié à la validité du lien
     */
    public void setValid(boolean valid)
    {
        this.valid = valid;
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

    /**
     * Permet d'ajouter le nombre de tableau à la base
     *
     * @param tableCount est l'entier correspondant au nombre de tableau de la page
     */
    public void setTableCount(int tableCount)
    {
        this.tableCount = tableCount;
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