package producer_consumer_18;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class MyBlockingQueue<E> {
    Queue<E> queue;
    int maxSize;
    ReentrantLock lock = new ReentrantLock(true);
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    public MyBlockingQueue(int maxSize) {
        queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    void put(E element) throws InterruptedException {
        lock.lock();
        while(queue.size() == maxSize){
            notFull.await();
        }
        queue.add(element);
        notEmpty.signalAll();
//        notEmpty.signal();
        lock.unlock();
    }

    E take() throws InterruptedException {
        lock.lock();
        while(queue.isEmpty()){
            notEmpty.await();
        }
        E element = queue.remove();
        notFull.signalAll();
//        notFull.signal();
        lock.unlock();
        return element;
    }

}
class ProducerConsumerWithCustomQueue {
    MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(2);

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
public class WithCustomBlockingQueueUsingAwaitSignal {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ProducerConsumerWithCustomQueue producerConsumer = new ProducerConsumerWithCustomQueue();
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
