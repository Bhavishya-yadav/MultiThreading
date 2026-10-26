public class WaitReleaseDemo_10 {

    static final Object lockA = new Object();
    static final Object lockB = new Object();
    static boolean ready = false;                       // guarded by lockB
    static final long START = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {

        Thread waiter = new Thread(() -> {
            synchronized (lockA) {                                  // (1) acquire A
                log("acquired A");
                synchronized (lockB) {                              // (2) acquire B
                    log("acquired B, calling lockB.wait()");
                    while (!ready) {
                        try {
                            lockB.wait();                           // (3) releases B ONLY
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    log("back from wait(): holds A and B again");    // (6)
                }
                log("left B's block: released B, still holds A");
            }
            log("left A's block: released A");                      // (7)
        }, "waiter");

        Thread notifier = new Thread(() -> {
            synchronized (lockB) {                                  // (4) succeeds: B is free
                log("acquired B");
                ready = true;
                lockB.notify();
                log("called notify(), still holding B for 2s");
                sleepQuietly(2000);
            }                                                       // (5) releases B
            log("released B");
        }, "notifier");

        Thread needsA = new Thread(() -> {
            log("trying to acquire A");
            synchronized (lockA) {                                  // stuck until (7)
                log("acquired A");
            }
        }, "needsA");

        waiter.start();
        Thread.sleep(500);
        log("waiter state = " + waiter.getState());

        notifier.start();
        needsA.start();
        Thread.sleep(500);
        log("waiter state = " + waiter.getState());
        log("needsA state = " + needsA.getState());
    }

    static void log(String msg) {
        System.out.printf("%5d ms  %-11s %s%n",
                System.currentTimeMillis() - START,
                "[" + Thread.currentThread().getName() + "]", msg);
    }

    static void sleepQuietly(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}