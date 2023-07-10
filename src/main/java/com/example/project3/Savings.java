package com.example.project3;

import java.util.StringTokenizer;

/**
 * This class is for a Savings Account that extends the Account class.
 * Calculates monthly interest and fee for a Savings Account.
 * Checks if the customer is loyal or not, and adjusts the interest rates accordingly.
 * @author Minseok Park, Amogh Sarangdhar
 */
public class Savings extends Account {

    public boolean isLoyalCustomer;

    private static final int ACCOUNT_TYPE_CODE = 4;
    private static final double ANNUAL_INTEREST = 0.003;
    private static final double ADD_INTEREST = 0.0015;
    public static final double MIN_BALANCE = 300;
    private static final double MONTHLY_FEE = 6;
    public static final int LOYALCODE_TOKEN = 4;

    /**
     * Parametrized constructor to create a Savings account for GUI program
     * This takes a firstname, lastname, dob, as strings, and balance as double.
     * @param fname first name of the holder.
     * @param lname last name of the holder.
     * @param dob date of birth of the holder.
     * @param balance balance in the account of the holder.
     * @param loyality loyalty of the holder.
     */
    public Savings(String fname, String lname, String dob, double balance, boolean loyality)
    {
        super(fname, lname, dob, balance);
        isLoyalCustomer = loyality;
    }

    /**
     * Parametrized constructor that takes an input String from terminal.
     * Tokenizes the input string and checks if the customer is loyal or not.
     * @param transaction transaction string for a savings account.
     */
    public Savings(String transaction) {
        super(transaction);

        int tokenCnt = 0;
        StringTokenizer st = new StringTokenizer(transaction);
        String commandToken = st.nextToken();
        st.nextToken();
        if (commandToken.equals("O"))
        {
            while(st.hasMoreTokens()) {
                String token = st.nextToken();{
                    if (tokenCnt == LOYALCODE_TOKEN) {
                        if(token.equals("0")){          //0 - non-loyal customer
                            isLoyalCustomer = false;
                        }
                        else if(token.equals("1"))
                        {                               //1 - loyal customer
                            isLoyalCustomer = true;
                        }
                    }
                    tokenCnt++;
                }
            }
        }
    }

    /**
     * Calculates the monthly interest for a Savings account, depending on if the customer is loyal or not.
     * @return monthly interest for a Savings account as a double.
     */
    @Override
    public double monthlyInterest()
    {
        if(isLoyalCustomer == true){
            return (balance * (ANNUAL_INTEREST + ADD_INTEREST)) / MONTHS;
        }
        else
        {
            return (balance * ANNUAL_INTEREST) / MONTHS;
        }

    }

    /**
     * Calculates the fee for Savings account, if the balance is greater or less than the minimum balance required.
     * @return fee for Savings account as a double.
     */
    @Override
    public double fee() {
        if(balance >= MIN_BALANCE || isClosed()){
            return FEE_WAIVED ;
        }
        return MONTHLY_FEE;
    }

    /**
     * Getter for the type of account (Savings).
     * @return "Savings" which is the type of account.
     */
    @Override
    public String getType() {
        return "Savings";
    }

    /**
     * String representation of a Savings account, depending on whether the customer is loyal or not.
     * @return String of the Savings account.
     */
    @Override
    public String toString() {
        if (isLoyalCustomer() == true)
        {
            return super.toString() + "::Loyal";
        }
        else
        {
            return super.toString();
        }
    }

    /**
     * Getter for ACCOUNT_TYPE_CODE.
     * @return the type code of the Account (=3 for Savings).
     */
    public int getTypeCode()
    {
        return ACCOUNT_TYPE_CODE;
    }


    /**
     * Checks if the customer is Loyal or not.
     * @return true if customer is loyal, false otherwise.
     */
    public boolean isLoyalCustomer(){      //checks if the customer is loyal or not
        return isLoyalCustomer;
    }

    /**
     * Testbed main to test the code in Savings class.
     * @param args
     */
    public static void main(String[] args){
        Savings s1 = new Savings("John Doe 2/19/1990 200 0");
        System.out.println(s1.isLoyalCustomer());
        Savings s2 = new Savings("John Doe 2/19/1990 200 1");
        System.out.println(s2.isLoyalCustomer());
    }


}
