package com.example.project3;


/**
 * This class is for a Checking Account that extends the Account class.
 * Calculates monthly interest and fee for a Checking Account.
 * @author Minseok Park, Amogh Sarangdhar
 */
public class Checking extends Account {

    private static final int ACCOUNT_TYPE_CODE = 1;
    private static final double ANNUAL_INTEREST = 0.001;
    private static final int MONTHLY_FEE = 25;
    private static final int MIN_BALANCE = 1000;

    /**
     * Parametrized constructor to create a Checking account for GUI program
     * This takes a firstname, lastname, dob, as strings, and balance as double.
     * @param fname first name of the holder.
     * @param lname last name of the holder.
     * @param dob date of birth of the holder.
     * @param balance balance in the account of the holder.
     */
    public Checking(String fname, String lname, String dob, double balance)
    {
        super(fname, lname, dob, balance);
    }

    /**
     * Parametrized constructor for Checking account that takes an input String from terminal, and tokenizes it.
     * @param inputString takes inputString from the terminal for Checking account.
     */
    public Checking(String inputString) {
        super(inputString);
    }

    /**
     * Calculates the monthly interest for a Checking account.
     * @return monthly interest for a Checking account as a double.
     */
    @Override
    public double monthlyInterest() {
        return (balance * ANNUAL_INTEREST) / MONTHS;
    }

    /**
     * Calculates the fee for Checking account, if the balance is greater or less than the minimum balance required.
     * @return fee for Checking account as a double.
     */
    @Override
    public double fee()
    {
        if(balance >= MIN_BALANCE || isClosed())
        {
            return FEE_WAIVED;
        }
        return MONTHLY_FEE;
    }

    /**
     * Getter for ACCOUNT_TYPE_CODE.
     * @return the type code of the Account (=1 for Checking).
     */
    public int getTypeCode()
    {
        return ACCOUNT_TYPE_CODE;
    }

    /**
     * String representation of a Checking account.
     * @return String for the Checking account.
     */
    @Override
    public String toString()
    {
        return super.toString();
    }

    /**
     * Checks if an account holder has both Checking or College Checking.
     * An account holder cannot have both Checking and College Checking account at the same time.
     * @param account the account to be verified for.
     * @return true if the two types of accounts are duplicated, false otherwise.
     */
    public boolean isDuplicated(Account account) {
        if (account.getType().equals("Checking") || account.getType().equals("College Checking") )
        {
            if (this.holder.equals(account.holder))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Getter for the type of account (Checking).
     * @return "Checking" which is the type of account.
     */
    @Override
    public String getType() {
        return "Checking";
    }


}
