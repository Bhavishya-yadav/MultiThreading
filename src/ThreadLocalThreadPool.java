import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// class ThreadSafeFormatter {
//     static ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
//         @Override 
//         protected SimpleDateFormat initialValue() {
//             System.out.println("Hii Iam here!!!!");
//             return new SimpleDateFormat("yyyy-MM-dd");
//         }

//         @Override 
//         public SimpleDateFormat get() {
//             return super.get();
//         }
//     };
// }

class ThreadSafeFormatter {
    static ThreadLocal<SimpleDateFormat> dateFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
}

class MyRunnableExecutorDateFormat implements Runnable {

    @Override
    public void run(){
        String res = ThreadLocalThreadPool.getFormattedDate(new Date());
        System.out.println(res);
    }
}

public class ThreadLocalThreadPool {
    
    public static void main(String[] args) throws Exception {
        MyRunnableExecutorDateFormat task = new MyRunnableExecutorDateFormat();
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        for(int i=0; i<50; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
        
    }

    public static String getFormattedDate(Date date) {
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df = ThreadSafeFormatter.dateFormatter.get(); 
        return df.format(date);
    }
}

