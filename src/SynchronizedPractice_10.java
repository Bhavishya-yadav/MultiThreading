class SynchronizedHelper {
    Object monitor = new Object();
    boolean isSet = false;

    public void method1() {
        synchronized(monitor) {
            System.out.println("Method1: Started Method1");

            try {
                System.out.println("Method1: Waiting!!!!");
                if(!isSet) {
                    System.out.println("Method1: Before wait statement");
                    monitor.wait();
                    System.out.println("Method1: After wait statement");
                }
                Thread.sleep(10000);
                System.out.println("Method1: After wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void method3() {
        synchronized(monitor) {
            System.out.println("Method3: Started Method3");

            try {
                System.out.println("Method3: Waiting!!!!");
                if(!isSet) {
                    System.out.println("Method3: Before wait statement");
                    monitor.wait();
                    System.out.println("Method3: After wait statement");
                }

                System.out.println("Method3: After wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void method2() {
        synchronized(monitor) {
            System.out.println("Method2: Started Method2");
            try {
                System.out.println("Method2: Before notify");
                isSet = true;
                monitor.notifyAll();
                System.out.println("Method2: After notify");

            } catch (IllegalMonitorStateException e) {
                e.printStackTrace();
            }
        }
    }

}

public class SynchronizedPractice_10 {
 
    public static void main(String[] args) {
        SynchronizedHelper syncHelper = new SynchronizedHelper();
        Thread t1 = new Thread(() -> syncHelper.method1());
        Thread t3 = new Thread(() -> syncHelper.method3());

        Thread t2 = new Thread(() -> {
            syncHelper.method2();
        });

        t1.start();
        t3.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        t2.start();
    }
}
