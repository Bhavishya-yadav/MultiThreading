package adderAccumulator_23;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;

class AdderTask implements Runnable{
    LongAdder  longAdder;
    AdderTask(LongAdder longAdder) {
        this.longAdder = longAdder;
    }

    @Override
    public void run() {
        longAdder.increment();
    }
}
public class Adder_23 {
    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(16);
        LongAdder  longAdder = new LongAdder();
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for(int i=0;i<100;i++){
//            executorService.submit(new AdderTask(longAdder));
            CompletableFuture<Void> future = CompletableFuture.runAsync(new AdderTask(longAdder));
            futureList.add(future);
        }
        CompletableFuture<Void> newFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        newFuture.join();
//        executorService.shutdown();
//        Thread.sleep(2000);
        System.out.println(longAdder.sum());
    }
}
