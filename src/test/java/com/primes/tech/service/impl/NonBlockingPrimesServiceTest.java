package com.primes.tech.service.impl;

import com.primes.tech.domain.PrimeNumbers;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class NonBlockingPrimesServiceTest {

    private static ApplicationContext ctx;

    @Test
    public void shouldGetPrimeNumbers() throws ExecutionException, InterruptedException {

        NonBlockingPrimesService nonBlockingPrimesService = getNonBlockingPrimeNumberService();
        Response response = nonBlockingPrimesService.getPrimeNumbers(10);

        PrimeNumbers primeNumbers = (PrimeNumbers) response.getEntity();
        assertTrue(primeNumbers.getInitial() == 10);
        assertTrue(primeNumbers.getPrimes().size() == 4);
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(2)));
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(3)));
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(5)));
        assertTrue(primeNumbers.getPrimes().contains(Integer.valueOf(7)));
    }


    protected NonBlockingPrimesService getNonBlockingPrimeNumberService() {
        return (NonBlockingPrimesService) getContext().getBean("nonBlockingPrimesService");
    }

    protected ApplicationContext getContext() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        }
        return ctx;
    }

}