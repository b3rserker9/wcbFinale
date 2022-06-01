package it.uniroma3.wcb.web.controller;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import it.uniroma3.wcb.course.sequence.SequenceService;
import java.util.Calendar;

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
public class SequenceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	   private SequenceService sequenceService;

    @RequestMapping(value = "/build/fromPage", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String buildFromPage(@RequestParam("pageTitle") final String pageTitle, @RequestParam("searchConstraints") final String searchConstraints, @RequestParam("depth") final int depth, @RequestParam("lang") final String language) throws Exception {
        try {
            long inizio = Calendar.getInstance().getTimeInMillis();
            String lang = "en";
            logger.info("Build From Title " + pageTitle);
            if (language != null) {

                if ( //lingue con più di 1.000.000 di pagine
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
                    lang = language;
                }

            }

            JSONObject jsonResponse = this.sequenceService.generateSequence(pageTitle, searchConstraints, depth, lang);

            jsonResponse.put("lang", lang);
            jsonResponse.put("success", true);
            long fine = Calendar.getInstance().getTimeInMillis();
            logger.info("Build Time " + (fine - inizio) + " ms");
            return jsonResponse.toString();

        } catch (Exception e) {
            logger.error("#Exception thrown building sequence from page " + pageTitle + " : "
                    + e.getMessage());
            throw new Exception("Error while building sequence from page " + pageTitle);
        }
    }

    //USED FOR OFFLINE MOCKED TEST - IGNORE IT
    @RequestMapping(value = "/mock/build/fromPage", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String mockBuildFromPage(@RequestParam("pageTitle") final String pageTitle, @RequestParam("searchConstraints") final String searchConstraints) throws Exception {
        try {

            File respFile = new File("src/test/resources/mock/buildResponse.txt");

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
            logger.error("#Exception thrown building sequence from page " + pageTitle + " : "
                    + e.getMessage());
            throw new Exception("Error while building sequence from page " + pageTitle);
        }
    }
}
