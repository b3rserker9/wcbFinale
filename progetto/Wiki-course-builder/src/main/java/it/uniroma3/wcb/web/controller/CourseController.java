package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.course.CourseService;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.dao.PageRepository;
import it.uniroma3.wcb.persistence.model.Course;
import it.uniroma3.wcb.persistence.model.CourseTopic;
import it.uniroma3.wcb.persistence.model.Page;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
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
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
@Controller
public class CourseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/course/getAll", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String getUserCourses() throws Exception {
        try {

            List<Course> courses = courseService.getUserCourses();

            JSONArray array = new JSONArray();

            if (courses != null && !courses.isEmpty()) {
                for (Course course : courses) {
                    array.put(course.toJSON());
                }
            }

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("total", array.length());
            response.put("data", array);

            return response.toString();

        } catch (Exception e) {
            logger.error("#getUserCourses exception thrown while retrieving user courses " + e.getMessage());
            throw new Exception("Error hile retrieving user courses");
        }

    }

    @RequestMapping(value = "/course/save", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String saveCourses(final HttpServletRequest request, @RequestParam("courses") final String courses) throws Exception {
        try {

            JSONArray input = new JSONArray(courses);

            List<Course> toUpdate = new ArrayList<>();

            for (int i = 0; i < input.length(); i++) {
                JSONObject obj = input.getJSONObject(i);

                Course course = new Course();
                course.fromJSON(obj);
                toUpdate.add(course);
            }

            List<Course> reloaded = this.courseService.syncUserCourses(toUpdate);

            JSONArray array = new JSONArray();

            if (courses != null) {
                for (Course course : reloaded) {
                    array.put(course.toJSON());
                }
            }

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("total", array.length());
            response.put("data", array);

            return response.toString();

        } catch (Exception e) {
            logger.error("#getUserCourses exception thrown while retrieving user courses " + e.getMessage());
            throw new Exception("Error while retrieving user courses");
        }
    }

    @RequestMapping(value = "/course/detail", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String detailCourse(@RequestParam("courseId") final String courseId) throws Exception {
        try {

            List<CourseTopic> courseTopics = courseService.getCourseTopics(new Long(courseId));

            JSONArray array = new JSONArray();

            if (courseTopics != null) {
                for (CourseTopic courseTopic : courseTopics) {
                    array.put(courseTopic.toJSON());
                }
            }

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("total", array.length());
            response.put("data", array);

            return response.toString();

        } catch (Exception e) {
            logger.error("#detailCourse exception thrown while retrieving course detail " + e.getMessage());
            throw new Exception("Error while retrieving course detail");
        }
    }

    @RequestMapping(value = "/course/topic/save", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String saveCourseTopicBuilt(final HttpServletRequest request, @RequestParam("courseId") final String courseId, @RequestParam("courseName") final String courseName, @RequestParam("topicId") final String topicId, @RequestParam("topicName") final String topicName, @RequestParam("queryConstraints") final String queryConstraints, @RequestParam("resultItems") final String resultItems, @RequestParam("graph") final String graph, @RequestParam("lang") final String lang) throws Exception {
        String language = "en";
        try {
            if (lang != null) {

                if ( //lingue con più di 1.000.000 di pagine
                        lang.equalsIgnoreCase("it")
                        || lang.equalsIgnoreCase("nl")
                        || lang.equalsIgnoreCase("de")
                        || lang.equalsIgnoreCase("fr")
                        || lang.equalsIgnoreCase("sv")
                        || lang.equalsIgnoreCase("es")
                        || lang.equalsIgnoreCase("ru")
                        || lang.equalsIgnoreCase("pl")
                        || lang.equalsIgnoreCase("vi")
                        || lang.equalsIgnoreCase("war")
                        || lang.equalsIgnoreCase("ceb")
                        || lang.equalsIgnoreCase("zh")
                        || lang.equalsIgnoreCase("ja")) {
                    language = lang;
                }

            }
            CourseTopic courseTopic = null;

            if (topicId != null && !"".equalsIgnoreCase(topicId) && !"-1".equalsIgnoreCase(topicId)) {
                courseTopic = courseService.updateCourseTopic(topicId, topicName, new JSONArray(resultItems), graph, language);
                logger.info("servlet updateTopic");
            } else {
                courseTopic = courseService.createCourseTopic(courseId, courseName, topicName, queryConstraints, new JSONArray(resultItems), graph, language);
                logger.info("servlet createTopic");
            }

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("data", courseTopic.toJSON());

            return response.toString();
        } catch (Exception e) {
            logger.error("#saveCourseTopicBuilt exception thrown while saving course topic " + e.getMessage());
            throw new Exception("Error while saving course topic");
        }
    }

    @RequestMapping(value = "/course/topics/update", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String updateCourseTopics(final HttpServletRequest request, @RequestParam("topics") final String topics) throws Exception {

        try {

            JSONArray ja = new JSONArray(topics);

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("data", this.courseService.updateTopicPosition(ja));

            return response.toString();
        } catch (Exception e) {
            logger.error("#updateCourseTopic exception thrown while saving course topic " + e.getMessage());
            throw new Exception("Error while update course topic");
        }
    }

    @RequestMapping(value = "/course/topic/detail", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String detailTopic(@RequestParam("topicId") final String topicId) throws Exception {
        try {
            
            CourseTopic courseTopic = courseService.getTopic(new Long(topicId));

            Course course = courseService.getCourse(courseTopic.getCourseId());

            if (courseTopic != null && course != null) {
                ExtraConfig ec =ExtraConfig.getIstance();
                JSONObject response = new JSONObject();
                response.put("success", true);
                response.put("topic", courseTopic.toJSON());
                response.put("course", course.toJSON());
                response.put("plm", ec.pluginPageLinkMode());
                response.put("pld", ec.isPageLinkDoubleLink()?1:0);
                response.put("plr", ec.isPageLinkReverseLink()?1:0);
                
                return response.toString();
            } else {
                throw new Exception("Topic doesn't exist");
            }

        } catch (Exception e) {
            logger.error("#detailTopic exception thrown while retrieving topic detail " + e.getMessage());
            throw new Exception("Error while retrieving topic detail");
        }
    }







    @RequestMapping(value = "/course/remove", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String removeCourse(@RequestParam("courseId") final String courseId) throws Exception {
        try {

            courseService.removeCourse(new Long(courseId));

            JSONObject response = new JSONObject();
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#removeCourse exception thrown while removing course " + e.getMessage());
            throw new Exception("Error while removing course");
        }
    }

    @RequestMapping(value = "/course/topic/remove", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String removeTopic(@RequestParam("topicId") final String topicId) throws Exception {
        try {

            courseService.removeTopic(new Long(topicId));

            JSONObject response = new JSONObject();
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#removeCourse exception thrown while removing topic " + e.getMessage());
            throw new Exception("Error while removing topic");
        }
    }














    
    
    
    
    //USED FOR OFFLINE MOCKED TEST - IGNORE IT
    @Autowired
    private PageRepository pageRepository;
    private final String destinationPath = "G:/uni/Texts";

    @RequestMapping(value = "/generateTextFiles", method = RequestMethod.GET)
    public void generateTextFiles() throws Exception {

        File folder = manageDestFolder();

        List<Page> pages = this.pageRepository.findAll();

        if (pages != null) {
            for (Page page : pages) {
                try {
                    this.writeFile(folder, page.getTitle().replace("_", " ") + ".txt", page.getOnlyText());
                    System.out.println("File  " + page.getTitle().replace("_", " ") + "  written.");
                } catch (Exception e) {
                    System.out.println("Error creating file " + page.getTitle());
                    e.printStackTrace();
                }
            }
        }

    }

    private File manageDestFolder() throws Exception {
        File folder = new File(this.destinationPath);

        if (!folder.exists() || !folder.isDirectory()) {
            if (!folder.mkdirs()) {
                throw new Exception("Error creating destination folder");
            }
        }

        return folder;
    }

    private void writeFile(File folder, String filename, String content) throws Exception {

        File destinationFile = new File(folder, filename);

        if (destinationFile.exists() && destinationFile.isFile()) {
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destinationFile);
            fos.write(content.getBytes());

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                fos.close();
            } catch (Exception inn) {
                fos = null;
            }
        }
    }

    

    
}
