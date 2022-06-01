package it.uniroma3.wcb.course.scan;

import it.uniroma3.wcb.course.ranking.RankType;
import it.uniroma3.wcb.persistence.model.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class PageLinksDTO implements Comparable<PageLinksDTO>, Serializable {

	private static final long serialVersionUID = 822867463360742881L;

	private String title;
	
	private Page page;
	
	private Map<RankType, Double> rankingScores = new HashMap<>();
	
	private int level = 0;
	
	private String id;
	
	private Map<RankType, Boolean> selectedFromSequenceAlgorithms = new HashMap<>();
	
	private Set<PageLinksDTO> linkedPages = new HashSet<>();
	
	public PageLinksDTO(Page page, int level, String id){
		this.page = page;
		this.title = page.getTitle();
		this.level = level;
		this.id =id;
	}
	
	public void addLinkedPage(PageLinksDTO linkedPage){
		if(linkedPage!=null)
			this.linkedPages.add(linkedPage);
	}
	
	public String getTitle() {
		return title;
	}

	public Page getPage() {
		return page;
	}
	
	public String getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public Set<PageLinksDTO> getLinkedPages() {
		return linkedPages;
	}
	
	public Double getRankingScore(RankType rankType) {
		return this.rankingScores.get(rankType);
	}
	
	public void setRankingScore(RankType rankType, Double score){
		if(rankType!=null && score !=null)
			this.rankingScores.put(rankType, score);
	}
	
	public Map<RankType, Double> getRankingScores() {
		return rankingScores;
	}

	public boolean isSelectedFromSequenceAlgorithm(RankType rankType) {
		
		Boolean check = this.selectedFromSequenceAlgorithms.get(rankType);
		
		if(check==null)
			return false;
		else
			return check.booleanValue();
	}
	
	public Map<RankType, Boolean> getSelectedFromSequenceAlgorithms() {
		return selectedFromSequenceAlgorithms;
	}

	public void setSelectedFromSequenceAlgorithm(RankType rankType, boolean selectedFromSequenceAlgorithm) {
		if(rankType!=null)
			this.selectedFromSequenceAlgorithms.put(rankType, new Boolean(selectedFromSequenceAlgorithm));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.toLowerCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageLinksDTO other = (PageLinksDTO) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equalsIgnoreCase(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(PageLinksDTO o) {
		if(o==null)
			return -1;
		
		if(title==null){
			if(o.getTitle() == null)
				return 0;
			else
				return 1;
		}
		
		if(o.getTitle() == null)
			return -1;
		
		return this.title.compareToIgnoreCase(o.getTitle());
	}

}
