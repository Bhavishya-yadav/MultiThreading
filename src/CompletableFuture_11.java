import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
class Order {
    String itemName;
    Boolean enriched = false;

    Order(String itemName) {
        this.itemName = itemName;
    }

    public void setEnriched(Boolean enriched) {
        this.enriched = enriched;
    }

    public String getItemName() {
        return itemName;
    }

    public Boolean getEnriched() {
        return enriched;
    }
    
}

class OrderManager {
    void logger(String funcName) {
        System.out.println("In thread: " + Thread.currentThread().getName() + "func: " + funcName);
    }
    Order createOrder() {
        logger("createOrder");
        return new Order("Order" + (int)(100*Math.random()));

    }

    Order enrich(Order order) {
        logger("enrich");
        order.setEnriched(true);
        return order;
    }

    void placeOrder(Order order) {
        logger("placeOrder");
        System.out.println("Order placed : " + order.getItemName() + " : " + order.getEnriched());
    }
}

public class CompletableFuture_11 {


    public static void main(String[] args) throws InterruptedException {
        OrderManager orderManager = new OrderManager();
        CompletableFuture.supplyAsync(() -> orderManager.createOrder())
                        .thenApply(order -> orderManager.enrich(order))
                        .thenAccept(order -> orderManager.placeOrder(order))
                        .join();
        
        // ExecutorService cpu = Executors.newFixedThreadPool(10);
        // ExecutorService io = Executors.newFixedThreadPool(10);

        AtomicInteger cpuCounter = new AtomicInteger(1);
        AtomicInteger ioCounter = new AtomicInteger(1);

        ExecutorService cpu = Executors.newFixedThreadPool(10, task ->
            new Thread(task, "cpu-thread-" + cpuCounter.getAndIncrement())
        );

        ExecutorService io = Executors.newFixedThreadPool(10, task ->
            new Thread(task, "io-thread-" + ioCounter.getAndIncrement())
        );

        CompletableFuture.supplyAsync(() -> orderManager.createOrder())
                        .thenApplyAsync(order -> orderManager.enrich(order), cpu)
                        .thenAcceptAsync(order -> orderManager.placeOrder(order), io)
                        .join();
        
        cpu.shutdown();
        io.shutdown();

    }
}
