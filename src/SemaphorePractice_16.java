import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class SemaphorePractice {
    
    public void beforeDataProcess() {
        System.out.println("Before data process : " +  Thread.currentThread().getName());
    }
    public void processData() {
        System.out.println("I have acquired semaphore : " + Thread.currentThread().getName());
        try{
            Thread.sleep(15000);
        } catch(Exception e) {

        }
    }
}
public class SemaphorePractice_16 {

    public static void main(String[] args) {
        SemaphorePractice obj = new SemaphorePractice();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Semaphore semaphore = new Semaphore(3);

        for(int i=0; i<5; i++) {
            executorService.submit(() -> {
                obj.beforeDataProcess();
                try {
                    semaphore.acquire(2);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                obj.processData();
                semaphore.release(2);
                System.out.println("I have released semaphore : " + Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
    }
}
