package it.uniroma3.wcb.web.controller;

import com.itextpdf.text.pdf.ByteBuffer;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import it.uniroma3.wcb.course.ranking.RankType;
import it.uniroma3.wcb.course.search.SearchService;
import it.uniroma3.wcb.spring.AppConfig;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Controller
public class SearchController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search/execute", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody
    String search(@RequestParam("queryString") final String queryString,
            @RequestParam("contextTerms") final String contextTerms, @RequestParam("orderType") final String orderType, @RequestParam("lang") final String language) throws Exception {
        try {
            RankType order = null;
            if (orderType == null) {
                order = RankType.TEACHING_STYLE; //default
            } else {
                try {
                    order = RankType.valueOf(orderType);
                } catch (Exception e) {
                    order = RankType.TEACHING_STYLE; //default
                }
            }
            String langu = "en";
            if (language != null) {

                if (
                        //lingue con più di 1.000.000 di pagine
                        language.equalsIgnoreCase("it")
                        || language.equalsIgnoreCase("nl")
                        || language.equalsIgnoreCase("de")
                        || language.equalsIgnoreCase("fr")
                        || language.equalsIgnoreCase("sv")
                        || language.equalsIgnoreCase("es")
                        || language.equalsIgnoreCase("ru")
                        || language.equalsIgnoreCase("pl")
                        || language.equalsIgnoreCase("vi")
                        || language.equalsIgnoreCase("war")
                        || language.equalsIgnoreCase("ceb")
                        || language.equalsIgnoreCase("zh")
                        || language.equalsIgnoreCase("ja")) {
                    langu = language;
                }

                
            }
            JSONObject jsonResponse = this.searchService.executeSearch(queryString, contextTerms, order, langu);
            jsonResponse.put("lang", langu);
            jsonResponse.put("success", true);
            String ret = jsonResponse.toString();
            
            return ret;

        } catch (Exception e) {
            logger.error("#search exception thrown while executing search " + e.getMessage());
            throw new Exception("Error while executing search", e);
        }
    }

    //USED FOR OFFLINE MOCKED TEST - IGNORE IT
    /*@RequestMapping(value = "/mock/search/execute", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody
    String mockSearch(@RequestParam("queryString") final String queryString,
            @RequestParam("contextTerms") final String contextTerms, @RequestParam("orderType") final String orderType) throws Exception {
        try {

            File respFile = new File("src/test/resources/mock/searchResponse.txt");

            StringBuilder textBuilder = new StringBuilder();
            char[] cbuf = new char[2048];
            int len = 0;

            Reader reader = new FileReader(respFile);

            while ((len = reader.read(cbuf, 0, 2048)) != -1) {
                textBuilder.append(ArrayUtils.subarray(cbuf, 0, len));
            }
            reader.close();

            return textBuilder.toString();

        } catch (Exception e) {
            logger.error("#search exception thrown while executing search " + e.getMessage());
            throw new Exception("Error while executing search", e);
        }
    }*/
}
