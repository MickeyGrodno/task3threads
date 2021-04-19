import java.util.concurrent.TimeUnit;

public class Operator {
    private int operatorId;

    public int getOperatorId() {
        return operatorId;
    }

    public Operator(int operatorId) {
        this.operatorId = operatorId;
    }

    public void inTouchWithTheClient() {
        try {
            TimeUnit.SECONDS.sleep((int) (Math.random() * 3 + 2));
        } catch (InterruptedException e){}
    }

}
