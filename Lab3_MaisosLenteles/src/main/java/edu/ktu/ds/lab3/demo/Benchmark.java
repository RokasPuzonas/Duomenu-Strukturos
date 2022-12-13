package edu.ktu.ds.lab3.demo;

import edu.ktu.ds.lab3.utils.HashManager;
import edu.ktu.ds.lab3.utils.HashMap;
import edu.ktu.ds.lab3.utils.HashMapOa;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
public class Benchmark {

    @Param({"10000", "20000", "40000", "80000", "160000"})
    public int elementCount;

    //@Param({"division", "multiplication", "JCF"})
    public String hashTypeStr = "division";

    HashMap<Integer, Integer> hashMap;
    HashMapOa<Integer, Integer> hashMapOa;
    ArrayList<Integer> keys;


    @Setup(Level.Iteration)
    public void generateIdsAndCars() throws Exception {
        HashManager.HashType hashType = null;
        switch (hashTypeStr) {
            case "division":
                hashType = HashManager.HashType.DIVISION;
                break;
            case "multiplication":
                hashType = HashManager.HashType.MULTIPLICATION;
                break;
            case "JCF7":
                hashType = HashManager.HashType.JCF7;
                break;
            case "JCF":
                hashType = HashManager.HashType.JCF;
                break;
            default:
                throw new Exception("Invalid hash type");
        };
        HashMap.DEFAULT_HASH_TYPE = hashType;
        HashMapOa.DEFAULT_HASH_TYPE = hashType;

        Random r = new Random();
        keys = new ArrayList<>();
        int keyCount = 0;
        while (keyCount < elementCount) {
            int key = r.nextInt(elementCount*2);
            if (!keys.contains(key)) {
                keys.add(key);
                keyCount++;
            }
        }

        hashMap = new HashMap<>();
        hashMapOa = new HashMapOa<>();
        for (Integer key : keys) {
            int value = r.nextInt(elementCount);
            hashMap.put(key, value);
            hashMapOa.put(key, value);
        }
        Collections.shuffle(keys, r);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void hashMapRemove() {
        for (Integer key : keys) {
            hashMap.remove(key);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void hashMapOaRemove() {
        for (Integer key : keys) {
            hashMapOa.remove(key);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
