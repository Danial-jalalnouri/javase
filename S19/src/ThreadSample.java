import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadSample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        newThread();
    }

    // java 21
    private static void newThread() throws ExecutionException, InterruptedException {
        var excuter = Executors.newVirtualThreadPerTaskExecutor();
        Future<?> future1 = excuter.submit(() -> {
            System.out.println("first Virtual thread start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("first Virtual thread second step");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("first Virtual thread end");
        });

        Future<?> future2 = excuter.submit(() -> {
            System.out.println("second virtual thread start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("second virtual thread second step");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("second virtual thread end");
        });

        future1.get();
        future2.get();
    }

    // old version
    private static void oldThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread end!");
        });

        thread.start();
        thread.join();
    }
}
