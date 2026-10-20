import java.util.concurrent.atomic.AtomicInteger;

class MyRunnableIncr implements Runnable {
    int count = 0;

    @Override
    public void run() {
        for(int i=0; i<10000; i++) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}

class MyRunnableIncrSolution implements Runnable {
    int count = 0;

    @Override
    public void run() {
        for(int i=0; i<1000000; i++) {
            synchronized(this) {
                count++;
            }
        }
    }

    public int getCount() {
        return count;
    }
}

class MyRunnableIncrSolutionTwo implements Runnable {
    AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        for(int i=0; i<10000000; i++) {
            count.getAndIncrement();
        }
    }

    public int getCount() {
        return count.get();
    }

}

public class Increment {
    public static void main(String[] args) throws Exception {
        MyRunnableIncrSolutionTwo task = new MyRunnableIncrSolutionTwo();
        Thread t1 = new Thread(task); 
        Thread t2 = new Thread(task);
        t2.start();
        t1.start();

        t1.join();
        t2.join();

        System.out.println(task.getCount());
        
    }
}
