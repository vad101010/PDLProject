package pdl.test;

import org.junit.Test;
import pdl.wiki.Page;
import pdl.wiki.*;
import org.junit.Assert.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Classe de test pour Page
 */
public class PageTest
{

    @Test
    public void getUrl()
    {
        Url url_test = new Url("https://fr.wikipedia.org/wiki/Nombre_premier");
        ArrayList array_test = new ArrayList();
        array_test.add("Liste Test 1");
        array_test.add("Liste Test 2");
        Page page_test = new Page(url_test,array_test);
        String test = page_test.getUrl().getLink();

        assertTrue("Fonction getUrl()",test.equals("https://fr.wikipedia.org/wiki/Nombre_premier"));

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