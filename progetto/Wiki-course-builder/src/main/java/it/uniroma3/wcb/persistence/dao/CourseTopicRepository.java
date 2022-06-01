package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.CourseTopic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
public interface CourseTopicRepository extends JpaRepository<CourseTopic, Long> {

    @Query("SELECT p FROM CourseTopic p WHERE p.courseId = :courseId ORDER BY p.position, p.id")
    public List<CourseTopic> getByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT COUNT(p) FROM CourseTopic p")
    public int getNumTopic();

}
