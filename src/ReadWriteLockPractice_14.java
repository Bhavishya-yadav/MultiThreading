import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

class ReadWritePractice {
    List<Integer> list = new ArrayList<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReadLock readLock = lock.readLock();
    WriteLock writeLock = lock.writeLock();
    static final long START = System.currentTimeMillis();


    void read() {
        readLock.lock();
        log("Entered in read lock");
        log(list.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        readLock.unlock();
        log("Exitted from read log");
    }

    void write() {
        writeLock.lock();
        log("Entered in write lock");
        list.add((int)(Math.random()*100));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writeLock.unlock();
        log("Exitted from write log");
    }
    static void log(String msg) {
        System.out.printf("%5d ms  %-11s %s%n",
                System.currentTimeMillis() - START,
                "[" + Thread.currentThread().getName() + "]", msg);
    }
}

public class ReadWriteLockPractice_14 {
    public static void main(String[] args) throws Exception{
        ReadWritePractice readWritePractice = new ReadWritePractice();

        Thread t1 = new Thread(() -> readWritePractice.write(), "t1");
        t1.start();

        Thread t2 = new Thread(() -> readWritePractice.read(), "t2");
        Thread t3 = new Thread(() -> readWritePractice.read(), "t3");
        Thread t4 = new Thread(() -> readWritePractice.read(), "t4");

        t2.start();
        t3.start();
        t4.start();
        
        Thread t5 = new Thread(() -> readWritePractice.write(), "t5");
        t5.start();
        Thread.sleep(100);
        Thread t6 = new Thread(() -> readWritePractice.read(), "t6");
        t6.start();

    }
}
