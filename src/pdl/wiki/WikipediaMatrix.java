package pdl.wiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WikipediaMatrix
{
    private static String savePath = "C:\\Users\\Public\\Documents";
    private static ArrayList<Page> pages;
    private static ArrayList<Url> urls;

    public static void main(String[] args)
    {
        System.out.println("Bonjour !");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choix = 0;
        int nbcsv = 0;
        while (true)
        {
            for (Page page : pages)
            {
                nbcsv += page.getCsvList().size();
            }
            System.out.println("Les CSV seront sauvegarder sous :" + savePath);
            System.out.println("Nombre de CSV trouvé :" + nbcsv);
            System.out.println("Veuillez choisir une option parmis:");
            System.out.println("1. Lister les liens");
            System.out.println("2. Ajouter un lien (Wikipedia)");
            System.out.println("3. Retirer un lien");
            System.out.println("4. Changer le lieu de sauvegarde");
            if (nbcsv > 0)
            {
                System.out.println("5. Sauvegarder les CSV et quitter");
            }
            System.out.println("0. Quitter");
            System.out.println("votre choix ?");
            try
            {
                String strchoix = reader.readLine();
                choix = Integer.parseInt(strchoix);
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
                    saveCSV();
                    break;
                default:
                    System.out.println("veuillez faire un choix entre 0 et 5");
            }
        }
    }

    private static void linkList()
    {
        for (Url url : urls) { url.toString();}
    }

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

    private static void removeLink()
    {
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
                numlien = Integer.parseInt(strnumlien);
            }
            urls.remove(numlien);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

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
                choix = Integer.parseInt(strchoix);
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
            Page page = new Page(url, extractor.getCSV(url));
        }
    }
}
