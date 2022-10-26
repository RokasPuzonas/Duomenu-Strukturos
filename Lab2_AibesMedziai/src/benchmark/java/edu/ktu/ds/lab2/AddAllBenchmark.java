package edu.ktu.ds.lab2;

import edu.ktu.ds.lab2.utils.AvlSet;
import edu.ktu.ds.lab2.utils.BstSet;
import edu.ktu.ds.lab2.utils.Set;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

// https://github.com/dastruktu/benchmark/blob/master/src/main/java/edu/ktu/ds/benchmark/JmhBenchmark.java
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
public class AddAllBenchmark {
    /*
    Benchmark                     (listSize)  Mode  Cnt         Score        Error  Units
    AddAllBenchmark.avlSetAddAll        4000  avgt    5     12274.192 ±   1052.512  us/op
    AddAllBenchmark.avlSetAddAll        8000  avgt    5     53572.221 ±   4584.040  us/op
    AddAllBenchmark.avlSetAddAll       16000  avgt    5    199682.597 ±  19894.433  us/op
    AddAllBenchmark.avlSetAddAll       32000  avgt    5    787332.020 ±  51936.079  us/op
    AddAllBenchmark.avlSetAddAll       64000  avgt    5   2572856.880 ±  71156.461  us/op
    AddAllBenchmark.avlSetAddAll      128000  avgt    5   7860938.300 ± 771355.219  us/op
    AddAllBenchmark.bstSetAddAll        4000  avgt    5    499094.557 ± 444521.742  us/op
    AddAllBenchmark.bstSetAddAll        8000  avgt    5   3312173.700 ± 200121.728  us/op
    AddAllBenchmark.bstSetAddAll       16000  avgt    5  25703766.940 ± 551969.795  us/op
     */

    public int chunkSize = 200;

    @Param({"4000", "8000", "16000", "32000", "64000", "128000"})
    public int listSize;

    ArrayList<Set<Integer>> chunks;
    BstSet<Integer> bstSet;
    AvlSet<Integer> avlSet;

    @Setup(Level.Trial)
    public void generateChunks() {
        chunks = new ArrayList<>();
        chunks.ensureCapacity(listSize/chunkSize);
        Random r = new Random();
        for (int i = 0; i < listSize/chunkSize; i++) {
            Set<Integer> chunk = new AvlSet<>();
            for (int j = 0; j < listSize; j++) {
                chunk.add(r.nextInt(100_000));
            }
            chunks.add(chunk);
        }
    }

    @Setup(Level.Iteration)
    public void createSets() {
        bstSet = new BstSet<>();
        avlSet = new AvlSet<>();
    }

    @Benchmark
    public void bstSetAddAll(Blackhole bh) {
        for (Set<Integer> chunk : chunks) {
            bstSet.addAll(chunk);
        }
    }

    @Benchmark
    public void avlSetAddAll(Blackhole bh) {
        for (Set<Integer> chunk : chunks) {
            avlSet.addAll(chunk);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AddAllBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
