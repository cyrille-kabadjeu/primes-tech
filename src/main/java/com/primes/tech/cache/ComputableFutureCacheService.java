package com.primes.tech.cache;

import com.primes.tech.util.PrimeNumberUtil;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ComputableFutureCacheService {

    private final ConcurrentMap<Integer, CompletableFuture<Set<Integer>>> cache = new ConcurrentHashMap<>();

    public Set<Integer> getPrimeNumbers(int keyNumber) throws ExecutionException, InterruptedException {
        while (true) {
            CompletableFuture<Set<Integer>> completableFuture = cache.get(keyNumber);
            if (completableFuture == null) {
                CompletableFuture<Set<Integer>> completableFutureTask =
                        completableFuture.supplyAsync(() -> PrimeNumberUtil.collectPrimeNumberParallel(keyNumber))
                                         .thenApply(TreeSet::new);
                completableFuture = cache.putIfAbsent(keyNumber, completableFutureTask);
                if (completableFuture == null) {
                    return completableFutureTask.join();
                }
            }
            if (completableFuture.isCompletedExceptionally()) {
                throw new RuntimeException("Failed");
            }

            try {
                return completableFuture.get(10, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                completableFuture.completedFuture(Collections.<Integer>emptySet());
            }
        }

    }

}
