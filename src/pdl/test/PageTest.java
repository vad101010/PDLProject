package pdl.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import pdl.wiki.Page;
import pdl.wiki.*;
import org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        ArrayList lien = new ArrayList();

        String lien_string = this.creationLien("inputdata/wikiurls.txt");
       // System.out.println("Premier : "+lien_string);
        int nblignes = StringUtils.countMatches(lien_string,"\n");
        //System.out.println("nb lignes : "+nblignes);
        for (int i =0;i<=nblignes;i++){
            lien.add(lien_string.split("$"));
        }
        for(int i =0;i<lien.size();i++) {
            System.out.println("Case ["+i+"]"+lien.get(i));
        }

           /* Url url_test = new Url(s.toString());
            ArrayList array_test = new ArrayList();
            array_test.add("Liste Test 1");
            array_test.add("Liste Test 2");
            Page page_test = new Page(url_test,array_test);
            String test = page_test.getUrl().getLink();

            assertTrue("Fonction getUrl()",test.equals("https://fr.wikipedia.org/wiki/"+s));*/


        }


    @Test
    public void getTitle()
    {
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
        Url url_test = new Url("https://fr.wikipedia.org/wiki/Nombre_premier");
        ArrayList array_test = new ArrayList();
        array_test.add("Liste Test 1");
        array_test.add("Liste Test 2");
        Page page_test = new Page(url_test,array_test);
        String test = page_test.getTitleWithoutSpace();
        assertTrue("Fonction getTitle()",test.equals("NombrePremier"));
    }
}