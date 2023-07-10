package com.example.project3;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * This class contains year, month, day as integers to represent a date.
 * This class can determine the given date is today, as well as before today.
 * This class can compare two Date objects.
 * This class can Determines whether the date is valid calendar type date or not.
 * @author Minseok Park, Amogh Sarangdhar
 */
public class Date implements Comparable<Date>
{
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    public static final int LONGMONTHDAYS = 31;
    public static final int SHORTMONTHDAYS = 30;
    public static final int FEB_NONLEAPDAYS = 28;
    public static final int FEB_LEAPDAYS = 29;

    public static final int LONGMONTHS = 1;
    public static final int SHORTMONTHS = 0;
    public static final int FEB_LEAP = -1;
    public static final int FEB_NONLEAP = -2;

    private static final int TOKEN_MONTH = 0;
    private static final int TOKEN_DAY = 1;
    private static final int TOKEN_YEAR = 2;

    private int year = -1;
    private int month = -1;
    private int day = -1;

    /**
     * Parameterized constructor that takes a piece of the input String from terminal.
     * Tokenize the input string, and initialize the fields.
     * @param date partially processed transaction string.
     */
    public Date(String date)
    {
        int tokenCnt = 0;
        StringTokenizer st = new StringTokenizer(date, "/");
        while (st.hasMoreTokens())
        {
            String token = st.nextToken();
            if (tokenCnt == TOKEN_MONTH)
            {
                this.month = Integer.parseInt(token);
            }
            else if (tokenCnt == TOKEN_DAY)
            {
                this.day = Integer.parseInt(token);
            }
            else if (tokenCnt == TOKEN_YEAR)
            {
                this.year = Integer.parseInt(token);
            }
            tokenCnt++;
        }
    }

    /**
     * Default constructor. Initialize month, day, year as today.
     */
    public Date()
    {     //Constructor that creates an object with today’s date
        this.month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.day = Calendar.getInstance().get(Calendar.DATE);
        this.year = Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Getter method to get Year integer of the Date
     * @return year as integer
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Getter method to get Month integer of the Date
     * @return month as integer
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * Getter method to get Day integer of the Date
     * @return day as integer
     */
    public int getDay()
    {
        return day;
    }

    /**
     * Determines whether the date is valid calendar type date or not.
     * Determines the date is future or not.
     * @return true, if the date is valid calendar type date; false, if the date is future or otherwise
     */
    public boolean isValid()
    {
        if (year < 0)
        {
            return false;
        }
        if (compareTo(new Date()) >= 0)
        {
            return false;
        }
        if (month < Calendar.JANUARY + 1 || month > Calendar.DECEMBER + 1)
        {
            return false;
        }
        if (day <= FEB_NONLEAPDAYS && day > 0)
        {
            return true;
        }
        else if (day == FEB_LEAPDAYS && monthType() == FEB_LEAP)
        {
            return true;
        }
        else if (day <= SHORTMONTHDAYS && monthType() == SHORTMONTHS)
        {
            return true;
        }
        else if (day <= LONGMONTHDAYS && monthType() == LONGMONTHS)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Compare day/month/year fields of the two "Date" objects, A and B. Use, A.CompareTo(B)
     * @param date Date B
     * @return -1, when Date A is earlier B; 1, when Date A is later than B; 0 when they have same date.
     */
    @Override
    public int compareTo(Date date)
    {
        if (year == date.getYear())
        {
            if (month == date.getMonth())
            {
                if (day == date.getDay())
                {
                    return 0;
                }
                else if (day > date.getDay())
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
            else if (month > date.getMonth())
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else if (year > date.getYear())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    /**
     * toString method that prints the string representation of Date.
     * @return date in the form mm/dd/yy
     */
    @Override
    public String toString()
    {
        String date = month + "/" + day + "/" + year;
        return date;
    }


    /**
     * Determine whether month has 30/31/28/29 days. 28 Days for Feb with non-Leap year, 29 Days for Feb with Leap year.
     * Helper Method for Date class.
     * @return 1, for months that have 31 days; 0, for months that have 30 days; -1, for Feb with 29 days(Leap year); -2 for Feb with 28 days
     */
    private int monthType()
    {
        if ((month == Calendar.FEBRUARY + 1))
        {
            if (isLeapYear() == true)
            {
                return FEB_LEAP; //for 29days. Leap year
            }
            else
            {
                return FEB_NONLEAP;
            }
        }
        else if ((month == Calendar.JANUARY + 1) || (month == Calendar.MARCH + 1)
                || (month == Calendar.MAY + 1) || (month == Calendar.JULY + 1)
                || (month == Calendar.AUGUST + 1) || (month == Calendar.OCTOBER + 1)
                || (month == Calendar.DECEMBER + 1))
        {
            return 1;
        }
        else
        {
            return 0;
        }

    }

    /**
     * Determine whether this year is leap year or not.
     * Rule for leap year:
     * Step 1. If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
     * Step 2. If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
     * Step 3. If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
     * Step 4. The year is a leap year.
     * Step 5. The year is not a leap year.
     * Or simply, year is divisible by (4 and 400) or (4, but not by 100) (DeMorgan law)
     * @return true, when it is Leap year; false, otherwise.
     */
    private boolean isLeapYear()
    {
        if ((year % QUADRENNIAL == 0) && (year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Testbed main to test the code in this Date class.
     * @param args
     */
    public static void main(String[] args)
    {
        Date today = new Date();

        Date testInval1 = new Date("12/4/-123");
        Date testInval2 = new Date("13/4/2211");
        Date testInval3 = new Date("0/11/2111");
        Date testInval4 = new Date("2/0/2020");
        Date testVal5 = new Date("2/12/2020");
        Date testInval6 = new Date("2/29/2021");
        Date testVal7 = new Date("2/29/2020");
        Date testInval8 = new Date("8/34/2014");
        Date testInval9 = new Date("4/31/2014");
        Date testInval10 = new Date("10/35/2017");
        Date future = new Date("3/10/2022");

        System.out.println("Testcase future:" + future.isValid());
        System.out.println("Future: " + future.compareTo(today));
        System.out.println("Testcase today:" + today.isValid());
        System.out.println("Testcase 1:" + testInval1.isValid());
        System.out.println("Testcase 2:" + testInval2.isValid());
        System.out.println("Testcase 3:" + testInval3.isValid());
        System.out.println("Testcase 4:" + testInval4.isValid());
        System.out.println("Testcase 5:" + testVal5.isValid());
        System.out.println("Testcase 6:" + testInval6.isValid());
        System.out.println("Testcase 7:" + testVal7.isValid());
        System.out.println("Testcase 8:" + testInval8.isValid());
        System.out.println("Testcase 9:" + testInval9.isValid());
        System.out.println("Testcase 10:" + testInval10.isValid());
        System.out.println("Testcase 11:" + today.isValid());

    }
}
