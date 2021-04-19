public class Client extends Thread{
    private boolean urgentCall = false;
    private CallCenter callCenter;
    private int clientId;
    private int waitingTime;

    public Client(int clientId, CallCenter callCenter) {
        this.clientId = clientId;
        this.callCenter = callCenter;
        if((Math.random()*4) > 2) {
            urgentCall = true;
        }
        waitingTime = (int) Math.random()*3+3;
    }

    public void run() {
        this.setName("Клиент №"+clientId);
        System.out.println(this.getName()+" дозвонился в справочную службу.");

        Operator operator;

        operator = callCenter.getOperator(waitingTime);

        if (operator == null && urgentCall) {
            while (operator == null) {
                System.out.println(this.getName()+" повторно позвонил в справочную службу.");
                operator = callCenter.getOperator(waitingTime);
            }
            operator.inTouchWithTheClient();
        }
        else if (operator == null){
            System.out.println(this.getName() + " не дождался ответа и положил трубку.");
            return;
        }

        System.out.println(this.getName()+" направлен к оператору №"+operator.getOperatorId());

        operator.inTouchWithTheClient();

        System.out.println(this.getName()+" закончил разговор с оператором №"+operator.getOperatorId());
        while (true) {
            if (callCenter.returnOperator(operator)) {
                break;
            }
        }
    }
}
