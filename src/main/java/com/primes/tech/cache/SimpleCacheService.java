package com.primes.tech.cache;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component("simpleCacheService")
public class SimpleCacheService implements CacheService {

	static Logger log = Logger.getLogger(SimpleCacheService.class);

	private final ConcurrentMap<Integer, List<Integer>> cache = new ConcurrentHashMap<>();

	@Override
	public List<Integer> getPrimeNumbers(int keyNumber) {
		log.info("Fetching Prime Numbers Result from the cache: Key is - " + keyNumber);

		return cache.get(keyNumber);
	}


	@Override
	public void putPrimeNumbers(int keyNumber, List<Integer> primeNumbersList) {
		log.info("Putting Prime Numbers Result in the cache: Key is - " + keyNumber);

		cache.putIfAbsent(keyNumber, primeNumbersList);
	}

}
