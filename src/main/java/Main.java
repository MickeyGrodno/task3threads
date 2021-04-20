import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CallCenter callCenter = new CallCenter(4);

        for (int i = 0; i < 50; i++) {
            new Client(i+1, callCenter).start();
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
