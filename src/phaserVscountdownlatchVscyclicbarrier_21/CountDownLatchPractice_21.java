package phaserVscountdownlatchVscyclicbarrier_21;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class DependentService implements  Runnable{
    CountDownLatch latch;
    int timeToSleepInMillis;
    public DependentService(CountDownLatch latch, int timeToSleepInMillis) {
        this.latch = latch;
        this.timeToSleepInMillis = timeToSleepInMillis;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeToSleepInMillis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println("Completed task at: "+  System.currentTimeMillis());
    }
}

public class CountDownLatchPractice_21 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CountDownLatch latch = new CountDownLatch(3);

        executorService.submit(new DependentService(latch, 1000));
        executorService.submit(new DependentService(latch, 2000));
        executorService.submit(new DependentService(latch, 3000));
        System.out.println("Awaiting for completion!!!");
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Completed all the 3 task at: "+  System.currentTimeMillis());
        System.out.println("Await completed!!!!");
        executorService.shutdown();
        
    }
}
