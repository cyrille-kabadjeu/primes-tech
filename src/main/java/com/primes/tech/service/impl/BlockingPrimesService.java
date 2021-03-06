package com.primes.tech.service.impl;

import com.primes.tech.cache.FutureProcessorCacheService;
import com.primes.tech.domain.PrimeNumbers;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.TreeSet;


@Path("/blockingPrimes/")
@Component("blockingPrimesService")
public class BlockingPrimesService {

	static Logger log = Logger.getLogger(BlockingPrimesService.class);

	private FutureProcessorCacheService futureProcessorCacheService = new FutureProcessorCacheService();

	@GET
	@Path("{maxNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPrimeNumbers(@PathParam("maxNumber") int maxNumber) {

		CacheControl cacheControl = createCacheControl();

		log.info("Getting the List of Prime Numbers");
		PrimeNumbers primeNumbers = new PrimeNumbers();
		List<Integer> primeNumberList = futureProcessorCacheService.getPrimeNumbers(maxNumber);
		primeNumbers.setInitial(maxNumber);
		primeNumbers.setPrimes(new TreeSet<>(primeNumberList));

		log.info("Returning the Set of Prime Numbers with - "
				+ primeNumbers.getPrimes().size() + " Numbers");
		return Response.status(Response.Status.OK).cacheControl(cacheControl)
				.entity(primeNumbers).build();
	}

	private CacheControl createCacheControl() {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(-1);
		cacheControl.setNoCache(true);

		return cacheControl;
	}

}
