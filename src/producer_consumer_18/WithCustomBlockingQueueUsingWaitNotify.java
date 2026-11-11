package producer_consumer_18;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class MyBlockingQueueNew<E> {
    Queue<E> queue;
    int maxSize;

    public MyBlockingQueueNew(int maxSize) {
        queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    synchronized void put(E element) throws InterruptedException {
        while(queue.size() == maxSize){
            this.wait();
        }
        queue.add(element);
        this.notifyAll();
    }

    synchronized E take() throws InterruptedException {
        while(queue.isEmpty()){
            this.wait();
        }
        E element = queue.remove();
        this.notifyAll();
        return element;
    }

}
class ProducerConsumerWithCustomQueueUsingWaitNotify {
    MyBlockingQueueNew<Integer> queue = new MyBlockingQueueNew<>(2);

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
public class WithCustomBlockingQueueUsingWaitNotify {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ProducerConsumerWithCustomQueueUsingWaitNotify producerConsumer = new ProducerConsumerWithCustomQueueUsingWaitNotify();
        for(int i=0;i<2;i++){
            executorService.submit(() -> {
                try {
                    producerConsumer.consume();
                }
                catch (Exception e) {
                    System.out.println("Consume error: "+ e);
                    throw new RuntimeException(e);
                }

            });
        }
        for(int i=0;i<10;i++){
            executorService.submit(() -> {
                try {
                    producerConsumer.produce();
                } catch (Exception e) {
                    System.out.println("Produce error: "+ e);
                    throw new RuntimeException(e);
                }

            });
            Thread.sleep(2000);
        }
        executorService.shutdown();
    }
}
