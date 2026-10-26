import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("tera baap hu mc");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("In interrupted ex");
            e.printStackTrace();
        }
        System.out.println("bhakkkk");
        return new Random().nextInt(100);
        
    }

}

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> future = executorService.submit(new MyCallable());
        try {
            System.out.println(future.isDone());
            Thread.sleep(2000);
            Thread.currentThread().wait();
            future.cancel(true);
            System.out.println(future.isCancelled());
            // System.out.println(future.get());
            System.out.println(future.isDone());
        } catch (Exception e) {
            System.out.println("In exception");
            e.printStackTrace();
        }

        executorService.shutdown();

    }
}
