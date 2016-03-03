package com.primes.tech.cache;

import com.primes.tech.util.PrimeNumberUtil;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureProcessorCacheService {

    private final ConcurrentMap<Integer, Future<List<Integer>>> cache = new ConcurrentHashMap<>();

    public List<Integer> getPrimeNumbers(int keyNumber) {
        while (true) {
            Future<List<Integer>> future = cache.get(keyNumber);
            if (future == null) {
                Callable<List<Integer>> processor = new Callable<List<Integer>>() {
                    public List<Integer> call() throws InterruptedException {
                        return PrimeNumberUtil.collectPrimeNumberParallel(keyNumber);
                    }
                };
                FutureTask<List<Integer>> futureTask = new FutureTask<>(processor);
                future = cache.putIfAbsent(keyNumber, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException ce) {
                cache.remove(keyNumber, future);
            } catch (ExecutionException ee) {
                throw new RuntimeException(ee);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }

    }

}
