package it.uniroma3.wcb.spring;

import it.uniroma3.wcb.course.ranking.TeachingStyleComparator;
import it.uniroma3.wcb.course.search.CustomWiki;

import java.util.HashSet;
import java.util.Properties;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
@Configuration
@ComponentScan(basePackages = {"it.uniroma3.wcb.registration"})
@PropertySources({
    @PropertySource("classpath:email.properties"),
    @PropertySource("classpath:wiki.properties"),
    @PropertySource("classpath:appconfigs.properties")
})
public class AppConfig {

    @Autowired
    private Environment env;

   
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(env.getProperty("smtp.host"));
        mailSenderImpl.setPort(env.getProperty("smtp.port", Integer.class));
        mailSenderImpl.setProtocol(env.getProperty("smtp.protocol"));
        mailSenderImpl.setUsername(env.getProperty("smtp.username"));
        mailSenderImpl.setPassword(env.getProperty("smtp.password"));
        Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }

    @Bean
    public CustomWiki customWiki() {
        CustomWiki wiki = new CustomWiki();
        wiki.setThrottle(env.getProperty("wiki.service.throttle.value", Integer.class));
        wiki.setDefaultMaxResults(env.getProperty("wiki.default.max.results", Integer.class));
        return wiki;
    }

    @Bean
    public TeachingStyleComparator teachingStyleComparator() {
        return new TeachingStyleComparator(this.getDecimalPrecision());
    }

    //utility methods
    
    
    //app not use this method but use method in extraConfig
    public TreeSet<String> getSectionsToSkip(String language) {
        TreeSet<String> sectionsToSkip = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        try {
            String prop1 = env.getProperty("wiki.excluded.sections."+language, String.class, "");
            //System.out.println("sectionsToSkip "+language+"=> "+prop1);
            String[] split = prop1.split("\\;");
            for(String s:split){
                sectionsToSkip.add(s.trim().toLowerCase());
            }
            //CollectionUtils.addAll(sectionsToSkip, prop1.split(";"));
        } catch (Exception e) {
            //System.out.println("Error sectionsToSkip "+language+"=> "+e.toString());
        }
        return sectionsToSkip;
    }
    //app not use this method but use method in extraConfig
    public HashSet<String> getStopWords(String language) {
        HashSet<String> sectionsToSkip = new HashSet<>();
        try {
            String prop1 = env.getProperty("stop.word." + language, String.class, "");
            String[] split = prop1.split("\\;");
            for(String s:split){
                sectionsToSkip.add(s.trim().toLowerCase());
            }
            // CollectionUtils.addAll(sectionsToSkip, prop1.split(";"));
        } catch (Exception e) {

        }

        return sectionsToSkip;
    }

    public int getDecimalPrecision() {
        return this.env.getProperty("scores.decimal.precision", Integer.class, 0);
    }

    public boolean cachePagesOnDatabase() {
        return this.env.getProperty("cache.resulting.query.pages.on.db", Boolean.class, true);
    }

    public int getScanDepthLevel() {

        Integer scanDepthLevel = env.getProperty("scan.max.depth.level", Integer.class, 1);
        if (scanDepthLevel < 0) {
            scanDepthLevel = 1;
        }
        return scanDepthLevel;

    }

    public int getScanNumberResults() {
        Integer scan = env.getProperty("scan.number.results", Integer.class, 1);
        if (scan < 0) {
            scan = 1;
        }
        return scan;
    }

    public int getLsiMaxMatrixReductionRatio() {
        Integer maxReductionRatio = env.getProperty("lsi.max.matrix.reduction.ratio", Integer.class, 0);
        if (maxReductionRatio < 0) {
            maxReductionRatio = 0;
        }
        return maxReductionRatio;
    }

    public int getLimitedCorpusTermsMaxSize() {
        Integer limit = env.getProperty("limited.corpus.terms.max.size", Integer.class, -1);
        return limit;
    }

    public int getSkipTermsSmallerThanSize() {
        Integer skipSize = env.getProperty("limited.corpus.skip.terms.smaller.than.size", Integer.class, -1);
        return skipSize;
    }

    public int getNumberSectionLinks() {
        Integer skipSize = env.getProperty("number.sections.links", Integer.class, -1);
        return skipSize;
    }

    public int getNumberSectionBuildText() {
        Integer skipSize = env.getProperty("number.sections.build.text", Integer.class, -1);
        return skipSize;
    }

    public int getNumberSectionSearchText() {
        Integer skipSize = env.getProperty("number.sections.search.text", Integer.class, -1);
        return skipSize;
    }

    public boolean isPluginPageLinkOn() {
        return this.env.getProperty("plugin.page.link.on", Boolean.class, true);
    }

    public String PluginPageLinkURL() {
        String s = env.getProperty("plugin.page.link.url", String.class, "");
        return s;
    }

    public int getMaxLoopSize() {
        Integer skipSize = env.getProperty("max.loop.size", Integer.class, -1);
        return skipSize;
    }

}
