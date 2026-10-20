class VolatileVisibility{
    volatile boolean flag = true;

    public void writerWork() {
        flag = false;
    }
    public void readerWork(){
        while (flag){
            //oper
        }
    }
}
public class VolatileVisibility_4 {
    public static void main(String[] args) throws InterruptedException{
        VolatileVisibility visibility = new VolatileVisibility();

        new Thread(() -> {
            visibility.readerWork();
        }).start();

        Thread.sleep(100);

        new Thread(() -> {
            visibility.writerWork();
        }).start();

        System.out.println();

    }
}
