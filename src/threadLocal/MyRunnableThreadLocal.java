package threadLocal;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class UserContextHolder {
    static ThreadLocal<User> holder = new ThreadLocal<>();
}
class Service1 {
    public void putUserData(Integer userId) {
        User user = new User(userId, "Amit" + userId);
        UserContextHolder.holder.set(user);
    }
}
class Service2 {
    public void getUser(){
        User user = UserContextHolder.holder.get();
        System.out.println("Service-2 " + user.getName());
    }
}
class Service3 {
    public void getUser(){
        User user = UserContextHolder.holder.get();
        System.out.println("Service-3 " + user.getName());
    }
}

class Service4 {
    public void getUser(){
        User user = UserContextHolder.holder.get();
        System.out.println("Service-4 " + user.getName());

        UserContextHolder.holder.remove();
    }
}

class Driver implements Runnable{
    @Override
    public void run() {
        myProgram();
    }

    public void myProgram(){
        new Service1().putUserData((int)(100*Math.random()));
        new Service2().getUser();
        new Service3().getUser();
        new Service4().getUser();
    }
}
public class MyRunnableThreadLocal {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        
        Runnable task = new Driver();
        for(int i=0;i<10;i++){
            executorService.execute(task); // executorService.submit(task);
        }
        executorService.shutdown();
    }
}
