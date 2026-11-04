import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplyAsyncExample_11 {

    static void log(String message) {
        System.out.println(
            Thread.currentThread().getName() + " : " + message
        );
    }

    public static void main(String[] args) {
        ExecutorService executor =
            Executors.newSingleThreadExecutor(
                task -> new Thread(task, "worker")
            );

        try {
            // Already completed with value 100.
            CompletableFuture<Integer> source =
                CompletableFuture.completedFuture(100);

            // 1. thenApply: transforms 100 into 200.
            // Here, main executes it because source is already complete.
            CompletableFuture<Integer> doubled =
                source.thenApply(value -> {
                    log("thenApply received " + value);
                    return value * 2;
                });

            // 2. thenAccept: consumes 200 without returning a value.
            // Here, main executes it because doubled is already complete.
            CompletableFuture<Void> printed =
                doubled.thenAccept(value -> {
                    log("thenAccept received " + value);
                });

            // 3. thenApplyAsync: transforms 100 into 300 on worker.
            CompletableFuture<Integer> tripled =
                source.thenApplyAsync(value -> {
                    log("thenApplyAsync received " + value);
                    return value * 3;
                }, executor);

            // 4. thenAcceptAsync: consumes 300 on worker.
            CompletableFuture<Void> finished =
                tripled.thenAcceptAsync(value -> {
                    log("thenAcceptAsync received " + value);
                }, executor);

            log("Main can continue doing other work");

            // Main waits here until the async chain finishes.
            finished.join();

        } finally {
            executor.shutdown();
        }
    }
}