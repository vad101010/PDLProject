package pdl.test;

import org.junit.Before;
import org.junit.Test;
import pdl.wiki.*;

import java.io.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test les URL du fichier wikiurls et les répartit en 2 fichiers
 * en fonction des extracteurs (dans output, soit html soit wikitext)
 */
public class BenchTest
{
    Extractor extractorwiki;
    Extractor extractorhtml;
    List<List<String>> csvwiki;
    List<List<String>> csvhtml;

    /**
     * Initialise les extracteurs html et wikitext avant de faire les tests
     */
    @Before
    public void setUp() throws Exception
    {
        extractorhtml = new HTMLExtractor();
        extractorwiki = new WikiTextExtractor();
    }

    /*
     *  the challenge is to extract as many relevant tables as possible
     *  and save them into CSV files
     *  from the 300+ Wikipedia URLs given
     *  see below for more details
     **/
    @Test
    public void testBenchExtractors() throws Exception
    {

        String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
        // directory where CSV files are exported (HTML extractor)
        String outputDirHtml = "output" + File.separator + "html" + File.separator;
        assertTrue(new File(outputDirHtml).isDirectory());
        // directory where CSV files are exported (Wikitext extractor)
        String outputDirWikitext = "output" + File.separator + "html" + File.separator;
        assertTrue(new File(outputDirWikitext).isDirectory());

        File file = new File("inputdata" + File.separator + "wikiurls.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String url;
        int nurl = 0;
        while ((url = br.readLine()) != null)
        {
            String wurl = BASE_WIKIPEDIA_URL + url;
            System.out.println("Wikipedia url: " + wurl);
            Url unurl = new Url(wurl);
            String csvFileName = mkCSVFileName(url, 0);
            System.out.println("CSV file name: " + csvFileName);

            if (unurl.getTableCount() > 0)
            {
                csvhtml = extractorhtml.getCSV(unurl);
                csvwiki = extractorwiki.getCSV(unurl);
                writefile("html", url, csvhtml);
                writefile("wikitext", url, csvwiki);
            }

            nurl++;
        }
        br.close();
        assertEquals(nurl, 336);


    }

    /**
     * Crée le fichier CSV à partir de l'url
     *
     * @param String url qui est l'url du fichier que l'on veut créer
     * @param int    n qui est le numéro du tableau de cette page
     * @return String le chemin du fichier CSV
     */
    private String mkCSVFileName(String url, int n)
    {
        return url.trim() + "-" + n + ".csv";
    }

    /**
     * Ecrit dans les fichiers html et wikitext le lien du fichier CSV
     *
     * @param String       method qui définit si le fichier doit aller dans html ou wikitext
     * @param String       url qui correspond à l'url de la page wikipédia
     * @param List<List<String>> qui est la liste des liens CSV
     */
    private void writefile(String method, String url, List<List<String>> csvlist) throws IOException
    {
        int i = 0;
        FileOutputStream fos = null;
        for (List<String> csv : csvlist)
        {
            File csvfile = new File("output/" + method + "/" + mkCSVFileName(url, i));
            fos = new FileOutputStream(csvfile.getAbsolutePath());
            for (String ligneCsv : csv)
            {
                fos.write(ligneCsv.getBytes());
                fos.write(System.getProperty("line.separator").getBytes());
            }
            fos.flush();
            fos.close();
            i++;
        }
    }

}
