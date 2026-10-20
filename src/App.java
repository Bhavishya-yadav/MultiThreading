// public class App {
//     // static boolean flag = true;
//     public static void main(String[] args) throws Exception {
//         volatile boolean flag = true;
//         Thread t1 = new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 while (flag) {
//                     System.out.println("In T1");
//                 }
//             }
//         });

//         t1.start();

//         flag = false;
//         t1.join();
        
//     }
// }

class MyRunnable implements Runnable {
    boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            System.out.println("In T1");
        }
    }
}


public class App {
    public static void main(String[] args) throws Exception {
        MyRunnable task = new MyRunnable();
        Thread t1 = new Thread(task);
        
        t1.start();
        Thread.sleep(1000);
        task.flag = false;
        t1.setDaemon(true);
        t1.join();
    }
}
