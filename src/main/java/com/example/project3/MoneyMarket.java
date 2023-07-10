package com.example.project3;


import java.text.DecimalFormat;

/**
 * This class is for a Money Market Account that extends the Savings class.
 * Calculates the monthly interest and fees, depending on the loyalty, minimum balance, and withdrawals of the customer.
 * @author Minseok Park, Amogh Sarangdhar
 */
public class MoneyMarket extends Savings
{

    private int numOfWithdrawls;      //number of withdrawals from an account

    private static final int ACCOUNT_TYPE_CODE = 3;
    private static final int WITHDRAWLS_WAIVE = 3;
    private static final double ANNUAL_INTEREST = 0.008;
    private static final double ADD_INTEREST = 0.0015;
    public static final double MIN_BALANCE = 2500;
    private static final double MONTHLY_FEE = 10;

    /**
     * Parametrized constructor to create a Money Market account for GUI program
     * This takes a firstname, lastname, dob, as strings, and balance as double.
     * @param fname first name of the holder.
     * @param lname last name of the holder.
     * @param dob date of birth of the holder.
     * @param balance balance in the account of the holder.
     */
    public MoneyMarket(String fname, String lname, String dob, double balance)
    {
        super(fname, lname, dob, balance, true);
    }

    /**
     * Parametrized constructor for Money Market account that takes an input String from terminal, and tokenizes it.
     * Defaults customer to a loyal customer.
     * @param transaction takes inputString from the terminal for Money Market account.
     */
    public MoneyMarket(String transaction)
    {
        super(transaction);
        isLoyalCustomer = true;
    }

    /**
     * Calculates the monthly interest for a Money Market account.
     * @return higher monthly interest if the balance is greater than minimum balance.
     */
    @Override
    public double monthlyInterest()
    {
        if (isLoyalCustomer == true)
        {
            return (balance * (ANNUAL_INTEREST + ADD_INTEREST)) / MONTHS;
        }
        else
        {
            return (balance * ANNUAL_INTEREST) / MONTHS;
        }
    }


    /**
     * Calculates the fees for Money Market account.
     * If the numberofWithdrawals is less than 3 or balance exceeds minimum balance then fee is waived.
     * @return monthly fee if numberofWithdrawals exceeds 3.
     */
    @Override
    public double fee()
    {
        if (isClosed())
        {
            return FEE_WAIVED;
        }
        else if (numOfWithdrawls > WITHDRAWLS_WAIVE)
        {
            return MONTHLY_FEE;
        }
        else
        {
            if (balance >= MIN_BALANCE)
            {
                return FEE_WAIVED;
            }
            return MONTHLY_FEE;
        }
    }

    /**
     * Getter for the type of account (Money Market).
     * @return "Money Market Savings" which is the type of account.
     */
    @Override
    public String getType()
    {
        return "Money Market";
    }

    /**
     * Getter for ACCOUNT_TYPE_CODE.
     * @return the type code of the Account (=3 for Money Market).
     */
    @Override
    public int getTypeCode()
    {
        return ACCOUNT_TYPE_CODE;
    }

    /**
     * String representation of a Money Market account.
     * @return String for the Money Market account.
     */
    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        if (closed == true)
        {
            if (isLoyalCustomer() == true)
            {
                return getType() + " " + super.getType() + "::" + holder.toString() + "::" + "Balance " + "$"
                        + df.format(getBalance()) + "::CLOSED" + "::Loyal" + "::withdrawl: " + numOfWithdrawls;
            }
            else
            {
                return getType() + " " + super.getType() + "::" + holder.toString() + "::" + "Balance " + "$"
                        + df.format(getBalance()) + "::CLOSED" + "::withdrawl: " + numOfWithdrawls;
            }
        }
        else
        {
            if (isLoyalCustomer() == true)
            {
                return getType() + " " + super.getType() + "::" + holder.toString() + "::" + "Balance " + "$"
                        + df.format(getBalance()) + "::Loyal" + "::withdrawl: " + numOfWithdrawls;
            }
            else
            {
                return getType() + " " + super.getType() + "::" + holder.toString() + "::" + "Balance " + "$"
                        + df.format(getBalance()) + "::withdrawl: " + numOfWithdrawls;
            }
        }
    }

    /**
     * The amount of money needed to withdraw from an account.
     * Also, increments the numOfWithdrawals.
     * @param amount the amount required to be withdrawn.
     */
    @Override
    public void withdraw(double amount)
    {
        balance = balance - amount;
        numOfWithdrawls++;
        if (balance < MIN_BALANCE)
        {
            isLoyalCustomer = false;
        }
    }


    /**
     * The amount of money that is to be deposited into the Account (Money Market)
     * @param amount money to be deposited. Also change loyality if the balance goes back to >= 2500.
     */
    @Override
    public void deposit(double amount)
    {
        balance = balance + amount;
        if (balance >= MIN_BALANCE)
        {
            isLoyalCustomer = true;
        }
    }

    /**
     * Check validity of an opening transaction (Money Market)
     * @return false, if it has invalid profile or balance < 2500; true, otherwise
     */
    @Override
    public boolean isValid_O()
    {
        if (!super.isValid_O() || balance < MIN_BALANCE)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Getter for numOfWithdrawls.
     * @return numOfWithdrawls
     */
    public int getNumOfWithdrawls()
    {
        return numOfWithdrawls;
    }

    /**
     * Resets to numOfWithdrawals to 0.
     */
    public void resetNumofWithdrawls()
    {
        this.numOfWithdrawls = 0;
    }


    /**
     * Testbed main class to test the code in Money Market class.
     * @param args
     */
    public static void main(String[] args)
    {
        MoneyMarket mm1 = new MoneyMarket("O MM Roy Brooks 3/29/2023 2909.10");
        System.out.println(mm1.balance);
        System.out.println(mm1.holder.getFname());
        System.out.println(mm1.monthlyInterest());
        System.out.println(mm1.fee());
    }

}
