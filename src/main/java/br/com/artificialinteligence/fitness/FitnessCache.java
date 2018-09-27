//package br.com.artificialinteligence.fitness;
//
//import br.com.artificialinteligence.maze.model.FitnessMazePath;
//import com.google.common.cache.CacheBuilder;
//import com.google.common.cache.CacheLoader;
//import com.google.common.cache.LoadingCache;
//
//import java.math.BigInteger;
//import java.util.concurrent.ExecutionException;
//import java.util.function.Function;
//
//public final class FitnessCache {
//
//    private static final FitnessCache INSTANCE = new FitnessCache();
//
//    private final LoadingCache<String, Integer> cache;
//
//    private FitnessCache(final Function<String, Integer> fitness) {
//        cache = CacheBuilder
//                .newBuilder()
//                .build(CacheLoader.createFrom(genes -> fitness.apply(genes)));
//    }
//
//    public Integer get(final String genes) {
//        try {
//            return cache.get(genes);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
