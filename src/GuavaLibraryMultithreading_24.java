import com.google.common.util.concurrent.Striped;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

class Bag {
    int blueCandies;
    String id;
    int bagNumber;

    public Bag(int bagNumber) {
        id = UUID.randomUUID().toString();
        this.blueCandies = 0;
        this.bagNumber = bagNumber;
    }

    public void addBlueCandy(){
        blueCandies++;
        System.out.println("Increased candies to: "+ blueCandies);
    }

    public int getBlueCandies() {
        return blueCandies;
    }

    public String getId() {
        return id;
    }

    public int getBagNumber() {
        return bagNumber;
    }
}

class BagTask implements Runnable {
    Striped<Lock> stripedLock;
    Bag bag;
    public BagTask(Bag bag, Striped<Lock> stripedLock) {
        this.stripedLock = stripedLock;
        this.bag = bag;
    }


    @Override
    public void run() {
        Lock lock = stripedLock.get(bag.getId());
        lock.lock();
        System.out.println("Bag Number: " + bag.getBagNumber() +  " lock: " +  lock.toString() );
        if(bag.getBlueCandies() == 0) {
            bag.addBlueCandy();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.unlock();

    }
}
public class GuavaLibraryMultithreading_24 {
    public static void main(String[] args) {
        Striped<Lock> stripedLock =  Striped.lock(10);
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        List<Bag> bagList = new ArrayList<>();
        for(int i=0;i<10;i++){
            bagList.add(new Bag(i+1));
        }

        for(int i=0;i<100;i++){
            executorService.submit(new BagTask(bagList.get((int)(Math.random()*10)), stripedLock));
        }
    }
}
