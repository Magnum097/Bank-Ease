package com.example.project3;

import com.sun.jdi.request.DuplicateRequestException;
import javafx.scene.control.TextArea;

import java.rmi.NoSuchObjectException;
import java.text.DecimalFormat;


/**
 * This class holds all the Accounts in an array.
 * It finds, withdraws, and deposits money into the account.
 * It also opens and closes an Account.
 * Also, prints the accounts by fees and interests, and also sorts them by Account type.
 * @author Minseok Park, Amogh Sarangdhar
 */
public class AccountDatabase {
    public Account[] accounts;
    private int numAcct;

    /**
     * Default constructor for Account Database class.
     */
    public AccountDatabase() {
        this.accounts = new Account[4];
        this.numAcct = 0;
    }

    /**
     * Getter method to return number of Accounts.
     * @return numAcct the number of accounts in the current Database.
     */
    public int getNumAcct() {
        return numAcct;
    }

    /**
     * Find the account that is in the Account[] database.
     * @param account the Account to be found.
     * @return i, the index of the Account in the Account[] array, else ACCOUNT_NOTFOUND.
     */
    private int find(Account account)
    {
        for (int i = 0; i < numAcct; i++)
        {
            if ( (accounts[i].getType()).equals(account.getType()))
            {
                if (accounts[i].equals(account) == true)
                {
                    return i;
                }
            }
        }
        return -1; //ACCOUNT_NOTFOUND
    }

    /**
     * This method grow Account[] by 4 when the array is full.
     */
    private void grow() {
        if (accounts.length == numAcct) {
            Account[] biggerArray = new Account[numAcct + 4];
            for (int i = 0; i < numAcct; i++) {
                biggerArray[i] = accounts[i];
            }
            accounts = biggerArray;
        }
    }

    /**
     * This method opens a new Account for the holder.
     * @param account takes in the Account to be opened.
     * @return true, if account is newly opened; false, if account is reopened.
     */
    public boolean open(Account account)
    {
        int accountIndx = find(account);
        if (accountIndx == -1) //ACCOUNT_NOTFOUND
        {
            if (findDoubleCheckingAcc(account) == true)
            {
                throw new DuplicateRequestException();
            }
            else
            {
                if (numAcct == accounts.length)
                {
                    grow();
                }
                accounts[numAcct] = account;
                numAcct++;
                return true; //new opening
            }
        }
        else             //the account already exist in db
        {
            if (accounts[accountIndx].isClosed() == true)
            {
                accounts[accountIndx] = account;
                return false; //reopening
            }
            else
            {
                throw new DuplicateRequestException();
            }
        }
    }

    /**
     * This method checks if a holder has both College Checking and Checking account.
     * @param account the account to be checked.
     * @return true if a holder has two Checking accounts, false otherwise.
     */
    private boolean findDoubleCheckingAcc(Account account)
    {
        if (account.getType().equals("College Checking") || account.getType().equals("Checking") )
        {
           for (int i = 0; i < numAcct; i++)
           {
                if ( ((Checking)account).isDuplicated(accounts[i]) )
                {
                    return  true;
                }
           }
           return  false;
        }
        else
        {
            return false;
        }
    }

    /**
     * This method closes the existing Account of the holder.
     * @param account Account that is to be closed
     * @return true, if the Account is closed, false otherwise.
     * @throws NoSuchObjectException
     */
    public boolean close(Account account) throws NoSuchObjectException
    {
        int target = find(account);
        if (target == -1) //ACCOUNT_NOTFOUND
        {
            throw new NoSuchObjectException(account.getHolder().toString() + " " + account.getType() + " is not in the database.\n");
        }
        else
        {
            if (accounts[target].isClosed() == true)
            {
                return false;
            }
            else
            {
                accounts[target].closed = true;
                accounts[target].setClosedBalance();
                if (accounts[target].getType().equals("Savings")
                        || accounts[target].getType().equals("Money Market"))
                {
                    if (accounts[target].getType().equals("Money Market"))
                    {
                        ((MoneyMarket)accounts[target]).resetNumofWithdrawls();
                    }
                    ((Savings)accounts[target]).isLoyalCustomer = false;
                }
                return true;
            }
        }
    }

    /**
     * This method deposits money into the Account.
     * @param account takes in Account and deposits the depositing value into the Account.
     * @throws ActionToClosedAccount
     * @throws NoSuchObjectException
     */
    public void deposit(Account account) throws ActionToClosedAccount, NoSuchObjectException {
        int target = find(account);
        if (target == -1) //ACCOUNT_NOTFOUND
        {
            throw new NoSuchObjectException(account.getHolder().toString() + " " + account.getType() + " is not in the database.\n");
        }
        else if (accounts[target].isClosed() == true)
        {
            throw new ActionToClosedAccount();
        }
        else //no error case
        {
            double deposit = account.balance;
            accounts[target].deposit(deposit);
        }
    }

    /**
     * This method withdraws money from the Account.
     * @param account takes in Account and withdraws the withdrawing value from the Account.
     * @throws ActionToClosedAccount
     * @throws NoSuchObjectException
     */
    public boolean withdraw(Account account) throws ActionToClosedAccount, NoSuchObjectException
    {
        int target = find(account);
        if (target == -1)  //ACCOUNT_NOTFOUND
        {
            throw new NoSuchObjectException(account.getHolder().toString() + " " + account.getType() + " is not in the database.\n");
        }
        else if (accounts[target].isClosed() == true)
        {
            throw new ActionToClosedAccount();
        }
        else if (accounts[target].balance >= account.balance)
        {
           accounts[target].withdraw(account.balance);
           return true;
        }
        else //insufficient fund
        {
            return false;
        }
    }

    /**
     * This method updates the balance of all the Accounts.
     */
    public void updateBalance()
    {
        for(int i = 0; i < numAcct; i++)
        {
            accounts[i].updateBalance();
        }
    }

    /**
     * This method prints out all accounts in current order of the array, Accounts[].
     */
    public void print(TextArea textArea)
    {
        for(int i = 0; i < numAcct; i++)
        {
            textArea.appendText(accounts[i].toString() + "\n");
        }
    }


    /**
     * Prints all the accounts when they are sorted by Account Type.
     */
    public void printByAccountType(TextArea textArea) {
        sortByAccountType();
        textArea.appendText("\n*list of accounts by account type.\n");
        print(textArea);

    }


    /**
     * Prints all the accounts in the database with calculated fees and monthly interest based on current balances.
     */
    public void printFeeAndInterest(TextArea textArea)
    {
        textArea.appendText("\n*list of accounts with fee and monthly interest \n");
        {
            for(int i = 0; i < numAcct; i++)
            {
                DecimalFormat df = new DecimalFormat("0.00");
                textArea.appendText(accounts[i].toString() + "::fee " + "$" + df.format(accounts[i].fee())
                        + "::monthly interest " + "$" + df.format(accounts[i].monthlyInterest()) + "\n");
            }
            textArea.appendText("*end of list*\n");
        }
    }


    /**
     * Sort all Accounts by Account types (Bubble Sort).
     * Sorting hierarchy, high to low: C, CC, MM, S.
     * The sorting hierarchy is dealt with account type code.
     */
    private void sortByAccountType()
    {
        for (int i = 0; i < numAcct; i++)
        {
            for (int j = 0; j < numAcct - i - 1; j++)
            {
                int typeA = (accounts[j].getTypeCode());
                int typeB = (accounts[j + 1]).getTypeCode();
                if (typeA > typeB)
                {
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }
}
