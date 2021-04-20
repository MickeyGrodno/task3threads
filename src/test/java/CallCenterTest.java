import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CallCenterTest {

    @Test
    public void getOperatorFromCallCenterTest() {
        CallCenter center = new CallCenter(2);
        Operator operator = center.getOperator(1);
        Assertions.assertEquals(1, operator.getOperatorId());
    }

    @Test
    public void releaseToFromCallCenterTest() {
        CallCenter center = new CallCenter(10);
        Operator operator = center.getOperator(1);
        center.releaseOperator(operator);
        Assertions.assertEquals(10, center.getOperators().size());
    }
}