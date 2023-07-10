package com.example.project3;

import java.util.StringTokenizer;

/**
 * This class is for a College Checking Account that extends Checking class.
 * Calculates monthly interest and fee for a College Checking Account.
 * Checks if the campus code entered by the user is valid or not.
 * @author Minseok Park, Amogh Sarangdhar
 */
public class CollegeChecking extends Checking {

    private static final int ACCOUNT_TYPE_CODE = 2;
    private static final double ANNUAL_INTEREST = 0.0025;
    private static final int COLLEGECODE_TOKEN = 4;

    public static final int NEWBRUNSWICK_CODE = 0;
    public static final int NEWARK_CODE = 1;
    public static final int CAMDEN_CODE = 2;
    public static final int INVALID_COLLEGE_CODE = -1;

    private int collegeCode = INVALID_COLLEGE_CODE;

    /**
     * Parametrized constructor to create a CollegeChecking Account for GUI program
     * This takes a firstname, lastname, dob, as strings, and balance as double.
     * @param fname first name of the holder.
     * @param lname last name of the holder.
     * @param dob date of birth of the holder.
     * @param balance balance in the account of the holder.
     */
    public CollegeChecking(String fname, String lname, String dob, double balance, int collegeCode)
    {
        super(fname, lname, dob, balance);
        this.collegeCode = collegeCode;
    }

    /**
     * Parametrized constructor for College Checking account that takes an input String from terminal, and tokenizes it.
     * @param transaction takes inputString from the terminal for College Checking account.
     */
    public CollegeChecking(String transaction) {
        super(transaction);

        int tokenCnt = 0;
        StringTokenizer st = new StringTokenizer(transaction);
        String commandToken = st.nextToken();
        st.nextToken();
        if (commandToken.equals("O")) {
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                {
                    if (tokenCnt == COLLEGECODE_TOKEN) {
                        collegeCode = Integer.parseInt(token);
                    }
                    tokenCnt++;
                }
            }
        }
    }


    /**
     * Checks if the college code is valid or not.
     * @return true if the college code is valid, false otherwise.
     */
    public boolean isValidCollegeCode() {
        if(collegeCode == NEWBRUNSWICK_CODE || collegeCode == NEWARK_CODE || collegeCode == CAMDEN_CODE){
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Calculates the monthly interest for a College Checking account.
     * @return monthly interest for a College Checking account as a double.
     */
    @Override
    public double monthlyInterest() {
        return (balance * ANNUAL_INTEREST) / MONTHS;
    }

    /**
     * Calculates the fee for College Checking account.
     * @return fee for Checking account as a double.
     */
    @Override
    public double fee()
    {
        return FEE_WAIVED;
    }

    /**
     * String representation of a College Checking account.
     * @return String for the College Checking account.
     */
    @Override
    public String toString() {
        return super.toString() + "::" + getCampusName();
    }

    /**
     * Getter for the name of the Campus depending on the college code.
     * @return String representation of the campus name.
     */
    private String getCampusName() {
        if (collegeCode == NEWBRUNSWICK_CODE) {
            return "NEW_BRUNSWICK";
        }
        else if (collegeCode == NEWARK_CODE) {
            return "NEWARK";
        }
        else if (collegeCode == CAMDEN_CODE) {
            return "CAMDEN";
        }
        else {
            return null;
        }
    }

    /**
     * Getter for the type of account (College Checking).
     * @return "College Checking" which is the type of account.
     */
    @Override
    public String getType() {
        return "College Checking";
    }

    /**
     * Check validity of an opening transaction (CollegeChecking)
     * @return false, if it has invalid profile/balance/collegeCode; true, otherwise
     */
    @Override
    public boolean isValid_O()
    {
        if (!super.isValid_O() || !isValidCollegeCode())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Getter for ACCOUNT_TYPE_CODE.
     * @return the type code of the Account (=2 for College Checking).
     */
    public int getTypeCode()
    {
        return ACCOUNT_TYPE_CODE;
    }

    /**
     * Testbed main to test the code in College Checking class.
     * @param args
     */
    public static void main(String[] args){
        CollegeChecking cc1 = new CollegeChecking("O CC John Doe 2/19/1989 600 0");
        CollegeChecking cc2 = new CollegeChecking("O CC John Doe 2/19/1989 600 2");
        System.out.println(cc1);
        System.out.println(cc2);
    }

}
