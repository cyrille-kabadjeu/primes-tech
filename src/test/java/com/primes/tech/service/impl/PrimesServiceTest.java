package com.primes.tech.service.impl;

import com.primes.tech.domain.PrimeNumbers;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertTrue;


public class PrimesServiceTest {

    private static ApplicationContext ctx;
	
	
	@Test
	public void testGetPrimeNumbersEmptyParam(){
		
		PrimesService primesService = getPrimeNumberService();
        Response response = primesService.getPrimeNumbers(10, "");

        PrimeNumbers primeNumbers = (PrimeNumbers) response.getEntity();
		assertTrue(primeNumbers.getInitial() == 10);
		assertTrue(primeNumbers.getPrimes().size() == 4);
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(2)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(3)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(5)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(7)));

	}
	
	@Test
	public void testGetPrimeNumbersSequentialParam(){
		
		PrimesService primesService = getPrimeNumberService();
		Response response = null;
		
		response = primesService.getPrimeNumbers(13, "sequential");
		
		PrimeNumbers primeNumbers = (PrimeNumbers) response.getEntity();
		assertTrue(primeNumbers.getInitial() == 13);
		assertTrue(primeNumbers.getPrimes().size() == 6);
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(2)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(3)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(5)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(7)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(11)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(13)));

	}
	
	@Test
	public void testGetPrimeNumbersParallelParam(){
		
		PrimesService primesService = getPrimeNumberService();
		Response response = null;
		
		response = primesService.getPrimeNumbers(15, "parallel");
		
		PrimeNumbers primeNumbers = (PrimeNumbers) response.getEntity();
		assertTrue(primeNumbers.getInitial() == 15);
		assertTrue(primeNumbers.getPrimes().size() == 6);
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(2)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(3)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(5)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(7)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(11)));
		assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(13)));

	}
	
	
	@Test(expected=WebApplicationException.class)
	public void testGetPrimeNumbersWrongExecutionNameParam(){
		
		PrimesService primesService = getPrimeNumberService();
		primesService.getPrimeNumbers(10, "badExecutionMode");

	}
	
    protected PrimesService getPrimeNumberService() {
    	return (PrimesService) getContext().getBean("primesService");
    }
	
    protected ApplicationContext getContext() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        }
        return ctx;
    }
}
