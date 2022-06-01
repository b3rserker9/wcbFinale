package it.uniroma3.wcb.stemmer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.commons.lang.Validate;

import de.tudarmstadt.ukp.wikipedia.parser.ParsedPage;
import de.tudarmstadt.ukp.wikipedia.parser.Section;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParser;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParserFactory;

/**
 *
 * @author Andrea Giardi
 *
 */
public abstract class Stemmer {

    protected HashSet<String> stopWords;
    protected TreeSet<String> sectionsToSkip;
    protected int textTypeRank;

    public Stemmer() {
        this(new HashSet<String>(), new TreeSet<String>(), 0);
    }

    public Stemmer(HashSet<String> stopWords, TreeSet<String> sectionsToSkip, int ttr) {
        /*
		Validate.notNull(stopWords);
		Validate.notNull(sectionsToSkip);
         */
        this.stopWords = stopWords;
        this.sectionsToSkip = sectionsToSkip;
        this.textTypeRank = ttr;
    }

    public String[] tokenizeAndStemText(String text) {
        return stem(clean(text)).split("\\s+");
    }

    public String stemString(String toStem) {
        return stem(clean(toStem));
    }

    public List<String> stemStringCollection(List<String> collection) {
        if (collection != null) {
            List<String> stemmedCollection = new ArrayList<>();

            for (String toStem : collection) {
                String stemmed = stem(clean(toStem));
                if (stemmed != null && !"".equals(stemmed)) {
                    stemmedCollection.add(stemmed);
                }
            }

            return stemmedCollection;
        } else {
            return collection;
        }
    }

    private String clean(String text) {
        String cleaned = text;
        try {
            Pattern p = Pattern.compile("<ref(.*?)</ref>|<ref(.*?)/>|\\[http(.*?)\\|\\[www(.*?)\\]]|\\{\\{cite (.*?)\\}\\}");
            Matcher m = p.matcher(text);
            cleaned = m.replaceAll("");

            p = Pattern.compile("<ref(.*?)</ref>|<ref(.*?)/>|\\[http(.*?)\\|\\[www(.*?)\\]]|\\{\\{cite (.*?)\\}\\}", Pattern.DOTALL);
            m = p.matcher(cleaned);
            cleaned = m.replaceAll("");
            cleaned = cleaned.replace("&nbsp", " ");
        } catch (Exception e) {
            //cleaned = text;
        }
        if (textTypeRank == 0 || textTypeRank == 1) {
            try {
                MediaWikiParserFactory pf = new MediaWikiParserFactory();
                MediaWikiParser parser = pf.createParser();

                ParsedPage pp = parser.parse(cleaned);
                List<Section> sections = pp.getSections();

                StringBuffer buffer = new StringBuffer();
                for (Section section : sections) {
                    String sectionTitle = section.getTitle();

                    if (sectionTitle != null && this.sectionsToSkip.contains(sectionTitle)) {
                        continue; //skip
                    }

                    buffer.append(section.getText());
                    buffer.append("\n");
                }

                return buffer.toString();
            } catch (Exception e) {
                return cleaned;
            }
        }
        cleaned=cleaned.replaceAll("\'", "\' ");
        return cleaned;
    }

    public abstract String stem(String text);
}
