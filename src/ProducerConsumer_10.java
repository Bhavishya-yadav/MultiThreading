import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProduceConsumer{
    private Lock lock = new ReentrantLock();
    private Condition added = lock.newCondition();
    private Condition removed = lock.newCondition();
    private int count = 0;


    public void produce() throws Exception{
        lock.lock();
        try {
            if (count == Integer.MAX_VALUE) {
                removed.await();
            }
            count++;
            System.out.println("Produced item, curr count: " + count);
            added.signal();
        }
        finally {
            lock.unlock();
        }
    }
    public void consume() throws Exception {
        lock.lock();
        try {
            if (count == 0) {
                added.await();
            }
            count--;
            System.out.println("Consumed item, curr count: " + count);
            removed.signal();
        }
        finally {
            lock.unlock();
        }
    }
}
public class ProducerConsumer_10 {
    public static void main(String[] args) throws Exception {
        ProduceConsumer task = new ProduceConsumer();
        Thread producer = new Thread(() -> {
            try {
                for(int i=0;i<6;i++) {
                    task.produce();
                    Thread.sleep(200);
                }
                Thread.sleep(5000);
                for(int i=0;i<3;i++) {
                    task.produce();
                }
            }
            catch (Exception e){
                System.out.println("Fat gaya: " + e);
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for(int i=0;i<5;i++) {
                    task.consume();
                    Thread.sleep(500);
                }
            }
            catch (Exception e){
                System.out.println("Fat gaya: " + e);
            }
        });

        consumer.start();
//        Thread.sleep(2000);
        producer.start();
    }
}
