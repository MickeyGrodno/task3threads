import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenter {
    private Queue<Operator> operators = new ConcurrentLinkedQueue<>();
    private Semaphore semaphore;
    private ReentrantLock lock = new ReentrantLock();

    public CallCenter(int numberOfOperators) {
        this.semaphore = new Semaphore(numberOfOperators, true);
        for(int i = 1; i <=numberOfOperators; i++) {
            operators.add(new Operator(i));
        }
    }

    public Operator getOperator(int waitingTime) {
        Operator operatorForClient = null;
        try {
            semaphore.tryAcquire(waitingTime, TimeUnit.SECONDS);
            Operator operator = operators.poll();
            operatorForClient = operator;

        } catch (InterruptedException e) {}
        return operatorForClient;
    }

    public boolean returnOperator(Operator operator) {
        boolean callIsOver = false;
        if (lock.tryLock()) {
            operators.add(operator);
            callIsOver = true;
        }
        System.out.println("Оператор №"+operator.getOperatorId()+" освободился.");
        semaphore.release();
        lock.unlock();
        return callIsOver;
    }
}
