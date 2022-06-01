package it.uniroma3.wcb.security;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

//import com.google.common.cache.CacheBuilder;
//import com.google.common.cache.CacheLoader;
//import com.google.common.cache.LoadingCache;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Service
public class LoginAttemptService {

	//FIXME uncomment to restore max attempts login block
	
    private final int MAX_ATTEMPT = 10;
   //private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
//        super();
//        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
//            public Integer load(String key) {
//                return 0;
//            }
//        });
    }

    public void loginSucceeded(String key) {
       // attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
//        int attempts = 0;
//        try {
//            attempts = attemptsCache.get(key);
//        } catch (ExecutionException e) {
//            attempts = 0;
//        }
//        attempts++;
//        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
//        try {
//            return attemptsCache.get(key) >= MAX_ATTEMPT;
//        } catch (ExecutionException e) {
//            return false;
//        }
    	return false;
    }
}
