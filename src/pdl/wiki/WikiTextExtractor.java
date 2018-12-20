package pdl.wiki;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.sweble.wikitext.parser.WikitextParser;
import org.sweble.wikitext.parser.nodes.WtNode;
import org.sweble.wikitext.parser.nodes.WtText;
import org.sweble.wikitext.parser.utils.SimpleParserConfig;
import xtc.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Extrait les tableaux des pages HTML en format Wikitext
 * @return la liste des tableaux CSV créés
 */
public class WikiTextExtractor implements Extractor
{
    @Override
    public List<List<String>> getCSV(Url purl)
    {
        List<List<String>> liste = new ArrayList<>();
        String wikitext = getWikitextFromApi(purl);
        iterateThroughAST(liste, wikitext);
        return liste;
    }

    /**
     * Récupère la page HTML à partir de l'URL
     * @return le wikitext de la page en String
     */
    private String getWikitextFromApi(Url pUrl) {
        String wt = "";
        try {
            URL apiUrl = new URL("https://" + pUrl.getLang() + ".wikipedia.org/w/api.php?action=parse&format=json&prop=wikitext&page=" + pUrl.getPageName());
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream is = connection.getInputStream();
            StringWriter sw = new StringWriter();
            IOUtils.copy(is, sw, "UTF-8");
            String jsonString = sw.toString();
            JSONObject apiResult = new JSONObject(jsonString);
            if (!apiResult.keySet().contains("error")) {
                wt = apiResult.getJSONObject("parse").getJSONObject("wikitext").toMap().get("*").toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wt;
    }

    /**
     * Méthode itérant à travers l'arbre généré par le WikitextParser pour trouver les nodes WtTable
     * @param liste
     * @param wikitext
     */
    private void iterateThroughAST(List<List<String>> liste, String wikitext) {
        if (!wikitext.equals("")) {
            WikitextParser parser = new WikitextParser(new SimpleParserConfig());
            try {
                WtNode article = parser.parseArticle(wikitext, "titre");
                for (int i = 0; i < article.toArray().length; i++) {
                    WtNode nodeArticle = (WtNode) article.toArray()[i];
                    // Dans le cas où il y a plusieurs sections
                    if (nodeArticle.getNodeName().equals("WtSection")) {
                        for (int j = 0; j < nodeArticle.toArray().length; j++) {
                            WtNode nodeSection = (WtNode) nodeArticle.toArray()[j];
                            if (nodeSection.getNodeName().equals("WtBody") && nodeSection.toString().contains("WtTable")) {
                                for (int k = 0; k < nodeSection.toArray().length; k++) {
                                    WtNode sectionBody = (WtNode) nodeSection.toArray()[k];
                                    if (sectionBody.toString().contains("WtTable")) {
                                        for (int l = 0; l < sectionBody.toArray().length; l++) {
                                            WtNode paragraphe = (WtNode) sectionBody.toArray()[l];
                                            if (paragraphe.getNodeName().equals("WtBody") && paragraphe.toString().contains("WtTable")) {
                                                for (int m = 0; m < paragraphe.toArray().length; m++) {
                                                    WtNode element = (WtNode) paragraphe.toArray()[m];
                                                    if (element.getNodeName().equals("WtTable") && element.toString().contains("WtValue[WtText(\"wikitable")) {
                                                        retrieveTableData(liste, element);
                                                    }
                                                }
                                            }
                                            checkIfWtTable(paragraphe, liste);
                                        }
                                    }
                                    checkIfWtTable(sectionBody, liste);
                                }
                            }
                            checkIfWtTable(nodeSection, liste);
                        }
                    }
                    checkIfWtTable(nodeArticle, liste);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Vérifie si la node sur laquelle on se trouve est une WtTable compatible
     * @param node
     * @param liste
     */
    private void checkIfWtTable(WtNode node, List<List<String>> liste) {
        if (node.getNodeName().equals("WtTable") && node.toString().contains("WtValue[WtText(\"wikitable")) {
            retrieveTableData(liste, node);
        }
    }

    /**
     * Méthode générant les csv à partir de la node WtTable fournie
     * @param liste : la liste des csv
     * @param element : la node WtTable
     */
    private void retrieveTableData(List<List<String>> liste, WtNode element) {
        if (!element.toString().contains("colspan") && !element.toString().contains("rowspan")) {
            List<String> csvLines = new ArrayList<>();
            // Ajout des lignes du tableau au CSV
            for (int n = 0; n <element.toArray().length; n++) {
                WtNode tableElement = (WtNode) element.toArray()[n];
                if (tableElement.getNodeName().equals("WtBody")) {
                    for (int numNode = 0; numNode < tableElement.toArray().length; numNode++) {
                        String csv = "";
                        WtNode bodyElement = (WtNode) tableElement.toArray()[numNode];
                        if (bodyElement.getNodeName().equals("WtTableHeader") || bodyElement.getNodeName().equals("WtTableCell")) {
                            // Récupération du nom de la colonne
                            csv += getColumnName(bodyElement).trim();
                            // On concatène les autres nom de colonnes
                            while (numNode + 1 < tableElement.toArray().length && (((WtNode) tableElement.toArray()[numNode + 1]).getNodeName().equals("WtTableHeader") || ((WtNode) tableElement.toArray()[numNode + 1]).getNodeName().equals("WtTableCell"))) {
                                numNode++;
                                bodyElement = (WtNode) tableElement.toArray()[numNode];
                                csv += getColumnName(bodyElement);
                            }
                        } else {
                            csv += getRowText(bodyElement);
                        }
                        if (csv.length() > 0) {
                            csv = beautifyCsvLine(csv);
                        }
                        csvLines.add(csv.trim());
                    }
                }
            }
            liste.add(csvLines);
        }
    }

    /**
     * Méthode supprimant les espaces en trop dans la ligne de csv passée en paramètre
     * @param csv
     * @return
     */
    private String beautifyCsvLine(String csv) {
        csv = csv.substring(0, csv.length() - 1);
        String[] csvLineTab = csv.split(";");
        for (int csvColNum = 0; csvColNum < csvLineTab.length; csvColNum++) {
            csvLineTab[csvColNum] = csvLineTab[csvColNum].trim();
        }
        csv = "";
        for (String csvLineElement : csvLineTab) {
            csv += csvLineElement + ";";
        }
        if (csv.length() > 0) {
            csv = csv.substring(0, csv.length() - 1);
        }
        return csv;
    }

    /**
     * Récupère le nom de la colonne (utilisé pour récupérer chaque header des tableaux)
     * @param node
     * @return "" si aucune node WtText n'a été trouvée sinon le contenu de la node
     */
    private String getColumnName(WtNode node) {
        String columnName = "";
        for (Object sNode : node.toArray()) {
            WtNode cNode = (WtNode) sNode;
            if (cNode.getNodeName().equals("WtBody")) {
                Iterator<WtNode> it = cNode.iterator();
                while (it.hasNext()) {
                    WtNode itNode = it.next();
                    if (itNode.getNodeName().equals("WtText")) {
                        columnName += ((WtText) itNode).getContent().replaceAll(";", ",");
                    }
                }
            }
        }
        return columnName + ";";
    }

    /**
     * Extrait chaque cellule de la node ligne passée en paramètre et renvoi la chaine de caractère générée
     * @param node
     * @return une chaine de caractère au format csv équivalente à la ligne du tableau dont la node est passée en paramètre
     */
    private String getRowText(WtNode node) {
        String cellName = "";
        for (Object sNode : node.toArray()) {
            WtNode cNode = (WtNode) sNode;
            if (cNode.getNodeName().equals("WtBody")) {
                Iterator<WtNode> it = cNode.iterator();
                while (it.hasNext()) {
                    WtNode itNode = it.next();
                    for (Object n : itNode.toArray()) {
                        WtNode nodeCell = (WtNode) n;
                        // Permet la concaténation du contenu des différents WtText si plusieurs existent pour la même cellule
                        int nbWtText = StringUtils.countMatches(nodeCell.toString(), "WtXmlEmptyTag[br]");
                        if (nodeCell.getNodeName().equals("WtBody")) {
                            // Permet de savoir si une référence vers une partie de la page existe
                            boolean isRef = false;
                            for (Object nBody : nodeCell.toArray()) {
                                WtNode nodeBody = (WtNode) nBody;
                                // Permet de savoir si la cellule analysée possède un hyperlien
                                boolean containsPName = nodeBody.toString().contains("WtLinkTitle[WtText(");
                                if (nodeBody.toString().contains("WtXmlStartTag[ref]")) {
                                    isRef = true;
                                }
                                if (!isRef) {
                                    if (containsPName) {
                                        if (nodeBody.getNodeName().equals("WtInternalLink")) {
                                            for (Object nInt : nodeBody.toArray()){
                                                WtNode nodeInternal = (WtNode) nInt;
                                                if (nodeInternal.getNodeName().equals("WtLinkTitle")) {
                                                    for (Object nIntChild : nodeInternal.toArray()) {
                                                        WtNode nodeIntChild = (WtNode) nIntChild;
                                                        if (nodeIntChild.getNodeName().equals("WtText")) {
                                                            String content = ((WtText) nodeIntChild).getContent();
                                                            if (!content.trim().startsWith("{{") || !content.trim().endsWith("}}")) {
                                                                cellName += content.replaceAll(";", ",");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (nodeBody.getNodeName().equals("WtText")) {
                                            String content = ((WtText) nodeBody).getContent();
                                            if (nbWtText > 0) {
                                                if (!content.trim().startsWith("{{") && !content.trim().endsWith("}}")) {
                                                    cellName += content.replaceAll(";", ",");
                                                    cellName += ",";
                                                }
                                                nbWtText--;
                                            } else {
                                                if (!content.trim().startsWith("{{") && !content.trim().endsWith("}}")) {
                                                    cellName += content.replaceAll(";", ",");;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (nodeBody.toString().contains("WtXmlEndTag[ref]")) {
                                    isRef = false;
                                }
                            }
                            cellName += ";";
                        }
                    }
                }
            }
        }
        return cellName;
    }

}


