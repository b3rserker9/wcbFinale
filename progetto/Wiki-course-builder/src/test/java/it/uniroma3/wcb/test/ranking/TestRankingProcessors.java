package it.uniroma3.wcb.test.ranking;

import it.uniroma3.wcb.course.Corpus;
import it.uniroma3.wcb.course.ranking.InformationGainProcessor;
import it.uniroma3.wcb.course.ranking.LSIProcessor_Jblas;
import it.uniroma3.wcb.course.ranking.TfIdfProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRankingProcessors {

	private Map<String, String> documents = null;
	private Corpus plainCorpus = null;
	private Corpus stemmedCorpus = null;
	
	@Before
	public void loadCorpora() throws FileNotFoundException, Exception{
		
		this.documents = new HashMap<String, String>();
		
		File folder = new File("src/test/resources/texts");
		if(folder.exists() && folder.isDirectory()){
			File[] files = folder.listFiles();
			
			for(int i=0; i<files.length; i++){
				File file = files[i];
				if(file.isFile()){
					this.documents.put(String.valueOf(i), this.getText(new FileReader(file)));
				}
			}
		}
		
		this.plainCorpus = new Corpus("en");
		this.stemmedCorpus = new Corpus("en",new HashSet<String>(), new TreeSet<String>(),0);
		
		for(Map.Entry<String, String> document : documents.entrySet()){
			plainCorpus.addDocumentToCollection(document.getKey(), document.getValue());
			this.stemmedCorpus.addDocumentToCollection(document.getKey(), document.getValue());
		}
	}
	
	@Test
	public void test01_PlainCorpusRank(){
		System.out.println("******************************** PLAIN CORPUS ********************************");
		this.plainCorpus.printResults("PLAIN CORPUS");
	}
	
	@Test
	public void test02_PlainTfIdf() throws Exception{
		System.out.println("******************************** PLAIN CORPUS - TF-IDF ********************************");
		TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(this.plainCorpus, 4);
		tfIdfProcessor.printResults("PLAIN CORPUS - TF-IDF");
	}
	
	@Test
	public void test03_PlainLSI() throws Exception{
		System.out.println("******************************** PLAIN CORPUS - LSI ********************************");
		LSIProcessor_Jblas lsiProcessor = new LSIProcessor_Jblas(this.plainCorpus,100, 4);
		lsiProcessor.printResults("PLAIN CORPUS - LSI");
	}
	
	@Test
	public void test04_PlainLSI_tfidf() throws Exception{
		System.out.println("******************************** PLAIN CORPUS - LSI (TF-IDF) ********************************");
		TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(this.plainCorpus, 4);
		LSIProcessor_Jblas lsiProcessor = new LSIProcessor_Jblas(this.plainCorpus, 100, 4, tfIdfProcessor);
		lsiProcessor.printResults("LAIN CORPUS - LSI (TF-IDF)");
	}
	
	@Test
	public void test05_StemmedCorpusRank(){
		System.out.println("******************************** STEMMED CORPUS ********************************");
		this.stemmedCorpus.printResults("STEMMED CORPUS");
	}
	
	@Test
	public void test06_StemmedTfIdf() throws Exception{
		System.out.println("******************************** STEMMED CORPUS - TF-IDF ********************************");
		TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(this.stemmedCorpus, 4);
		tfIdfProcessor.printResults("STEMMED CORPUS - TF-IDF");
	}
	
	@Test
	public void test07_StemmedLSI() throws Exception{
		System.out.println("******************************** STEMMED CORPUS - LSI ********************************");
		LSIProcessor_Jblas lsiProcessor = new LSIProcessor_Jblas(this.stemmedCorpus, 100, 4);
		lsiProcessor.printResults("STEMMED CORPUS - LSI");
	}
	
	@Test
	public void test08_StemmedLSI_tfidf() throws Exception{
		System.out.println("******************************** STEMMED CORPUS - LSI (TF-IDF) ********************************");
		TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(this.stemmedCorpus, 4);
		LSIProcessor_Jblas lsiProcessor = new LSIProcessor_Jblas(this.stemmedCorpus, 100, 4, tfIdfProcessor);
		lsiProcessor.printResults("STEMMED CORPUS - LSI (TF-IDF)");
	}
	
	@Test
	public void test09_PlainIG() throws Exception{
		System.out.println("******************************** PLAIN CORPUS - INFORMATION GAIN ********************************");
		InformationGainProcessor igProcessor = new InformationGainProcessor(this.plainCorpus, 4);
		igProcessor.printResults("PLAIN CORPUS - INFORMATION GAIN");
	}
	
	@Test
	public void test10_StemmedIG() throws Exception{
		System.out.println("******************************** STEMMED CORPUS - INFORMATION GAIN ********************************");
		InformationGainProcessor igProcessor = new InformationGainProcessor(this.stemmedCorpus, 4);
		igProcessor.printResults("STEMMED CORPUS - INFORMATION GAIN");
	}
	
	private String getText(Reader reader) throws Exception {
		StringBuilder textBuilder = new StringBuilder();
		char[] cbuf = new char[2048];
		int len = 0;
		while ((len = reader.read(cbuf, 0, 2048)) != -1) {
			textBuilder.append(ArrayUtils.subarray(cbuf, 0, len));
		}
		reader.close();
		return textBuilder.toString();
	}
}
