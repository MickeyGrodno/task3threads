import java.util.concurrent.TimeUnit;

//4.	CallCenter. В организации работает несколько операторов. Оператор может обслуживать одновременно только одного
// клиента. При отсутствии свободных операторов CallCenter ставит звонки в очередь. Клиент, стоящий в очереди, может
// положить трубку и перезвонить еще раз через некоторое время.

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CallCenter callCenter = new CallCenter(4);

        for (int i = 1; i <= 50; i++) {
            new Client(i, callCenter).start();
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
