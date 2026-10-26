import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockHelper {
    Lock lock = new ReentrantLock();
    Condition conditionMet = lock.newCondition();

    public void method1() {
        System.out.println("Method1: Before acquiring lock");
        lock.lock();
        System.out.println("Method1: After acquiring lock");

        try {
            System.out.println("Method1: Waiting!!!!");
            conditionMet.await();
            System.out.println("Method1: Condition is met in method1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.unlock();
    }

    public void method2() {
        System.out.println("Method2: Before acquiring lock");
        lock.lock();
        System.out.println("Method2: After acquiring lock");
        try {
            System.out.println("Method2: Before signal");
            conditionMet.signal();
            System.out.println("Method2: After signal");

        } catch (IllegalMonitorStateException e) {
            e.printStackTrace();
        }

        lock.unlock();
    }

}

public class LockPractice_10 {
 
    public static void main(String[] args) {
        LockHelper lockHelper = new LockHelper();
        Thread t1 = new Thread(() -> lockHelper.method1());

        Thread t2 = new Thread(() -> {
            lockHelper.method2();
        });

        t1.start();
        try {
            t1.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        t2.start();
    }
}
