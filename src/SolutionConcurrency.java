import java.lang.invoke.VarHandle;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SolutionConcurrency {

    private volatile Integer valueSolution = 0;
    volatile static AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public void set(int value) {
        if (valueSolution == 0) {
            valueSolution = value;
            synchronized (SolutionConcurrency.class) {
                valueSolution.notifyAll();
            }
        }

    }

    public void resultSet(int value) {
        if (valueSolution == 0 && atomicBoolean.getAndSet(false)) {
            valueSolution = value;
            System.out.println(Thread.currentThread().getName() + " change data");
            System.out.println(true);
        }
    }

    public static void main(String[] args) {
        var s = Executors.newFixedThreadPool(10);
        SolutionConcurrency solutionConcurrency =new SolutionConcurrency();
        for (int i = 0; i < 10; i++) {
            s.submit(() -> solutionConcurrency.resultSet((int) ((Math.random() + 1) * 10)));
        }
    }
}
