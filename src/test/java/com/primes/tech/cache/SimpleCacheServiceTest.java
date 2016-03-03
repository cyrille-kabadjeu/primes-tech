package com.primes.tech.cache;

import com.primes.tech.service.impl.PrimesService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SimpleCacheServiceTest {

    private static ApplicationContext ctx;
	
	@Test
	public void testSimpleCacheService(){
		
		PrimesService primesService = getPrimeNumberService();
		primesService.getPrimeNumbers(10, "");
		
		SimpleCacheService simpleCacheService = getSimpleCacheService();

		List<Integer> primeNumberList = simpleCacheService.getPrimeNumbers(10);
		assertTrue(primeNumberList.size() == 4);
		assertTrue(primeNumberList.get(0).equals(Integer.valueOf(2)));
		assertTrue(primeNumberList.get(1).equals(Integer.valueOf(3)));
		assertTrue(primeNumberList.get(2).equals(Integer.valueOf(5)));
		assertTrue(primeNumberList.get(3).equals(Integer.valueOf(7)));

	}
	
    protected PrimesService getPrimeNumberService() {
    	return (PrimesService) getContext().getBean("primesService");
    }
    
    protected SimpleCacheService getSimpleCacheService() {
    	return (SimpleCacheService) getContext().getBean("simpleCacheService");
    }
    
    protected ApplicationContext getContext() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        }
        return ctx;
    }
}
