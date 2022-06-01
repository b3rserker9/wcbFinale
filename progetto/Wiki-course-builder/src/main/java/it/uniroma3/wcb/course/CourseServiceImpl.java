package it.uniroma3.wcb.course;

import it.uniroma3.wcb.course.search.PageService;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.dao.CourseRepository;
import it.uniroma3.wcb.persistence.dao.CourseTopicRepository;
import it.uniroma3.wcb.persistence.dao.PageRelationRepository;
import it.uniroma3.wcb.persistence.dao.PageRepository;
import it.uniroma3.wcb.persistence.dao.nosql.MongoDbDaoImp;
import it.uniroma3.wcb.persistence.dao.nosql.NoSqlDbDao;
import it.uniroma3.wcb.persistence.model.Course;
import it.uniroma3.wcb.persistence.model.CourseTopic;
import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.persistence.model.PageRelation;
import it.uniroma3.wcb.persistence.model.PageRelation.PageRelationType;
import it.uniroma3.wcb.persistence.model.PageTeachingStyle;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.persistence.model.UserTeachingStyle;
import it.uniroma3.wcb.security.RequestUserService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link CourseService} standard implementation.
 * <p>
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTopicRepository courseTopicRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageRelationRepository pageRelationRepository;

    private NoSqlDbDao mongoRepository;
    
    private final ExtraConfig extraConfigs = ExtraConfig.getIstance();
    
        
    @Autowired
    private RequestUserService userDetailsService;

    @Autowired
    private PageService pageService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getUserCourses() throws Exception {

        try {
            User user = this.userDetailsService.getRequestUser();
            return this.courseRepository.getByUserId(user.getId());

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course save(Course course) throws Exception {

        try {
            User user = this.userDetailsService.getRequestUser();

            course.setUserId(user.getId());
            return this.courseRepository.save(course);

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> syncUserCourses(List<Course> updatedCourses) throws Exception {

        try {
            if (updatedCourses == null) {
                return null;
            }

            User user = this.userDetailsService.getRequestUser();

            Set<Long> updatedCoursesId = new HashSet<>();

            for (Course updatedCourse : updatedCourses) {
                updatedCourse.setUserId(user.getId());
                Course saved = this.courseRepository.save(updatedCourse);
                updatedCoursesId.add(saved.getId());
            }

            Set<Course> coursesToRemove = new HashSet<>();
            //verify if there are courses to delete
            List<Course> reloaded = this.courseRepository.getByUserId(user.getId());
            if (reloaded != null) {
                for (Course course : reloaded) {
                    if (!updatedCoursesId.contains(course.getId())) {
                        coursesToRemove.add(course);
                    }
                }
            }

            this.courseRepository.delete(coursesToRemove); //TODO remove also linked course topics 

            return this.courseRepository.getByUserId(user.getId());

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course getCourse(Long courseId) throws Exception {
        try {
            Course course = this.courseRepository.findOne(courseId);
            return course;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CourseTopic> getCourseTopics(Long courseId) throws Exception {
        try {
            List<CourseTopic> courseSubjects = this.courseTopicRepository.getByCourseId(courseId);
            return courseSubjects;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CourseTopic createCourseTopic(String courseId, String courseName,
            String topicName, String queryConstraints, JSONArray resultItems, String graph, String lang) throws Exception {

        try {
            User user = this.userDetailsService.getRequestUser();

            Course course = null;
            if (courseId != null && !"".equals(courseId)) {
                course = this.courseRepository.findOne(new Long(courseId));
            }

            Date now = new Date();

            if (course == null) {
                //creating new course
                course = new Course();
                course.setCreated(now);
                course.setLastModify(now);
                course.setName(courseName);
                course.setDescription("");
                course.setUserId(user.getId());
                course = this.courseRepository.save(course);
            }

            //creating a new course topic
            CourseTopic courseTopic = new CourseTopic();
            courseTopic.setCourseId(course.getId());
            courseTopic.setCreated(now);
            courseTopic.setLastModify(now);
            courseTopic.setQueryConstraints(queryConstraints);
            courseTopic.setTopicName(topicName);
            courseTopic.setResultItems(resultItems);
            courseTopic.setGraph(graph);
            courseTopic.setLang(lang);
            

            //all topic pages are tagged with user teaching style
            
            this.updatePagesTeachingStyle(null, resultItems, user.getTeachingStyle(), lang);

            //create topic pages relations
            this.updatePagesRelations(courseTopic);
            
            courseTopic = this.courseTopicRepository.save(courseTopic);

            return courseTopic;

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CourseTopic getTopic(Long topicId) throws Exception {
        try {
            CourseTopic courseTopic = this.courseTopicRepository.findOne(topicId);
            return courseTopic;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CourseTopic updateCourseTopic(String topicId, String topicName, JSONArray resultItems, String graph, String lang) throws Exception {

        try {
            CourseTopic courseTopic = this.courseTopicRepository.findOne(new Long(topicId));

            if (courseTopic == null) {
                throw new Exception("Topic with id \"" + topicId + "\" doesn't exist.");
            }

            User user = this.userDetailsService.getRequestUser();
            
            courseTopic.setTopicName(topicName);
            courseTopic.setLastModify(new Date());
            //logger.info("CourseService oldPagesTopic: "+courseTopic.getResultItems().getJSONObject(0).getString("title"));
            //logger.info("CourseService newPagesTopic: "+resultItems.getJSONObject(0).getString("title"));
            
            //all topic pages are tagged with user teaching style
            this.updatePagesTeachingStyle(courseTopic.getResultItems(), resultItems, user.getTeachingStyle(), lang);

            //update pageRelations
            this.updatePagesRelations(courseTopic);

            courseTopic.setResultItems(resultItems);
            courseTopic.setGraph(graph);

            //update course topic
            courseTopic = this.courseTopicRepository.save(courseTopic);
            
            
            
            return courseTopic;

        } catch (final Exception e) {
            throw new Exception("Error while updating topic: ", e);
        }
    }

    /**
     * Updates page's teaching styles. Add/remove user contribute to/from all
     * pages.
     * <p>
     *
     * @param oldResultItems pre-update resulting set
     * @param newResultItems new result set
     * @param userTeachingStyle user teaching style
     */
    private void updatePagesTeachingStyle(JSONArray oldResultItems, JSONArray newResultItems, UserTeachingStyle userTeachingStyle, String lang) {
        //logger.info("language UTS => "+lang);
        if (newResultItems != null && userTeachingStyle != null) {
            
            if (oldResultItems == null) {
                System.out.println(1);
                for (int i = 0; i < newResultItems.length(); i++) {
                    try {
                        // pageId, title, builtPosition, userPosition, ts_rank, tfidf_rank, lsi_rank, ig_rank
                        JSONObject item = newResultItems.getJSONObject(i);

                        //if(item.has("pageId")){
                        if (item.has("title")) {
                            //long pageId=-1;
                            Page page = null;
                            /*try{
                                                            pageId = item.getLong("pageId");
                                                            page = this.pageRepository.findOne(pageId);
                                                        }
                                                        catch(Exception e){*/
                            page = this.getPage(item.getString("title"), lang);
                            // }

                            if (page != null) {
                                addUserContributeToPageTeachingStyle(page, userTeachingStyle);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error while updating page teachingStyle:\n", e);
                    }
                }
            } else {
                /*
				Set<Long> prevItems = new HashSet<>();
				Set<Long> newItems = new HashSet<>();
                 */
                Set<String> prevItems = new HashSet<>();
                Set<String> newItems = new HashSet<>();
                for (int i = 0; i < oldResultItems.length(); i++) {
                    try {
                        JSONObject item = oldResultItems.getJSONObject(i);
                        //if(item.has("pageId")){
                        if (item.has("title")) {

                            Page page = null;
                            String pageId = item.getString("title");
                            prevItems.add(pageId);
                            /*    try{
                                                            pageId = item.getLong("pageId");
                                                            prevItems.add(pageId);
                                                        }
                                                        catch(Exception e){
                                                        }*/

                        }
                    } catch (Exception e) {
                        logger.error("Error while retrieving item from existing topic build:\n", e);
                    }

                }

                for (int j = 0; j < newResultItems.length(); j++) {
                    try {
                        JSONObject item = newResultItems.getJSONObject(j);
                        //if(item.has("pageId")){
                        if (item.has("title")) {
                            //long pageId = item.getLong("pageId");
                            //newItems.add(pageId);
                            String pageId = item.getString("title");
                            newItems.add(pageId);
                        }
                    } catch (Exception e) {
                        logger.error("Error while retrieving item from new topic build:\n", e);
                    }
                }

                for (String newItem : newItems) {
                    if (!prevItems.contains(newItem)) {
                        try {
                            //Page page = this.pageRepository.findOne(newItem);
                            //Page page = this.pageRepository.getByTitle(newItem, lang);
                            Page page = getPage(newItem, lang);
                            if (page != null) {
                                addUserContributeToPageTeachingStyle(page, userTeachingStyle);
                            }
                        } catch (Exception e) {
                            logger.error("Error while updating page teachingStyle:\n", e);
                        }
                    }
                }

                for (String prevItem : prevItems) {
                    if (!newItems.contains(prevItem)) {
                        try {
                            //Page page = this.pageRepository.findOne(prevItem);
                            //Page page = this.pageRepository.getByTitle(prevItem, lang);
                            Page page = getPage(prevItem, lang);
                            //logger.info("remove ts");
                            if (page != null) {
                                removeUserContributeToPageTeachingStyle(page, userTeachingStyle);
                            }
                        } catch (Exception e) {
                            logger.error("Error while updating page teachingStyle:\n", e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Create relations between all topic pages.<br />
     * Each page will be linked with other topic pages (full connected graph).
     * <p>
     *
     * @param courseTopic course topic
     */
    private void updatePagesRelations(CourseTopic courseTopic) {
        try {
            List<PageRelation> pageRelations = this.pageRelationRepository.getByTopicId(courseTopic.getId());

            // database clean
            if (pageRelations != null) {
                this.pageRelationRepository.delete(pageRelations);
            }

            JSONArray resultItems = courseTopic.getResultItems();

            if (resultItems != null) {
                Set<String> pageTitles = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

                for (int i = 0; i < resultItems.length(); i++) {
                    JSONObject item = resultItems.getJSONObject(i);

                    if (item.has("title")) {
                        String pageTitle = item.getString("title");
                        pageTitles.add(pageTitle);
                    }
                }

                String[] array = new String[pageTitles.size()];

                pageTitles.toArray(array);

                for (int i = 0; i < array.length - 1; i++) {
                    try {
                        String pageOneTitle = array[i];

                        for (int j = (i + 1); j < array.length; j++) {
                            String pageTwoTitle = array[j];

                            PageRelation pageRelation = new PageRelation();
                            pageRelation.setPageOneTitle(pageOneTitle);
                            pageRelation.setPageTwoTitle(pageTwoTitle);
                            pageRelation.setCourseId(courseTopic.getCourseId());
                            pageRelation.setTopicId(courseTopic.getId());
                            pageRelation.setType(PageRelationType.TOPIC);
                            pageRelation.setCreated(new Date());
                            pageRelation.setLanguage(courseTopic.getLang());

                            this.pageRelationRepository.save(pageRelation);
                        }

                    } catch (Exception e) {
                        logger.error("Error while creating pages linkd. ", e);
                    }
                }

                //commented - relation based on wiki links won't be stored for space/confusion reasons. 
                /*
				for(String pageTitle : pageTitles){
					try{
						PageLinksDTO pageLinksDto = this.scanService.scanPageLinks(pageTitle, 1);
						
						if(pageLinksDto.getLinkedPages()!=null){
							for(PageLinksDTO linkedPage : pageLinksDto.getLinkedPages()){
								if(this.pageRelationRepository.getByTitlesAndType(pageTitle, linkedPage.getTitle(), PageRelationType.LINK)==null){
									PageRelation pageRelation = new PageRelation();
									pageRelation.setPageOneTitle(pageTitle);
									pageRelation.setPageTwoTitle(linkedPage.getTitle());
									pageRelation.setType(PageRelationType.LINK);
									pageRelation.setCreated(new Date());
									
									this.pageRelationRepository.save(pageRelation);
								}
							}
						}
					}
					catch(Exception e){
						logger.error("Error while creating pages linkd. ", e);
					}
				}
                 */
            }
        } catch (Exception e) {
            logger.error("Error while updating course topic pages relations:\n", e);
        }
    }

    /**
     * Add user contribute to page teaching style.
     * <p>
     *
     * @param page page to update
     * @param userTeachingStyle user teaching style
     * @throws Exception
     */
    private void addUserContributeToPageTeachingStyle(Page page, UserTeachingStyle userTeachingStyle) throws Exception {
        PageTeachingStyle pTS = page.getTeachingStyle();
        System.out.println(1);
        if (pTS == null) {
            // the page was never tagged; its T.S. will be equal to user T.S.
            pTS = new PageTeachingStyle();
            pTS.setDelegator(userTeachingStyle.getDelegator());
            pTS.setExpert(userTeachingStyle.getExpert());
            pTS.setFacilitator(userTeachingStyle.getFacilitator());
            pTS.setFormalAuthority(userTeachingStyle.getFormalAuthority());
            pTS.setPersonalModel(userTeachingStyle.getPersonalModel());
            pTS.addDisciplineContribute(userTeachingStyle.getDiscipline());

            pTS.setAvgContributors(1);
            System.out.println(pTS);
        } else {
            //calculate arithmetical average
            int contributors = pTS.getAvgContributors() + 1;

            pTS.setDelegator(increaseAvg(pTS.getDelegator(), userTeachingStyle.getDelegator(), contributors));
            pTS.setExpert(increaseAvg(pTS.getExpert(), userTeachingStyle.getExpert(), contributors));
            pTS.setFacilitator(increaseAvg(pTS.getFacilitator(), userTeachingStyle.getFacilitator(), contributors));
            pTS.setFormalAuthority(increaseAvg(pTS.getFormalAuthority(), userTeachingStyle.getFormalAuthority(), contributors));
            pTS.setPersonalModel(increaseAvg(pTS.getPersonalModel(), userTeachingStyle.getPersonalModel(), contributors));
            pTS.addDisciplineContribute(userTeachingStyle.getDiscipline());

            pTS.setAvgContributors(contributors);
            System.out.println(pTS);
        }

        page.setTeachingStyle(pTS);
        System.out.println(page);
        this.savePage(page);
    }

    /**
     * Remove user contribute (previously added) from page teaching style.
     * <p>
     *
     * @param page page to update
     * @param userTeachingStyle user teaching style
     * @throws Exception
     */
    private void removeUserContributeToPageTeachingStyle(Page page, UserTeachingStyle userTeachingStyle) throws Exception {
        PageTeachingStyle pTS = page.getTeachingStyle();

        if (pTS == null) {
            return;
        }

        int contributors = pTS.getAvgContributors() - 1;

        if (contributors > 0) {
            pTS.setDelegator(decreaseAvg(pTS.getDelegator(), userTeachingStyle.getDelegator(), pTS.getAvgContributors(), contributors));
            pTS.setDelegator(decreaseAvg(pTS.getDelegator(), userTeachingStyle.getDelegator(), pTS.getAvgContributors(), contributors));
            pTS.setExpert(decreaseAvg(pTS.getExpert(), userTeachingStyle.getExpert(), pTS.getAvgContributors(), contributors));
            pTS.setFacilitator(decreaseAvg(pTS.getFacilitator(), userTeachingStyle.getFacilitator(), pTS.getAvgContributors(), contributors));
            pTS.setFormalAuthority(decreaseAvg(pTS.getFormalAuthority(), userTeachingStyle.getFormalAuthority(), pTS.getAvgContributors(), contributors));
            pTS.setPersonalModel(decreaseAvg(pTS.getPersonalModel(), userTeachingStyle.getPersonalModel(), pTS.getAvgContributors(), contributors));
            pTS.removeDisciplineContribute(userTeachingStyle.getDiscipline());
            pTS.setAvgContributors(contributors);
        } else {
            pTS = null;
        }

        page.setTeachingStyle(pTS);
        this.savePage(page);
    }

    /**
     * Support method to add a contribute to average.
     * <p>
     *
     * @param a
     * @param b
     * @param den
     * @return {@code  (a+b) / den}
     */
    private double increaseAvg(double a, double b, int den) {
        return (double) ((a + b) / den);
    }

    /**
     * Support method to remove a contribute from an existing average.
     * <p>
     *
     * @param actualAvg
     * @param toRemove
     * @param actualDen
     * @param newDen
     * @return {@code  ((actualAvg * actualDen) - toRemove) / newDen}
     */
    private double decreaseAvg(double actualAvg, double toRemove, int actualDen, int newDen) {
        return (double) (((actualAvg * actualDen) - toRemove) / newDen);
    }
    
    private void savePage(Page page) {
        System.out.println(page);
        if (page != null) {
            System.out.println("sto salvando");
            
            boolean excludeSecondDb;
            String lang = page.getLang();
            excludeSecondDb = this.extraConfigs.isExcludeSecondDb(lang);
            int dbOrder = this.extraConfigs.getDBOrder(lang);

            boolean mysqlYes = true;
            boolean mongoYes = true;
            if (dbOrder > 0 && excludeSecondDb) {
                mysqlYes = false;
            } else if (dbOrder < 1 && excludeSecondDb) {
                mongoYes = false;
            }
            if (mysqlYes) {
                System.out.println("sto salvando 2");
                logger.info("CourseSevice Impl savePage : " + page.getTitle());

                Page byTitle = this.pageRepository.getByTitle(page.getTitle(), lang);
                System.out.println(byTitle);
                if (byTitle != null) {
                    byTitle.setTeachingStyle(page.getTeachingStyle());
                } else {
                    byTitle = page;
                }
                System.out.println(byTitle.toString());
                this.pageRepository.save(byTitle);
            }
            if (extraConfigs.isMongoDbCourseOn(lang) && mongoYes) {
                if (this.mongoRepository == null) {

                    this.mongoRepository = new MongoDbDaoImp(lang);
                } else if (!this.mongoRepository.getLanguage().equalsIgnoreCase(lang)) {
                    mongoRepository.closeConnection();
                    this.mongoRepository = new MongoDbDaoImp(lang);
                }
                Page pg = this.mongoRepository.getWikipediaPage(page.getTitle().toLowerCase());
                if (pg == null) {
                    this.mongoRepository.saveWikipediaPage(page);
                } else {
                    this.mongoRepository.saveWikipediaPageTS(page.getTitle().toLowerCase(), page.getTeachingStyle());
                }

            }

        }
    }
 
    private Page getPage(String title, String lang){
        Page p = null;//this.pageRepository.getByTitle(title, lang);
        if(extraConfigs.isMongoDbCourseOn(lang)&&extraConfigs.getDBOrder(lang)==1){
            try {
                if (this.mongoRepository == null) {
                    
                    this.mongoRepository = new MongoDbDaoImp(lang);
                }
                else if(!this.mongoRepository.getLanguage().equalsIgnoreCase(lang)){
                    mongoRepository.closeConnection();
                    this.mongoRepository = new MongoDbDaoImp(lang);
                }
                p = this.mongoRepository.getWikipediaPage(title.toLowerCase());
               

            } catch (Exception e) {
                p = null;
            }
            if (p == null) {
                p = this.pageRepository.getByTitle(title, lang);
            }
        }
        else {
            p = this.pageRepository.getByTitle(title, lang);
            if (p == null) {
                try {
                    if (this.mongoRepository == null) {

                        this.mongoRepository = new MongoDbDaoImp(lang);
                    } else if (!this.mongoRepository.getLanguage().equalsIgnoreCase(lang)) {
                        mongoRepository.closeConnection();
                        this.mongoRepository = new MongoDbDaoImp(lang);
                    }
                    p = this.mongoRepository.getWikipediaPage(title.toLowerCase());

                } catch (Exception e) {
                    p = null;
                }
            }
            
        }
        return p;
    }

    @Override
    public void removeTopic(Long topicId) throws Exception {
        try {
            User user = this.userDetailsService.getRequestUser();
            CourseTopic courseTopic = this.courseTopicRepository.findOne(topicId);
            

            if (courseTopic == null) {
                throw new Exception("Topic with id \"" + topicId + "\" doesn't exist.");
            }
            else {
                JSONArray resultItems = courseTopic.getResultItems();
                for (int x = 0; x < resultItems.length(); x++) {
                    JSONObject item = resultItems.getJSONObject(x);
                    if (item.has("title")) {
                        Page page = null;

                        page = this.getPage(item.getString("title"), courseTopic.getLang());

                        if (page != null) {
                            removeUserContributeToPageTeachingStyle(page, user.getTeachingStyle());
                        }
                    }

                }
                this.courseTopicRepository.delete(courseTopic);
            }
        } catch (Exception ex) {
            logger.error("removeTopic => "+ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeCourse(Long courseId) throws Exception {
        try {
            User user = this.userDetailsService.getRequestUser();
            //Course course = this.courseRepository.findOne(courseId);
            List<CourseTopic> byCourseId = this.courseTopicRepository.getByCourseId(courseId);

            if (byCourseId == null) {
                throw new Exception("Course with id \"" + courseId + "\" doesn't exist.");
            } else {
                for (CourseTopic courseTopic : byCourseId) {
                    if (courseTopic == null) {
                        //throw new Exception("Course with id \"" + courseId + "\" doesn't exist.");
                    } else {
                        JSONArray resultItems = courseTopic.getResultItems();
                        for (int x = 0; x < resultItems.length(); x++) {
                            JSONObject item = resultItems.getJSONObject(x);
                            if (item.has("title")) {
                                Page page = null;

                                page = this.getPage(item.getString("title"), courseTopic.getLang());

                                if (page != null) {
                                    removeUserContributeToPageTeachingStyle(page, user.getTeachingStyle());
                                }
                            }

                        }
                    }
                    this.courseTopicRepository.delete(courseTopic);
                }
                Course findOne = this.courseRepository.findOne(courseId);
                this.courseRepository.delete(findOne);
            }
        } catch (Exception ex) {
            logger.error("removeTopic => "+ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    
    
    
    
    @Override
    public String updateTopicPosition(JSONArray topics) {
        for(int x=0;x<topics.length();x++){
            JSONObject j= topics.getJSONObject(x);
            CourseTopic findOne = this.courseTopicRepository.findOne(new Long(j.getString("id")));
            findOne.setPosition(new Long(j.getString("position")));
            this.courseTopicRepository.save(findOne);
        }
        return "ok";
    }

    @Override
    public void updatePagesTopic(Long topicId) throws Exception {
        try {
            //User user = this.userDetailsService.getRequestUser();
            CourseTopic courseTopic = this.courseTopicRepository.findOne(topicId);
            Set<String> tit=new TreeSet<>();

            if (courseTopic == null) {
                throw new Exception("Topic with id \"" + topicId + "\" doesn't exist.");
            }
            else {
                
                JSONArray resultItems = courseTopic.getResultItems();
                for (int x = 0; x < resultItems.length(); x++) {
                    JSONObject item = resultItems.getJSONObject(x);
                    if (item.has("title")) {
                        tit.add(item.getString("title"));
                    }

                }
                this.pageService.updatePages(tit, courseTopic.getLang(), this.extraConfigs.isMongoDbCourseOn(courseTopic.getLang()));
            }
        } catch (Exception ex) {
            logger.error("updatePagesTopic => "+ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updatePagesCourse(Long courseId) throws Exception {
        try {
            //User user = this.userDetailsService.getRequestUser();
            //Course course = this.courseRepository.findOne(courseId);
            //CourseTopic courseTopic = this.courseTopicRepository.findOne(topicId);
            List<CourseTopic> courseTopics = this.courseTopicRepository.getByCourseId(courseId);
            Set<String> tit=new TreeSet<>();

            if (courseTopics == null) {
                throw new Exception("List<Topic> \" doesn't exist.");
            }
            else {
                for (CourseTopic courseTopic : courseTopics) {
                    JSONArray resultItems = courseTopic.getResultItems();
                    for (int x = 0; x < resultItems.length(); x++) {
                        JSONObject item = resultItems.getJSONObject(x);
                        if (item.has("title")) {
                            tit.add(item.getString("title"));
                        }

                    }
                    this.pageService.updatePages(tit, courseTopic.getLang(), this.extraConfigs.isMongoDbCourseOn(courseTopic.getLang()));
                }
            }
        } catch (Exception ex) {
            logger.error("updatePagesCourse => "+ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
