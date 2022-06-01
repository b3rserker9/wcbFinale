package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.userId=?1 ORDER BY c.name ASC")
    public List<Course> getByUserId(Long userId);

    @Query("SELECT COUNT(p) FROM Course p")
    public int getNumCourse();

}
