package pdl.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pdl.wiki.Extractor;
import pdl.wiki.HTMLExtractor;
import pdl.wiki.WikiTextExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Classe de test pour la classe Extractor
 */
public class ExtractorTest
{

    Extractor extractorwiki;
    Extractor extractorhtml;
    Map<String, Integer> liens;
    List<String> csvwiki;
    List<String> csvhtml;

    @Before
    public void setUp() throws Exception
    {
        extractorhtml = new HTMLExtractor();
        extractorwiki = new WikiTextExtractor();
        liens.put("https://fr.wikipedia.org/wiki/Th%C3%BCringer_HC", 1);
        liens.put("https://fr.wikipedia.org/wiki/Championnat_d%27Allemagne_f%C3%A9minin_de_handball", 6);
        liens.put("https://fr.wikipedia.org/wiki/Parti_communiste_de_l%27Union_sovi%C3%A9tique", 3);
        liens.put("https://fr.wikipedia.org/wiki/Union_des_r%C3%A9publiques_socialistes_sovi%C3%A9tiques", 2);
        liens.put("https://fr.wikipedia.org/wiki/Oulan-Bator", 1);
    }

    @Test
    public void getCSV()
    {

    }

//    @After
//    public void Check()
//    {
//
//    }
}