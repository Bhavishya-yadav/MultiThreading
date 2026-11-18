package singleton_27;

enum MySingleton3 {
    INSTANCE;
    int x;

    MySingleton3() {
        x = 19;
    }


    public int getX() {
        return x;
    }

}

public class EnumSingletonImpl {
    public static void main(String[] args) {
        MySingleton3 instance = MySingleton3.INSTANCE;
        System.out.println("Initialized singled, x: " + instance.getX());
    }
}
