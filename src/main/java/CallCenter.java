import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallCenter.class);
    private Queue<Operator> operators = new ConcurrentLinkedQueue<>();
    private Semaphore semaphore;
    private ReentrantLock lock = new ReentrantLock();

    public CallCenter(int numberOfOperators) {
        this.semaphore = new Semaphore(numberOfOperators, true);
        for (int i = 1; i <= numberOfOperators; i++) {
            operators.add(new Operator(i));
        }
        LOGGER.info("Создан объект класса CallCenter, количество операторов = "+numberOfOperators);
    }

    public Operator getOperator(int waitingTime) {
        Operator operatorForClient = null;
        try {
            semaphore.tryAcquire(waitingTime, TimeUnit.SECONDS);
            operatorForClient = operators.poll();
        } catch (InterruptedException e) {
        }
        LOGGER.info("Вызван оператор "+operatorForClient);
        return operatorForClient;

    }

    public boolean releaseOperator(Operator operator) {
        boolean callIsOver = false;
        if (lock.tryLock()) {
            if(callIsOver = operators.add(operator)) {
                System.out.println("Оператор №" + operator.getOperatorId() + " освободился.");
                semaphore.release();
                lock.unlock();
                LOGGER.info("Оператор №"+operator.getOperatorId()+" возвращен в очередь.");
            }
        }
        return callIsOver;
    }

    public Queue<Operator> getOperators() {
        return operators;
    }
}