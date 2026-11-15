package adderAccumulator_23;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;

class AccumulatorTask implements Runnable {
    LongAccumulator accumulator;

    AccumulatorTask(LongAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    @Override
    public void run() {
        accumulator.accumulate(1);
    }

}
public class Accumulator_23 {
    public static void main(String[] args) {
        // ExecutorService executorService = Executors.newFixedThreadPool(16);
        LongAccumulator accumulator = new LongAccumulator((x,y) -> x + y, 0); 
        // LongAccumulator accumulator = new LongAccumulator((x,y) -> Math.max(x, y), 0); 
        // LongAccumulator accumulator = new LongAccumulator((x,y) -> x * y, 1); 

        List<CompletableFuture<Void>> futuresList = new ArrayList<>();
        for(int i=0; i<120; i++) {
            // executorService.submit(new AccumulatorTask(accumulator));
            CompletableFuture<Void> future = CompletableFuture.runAsync(new AccumulatorTask(accumulator));
            futuresList.add(future);
        }
        // executorService.shutdown();
        // try {
        //     Thread.sleep(2000);
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0]));

        combinedFuture.join();

        System.out.println("Final value: " + accumulator.get());
    }

}
