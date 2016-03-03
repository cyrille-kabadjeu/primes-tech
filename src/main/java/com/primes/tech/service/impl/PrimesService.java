package com.primes.tech.service.impl;

import com.primes.tech.cache.SimpleCacheService;
import com.primes.tech.domain.ExecutionModeEnum;
import com.primes.tech.domain.PrimeNumbers;
import com.primes.tech.util.PrimeNumberUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.TreeSet;

@Path("/primes/")
@Component("primesService")
public class PrimesService {

	static Logger log = Logger.getLogger(PrimesService.class);

	@Autowired
	private SimpleCacheService simpleCacheService = new SimpleCacheService();

	@GET
	@Path("{maxNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPrimeNumbers(@PathParam("maxNumber") int maxNumber,
			@DefaultValue("") @QueryParam("executionMode") String executionMode) {

		CacheControl cacheControl = createCacheControl();

		log.info("Getting the List of Prime Numbers");
		PrimeNumbers primeNumbers = new PrimeNumbers();
		List<Integer> primeNumberList = null;

		/*
		 * If no executionMode provided then: 
		 * 		- check the local cache, if null value return 
		 * 		- Process in Parallel mode
		 */
		if (executionMode == null || executionMode.trim().isEmpty()) {
			primeNumberList = simpleCacheService.getPrimeNumbers(maxNumber);
			if (primeNumberList == null) {
				primeNumberList = PrimeNumberUtil
						.collectPrimeNumberParallel(maxNumber);

			}
		} else {
			try {
				/*
				 * If executionMode provided then process without checking the cache
				 */
                ExecutionModeEnum executionModeEnum = ExecutionModeEnum.fromValue(executionMode);

                if (executionModeEnum.equals(ExecutionModeEnum.SEQUENTIAL)) {
					primeNumberList = PrimeNumberUtil
							.collectPrimeNumberSequential(maxNumber);
				} else if (executionModeEnum.equals(ExecutionModeEnum.PARALLEL)) {
					primeNumberList = PrimeNumberUtil
							.collectPrimeNumberParallel(maxNumber);

				}
			} catch (IllegalArgumentException illAgrEx) {
				throw new WebApplicationException(
						Response.status(Response.Status.BAD_REQUEST)
								.type(MediaType.TEXT_PLAIN)
								.cacheControl(cacheControl)
								.entity("Bad Request -  Unknow Value executionMode. "
										+ "Values Permitted are: sequential and parallel")
								.build());
			}

		}

		simpleCacheService.putPrimeNumbers(maxNumber, primeNumberList);
		primeNumbers.setInitial(maxNumber);
		primeNumbers.setPrimes(new TreeSet<>(primeNumberList));

		log.info("Returning the Set of Prime Numbers with - "
				+ primeNumbers.getPrimes().size() + " Numbers");
		return Response.status(Response.Status.OK).cacheControl(cacheControl)
				.entity(primeNumbers).build();
	}

	/**
	 * The cache control is set to avoid any response caching in the browser.
	 * 
	 * @return CacheControl
	 */
	private CacheControl createCacheControl() {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(-1);
		cacheControl.setNoCache(true);

		return cacheControl;
	}

}
