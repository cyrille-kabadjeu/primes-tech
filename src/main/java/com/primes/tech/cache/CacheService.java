package com.primes.tech.cache;

import java.util.List;

/**
 * This is a common interface to provide caching service
 * 
 * @author cyrille.kabadjeu
 */
public interface CacheService {

	/**
	 *  Get the list of Prime Numbers if register in the cache
	 * 
	 * @param keyNumber
	 * 
	 * @return List of prime numbers
	 */
	public List<Integer> getPrimeNumbers(int keyNumber);
	
	/**
	 *  Put the list of Prime Numbers if it is not already in the cache
	 * 
	 * @param keyNumber
	 * 
	 * @param primeNumbersList List to put in the cache
	 * 
	 * @return List of prime numbers
	 */
	public void putPrimeNumbers(int keyNumber, List<Integer> primeNumbersList);

}
