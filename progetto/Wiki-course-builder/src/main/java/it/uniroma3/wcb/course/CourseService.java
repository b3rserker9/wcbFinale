package it.uniroma3.wcb.course;

import it.uniroma3.wcb.persistence.model.Course;
import it.uniroma3.wcb.persistence.model.CourseTopic;

import java.util.List;

import org.json.JSONArray;

/**
 * Course Service Manager.
 * <p>
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 */
public interface CourseService {

    /**
     * Retrieves a course by id.
     * <p>
     *
     * @param courseId course identifier
     * @return retrieved course
     * @throws Exception
     */
    public Course getCourse(Long courseId) throws Exception;

    /**
     * Retrieves logged user courses.
     * <p>
     *
     * @return user courses
     * @throws Exception
     */
    public List<Course> getUserCourses() throws Exception;

    /**
     * Save given course on database.
     * <p>
     *
     * @param course course to save
     * @return saved course
     * @throws Exception
     */
    public Course save(Course course) throws Exception;

    /**
     * Updates stored user courses using given list.
     * <p>
     *
     * @param updatedCourses list of validated user courses
     * @return stored user courses
     * @throws Exception
     */
    public List<Course> syncUserCourses(List<Course> updatedCourses) throws Exception;

    /**
     * Return all topics related to a course.
     * <p>
     *
     * @param courseId course identifier
     * @return all related course topics.
     * @throws Exception
     */
    public List<CourseTopic> getCourseTopics(Long courseId) throws Exception;

    /**
     * Create a new course topic and save it on databases.
     * <p>
     *
     * @param courseId course identifier
     * @param courseName course name
     * @param topicName new topic name
     * @param queryConstraints topic query constraints
     * @param resultItems serialized resulting items
     * @param graph serialized graph object
     * @param lang language of topic
     * @return saved course topic
     * @throws Exception
     */
    public CourseTopic createCourseTopic(String courseId, String courseName, String topicName, String queryConstraints, JSONArray resultItems, String graph, String lang) throws Exception;

    /**
     * Update an existing course topic.
     * <p>
     *
     * @param topicId topic identifier
     * @param topicName topic name
     * @param resultItems serialized updated items
     * @param graph serialized graph object
     * @param lang language of topic
     * @return updated course topic
     * @throws Exception
     */
    public CourseTopic updateCourseTopic(String topicId, String topicName, JSONArray resultItems, String graph, String lang) throws Exception;

    /**
     * Retrieves a course topic by id.
     * <p>
     *
     * @param topicId topic identifier
     * @return retrieved course topic
     * @throws Exception
     */
    public CourseTopic getTopic(Long topicId) throws Exception;

    /**
     *
     * @param topicId
     * @throws java.lang.Exception
     */
    public void removeTopic(Long topicId) throws Exception;

    /**
     *
     * @param courseId
     * @throws java.lang.Exception
     */
    public void removeCourse(Long courseId) throws Exception;
    
    
    /**
     *
     * @param topics
     * @return
     */
    public String updateTopicPosition(JSONArray topics);
    
    
    /**
     *
     * @param topicId
     * @throws java.lang.Exception
     */
    public void updatePagesTopic(Long topicId) throws Exception;

    /**
     *
     * @param courseId
     * @throws java.lang.Exception
     */
    public void updatePagesCourse(Long courseId) throws Exception;
    
}
