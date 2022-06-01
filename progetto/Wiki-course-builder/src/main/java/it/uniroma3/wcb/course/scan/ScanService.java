package it.uniroma3.wcb.course.scan;


/**
 * 
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
public interface ScanService {

	/**
	 * Retrieves all wiki pages referenced from the starting page passed as a parameter
	 * 
	 * @param startingPageTitle  title of starting page
	 * @param scanDepthLevel   max scan depth
	 * @return pages with external links
	 * @throws Exception
	 */
	public PageLinksDTO scanPageLinks(String startingPageTitle, int scanDepthLevel, String language) throws Exception;
        
        /**
	 * Retrieves all wiki pages from MongoDB referenced from the starting page passed as a parameter
	 * 
	 * @param startingPageTitle  title of starting page
	 * @param scanDepthLevel   max scan depth
	 * @return pages with external links
	 * @throws Exception
	 */
	public PageLinksDTO scanPageLinksNoSql(String startingPageTitle, int scanDepthLevel, String language) throws Exception;
        
         /**
	 * Retrieves all wiki pages from MongoDB referenced from the starting page passed as a parameter
	 * 
	 * @param startingPageTitle  title of starting page
	 * @param scanDepthLevel   max scan depth
	 * @return pages with external links
	 * @throws Exception
	 */ 
        public PageLinksDTO scanPageLinksNoSqlLevel(String startingPageTitle, int scanDepthLevel, String language) throws Exception;
        
}
