package scatter_gather_19;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class CompletableFutureImpl {
    Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());

    public Set<Integer> aggregatePrices() {
        Client2 client1 = new Client2("amazon.com", "1234", prices);
        Client2 client2 = new Client2("flipkart.com", "1234", prices);
        Client2 client3 = new Client2("snapdeal.com", "1234", prices);

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(client1);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(client2);
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(client3);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
        try {
            combinedFuture.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("e: " + e);
            e.printStackTrace();
        }
        
        
        // combinedFuture.join();
        System.out.println(prices);
        return prices;
    }
    

}

class Client2 implements Runnable{
    String url;
    String productId;
    Set<Integer> prices;

    Client2(String url, String productId, Set<Integer> prices) {
        this.url = url;
        this.productId = productId;
        this.prices = prices;
    }

    @Override
    public void run() {
        System.out.println("Fetching price for " + productId + " from url " + url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        prices.add((int)(Math.random()*1000));
    }
}

public class PriceAggregatorUsingCompletableFuture {
        public static void main(String[] args) {
        CompletableFutureImpl countdownLatchImpl = new CompletableFutureImpl();
        Set<Integer> prices = countdownLatchImpl.aggregatePrices();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("prices in main " + prices);
    }
}
