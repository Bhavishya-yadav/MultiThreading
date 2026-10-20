import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

class MyRunnableDateFormat implements Runnable {

    @Override
    public void run(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ThreadLocalPractice.getFormattedDate(new Date());
        // System.out.println();
    }
}

public class ThreadLocalPractice {
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    
    public static void main(String[] args) throws Exception {
        MyRunnableDateFormat task = new MyRunnableDateFormat();
        for(int i=0; i<100; i++) {
            Thread th = new Thread(task);
            th.start();
        }
        
    }

    public static String getFormattedDate(Date date) {
        
        return df.format(date);
    }
}
