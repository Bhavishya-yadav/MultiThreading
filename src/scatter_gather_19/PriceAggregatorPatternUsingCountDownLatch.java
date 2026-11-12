package scatter_gather_19;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class CountdownLatchImpl {
    Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
    CountDownLatch latch = new CountDownLatch(3);

    public Set<Integer> aggregatePrices() {
        Client client1 = new Client("amazon.com", "1234", prices, latch);
        Client client2 = new Client("flipkart.com", "1234", prices, latch);
        Client client3 = new Client("snapdeal.com", "1234", prices, latch);

        Thread t1 = new Thread(client1);
        Thread t2 = new Thread(client2);
        Thread t3 = new Thread(client3);
        t1.start();
        t2.start();
        t3.start();

        
        try {
            // latch.await();
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(prices);
        return prices;
    }
    

}

class Client implements Runnable{
    String url;
    String productId;
    Set<Integer> prices;
    CountDownLatch latch;

    Client(String url, String productId, Set<Integer> prices, CountDownLatch latch) {
        this.url = url;
        this.productId = productId;
        this.prices = prices;
        this.latch = latch;
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
        latch.countDown();
    }
}



public class PriceAggregatorPatternUsingCountDownLatch {
    public static void main(String[] args) {
        CountdownLatchImpl countdownLatchImpl = new CountdownLatchImpl();
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