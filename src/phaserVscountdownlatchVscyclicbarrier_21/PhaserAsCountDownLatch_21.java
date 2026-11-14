package phaserVscountdownlatchVscyclicbarrier_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

class DependentServiceUsingPhaser implements  Runnable {
    Phaser phaser;
    int timeToSleepInMillis;
    public DependentServiceUsingPhaser(Phaser phaser, int timeToSleepInMillis) {
        this.phaser = phaser;
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
        
        System.out.println("Completed task at: "+  System.currentTimeMillis());
        phaser.arrive();
    }
}

public class PhaserAsCountDownLatch_21 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Phaser phaser = new Phaser(3);

        executorService.submit(new DependentServiceUsingPhaser(phaser, 1000));
        executorService.submit(new DependentServiceUsingPhaser(phaser, 3000));
        executorService.submit(new DependentServiceUsingPhaser(phaser, 5000));
        System.out.println("Awaiting for completion!!!");
        try {
            phaser.awaitAdvance(0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Completed all the 3 task at: "+  System.currentTimeMillis());
        System.out.println("Await completed!!!!");
        executorService.shutdown();
    }
}
