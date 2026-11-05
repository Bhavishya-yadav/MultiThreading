import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockPractice{
    Lock lock = new ReentrantLock();

    void fn() throws Exception{
        lock.lock();

        Thread.sleep(2000);

        lock.unlock();
    }

//    synchronized void fn() throws Exception{
//        Thread.sleep(2000);
//    }
}
public class LockAndTryLock_13 {

    public static void main(String[] args) {

        try {
            LockPractice lockPractice = new LockPractice();
            Thread t1 = new Thread(() -> {
                try {
                    lockPractice.fn();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            t1.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Thread t2 = new Thread(() -> {
                try {
                    lockPractice.fn();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            t2.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("T1 state:" + t1.getState());
            System.out.println("T2 state:" + t2.getState());
            t1.join();
            t2.join();
        }
        catch (Exception e){

        }

    }
}
