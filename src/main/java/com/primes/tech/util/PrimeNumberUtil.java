package com.primes.tech.util;

import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Utility class to define common operations on Prime Numbers
 * 
 * @author cyrille.kabadjeu
 * 
 */
public class PrimeNumberUtil {

	static Logger log = Logger.getLogger(PrimeNumberUtil.class);
	
	/**
	 * The minimum number range value for prime numbers
	 */
	public static final int MINIMUM_NUMBER = 1;
	
	/**
	 * The first prime number value
	 */
	public static final int FISRT_PRIME_NUMBER = 2;

	/**
	 * Test if the number is prime or not
	 * 
	 * @param number the to test
	 * 
	 * @return true is number is prime  otherwise false
	 */
	public static boolean isPrime(final int number) {
		
		//log.info("Checking if " + number + " is a prime number");
		boolean isPrime = (number > MINIMUM_NUMBER)
				&& (IntStream.rangeClosed(FISRT_PRIME_NUMBER, (int) Math.sqrt(number))
						.noneMatch(divisor  -> number % divisor  == 0));
		return isPrime;
	}

	/**
	 *  Collect the list of Prime Numbers between
	 *   MINIMUM_NUMBER = 1 and the number passed as argument
	 *   The function test each number sequentially 
	 * 
	 * @param maxNumber the max number of the range to search prime numbers
	 * 
	 * @return List of prime numbers between MINIMUM_NUMBER = 1 and maxNumber
	 */
	public static List<Integer> collectPrimeNumberSequential(final int maxNumber) {

		log.info("Sequential mode finding list of Prime Numbers from 0 - " + maxNumber);
        Instant start = Instant.now();
		int[] arrayResult = IntStream.rangeClosed(FISRT_PRIME_NUMBER, maxNumber)
									 .filter(PrimeNumberUtil::isPrime)
									 .toArray();
        Instant end = Instant.now();
		log.info( arrayResult.length + " Prime Number(s) found: ");
        log.info( "Sequential Execution took: " + Duration.between(start, end).getNano() + " Nanos Seconds");
        log.info( "Sequential Execution took: " + Duration.between(start, end).toMillis() + " Millis Seconds");

		return Ints.asList(arrayResult);

	}

	/**
	 *  Collect the list of Prime Numbers between
	 *   MINIMUM_NUMBER = 1 and the number passed as argument
	 *   The function test numbers in parallel.  
	 * 
	 * @param maxNumber the max number of the range to search prime numbers
	 * 
	 * @return List of prime numbers between MINIMUM_NUMBER = 1 and maxNumber
	 */
	public static List<Integer> collectPrimeNumberParallel(final int maxNumber) {

        log.info("Parallel mode finding list of Prime Numbers from 0 - " + maxNumber);
        Instant start = Instant.now();
        int[] arrayResult = IntStream.rangeClosed(FISRT_PRIME_NUMBER, maxNumber)
									 .parallel()
									 .filter(PrimeNumberUtil::isPrime)
									 .toArray();
        Instant end = Instant.now();
        log.info( arrayResult.length + " Prime Number(s) found: ");
        log.info( "Parallel Execution took: " + Duration.between(start, end).getNano() + " Nanos Seconds");
        log.info( "Parallel Execution took: " + Duration.between(start, end).toMillis() + " Millis Seconds");

		return Ints.asList(arrayResult);
	}

}
