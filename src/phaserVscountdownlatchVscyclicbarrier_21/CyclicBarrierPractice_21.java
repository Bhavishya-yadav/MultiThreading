package phaserVscountdownlatchVscyclicbarrier_21;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CommonProcessorService implements Runnable{
    CyclicBarrier barrier;
    int timeToSleepInMillis;

    CommonProcessorService(CyclicBarrier barrier, int timeToSleepInMillis) {
        this.barrier = barrier;
        this.timeToSleepInMillis = timeToSleepInMillis;
    }

    @Override
    public void run() {
        System.out.println("Task started at: " + System.currentTimeMillis());
        try {
            Thread.sleep(timeToSleepInMillis);
            barrier.await();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Task Completed at: " + System.currentTimeMillis());
        
    }

}

public class CyclicBarrierPractice_21 {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(new CommonProcessorService(barrier, 2000));
        executorService.submit(new CommonProcessorService(barrier, 5000));
        executorService.submit(new CommonProcessorService(barrier, 8000));
        System.out.println("Awaiting for completion!!!");

        executorService.shutdown();
        
    }
}
