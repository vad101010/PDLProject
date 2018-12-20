package fr.istic;

import fr.istic.Extractor;
import fr.istic.HTMLExtractor;
import fr.istic.Url;
import fr.istic.WikiTextExtractor;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test pour la classe fr.istic.Extractor
 */
public class ExtractorTest
{

    Extractor extractorwiki;
    Extractor extractorhtml;
    String UrlWithTables;
    Map<String, Integer> nbtabliens;
    ArrayList<String> liens;
    List<List<String>> csvwiki;
    List<List<String>> csvhtml;
    List<String> csvTest;

    @BeforeEach
    public void setUp() throws Exception
    {
        extractorhtml = new HTMLExtractor();
        extractorwiki = new WikiTextExtractor();
        UrlWithTables = "https://fr.wikipedia.org/wiki/Championnat_d%27Allemagne_f%C3%A9minin_de_handball";
        liens = new ArrayList<>();
        liens.add("https://fr.wikipedia.org/wiki/Th%C3%BCringer_HC");
        liens.add("https://fr.wikipedia.org/wiki/Championnat_d%27Allemagne_f%C3%A9minin_de_handball");
        liens.add("https://fr.wikipedia.org/wiki/Parti_communiste_de_l%27Union_sovi%C3%A9tique");
        liens.add("https://fr.wikipedia.org/wiki/Union_des_r%C3%A9publiques_socialistes_sovi%C3%A9tiques");
        liens.add("https://fr.wikipedia.org/wiki/Oulan-Bator");
        nbtabliens = new HashMap<>();
        nbtabliens.put("https://fr.wikipedia.org/wiki/Th%C3%BCringer_HC", 1);
        nbtabliens.put("https://fr.wikipedia.org/wiki/Championnat_d%27Allemagne_f%C3%A9minin_de_handball", 5);
        nbtabliens.put("https://fr.wikipedia.org/wiki/Parti_communiste_de_l%27Union_sovi%C3%A9tique", 3);
        nbtabliens.put("https://fr.wikipedia.org/wiki/Union_des_r%C3%A9publiques_socialistes_sovi%C3%A9tiques", 2);
        nbtabliens.put("https://fr.wikipedia.org/wiki/Oulan-Bator", 1);
        csvTest = new ArrayList<>();
        for (int i = 1; i < 6; i++)
        {
            csvTest.add(FileUtils.readFileToString(new File("inputdata" + File.separator + "PDL" + i + ".csv")));
        }
    }

    @Test
    public void getCSV()
    {
        //test du nombre de tableau trouvé
        for (String lien : liens)
        {
            assertTrue( nbtabliens.get(lien) == extractorwiki.getCSV(new Url(lien)).size(),"nombre de tableau trouvé incorrecte (extractor wiki, lien:" + lien + ")");
            assertTrue( nbtabliens.get(lien) == extractorhtml.getCSV(new Url(lien)).size(),"nombre de tableau trouvé incorrecte (extractor HTML, lien:" + lien + ")");
        }
    }

    @Test
    public void getCSV2() throws IOException
    {
        csvhtml = extractorhtml.getCSV(new Url(UrlWithTables));
        csvwiki = extractorwiki.getCSV(new Url(UrlWithTables));
        for (int i = 0; i < 5; i++)
        {
            assertTrue(csvhtml.get(i).size() == countCsvLines(csvTest.get(i), false), "Nombre de lignes du CSV différent trouvé (HTML)");
            assertTrue(countCsvLines(csvwiki.get(i).get(0), true) == countCsvLines(csvTest.get(i), true), "Nombre de colonnes du CSV différent trouvé (Wiki)");
            ;
        }
    }

    //retourne le nombre de lignes ou colonnes du fichier text CSV
    private int countCsvLines(String csv, boolean col) throws IOException
    {
        InputStream is = new ByteArrayInputStream(csv.getBytes());
        try
        {
            byte[] c = new byte[1024];
            int nbLigCol = 0;
            int nbCharLu = 0;
            boolean fichierVide = true;
            while ((nbCharLu = is.read(c)) != -1)
            {
                fichierVide = false;
                for (int i = 0; i < nbCharLu; ++i)
                {
                    if (col)
                    {
                        if (c[i] == '\n')
                        {
                            return nbLigCol;
                        }
                        else if (c[i] == ';')
                        {
                            nbLigCol++;
                        }
                    }
                    else
                    {
                        if (c[i] == '\n')
                        {
                            nbLigCol++;
                        }
                    }
                }
            }
            return (nbLigCol == 0 && !fichierVide) ? 1 : nbLigCol;
        }
        finally
        {
            is.close();
        }
    }

//    @After
//    public void Check()
//    {
//
//    }
}