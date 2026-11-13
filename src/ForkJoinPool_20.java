import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

class Fibonnaci extends RecursiveTask<Integer> {
    int n;
    Map<String,Integer> threads;
    public Fibonnaci(int n, Map<String,Integer>threads) {
        this.n = n;
        this.threads = threads;
    }

    @Override
    protected Integer compute() {
//        System.out.println("n value: " + n);
        threads.putIfAbsent(Thread.currentThread().getName(),0);
        threads.put(Thread.currentThread().getName(), threads.get(Thread.currentThread().getName()) + 1);
        System.out.println();
        if(n <= 1) return n;

        Fibonnaci f1 = new Fibonnaci(n-1,threads);
        f1.fork();
        Fibonnaci f2 = new Fibonnaci(n-2,threads);
        f2.fork();

        return f1.join() + f2.join();
    }
}
public class ForkJoinPool_20 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Map<String,Integer> threads = Collections.synchronizedMap(new HashMap<>());
        Fibonnaci fibonnaci = new Fibonnaci(5,threads);
        fibonnaci.fork();
        System.out.println(fibonnaci.get());
        System.out.println(threads);
    }
}
