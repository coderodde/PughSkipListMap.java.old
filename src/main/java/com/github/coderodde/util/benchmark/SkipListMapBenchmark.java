package com.github.coderodde.util.benchmark;

import com.github.coderodde.util.SkipListMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class SkipListMapBenchmark {
    
    private static final int MAP_SIZE = 10_000;
    
    public static void main(String[] args) {
        long seed = parseSeed(args);
        double coinProbability = parseCoinProbability(args);
        coinProbability = 0.5;
        seed = 1709125105008L;
        System.out.printf("Seed = %d.\n", seed);
        System.out.printf("Coin probability = %f.\n", coinProbability);
        System.out.println();
        
        Random random = new Random(seed);
        
        List<Integer> list = new ArrayList<>(MAP_SIZE);
        
        for (int i = 0; i < MAP_SIZE; i++) {
            list.add(i);
        }
        
        Collections.shuffle(list, random);
        
        SkipListMap<Integer, Long> skipListMap = 
                new SkipListMap<>(coinProbability, random);
        
        ConcurrentSkipListMap<Integer, Long> concurrentSkipListMap = 
                new ConcurrentSkipListMap<>();
        
        TreeMap<Integer, Long> treeMap = new TreeMap<>();
        
        long totalSkipListMap = 0L;
        long totalConcurrentSkipListMap = 0L;
        long totalTreeMap = 0L;
        
        //// put()
        long start = System.currentTimeMillis();
        
        for (Integer key : list) {
            skipListMap.put(key, Long.valueOf(key));
        }
        
        long end = System.currentTimeMillis();
        
        totalSkipListMap += end - start;
        
        System.out.printf("SkipListMap.put in %d milliseconds.\n",
                          end - start);
        
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            concurrentSkipListMap.put(key, Long.valueOf(key));
        }
        
        end = System.currentTimeMillis();
        
        totalConcurrentSkipListMap += end - start;
        
        System.out.printf("ConcurrentSkipListMap.put in %d milliseconds.\n",
                          end - start);
        
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            treeMap.put(key, Long.valueOf(key));
        }
        
        end = System.currentTimeMillis();
        
        totalTreeMap += end - start;
        
        System.out.printf("TreeMap.put in %d milliseconds.\n",
                          end - start);
        
        Collections.shuffle(list, random);
        
        System.out.println();
        
        //// get()
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            skipListMap.get(key);
        }
        
        end = System.currentTimeMillis();
        
        totalSkipListMap += end - start;
        
        System.out.printf("SkipListMap.get %d milliseconds.\n",
                          end - start);
        
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            concurrentSkipListMap.get(key);
        }
        
        end = System.currentTimeMillis();
        
        totalConcurrentSkipListMap += end - start;
        
        System.out.printf("ConcurrentSkipListMap.get %d milliseconds.\n",
                          end - start);
        
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            treeMap.get(key);
        }
        
        end = System.currentTimeMillis();
        
        totalTreeMap += end - start;
        
        System.out.printf("TreeMap.get %d milliseconds.\n",
                          end - start);
        
        Collections.shuffle(list, random);
        
        System.out.println();
        
        //// remove()
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            skipListMap.remove(key);
        }
        
        end = System.currentTimeMillis();
        
        totalSkipListMap += end - start;
        
        System.out.printf("SkipListMap.remove %d milliseconds.\n",
                          end - start);
        
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            concurrentSkipListMap.remove(key);
        }
        
        end = System.currentTimeMillis();
        
        totalConcurrentSkipListMap += end - start;
        
        System.out.printf("ConcurrrentSkipListMap.remove %d milliseconds.\n",
                          end - start);
        
        start = System.currentTimeMillis();
        
        for (Integer key : list) {
            treeMap.remove(key);
        }
        
        end = System.currentTimeMillis();
        
        totalTreeMap += end - start;
        
        System.out.printf("TreeMap.remove %d milliseconds.\n",
                          end - start);
        
        System.out.println();
        
        System.out.printf("SkipListMap total: %d milliseconds.\n",
                          totalSkipListMap);
        
        System.out.printf("ConcurrentSkipListMap total: %d milliseconds.\n",
                          totalConcurrentSkipListMap);
        
        System.out.printf("TreeMap total: %d milliseconds.\n",
                          totalTreeMap);
    }
    
    private static long parseSeed(String[] args) {
        if (args.length < 1) {
            return System.currentTimeMillis();
        }
        
        try {
            return Long.parseLong(args[0]);
        } catch (NumberFormatException ex) {
            return System.currentTimeMillis();
        }
    }
    
    private static double parseCoinProbability(String[] args) {
        if (args.length < 2) {
            return SkipListMap.DEFAULT_COIN_PROBABILITY;
        }
        
        try {
            return Double.parseDouble(args[0]);
        } catch (NumberFormatException ex) {
            return SkipListMap.DEFAULT_COIN_PROBABILITY;
        }
    }
}
