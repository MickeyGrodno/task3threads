import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private boolean urgentCall = false;
    private final CallCenter CALL_CENTER;
    private final int CLIENT_ID;
    private final int WAITING_TIME = (int) ((Math.random()*4) +3);

    public Client(int CLIENT_ID, CallCenter CALL_CENTER) {
        this.CLIENT_ID = CLIENT_ID;
        this.CALL_CENTER = CALL_CENTER;
        if((Math.random()*4) > 2) {
            urgentCall = true;
        }
    }

    public void run() {
        this.setName("Клиент №"+getClientId());
        LOGGER.info("Поток запущен");
        System.out.println(this.getName()+" дозвонился в справочную службу.");
        Operator operator;
        LOGGER.info("Попытка вызова оператора потоком");
        operator = CALL_CENTER.getOperator(getWaitingTime());

        if (operator == null && isUrgentCall()) {
            while (operator == null) {
                System.out.println(this.getName()+" повторно позвонил в справочную службу.");
                operator = CALL_CENTER.getOperator(getWaitingTime());
            }
            operator.inTouchWithTheClient();
        }
        else if (operator == null){
            LOGGER.info("Поток не дождался вызова оператора.");
            System.out.println(this.getName() + " не дождался ответа и положил трубку.");
            return;
        }
        LOGGER.info("Потоком был вызван оператор №"+operator.getOperatorId());
        System.out.println(this.getName()+" направлен к оператору №"+operator.getOperatorId());

        operator.inTouchWithTheClient();

        System.out.println(this.getName()+" закончил разговор с оператором №"+operator.getOperatorId());
        while (true) {
            if (CALL_CENTER.releaseOperator(operator)) {
                LOGGER.info("Поток вернул в очередь оператора №"+operator.getOperatorId());
                break;
            }
        }
    }

    public boolean isUrgentCall() {
        return urgentCall;
    }

    public int getClientId() {
        return CLIENT_ID;
    }

    public int getWaitingTime() {
        return WAITING_TIME;
    }
}