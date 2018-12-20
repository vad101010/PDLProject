package fr.istic;

import fr.istic.Page;
import fr.istic.Url;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Classe de test pour la classe fr.istic.Page
 * � partir du fichier wikiurls
 */
public class PageTest
{

    /**
     * Modifie les liens du fichier wikiurls en url wikip�dia
     *
     * @param String url du fichier wikiurls
     * @return String url conforme
     */
    private static String creationLien(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append("https://wikipedia.org/wiki/").append(s).append("\n"));

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    ArrayList<String[]> lien = new ArrayList<>();
    String lien_string = this.creationLien("inputdata/wikiurls.txt");
    List<String> list_url = new ArrayList<String>(Arrays.asList(lien_string.split("\n")));

    /**
     * Test si la valeur du String pr�sent dans le tableau des url est conforme
     * et si elle correspond � la valeur attendue
     */
    @Test
    public void getUrl() throws IOException
    {


        for (int i = 0; i < list_url.size(); i++)
        {
            //System.out.println("Case [" + i + "] : " + list.get(i));
            Url url_array = new Url(list_url.get(i));
            ArrayList array_test = new ArrayList();
            array_test.add("Liste Test 1");
            array_test.add("Liste Test 2");
            Page page_test = new Page(url_array, array_test);
            String test = page_test.getUrl().getLink();
            assertTrue(test.equals(list_url.get(i)), "Fonction getUrl() pour l'url contenu à la case [" + i + "] : ");
            System.out.println("test ok pour : " + url_array);
        }
        //Affiche une erreur quand les sites comportent des parenthèses dans l'url. Dans la liste, ici 22. Sinon les tests passent tous
    }


    @Test
    public void getTitle()
    {

        for (int i = 0; i < list_url.size(); i++)
        {
            //System.out.println("Case [" + i + "] : " + list.get(i)); fr.istic.Url url_array = new fr.istic.Url(list.get(i));
            Url url_array = new Url(list_url.get(i));
            ArrayList array_test = new ArrayList();
            array_test.add("Liste Test 1");
            array_test.add("Liste Test 2");
            Page page_test = new Page(url_array, array_test);
            String test = page_test.getTitle();
            assertTrue(test.equals(page_test.getTitle()), "Fonction getUrl() pour l'url contenu à la case [" + i + "] : ");
            //System.out.println("test ok pour : "+url_array);
        }
    }

    /**
     * Test si la liste des fichiers CSV soit la m�me entre celle extraite et celle attendue
     */
    @Test
    public void getCsvList()
    {


        //for(int i = 0;i<list_url.size();i++)
        for (int i = 0; i < list_url.size(); i++)
        {
            //System.out.println("Case [" + i + "] : " + list_url.get(i));

            //attendre que guigui finissent HTMLextractor pour récuperer les listes CSV
            Url url_test = new Url("https://fr.wikipedia.org/wiki/Nombre_premier");
            ArrayList array_test = new ArrayList();
            Page page_test = new Page(url_test, array_test);
            array_test.add(page_test.getCsvList());

            for (Object s : array_test)
            {
                assertArrayEquals(array_test.toArray(), page_test.getCsvList().toArray(), "Fonction getCsvLest()");
            }
        }

        /*fr.istic.Url url_test = new fr.istic.Url("https://fr.wikipedia.org/wiki/Nombre_premier");
        ArrayList array_test = new ArrayList();
        array_test.add("Liste Test 1");
        array_test.add("Liste Test 2");
        fr.istic.Page page_test = new fr.istic.Page(url_test,array_test);


        for (Object s : array_test) {
            assertArrayEquals("Fonction getCsvLest()",array_test.toArray(), page_test.getCsvList().toArray());
        }*/
    }

    /**
     * Test si le titre de la page correspond � ce qu'il y a dans le fichier wikiurls
     * (sans les espaces)
     */
    @Test
    public void getTitleWithoutSpace()
    {


        for (int i = 0; i < list_url.size(); i++)
        {
            System.out.println("Case [" + i + "] : " + list_url.get(i));
        }

        Url url_test = new Url("https://fr.wikipedia.org/wiki/Nombre_premier");
        ArrayList array_test = new ArrayList();
        array_test.add("Liste Test 1");
        array_test.add("Liste Test 2");
        Page page_test = new Page(url_test, array_test);
        String test = page_test.getTitleWithoutSpace();
        assertTrue(test.equals("NombrePremier"), "Fonction getTitle()");
    }
}