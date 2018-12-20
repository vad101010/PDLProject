package pdl.wiki;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * Classe de test pour la classe PageChecker
 */
public class PageCheckerTest
{

    /**
     * VÈrifie si la page testÈe existe
     */
    @Test
    public void existingPagesTest_withHttps()
    {
        Map<String, Integer> urlMap = new HashMap<>();
        urlMap.put("https://fr.wikipedia.org/wiki/Jeux_olympiques_d'√©t√©", 2);
        urlMap.put("https://fr.wikipedia.org/wiki/Wikip√©dia:Accueil_principal", 0);
        urlMap.put("https://fr.wikipedia.org/wiki/Jeux_olympiques", 0);
        urlMap.put("https://fr.wikipedia.org/wiki/Jeux_olympiques_d'hiver", 4);
        urlMap.put("https://fr.wikipedia.org/wiki/Jean_Capdouze", 1);
        for (Map.Entry set : urlMap.entrySet())
        {
            assertEquals((int) set.getValue(), PageChecker.urlCheck(set.getKey().toString()).size());
        }
    }

    /**
     * VÈrifie si une page testÈe sans http peut Ítre testÈe
     * (la fonction ajoute elle mÍme le http manquant)
     */
    @Test
    public void existingPagesTest_WithoutHttps()
    {
        Map<String, Integer> urlMap = new HashMap<>();
        urlMap.put("http://fr.wikipedia.org/wiki/Jeux_olympiques_d'√©t√©", 2);
        urlMap.put("fr.wikipedia.org/wiki/Jeux_olympiques_d'√©t√©", 2);
        urlMap.put("http://fr.wikipedia.org/wiki/Wikip√©dia:Accueil_principal", 0);
        urlMap.put("fr.wikipedia.org/wiki/Wikip√©dia:Accueil_principal", 0);
        urlMap.put("http://fr.wikipedia.org/wiki/Jeux_olympiques", 0);
        urlMap.put("fr.wikipedia.org/wiki/Jeux_olympiques", 0);
        urlMap.put("http://fr.wikipedia.org/wiki/Jeux_olympiques_d'hiver", 4);
        urlMap.put("fr.wikipedia.org/wiki/Jeux_olympiques_d'hiver", 4);
        urlMap.put("http://fr.wikipedia.org/wiki/Jean_Capdouze", 1);
        urlMap.put("fr.wikipedia.org/wiki/Jean_Capdouze", 1);
        for (Map.Entry set : urlMap.entrySet())
        {
            assertEquals((int) set.getValue(), PageChecker.urlCheck(set.getKey().toString()).size());
        }
    }

    /**
     * VÈrifie si la page est valide ou non
     */
    @Test
    public void pageNotValid()
    {
        List<String> urlToTest = new ArrayList<>();
        urlToTest.add("https://forum.xda-developers.com/");
        urlToTest.add("https://fr.wikipedia.com/");
        urlToTest.add("https://github.com/vad101010/PDLProject");
        urlToTest.add("https://www.google.fr/");
        for (String url : urlToTest)
        {
            assertNull(PageChecker.urlCheck(url));
        }
    }

}