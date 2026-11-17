package singleton_27;

import java.util.ArrayList;
import java.util.List;

class MySingleton {
    int x;
    List<String> list;
    private static MySingleton instance;

    private MySingleton() {
        x = 10;
        list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
    }

    public static MySingleton getInstance() {

        if(instance == null) {
            synchronized(MySingleton.class) {
                if(instance == null)
                    instance = new MySingleton();
            }
        }
        return instance;
    }

    public int getX() {
        return x;
    }

    public void setX() {
        x = 550;
    }

    public List<String> geList() {
        return list;
    }

}

public class DoubleCheckedSingletonImpl {
    public static void main(String[] args) {
        MySingleton instance = MySingleton.getInstance();
        instance.setX();
        System.out.println("Initialized singled, x: " + instance.getX());

        
        // instance = null;
        // MySingleton instance2 = MySingleton.getInstance();
        // System.out.println("Initialized singled, x: " + instance2.getX());

    }
}

