package com.primes.tech.service.impl;

import com.primes.tech.domain.PrimeNumbers;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertTrue;

public class BlockingPrimesServiceTest {

    private static ApplicationContext ctx;

    @Test
    public void shouldGetPrimeNumbers(){

        BlockingPrimesService blockingPrimesService = getBlockingPrimeNumberService();
        Response response = blockingPrimesService.getPrimeNumbers(10);

        PrimeNumbers primeNumbers = (PrimeNumbers) response.getEntity();
        assertTrue(primeNumbers.getInitial() == 10);
        assertTrue(primeNumbers.getPrimes().size() == 4);
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(2)));
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(3)));
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(5)));
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(7)));
    }


    protected BlockingPrimesService getBlockingPrimeNumberService() {
        return (BlockingPrimesService) getContext().getBean("blockingPrimesService");
    }

    protected ApplicationContext getContext() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        }
        return ctx;
    }
}