/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.course.filemanager.FileService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author J-HaRd
 */
@Controller
public class FileController {
private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FileService fileService;


    @RequestMapping(value = "/course/export/pdf", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String exportPdfCourse(@RequestParam("courseId") final String courseId) throws Exception {
        try {

            String createCoursePDF = fileService.createCoursePDF(new Long(courseId));

            JSONObject response = new JSONObject(createCoursePDF);
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#exception while exporting pdf of course" + e.getMessage());
            throw new Exception("Error while exporting pdf topic");
        }
    }

    @RequestMapping(value = "/course/topic/export/pdf", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String exportPdfTopic(@RequestParam("topicId") final String topicId) throws Exception {
        try {

            String createTopicPDF = fileService.createTopicPDF(new Long(topicId));

            JSONObject response = new JSONObject(createTopicPDF);
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#exception while exporting pdf of courseTopic" + e.getMessage());
            throw new Exception("Error while exporting pdf topic");
        }
    }
    
    
    @RequestMapping(value = "docs/pdf/{path_file}", method = RequestMethod.GET)
    public void downloadPDF(HttpServletRequest request, @PathVariable("path_file") String filepath, HttpServletResponse response) throws IOException {

        //final ServletContext servletContext = request.getSession().getServletContext();
        //final File tempDirectory = (File) servletContext.getAttribute("/pdf/");
        File tempDirectory = new File("docs"+File.separator+"pdf"+File.separator);
        File tempDirectoryV = new File("docs"+File.separator);
        if(!tempDirectoryV.exists()||!tempDirectoryV.isDirectory()){
            String s =System.getProperty("user.dir");
            tempDirectoryV  = new File(s);
            tempDirectoryV =tempDirectoryV.getParentFile();
            tempDirectoryV =tempDirectoryV.getParentFile();
            tempDirectory = new File(tempDirectoryV.getAbsolutePath()+File.separator+"docs"+File.separator+"pdf"+File.separator);
        }
        final String temperotyFilePath = tempDirectory.getAbsolutePath();
        
        String fileName = filepath + ".pdf";
        this.logger.info("filename " + filepath);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + "docs/pdf/" + fileName);

        try {

            //CreatePDF.createPDF(temperotyFilePath+"\\"+fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = convertToByteArrayOutputStream(temperotyFilePath +File.separator+fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    
    
    
    
    
    
    
    @RequestMapping(value = "/course/export/txt", method = RequestMethod.POST)
    @ResponseBody
    public String exportTxtCourse(@RequestParam("courseId") final String courseId, @RequestParam("clean") final String clean) throws Exception {
        try {
            boolean c=clean.trim().toLowerCase().equalsIgnoreCase("0");
            
            String createCourseTxt = fileService.createCourseTxt(new Long(courseId),c);

            JSONObject response = new JSONObject(createCourseTxt);
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#rexception while exporting txt of course" + e.getMessage());
            throw new Exception("Error while exporting txt topic");
        }
    }

    @RequestMapping(value = "/course/topic/export/txt", method = RequestMethod.POST)
    @ResponseBody
    public String exportTxtTopic(@RequestParam("topicId") final String topicId, @RequestParam("clean") final String clean) throws Exception {
        try {
            boolean c=clean.trim().toLowerCase().equalsIgnoreCase("0");
            String createTopicTxt = fileService.createTopicTxt(new Long(topicId), c);
            logger.info("TExt of Page => "+createTopicTxt);
            JSONObject response = new JSONObject(createTopicTxt);
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#rexception while exporting txt of courseTopic" + e.getMessage());
            throw new Exception("Error while exporting txt topic");
        }
    }
    
    
    @RequestMapping(value = "docs/txt/{path_file}", method = RequestMethod.GET)
    public void downloadTxt(HttpServletRequest request, @PathVariable("path_file") String filepath, HttpServletResponse response) throws IOException {

        //final ServletContext servletContext = request.getSession().getServletContext();
        //final File tempDirectory = (File) servletContext.getAttribute("/pdf/");
        File tempDirectory = new File("docs"+File.separator+"txt"+File.separator);
        File tempDirectoryV = new File("docs"+File.separator);
        if(!tempDirectoryV.exists()||!tempDirectoryV.isDirectory()){
            String s =System.getProperty("user.dir");
            tempDirectoryV  = new File(s);
            tempDirectoryV =tempDirectoryV .getParentFile();
            tempDirectoryV =tempDirectoryV .getParentFile();
            tempDirectory = new File(tempDirectoryV .getAbsolutePath()+File.separator);
            this.logger.info("directory ---------> " + tempDirectoryV.getAbsolutePath());
            tempDirectory = new File(tempDirectoryV .getAbsolutePath()+File.separator+"docs"+File.separator+"txt"+File.separator);
        }
        final String temperotyFilePath = tempDirectory.getAbsolutePath();

        String fileName = filepath + ".txt";
        this.logger.info("filename " + filepath);
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=" + "docs/txt/" + fileName);

        try {

            //CreatePDF.createPDF(temperotyFilePath+"\\"+fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = convertToByteArrayOutputStream(temperotyFilePath +File.separator+ fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
    
    
    
    
    
    
    
    private ByteArrayOutputStream convertToByteArrayOutputStream(String fileName) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos;
    }
}