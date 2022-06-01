package it.uniroma3.wcb.test.ranking;

import it.uniroma3.wcb.course.ranking.AscendingComparator;
import it.uniroma3.wcb.course.ranking.DescendingComparator;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class TestRankingComparator {
	
	@Test
	public void testComparator(){
		
		Double v1 = null;
		Double v2 = null;
		
		AscendingComparator ac = new AscendingComparator();
		DescendingComparator dc = new DescendingComparator();
		Assert.assertEquals(0, ac.compare(v1, v2));
		Assert.assertEquals(0, dc.compare(v1, v2));
		Assert.assertTrue(ac.isEqual(v1, v2));
		Assert.assertTrue(ac.isEqual(v1, v2));
		
		v1 = 7.2D;
		v2 = null;
		Assert.assertTrue(ac.compare(v1, v2)>0);
		Assert.assertTrue(dc.compare(v1, v2)>0);
		Assert.assertTrue(ac.isBetter(v1, v2));
		Assert.assertTrue(dc.isBetter(v1, v2));
		
		v1 = null;
		v2 = 5.4D;
		Assert.assertTrue(ac.compare(v1, v2)<0);
		Assert.assertTrue(dc.compare(v1, v2)<0);
		Assert.assertFalse(ac.isBetter(v1, v2));
		Assert.assertFalse(dc.isBetter(v1, v2));
		
		v1 = 5.4D;
		v2 = 5.4D;
		Assert.assertTrue(ac.compare(v1, v2)==0);
		Assert.assertTrue(dc.compare(v1, v2)==0);
		Assert.assertTrue(ac.isEqual(v1, v2));
		Assert.assertTrue(ac.isEqual(v1, v2));
		
		v1 = 5.41D;
		v2 = 5.4D;
		Assert.assertTrue(ac.compare(v1, v2)<0);
		Assert.assertTrue(dc.compare(v1, v2)>0);
		Assert.assertFalse(ac.isBetter(v1, v2));
		Assert.assertTrue(dc.isBetter(v1, v2));
		
		v1 = 5.41D;
		v2 = 9.4D;
		Assert.assertTrue(ac.compare(v1, v2)>0);
		Assert.assertTrue(dc.compare(v1, v2)<0);
		Assert.assertTrue(ac.isBetter(v1, v2));
		Assert.assertFalse(dc.isBetter(v1, v2));
	}
}
