package pdl.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import pdl.wiki.Page;
import pdl.wiki.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Classe de test pour Page
 */
public class PageTest
{

    private static String creationLien(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s->contentBuilder.append("https://wikipedia.org/wiki/").append(s).append("\n"));

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    @Test
    public void getUrl() throws IOException {

        ArrayList<String[]> lien = new ArrayList<>();
        String lien_string = this.creationLien("inputdata/wikiurls.txt");
        List<String> list = new ArrayList<String>(Arrays.asList(lien_string.split("\n")));
        for(int i = 0;i<list.size();i++){
            //System.out.println("Case [" + i + "] : " + list.get(i));
            Url url_array = new Url(list.get(i));
            ArrayList array_test = new ArrayList();
            array_test.add("Liste Test 1");
            array_test.add("Liste Test 2");
            Page page_test = new Page(url_array,array_test);
            String test = page_test.getUrl().getLink();
            assertTrue("Fonction getUrl() pour l'url contenu à la case ["+i+"] : " ,test.equals(list.get(i)));
        }
            //Affiche une erreur quand les sites comportent des parenthèses dans l'url. Dans la liste, ici 22. Sinon les tests passent tous
        }


    @Test
    public void getTitle()
    {
        ArrayList<String[]> lien = new ArrayList<>();
        String lien_string = this.creationLien("inputdata/wikiurls.txt");
        List<String> list = new ArrayList<String>(Arrays.asList(lien_string.split("\n")));
        for(int i = 0;i<list.size();i++){
            System.out.println("Case [" + i + "] : " + list.get(i));
        }

        Url url_test = new Url("https://fr.wikipedia.org/wiki/Nombre_premier");
        ArrayList array_test = new ArrayList();
        array_test.add("Liste Test 1");
        array_test.add("Liste Test 2");
        Page page_test = new Page(url_test,array_test);
        String test = page_test.getTitle();

        assertTrue("Fonction getTitle()",test.equals("Nombre premier"));

    }

    @Test
    public void getCsvList()
    {

        ArrayList<String[]> lien = new ArrayList<>();
        String lien_string = this.creationLien("inputdata/wikiurls.txt");
        List<String> list = new ArrayList<String>(Arrays.asList(lien_string.split("\n")));
        for(int i = 0;i<list.size();i++){
            System.out.println("Case [" + i + "] : " + list.get(i));
        }

        Url url_test = new Url("https://fr.wikipedia.org/wiki/Nombre_premier");
        ArrayList array_test = new ArrayList();
        array_test.add("Liste Test 1");
        array_test.add("Liste Test 2");
        Page page_test = new Page(url_test,array_test);


        for (Object s : array_test) {
            assertArrayEquals("Fonction getCsvLest()",array_test.toArray(), page_test.getCsvList().toArray());
        }
    }

    @Test
    public void getTitleWithoutSpace()
    {

        ArrayList<String[]> lien = new ArrayList<>();
        String lien_string = this.creationLien("inputdata/wikiurls.txt");
        List<String> list = new ArrayList<String>(Arrays.asList(lien_string.split("\n")));
        for(int i = 0;i<list.size();i++){
            System.out.println("Case [" + i + "] : " + list.get(i));
        }

        Url url_test = new Url("https://fr.wikipedia.org/wiki/Nombre_premier");
        ArrayList array_test = new ArrayList();
        array_test.add("Liste Test 1");
        array_test.add("Liste Test 2");
        Page page_test = new Page(url_test,array_test);
        String test = page_test.getTitleWithoutSpace();
        assertTrue("Fonction getTitle()",test.equals("NombrePremier"));
    }
}