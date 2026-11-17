package singleton_27;

class MySingleton2 {

    static class Holder {
        public static final MySingleton2 instance = new MySingleton2();
    }

    int x;

    private MySingleton2() {
        x = 19;
    }

    public static MySingleton2 getInstance() {
        return Holder.instance;
    }

    public int getX() {
        return x;
    }

}
public class SingletonImplUsingHolderClass {
    public static void main(String[] args) {
        MySingleton2 instance = MySingleton2.getInstance();
        System.out.println("Initialized singled, x: " + instance.getX());
    }
}
