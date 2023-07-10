import com.example.project3.MoneyMarket;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests monthlyInterest() in MoneyMarket class.
 */
public class MoneyMarketTest {

    @Test
    public void interest_when_balance_equals_2500_loyal() {
        MoneyMarket mm1 = new MoneyMarket("O MM Jane Doe 10/1/1995 2500");
        double monthlyInterest = mm1.monthlyInterest();
        assertEquals(1.9791666666666667, monthlyInterest, 0);
    }

    @Test
    public void interest_when_balance_less_than_2500_notloyal() {
        MoneyMarket mm1 = new MoneyMarket("O MM Jane Doe 10/1/1995 2300");
        double monthlyInterest = mm1.monthlyInterest();
        assertEquals(1.5333333333333334, monthlyInterest, 0);
    }

    @Test
    public void interest_when_balance_above_2500_loyal() {
        MoneyMarket mm1 = new MoneyMarket("O MM Jane Doe 10/1/1995 3500");
        double monthlyInterest = mm1.monthlyInterest();
        assertEquals(2.7708333333333335, monthlyInterest, 0);
    }

}