package com.example.project3;

import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * This class is an ABSTRACT Account class.
 * This includes the most inclusive data of any type of accounts.
 * Parent class that generalizes an account.
 * @author Minseok Park, Amogh Sarangdhar
 */
public abstract class Account {

    public static final int MONTHS = 12;

    public static final int PROFILE_TOKENS_START = 0;
    public static final int PROFILE_TOKENS_END = 2;
    public static final int BALANCE_TOKEN = 3;
    public static final int REQUIRED_TOKENS_ODW = 4;
    public static final int REQUIRED_TOKENS_C = 3;
    public static final int INVALID_AMOUNT = 0;
    public static final int FEE_WAIVED = 0;

    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * Parametrized constructor to create an Account for GUI program
     * This takes a firstname, lastname, dob, as strings, and balance as double.
     * @param fname first name of the holder.
     * @param lname last name of the holder.
     * @param dob date of birth of the holder.
     * @param balance balance in the account of the holder.
     */
    public Account(String fname, String lname, String dob, double balance)
    {
        Date DOB = new Date(dob);
        this.holder = new Profile(fname, lname, DOB);
        this.balance = balance;
    }


    /**
     * Parametrized constructor for Account that takes an input String from terminal, and tokenizes it.
     * Checks if the first token equals "0", "D", "W", then open, deposits, and withdraws accordingly.
     * @param transaction takes inputString from the terminal for any Account.
     */
    public Account(String transaction)  {
        StringTokenizer st = new StringTokenizer(transaction);
        String commandToken = st.nextToken();
        st.nextToken();
        if (commandToken.equals("O") || commandToken.equals("D") || commandToken.equals("W"))
        {
            userInput_ODW(st);
        }
        else if (commandToken.equals("C"))
        {
            userInput_C(st);
        }
    }

    /**
     * Tokenizes the string input accordingly from the terminal when the user inputs "O", "D", "W".
     * @param st takes in the string tokens.
     */
    private void userInput_ODW(StringTokenizer st)
    {
        int tokenCnt = 0;
        String profileToken = "";
        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if (tokenCnt >= PROFILE_TOKENS_START && tokenCnt <= PROFILE_TOKENS_END) {
                profileToken = profileToken + token + " ";
            }
            if (tokenCnt == BALANCE_TOKEN) {
                double tempBalance = Double.parseDouble(token);
                balance = tempBalance;
            }
            tokenCnt++;
        }
        holder = new Profile(profileToken);
        closed = false;
        if (tokenCnt < REQUIRED_TOKENS_ODW)
        {
            throw new NoSuchElementException();
        }
    }

    /**
     * Tokenizes the string input accordingly from the terminal when the user inputs "C".
     * @param st takes in the string tokens.
     */
    private void userInput_C(StringTokenizer st) {
        int tokenCnt = 0;
        String profileToken = "";
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (tokenCnt >= PROFILE_TOKENS_START && tokenCnt <= PROFILE_TOKENS_END) {
                profileToken = profileToken + token + " ";
            }
            tokenCnt++;
        }
        holder = new Profile(profileToken);
        if (tokenCnt < REQUIRED_TOKENS_C)
        {
            throw new NoSuchElementException();
        }
    }

    /**
     * Checks if two accounts are equal or not.
     * @param obj of type Account.
     * @return true if two Accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if ( (this.getType()).equals(((Account)obj).getType()) && holder.equals(((Account)obj).holder ) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * String representation of Account class, depending on whether the account is closed or not.
     * @return String for an Account.
     */
    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        if (closed == true)
        {
            return getType() + "::" + holder.toString() + "::" + "Balance " + "$" + df.format(getBalance()) + "::CLOSED";
        }
        else
        {
            return getType() + "::" + holder.toString() + "::" + "Balance " + "$" + df.format(getBalance());
        }

    }

    /**
     * Check validity of an opening transaction (abstract Account)
     * @return false, if it has invalid profile/balance; true, otherwise
     */
    public boolean isValid_O()
    {
        if(!holder.isValid() || balance <= INVALID_AMOUNT)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Check validity of a closing transaction (abstract Account)
     * @return false, if it has invalid profile; true, otherwise
     */
    public boolean isValid_C()
    {
        if(!holder.isValid())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Check validity of a withdrawing/depositing transaction (abstract Account)
     * @return false, if it has invalid profile/balance; true, otherwise
     */
    public boolean isValid_WD()
    {
        if(!holder.isValid() || balance <= INVALID_AMOUNT)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    /**
     * Sets the balance of the closed account to 0.
     */
    public void setClosedBalance()
    {
        this.balance = 0;
    }

    /**
     * The amount of money that is to be withdrawn from the Account.
     * @param amount money to be withdrawn.
     */
    public void withdraw(double amount)
    {
        balance = balance - amount;
    }

    /**
     * The amount of money that is to be deposited into the Account (abstract Account)
     * @param amount money to be deposited.
     */
    public void deposit(double amount)
    {
        balance = balance + amount;
    }

    /**
     * Method to update the balance of the Account holder.
     * Calculates balance after reducing the fee and adding the monthly interest.
     */
    public void updateBalance()
    {
        balance = balance + monthlyInterest();
        balance = balance - fee();
    }

    /**
     * Abstract method for monthly interest of the Account.
     * @return the monthly interest of the Account.
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method for fees of the Account.
     * @return fees of the Account.
     */
    public abstract double fee();

    /**
     * Abstract method for the type of the Account.
     * @return the type of the Account (Checking, College Checking, Savings, Money Market Savings).
     */
    public abstract String getType();

    /**
     * Abstract method for getting the type code of the Account.
     * @return the type code of the Account (1= Checking, 2= College Checking, 3=Money Market, 4=Savings).
     */
    public abstract int getTypeCode();

    /**
     * Getter for Account holder.
     * @return Profile of the account holder.
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * Checks if the Account is closed or not.
     * @return true if the Account is closed, false otherwise.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Getter for the balance in the Account.
     * @return the current balance of the Account.
     */
    public double getBalance() {
        return balance;
    }
}
