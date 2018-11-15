package pdl.wiki;

import java.io.*;
import java.util.ArrayList;

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
            System.out.println("Les CSV seront sauvegarder sous : '" + savePath + "'");
            System.out.println("Nombre de tableau trouvé : " + nbcsv);
            System.out.println("Veuillez choisir une option parmis:");
            System.out.println("1. Lister les liens");
            System.out.println("2. Ajouter un lien (Wikipedia)");
            System.out.println("3. Retirer un lien");
            System.out.println("4. Changer le lieu de sauvegarde");
            if (nbcsv > 0)
            {
                System.out.println("5. Sauvegarder les tableaux (au format CSV) et quitter");
            }
            System.out.println("0. Quitter sans sauvegarder");
            System.out.println("votre choix ?");
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
                    System.out.println("veuillez faire un choix entre 0 et 5");
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
        System.out.println("collez le lien (CTRL+V):");
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
        System.out.println("liste des liens enregistrés:");
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
        int choix = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Extractor extractor = null;
        while (choix != 1 && choix != 2)
        {
            System.out.println("Sauvegarder via:");
            System.out.println("1. HTML");
            System.out.println("2. WikiText");
            String strchoix = null;
            try
            {
                strchoix = reader.readLine();
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
                case 1:
                    extractor = new HTMLExtractor();
                    break;
                case 2:
                    extractor = new WikiTextExtractor();
            }
        }
        for (Url url : urls)
        {
            if (url.isValid())
            {
                Page page = new Page(url, extractor.getCSV(url));
                pages.add(page);
            }
        }
        PagetoFile();
    }

    /**
     * enregistre les Pages au format CSV
     */
    private static void PagetoFile()
    {
        for (Page page : pages)
        {
            int i = 0;
            for (String csv : page.getCsvList())
            {
                String fileSeparator = System.getProperty("file.separator");
                File file = new File(savePath + fileSeparator + page.getTitleWithoutSpace() + "(" + i + ")" + ".csv");
                try
                {
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
                    fos.write(csv.getBytes());
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
