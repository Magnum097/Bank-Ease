import com.example.project3.*;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.Test;

import java.rmi.NoSuchObjectException;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests open() and close() in AccountDatabase class.
 */
public class AccountDatabaseTest
{
    //open() returns true if account is newly opened; returns false if the account is reopened.
    //throws exceptions for error cases. Therefore, falser of open() does not mean an error case.

    @Test
    public void open_valid_newly_open_account_TRUE()
    {
        Account validNewAccount1 = null;
        AccountDatabase accntDB = new AccountDatabase();
        validNewAccount1 = new CollegeChecking("O  CC   John Doe 2/19/1989 600 0");
        assertTrue(accntDB.open(validNewAccount1));
    }

    @Test
    public void open_valid_reopening_account_FALSE()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validNewAccount1  = new Checking("O  C   Pizza Doe 4/17/1999 900 0");
        accntDB.open(validNewAccount1);
        try{
            accntDB.close(validNewAccount1);
        }
        catch (NoSuchElementException e)
        {
        } catch (NoSuchObjectException e) {
        }
        //The states above is to set the "validNewAccount1" is newly opened then closed.
        Account validReopenAccount1 = new Checking("O  C   Pizza Doe 4/17/1999 900 0");
        assertFalse(accntDB.open(validReopenAccount1));
    }

    @Test
    public void open_invalid_duplicated_account_general()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validNewAccount1 = new CollegeChecking("O  MM   Pasta Dou 6/19/1989 5000");
        Account validNewAccount2 = new CollegeChecking("O  MM   Pasta Dou 6/19/1989 5000");
        boolean thrown = false;
        try
        {
            accntDB.open(validNewAccount1);
            accntDB.open(validNewAccount2);
        }
        catch (DuplicateRequestException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void open_invalid_duplicated_Checking_account()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validNewAccount1 = new Checking("O  C   Tikka Masala 7/19/1979 600 0");
        Account validNewAccount2 = new CollegeChecking("O  CC   Tikka Masala 7/19/1979 700 0");
        boolean thrown = false;
        try
        {
            accntDB.open(validNewAccount1);
            accntDB.open(validNewAccount2);
        }
        catch (DuplicateRequestException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }


    ////close() returns if the account is closed successfully; returns false if the user try to close a closed account
    //close() throws exception "NoSuchObjectException" for closing nonexisting account,


    @Test
    public void close_valid_closing_an_account()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validPresentAccount = new CollegeChecking("O  S   Gamja Fry 11/2/2000 8000 1");
        Account validCloseTheAccount = new CollegeChecking("C  S   Gamja Fry 11/2/2000");
        boolean thrown = false;
        try
        {
            accntDB.open(validPresentAccount);
            assertTrue(accntDB.close(validCloseTheAccount));

        }
        catch (NoSuchObjectException e)
        {
            thrown = true;
            assertFalse(thrown);
        }
    }

    @Test
    public void close_invalid_Closing_no_account()
    {
        AccountDatabase accntDB = new AccountDatabase();
        //Account validPresentAccount = new CollegeChecking("O  C   Kimchi Rice 1/7/2001 1000 0");
        Account validCloseTheAccount = new CollegeChecking("C  MM   Kimchi Rice 1/7/2001");
        boolean thrown = false;
        try
        {
            assertFalse(accntDB.close(validCloseTheAccount));
        }
        catch (NoSuchObjectException e)
        {
            thrown = true;
            assertTrue(thrown);
        }
    }

    @Test
    public void close_invalid_double_Closing()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validPresentAccount = new CollegeChecking("O  CC   Mark Zuck 1/1/2001 1000 0");
        Account validCloseTheAccount = new CollegeChecking("C  CC   Mark Zuck 1/1/2001");
        boolean thrown = false;
        try
        {
            accntDB.open(validPresentAccount);
            accntDB.close(validPresentAccount);
            assertFalse(accntDB.close(validCloseTheAccount));

        }
        catch (NoSuchObjectException e)
        {
            thrown = true;
            assertFalse(thrown);
        }
    }

    @Test
    public void close_valid_cleanUp_account_info_nonSavings()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validPresentAccount = new Checking("O  C  Cat Dog 8/18/1988 30 1");
        Account validCloseTheAccount = new Checking("C  C   Cat Dog 8/18/1988");
        boolean thrown = false;
        try
        {
            accntDB.open(validPresentAccount);
            accntDB.close(validCloseTheAccount);
            assertTrue(validPresentAccount.isClosed() && validPresentAccount.getBalance() == 0); //if true, then it means the account is closed
        }
        catch (NoSuchObjectException e)
        {
            thrown = true;
            assertFalse(thrown);
        }
    }

    @Test
    public void close_valid_cleanUp_account_info_Savings()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validPresentAccount = new Savings("O  S    Meow Bark 10/1/1995 1000 1");
        Account validCloseTheAccount = new Savings("C  S    Meow Bark 10/1/1995");
        boolean thrown = false;
        try
        {
            accntDB.open(validPresentAccount);
            accntDB.close(validCloseTheAccount);
            System.out.println(validPresentAccount.toString());
            assertFalse(validPresentAccount.getBalance() == 0 &&  ((Savings)validPresentAccount).isLoyalCustomer()  );
        }
        catch (NoSuchObjectException e)
        {
            thrown = true;
            assertFalse(thrown);
        }
    }

    @Test
    public void close_valid_cleanUp_account_info_MoneyMarket()
    {
        AccountDatabase accntDB = new AccountDatabase();
        Account validPresentAccount = new MoneyMarket("O  MM    Rutgers Tuition 12/1/2010 3700 1");
        Account validCloseTheAccount = new MoneyMarket("C  MM    Rutgers Tuition 12/1/2010");
        boolean thrown = false;
        try
        {
            accntDB.open(validPresentAccount);
            accntDB.close(validCloseTheAccount);
            System.out.println(validPresentAccount.toString());
            assertFalse(validPresentAccount.getBalance() == 0
                    &&  ((Savings)validPresentAccount).isLoyalCustomer()
                    &&  ((MoneyMarket)validPresentAccount).getNumOfWithdrawls()  == 0 );
        }
        catch (NoSuchObjectException e)
        {
            thrown = true;
            assertFalse(thrown);
        }
    }
}