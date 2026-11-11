package producer_consumer_18;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ProducerConsumer {
    ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    void produce() throws InterruptedException {
        int number = (int)(Math.random()*100);
        queue.put(number);
        System.out.println("Produced: " + number);
    }

    void consume() throws InterruptedException {
        Integer number = queue.take();
        System.out.println("Consumed: " + number);

    }
}
public class WithArrayBlockingQueue {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ProducerConsumer producerConsumer = new ProducerConsumer();
        for(int i=0;i<10;i++){
            executorService.submit(() -> {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        for(int i=0;i<10;i++){
            executorService.submit(() -> {
                try {
                    producerConsumer.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Thread.sleep(5000);
        }





    }
}
