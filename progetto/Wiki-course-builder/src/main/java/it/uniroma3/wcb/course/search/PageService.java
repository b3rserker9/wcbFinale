package it.uniroma3.wcb.course.search;

import it.uniroma3.wcb.persistence.model.Page;

import java.io.IOException;
import java.util.Set;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
public interface PageService {

    public Page getPage(String title, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language) throws IOException;

    public Set<Page> getPages(Set<String> titles, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language) throws IOException;
    
    public void updatePage(String title, String language, boolean isMongoOn);
    
    public void updatePages(Set<String> titles, String language, boolean isMongoOn);

}
