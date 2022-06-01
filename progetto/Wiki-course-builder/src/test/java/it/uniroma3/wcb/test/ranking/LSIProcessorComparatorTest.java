package it.uniroma3.wcb.test.ranking;

import it.uniroma3.wcb.course.Corpus;
import it.uniroma3.wcb.course.ranking.LSIProcessor_Jama;
import it.uniroma3.wcb.course.ranking.LSIProcessor_Jblas;
import it.uniroma3.wcb.course.ranking.TfIdfProcessor;
import it.uniroma3.wcb.spring.AppConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class LSIProcessorComparatorTest {

	private static Corpus stemmedCorpus = null;
	private static TreeSet<String> sectionToSkip = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	
	private static String inputFolder = "src/test/resources/texts";
	private static String outputFolder = "C:/corpus/test";
	private static String ignoreSections = "See also;References;References and citations;Citations;Sources;Primary sources;Web sources;Books;Further reading;External links;Notes;Bibliography;Bibliography and further reading;Website sources";
	
	
	@BeforeClass
	public static void loadCorpora() throws FileNotFoundException, Exception{
		for(String sectionName : ignoreSections.split(";")){
			sectionToSkip.add(sectionName);
		}

		AppConfig appConfig = new AppConfig();
		
		stemmedCorpus = new Corpus("en",appConfig.getStopWords("en"), sectionToSkip, 0);
		
		File folder = new File(inputFolder);
		if(folder.exists() && folder.isDirectory()){
			File[] files = folder.listFiles();
			
			long globalStart = System.currentTimeMillis();
			int counter = 0;
			for(File file : files){
				if(file.isFile()){
					long start = System.currentTimeMillis();
					String text = getText(new FileReader(file));
					stemmedCorpus.addDocumentToCollection(String.valueOf(counter), text);
					counter ++;
					System.out.println("Loaded file: "+file.getName()+" - "+(System.currentTimeMillis()-start)+" ms.");
				}
			}
			System.out.println("\nCorpus loaded in "+(System.currentTimeMillis()-globalStart)+" ms.\n");
		}
	}
	
	@Test
	public void generateTermFiles() throws Exception{
		
		File folder = new File(outputFolder);
		if(!folder.exists() && !folder.isDirectory()){
			if(!folder.mkdirs())
				throw new Exception("Error creating folder "+outputFolder);
		}
		
		System.out.println("Writing Corpus terms file...");
		
		StringBuffer buffer = new StringBuffer("");
		for(Map.Entry<String, Integer> term : stemmedCorpus.getCorpusTermsCardinality().entrySet()){
			double value = stemmedCorpus.getCorpusTermsOccurencies().get(term.getKey()).doubleValue() / term.getValue().doubleValue();
			buffer.append(String.valueOf(value)+" - "+term.getKey()+"\n");
		}
		writeFile(folder, "stemmed.txt", buffer.toString());
		System.out.println("Done.\n");
		
		long start = System.currentTimeMillis(); 
		stemmedCorpus.limitCorpusTerms(5000, 4, "romeo and juliet");
		System.out.println("\nCorpus terms limited in "+(System.currentTimeMillis()-start)+" ms.\n");
		
		
		System.out.println("Writing Limited Corpus terms file...");
		buffer = new StringBuffer("");
		for(Map.Entry<String, Integer> term : stemmedCorpus.getCorpusTermsCardinality().entrySet()){
			double value = stemmedCorpus.getCorpusTermsOccurencies().get(term.getKey()).doubleValue() / term.getValue().doubleValue();
			buffer.append(String.valueOf(value)+" - "+term.getKey()+"\n");
		}
		writeFile(folder, "limited.txt", buffer.toString());
		System.out.println("Done.\n");
	}
	
	
	@Test
	public void compareLsiProcessorComputationTime() throws Exception{
		TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(stemmedCorpus, 5);
		long start = System.currentTimeMillis();
		LSIProcessor_Jblas lsiProcessor = new LSIProcessor_Jblas(stemmedCorpus, 100, 5, tfIdfProcessor);
		
		Map<String, Double> res1 = lsiProcessor.scoreDocuments("romeo and juliet");
		long end = System.currentTimeMillis() - start;
		System.out.println("LSIProcessor_Jblas computation time with complete terms list: "+end+" ms.");
		
		
		start = System.currentTimeMillis(); 
		stemmedCorpus.limitCorpusTerms(5000, 4, "romeo and juliet");
		System.out.println("\nCorpus terms limited in "+(System.currentTimeMillis()-start)+" ms.\n");
		
		tfIdfProcessor = new TfIdfProcessor(stemmedCorpus, 5);
		start = System.currentTimeMillis();
		lsiProcessor = new LSIProcessor_Jblas(stemmedCorpus, 100, 5, tfIdfProcessor);
		
		Map<String, Double> res2 = lsiProcessor.scoreDocuments("romeo and juliet");
		end = System.currentTimeMillis() - start;
		System.out.println("LSIProcessor_Jblas computation time with limited terms list: "+end+" ms.");
		
		
		StringBuffer res = new StringBuffer("");
		
		for(String key : res1.keySet()){
			res.append(res1.get(key).toString() +" | " +res2.get(key).toString() +"  |  " +key);
			res.append("\n");
		}
		
		System.out.println(res.toString());
	}
	

	@Test
	public void compareLsiProcessors() throws Exception{
		TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(stemmedCorpus, 5);
		long start = System.currentTimeMillis();
		LSIProcessor_Jblas lsiProcessor1 = new LSIProcessor_Jblas(stemmedCorpus, 100, 5, tfIdfProcessor);
		Map<String, Double> res1 = lsiProcessor1.scoreDocuments("romeo and juliet");
		long end = System.currentTimeMillis() - start;
		System.out.println("LSIProcessor_Jblas computation time: "+end+" ms.");
		
		start = System.currentTimeMillis();
		LSIProcessor_Jama lsiProcessor2 = new LSIProcessor_Jama(stemmedCorpus, 100, 5, tfIdfProcessor);
		Map<String, Double> res2 = lsiProcessor2.scoreDocuments("romeo and juliet");
		end = System.currentTimeMillis() - start;
		System.out.println("LSIProcessor_Jama computation time: "+end+" ms.");
		
		for(String key : res1.keySet()){
			Assert.assertEquals(res1.get(key).doubleValue(), res2.get(key).doubleValue(), 0);
		}
	}
	
	private static String getText(Reader reader) throws Exception {
		StringBuilder textBuilder = new StringBuilder();
		char[] cbuf = new char[2048];
		int len = 0;
		while ((len = reader.read(cbuf, 0, 2048)) != -1) {
			textBuilder.append(ArrayUtils.subarray(cbuf, 0, len));
		}
		reader.close();
		String res =  textBuilder.toString();
		
		return res;
	}
	
	private void writeFile(File folder, String fileName, String content) throws Exception{
		File file = new File(folder, fileName);
		if(file.exists() && file.isFile()){
			if(!file.delete())
				throw new Exception("Cannot override file "+file.getAbsolutePath());
		}
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.flush();
		}catch(Exception e){
			throw e;
		}
		finally{
			try{
				fos.close();
			}catch(Exception inn){
				fos = null;
			}
		}
	}
}
