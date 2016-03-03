package com.primes.tech.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrimeNumberUtilTest {

	
	@Test
	public void shouldValidatePrimeNumbers(){
		
		assertTrue(PrimeNumberUtil.isPrime(2));
		assertTrue(PrimeNumberUtil.isPrime(3));
		assertFalse(PrimeNumberUtil.isPrime(4));
		assertTrue(PrimeNumberUtil.isPrime(5));
		assertFalse(PrimeNumberUtil.isPrime(6));
		assertTrue(PrimeNumberUtil.isPrime(7));

	}
	
	@Test
	public void shouldCollectPrimeNumbersSequentially(){
		
		List<Integer> primeNumberList = PrimeNumberUtil.collectPrimeNumberSequential(10);
		assertTrue(primeNumberList.size() == 4);
		assertTrue(primeNumberList.get(0)== 2);
		assertTrue(primeNumberList.get(1)== 3);
		assertTrue(primeNumberList.get(2)== 5);
		assertTrue(primeNumberList.get(3)== 7);

	}
	
	@Test
	public void shouldCollectPrimeNumbersInParallel(){
		
		List<Integer> primeNumberList = PrimeNumberUtil.collectPrimeNumberParallel(10);

		assertTrue(primeNumberList.size() == 4);
		assertTrue(primeNumberList.get(0)== 2);
		assertTrue(primeNumberList.get(1)== 3);
		assertTrue(primeNumberList.get(2)== 5);
		assertTrue(primeNumberList.get(3)== 7);

	}

    @Test
    public void benchmarkingDurationForSequential(){

        PrimeNumberUtil.collectPrimeNumberSequential(10);
        PrimeNumberUtil.collectPrimeNumberSequential(20);
        PrimeNumberUtil.collectPrimeNumberSequential(40);
        PrimeNumberUtil.collectPrimeNumberSequential(100);
        PrimeNumberUtil.collectPrimeNumberSequential(500);
        PrimeNumberUtil.collectPrimeNumberSequential(1000);
    }

    @Test
    public void benchmarkingDurationForParallel(){
        PrimeNumberUtil.collectPrimeNumberParallel(10);
        PrimeNumberUtil.collectPrimeNumberParallel(20);
        PrimeNumberUtil.collectPrimeNumberParallel(40);
        PrimeNumberUtil.collectPrimeNumberParallel(100);
        PrimeNumberUtil.collectPrimeNumberParallel(500);
        PrimeNumberUtil.collectPrimeNumberParallel(1000);

    }
}
