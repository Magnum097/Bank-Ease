import com.example.project3.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests isValid() in Date class.
 */
public class DateTest {

    @org.junit.Test
    public void year_less_than_zero() {
        Date testInval1 = new Date("12/4/-123");
        assertFalse(testInval1.isValid());
    }

    @org.junit.Test
    public void month_greater_than_12() {
        Date testInval2 = new Date("13/4/2211");
        assertFalse(testInval2.isValid());
    }

    @org.junit.Test
    public void month_less_than_1() {
        Date testInval3 = new Date("0/11/2111");
        assertFalse(testInval3.isValid());
    }

    @org.junit.Test
    public void day_less_than_zero() {
        Date testInval4 = new Date("2/0/2020");
        assertFalse(testInval4.isValid());
    }

    @org.junit.Test
    public void valid_date() {
        Date testVal5 = new Date("2/12/2020");
        assertTrue(testVal5.isValid());
    }

    @org.junit.Test
    public void test_days_feb_non_leap() {
        Date testInval6 = new Date("2/29/2021");
        assertFalse(testInval6.isValid());
    }

    @org.junit.Test
    public void test_days_feb_leap() {
        Date testVal7 = new Date("2/29/2020");
        assertTrue(testVal7.isValid());
    }

    @org.junit.Test
    public void days_greater_than_31_for_long_months() {
        Date testInval8 = new Date("8/34/2014");
        assertFalse(testInval8.isValid());
    }

    @org.junit.Test
    public void days_greater_than_30_for_short_months() {
        Date testInval9 = new Date("4/31/2014");
        assertFalse(testInval9.isValid());
    }

    @org.junit.Test
    public void days_more_than_32() {
        Date testInval10 = new Date("10/35/2017");
        assertFalse(testInval10.isValid());
    }

    @org.junit.Test
    public void todays_date() {
        Date today = new Date();
        assertTrue(today.isValid());
    }

    @org.junit.Test
    public void dob_after_today() {
        Date testInval11 = new Date("2/27/2030");
        assertFalse(testInval11.isValid());
    }

}