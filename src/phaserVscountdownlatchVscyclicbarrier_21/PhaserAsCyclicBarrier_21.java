package phaserVscountdownlatchVscyclicbarrier_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

class CommonProcessorServiceUsingPhaser implements Runnable{
    Phaser phaser;
    int timeToSleepInMillis;

    CommonProcessorServiceUsingPhaser(Phaser phaser, int timeToSleepInMillis) {
        this.phaser = phaser;
        this.timeToSleepInMillis = timeToSleepInMillis;
    }


    @Override
    public void run() {
        System.out.println("Task started at: " + System.currentTimeMillis());
        try {
            Thread.sleep(timeToSleepInMillis);
            phaser.arriveAndAwaitAdvance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Task Completed at: " + System.currentTimeMillis());
        
    }

}

public class PhaserAsCyclicBarrier_21 {
        public static void main(String[] args) {
        
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Phaser phaser = new Phaser(3);

        executorService.submit(new CommonProcessorServiceUsingPhaser(phaser, 1000));
        executorService.submit(new CommonProcessorServiceUsingPhaser(phaser, 3000));
        executorService.submit(new CommonProcessorServiceUsingPhaser(phaser, 5000));
        
        System.out.println("Awaiting for completion!!!");

        executorService.shutdown();
        
    }
}
