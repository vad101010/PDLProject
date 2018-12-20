package pdl.wiki;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale avec l'interface utilisateur
 */
public class WikipediaMatrix
{
    private static String savePath = System.getProperty("user.home");
    private static ArrayList<Page> pages = new ArrayList<>();
    private static ArrayList<Url> urls = new ArrayList<>();

    public static void main(String[] args)
    {
        System.out.println("Bonjour !");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choix = -1;
        while (true)
        {
            int nbcsv = 0;
            for (Url url : urls)
            {
                if (url.isValid())
                {
                    nbcsv += url.getTableCount();
                }
            }
            System.out.println("Les CSV seront sauvegardés sous : '" + savePath + "'");
            System.out.println("Nombre de tableau(x) trouvé(s) : " + nbcsv);
            System.out.println("Veuillez choisir une option parmi :");
            System.out.println("1. Lister les liens");
            System.out.println("2. Ajouter un lien (Wikipedia)");
            System.out.println("3. Retirer un lien");
            System.out.println("4. Changer le lieu de sauvegarde");
            if (nbcsv > 0)
            {
                System.out.println("5. Sauvegarder les tableaux (au format CSV) et quitter");
            }
            System.out.println("0. Quitter sans sauvegarder");
            System.out.println("Votre choix ?");
            try
            {
                String strchoix = reader.readLine();
                if (tryParseInt(strchoix))
                {
                    choix = Integer.parseInt(strchoix);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            switch (choix)
            {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    linkList();
                    break;
                case 2:
                    addLink();
                    break;
                case 3:
                    removeLink();
                    break;
                case 4:
                    changeSaveLocation();
                    break;
                case 5:
                    if (nbcsv > 0)
                    {
                        saveCSV();
                        System.exit(0);
                        break;
                    }
                default:
                    System.out.println("Veuillez faire un choix entre 0 et 5");
            }
        }
    }

    /**
     * Liste les liens ajoutés par l'utilisateur
     */
    private static void linkList()
    {
        if (urls.size() == 0)
        {
            System.out.println("Aucun lien enregistré");
        }
        for (Url url : urls) { System.out.println(url.toString()); }
    }

    /**
     * Propose à l'utilisateur d'ajouter un lien
     */
    private static void addLink()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Collez le lien (CTRL+V):");
        try
        {
            String lien = reader.readLine();
            urls.add(new Url(lien));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * propose de retirer un lien parmis la liste à l'utilisateur
     */
    private static void removeLink()
    {
        if (urls.size() == 0)
        {
            System.out.println("Aucun lien enregistré");
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Liste des liens enregistrés:");
        for (int i = 0; i < urls.size(); i++)
        {
            System.out.println(i + ". " + urls.get(i).toString());
        }
        try
        {
            int numlien = urls.size();
            while (numlien >= urls.size() || numlien < 0)
            {
                System.out.println("Numéro du lien à supprimer ?");
                String strnumlien = reader.readLine();
                if (tryParseInt(strnumlien))
                {
                    numlien = Integer.parseInt(strnumlien);
                }
            }
            urls.remove(numlien);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Propose à l'utilisateur un nouveau lieu de stockage pour ses sauvegarde de CSV
     */
    private static void changeSaveLocation()
    {
        boolean exists = false;
        while (!exists)
        {
            System.out.println("Entrez le chemin de sauvegarde des CSV");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try
            {
                savePath = reader.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            File Dir = new File(savePath);
            exists = Dir.exists();
        }
    }

    /**
     * Sauvegarde les CSV trouvé dans le lieu de stockage enregistré
     */
    private static void saveCSV()
    {
        Extractor htmlExtract = new HTMLExtractor();
        Extractor wikiExtract = new WikiTextExtractor();
        for (Url url : urls)
        {
            if (url.getTableCount() > 0)
            {
                Page page = new Page(url);
                page.setCsvListHtml(htmlExtract.getCSV(url));
                page.setCsvListWikitext(wikiExtract.getCSV(url));
                pages.add(page);
            }
        }
        pagestoFile();
        /*for (int i = 0; i < 2; i++)
        {
            String dirname = "";
            switch (i)
            {
                case 0:
                    extractor = new HTMLExtractor();
                    dirname = "html";
                    break;
                case 1:
                    extractor = new WikiTextExtractor();
                    dirname = "wikitext";
            }
            for (Url url : urls)
            {
                System.out.println(dirname + " - " + urls.size());
                if (url.getTableCount() > 0)
                {
                    Page page = new Page(url);
                    page.setCsvListHtml(extractor.getCSV(url));
                    page.setCsvListWikitext(extractor.getCSV(url));
                    pages.add(page);
                }
            }
            PagetoFile(dirname);
        }*/
    }

    /**
     * enregistre les Pages au format CSV
     */
    private static void pagestoFile()
    {
        for (Page page : pages)
        {
            createAndSave("html", page);
            createAndSave("wikitext", page);
        }
    }

    private static void createAndSave(String type, Page page)
    {
        int i = 0;
        List<List<String>> list = null;
        String dirname = type;
        switch (type)
        {
            case "html":
                list = page.getCsvListHtml();
                break;
            case "wikitext":
                list = page.getCsvListWikitext();
        }
        for (List<String> csvList : list)
        {
            String fileSeparator = System.getProperty("file.separator");
            File file = new File(savePath + fileSeparator + dirname + fileSeparator + page.getTitleWithoutSpace() + "-" + i + ".csv");
            try
            {
                new File(savePath + fileSeparator + dirname).mkdir();
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(file.getAbsolutePath());
                for (String ligneCsv : csvList)
                {
                    fos.write(ligneCsv.getBytes());
                    fos.write(System.getProperty("line.separator").getBytes());
                }
                fos.flush();
                fos.close();
                System.out.println("'" + file.getAbsolutePath() + "' a été enregistré");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            i++;
        }
    }

    /**
     * vérifie si il est possible ou non de parser un String en int
     *
     * @param value String à parser
     * @return true ssi un String peut être parsé en int
     */
    private static boolean tryParseInt(String value)
    {
        try
        {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
