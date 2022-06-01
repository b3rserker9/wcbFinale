/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.filemanager;

/**
 *
 * @author J-HaRd
 */
public interface FileService {
    /**
     *
     * @param courseId
     * @param clean
     * @return
     */
    public String createCourseTxt(Long courseId, boolean clean);
    
    /**
     *
     * @param topicId
     * @param clean
     * @return
     */
    public String createTopicTxt(Long topicId, boolean clean); 
    
    
    /**
     *
     * @param courseId
     * @return
     */
    public String createCoursePDF(Long courseId);
    
    /**
     *
     * @param topicId
     * @return
     */
    public String createTopicPDF(Long topicId);
    

}
