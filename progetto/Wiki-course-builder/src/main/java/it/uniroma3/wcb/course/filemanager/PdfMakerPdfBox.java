/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.filemanager;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONObject;
import org.openide.util.Exceptions;
/**
 *
 * @author J-HaRd
 */
public class PdfMakerPdfBox {

    private static final Logger LOG = Logger.getLogger(PdfMakerPdfBox.class.getName());
    
    
    public static PDDocument init(){
        //Creating PDF document object
        PDDocument document = new PDDocument();
            
        //Creating a blank page
        PDPage blankPage = new PDPage();
        PDDocumentInformation pdd = document.getDocumentInformation();
        Calendar date = new GregorianCalendar();
        //date.set(2015, 11, 5);
        pdd.setCreationDate(date);
        //Adding the blank page to the document
        //document.addPage( blankPage );
        return document;
    }

    public static void save(PDDocument document, String path) {
        try {
            //Saving the document
            document.save(path);//"C:/PdfBox_Examples/doc_attributes.pdf");

            LOG.info("Properties added successfully ");
            //Closing the document
            document.close();
        } catch (IOException ex) {
            LOG.warning("error saving pdf");
        }
    }

    public static void addMetaData(PDDocument document, Map<String, String> metadata) {
        //Creating the PDDocumentInformation object
        PDDocumentInformation pdd = document.getDocumentInformation();
        //Setting the author of the document
        pdd.setAuthor(metadata.get("author"));
        // Setting the title of the document
        pdd.setTitle(metadata.get("title"));
        //Setting the creator of the document
        pdd.setCreator(metadata.get("creator"));
        //Setting the subject of the document
        pdd.setSubject(metadata.get("subject"));
        //Setting the created date of the document
        Calendar date = new GregorianCalendar();
        //date.set(2015, 11, 5);
        //pdd.setCreationDate(date);
        //Setting the modified date of the document
        //date.set(2016, 6, 5);
        pdd.setModificationDate(date);
        //Setting keywords for the document
        pdd.setKeywords(metadata.get("keywords"));
    }

    public static void pdfFromJSON(PDDocument document, JSONObject pagson) {

        try {
            PDPage page = new PDPage();
            document.addPage(page);

// Create a new font object selecting one of the PDF base fonts
            PDFont font = PDType1Font.HELVETICA_BOLD;

// Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setLeading(14.5f);

// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(25, 725);
            
            //contentStream.moveTextPositionByAmount(100, 700);
            contentStream.showText("Hello World safguafb  sdkjsbmcn   dajgrfbvm nzshd uikjsbdc awskuhxmc ggdsjhb vilknb  fhsd, vxlidfbnfds");
            contentStream.newLine();
            contentStream.setFont(font, 20);
            //ontentStream.moveTextPositionByAmount(100, 700);
            contentStream.showText("Hello World");
            contentStream.endText();

// Make sure that the content stream is closed:
            contentStream.close();

// Save the results and ensure that the document is properly closed:
            document.save("Hello World.pdf");
            document.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
    
    /**
     *
     * @param args
     *
    public static void main(String args[]){
       PDDocument init = init();
       pdfFromJSON(init, new JSONObject());
    }
*/
}
