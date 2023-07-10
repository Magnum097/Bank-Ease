package com.example.project3;

import com.sun.jdi.request.DuplicateRequestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.rmi.NoSuchObjectException;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * BankTellerController which is used to initialize the UI elements of the BankTellerMain class
 * @author Minseok Park, Amogh Sarangdhar
 */
public class BankTellerController
{

    AccountDatabase accntDB = new AccountDatabase();

    @FXML
    private TextField amount_WD;

    @FXML
    private Button button_Close, button_Deposit, button_Open, button_PI, button_PT, button_Print, button_UB, button_Withdraw;

    @FXML
    private CheckBox cButton_loyal;

    @FXML
    private ToggleGroup collegeCodes;

    @FXML
    private DatePicker date_OC, date_WD;

    @FXML
    private TextField fname_OC, fname_WD;

    @FXML
    private TextField lname_OC, lname_WD, initialDeposit;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private RadioButton rButton_CC_OC, rButton_C_OC, rButton_MM_OC, rButton_S_OC;

    @FXML
    private RadioButton rButton_CC_WD, rButton_C_WD, rButton_MM_WD, rButton_S_WD;

    @FXML
    private RadioButton rButton_NB, rButton_NWK, rButton_CMD;

    @FXML
    private ToggleGroup tgAccountType_OC;

    @FXML
    private ToggleGroup tgAccountType_WD;

    /**
     * Trigger deposit money to an account by requested amount, when Deposit button clicked.
     * @param event Action event to deposit money into the account.
     */
    @FXML
    void trigger_Deposit(ActionEvent event)
    {
        Account transaction = null;
        try
        {
            if (isFullyInputted_WD() == true)
            {
                transaction = accountCreator_WD();
                if (transaction.isValid_WD())
                {
                    accntDB.deposit(transaction);
                    outputTextArea.appendText("Deposit successfully completed! Balance is also updated. \n");
                }
                else
                {
                    invalidInfoSearcher_D(transaction);
                }
            }
            else
            {
                outputTextArea.appendText("Missing data for depositing into the account. \n");
            }
        }
        catch (ActionToClosedAccount e)
        {
            outputTextArea.appendText("You cannot deposit to a closed account. \n");
        }
        catch (NoSuchObjectException e)
        {
            outputTextArea.appendText(e.getMessage() + " \n");
        }
        catch (NumberFormatException e)
        {
            outputTextArea.appendText("Not a valid amount. \n");
        }
        catch (NoSuchElementException e) //for debugging. This should never happen.
        {
            outputTextArea.appendText("Debug! Missing data for depositing into the account. \n");
        }
    }

    /**
     * Trigger printing out all accounts with their fees and interests in the database, when "Calculate Interests and Fee" button clicked.
     * @param event Action event to print accounts by fees and interests.
     */
    @FXML
    void trigger_PI(ActionEvent event)
    {
        if (accntDB.getNumAcct() <= 0)
        {
            outputTextArea.appendText("Account Database is empty!\n");
        }
        else
        {
            accntDB.printFeeAndInterest(outputTextArea);
        }
    }

    /**
     * Trigger printing out all accounts in the database in order of account types, when "Print All Accounts by Types" button clicked
     * The Order is: Checking -> College Checking -> Money Market -> Savings.
     * @param event Action event to print accounts by their Types.
     */
    @FXML
    void trigger_PT(ActionEvent event)
    {
        if (accntDB.getNumAcct() <= 0)
        {
            outputTextArea.appendText("Account Database is empty!\n");
        }
        else
        {
            accntDB.printByAccountType(outputTextArea);
            outputTextArea.appendText("*end of list.\n");
        }
    }

    /**
     * Trigger printing out all accounts in the database, when "Print All Accounts" button clicked
     * @param event Action event to print list of accounts.
     */
    @FXML
    void trigger_Print(ActionEvent event)
    {
        if (accntDB.getNumAcct() <= 0)
        {
            outputTextArea.appendText("Account Database is empty!\n");
        }
        else
        {
            outputTextArea.appendText("\n*list of accounts in the database*\n");
            accntDB.print(outputTextArea);
            outputTextArea.appendText("*end of list*\n");
        }
    }

    /**
     * Trigger update balance applying fees and interests of all accounts, when "Update Balances" button clicked
     * @param event Action event to update balances of the account.
     */
    @FXML
    void trigger_UB(ActionEvent event)
    {
        if (accntDB.getNumAcct() <= 0)
        {
            outputTextArea.appendText("Account Database is empty to update.\n");
        }
        else
        {
            accntDB.updateBalance();
            outputTextArea.appendText("Balances are upto date.\n");

        }
    }

    /**
     * Trigger withdraw money from an account by requested amount, when withdraw button is clicked.
     * @param event Action event to withdraw money from the account.
     */
    @FXML
    void trigger_Withdraw(ActionEvent event)
    {
        Account transaction = null;
        try
        {
            if (isFullyInputted_WD() == true)
            {
                transaction = accountCreator_WD();
                if (transaction.isValid_WD())
                {
                    if(accntDB.withdraw(transaction))
                    {
                        outputTextArea.appendText("Withdraw successfully completed! Balance is also updated.\n");
                    }
                    else
                    {
                        outputTextArea.appendText("The account has insufficient fund to withdraw.\n");
                    }
                }
                else
                {
                    invalidInfoSearcher_W(transaction);
                }
            }
            else
            {
                outputTextArea.appendText("Missing data for withdrawing from the account. \n");
            }
        }
        catch (ActionToClosedAccount e)
        {
            outputTextArea.appendText("You cannot withdraw from a closed account.\n");
        }
        catch (NoSuchObjectException e)
        {
            outputTextArea.appendText(e.getMessage() + "\n");
        }
        catch (NumberFormatException e)
        {
            outputTextArea.appendText("Not a valid amount.\n");
        }
        catch (NoSuchElementException e) //for debugging. This should never happen.
        {
            outputTextArea.appendText("Debug! Missing data for opening an account.\n");
        }

    }

    /**
     * Control and restrain options on Open/Close tab depends on the user's action.
     * @param event Action event to control open/close tabs.
     */
    @FXML
    void trigger_accntDetail(ActionEvent event)
    {
        if (rButton_CC_OC.isSelected())
        {
            collegeCodes.getToggles().forEach(toggle ->
            {
                RadioButton button = (RadioButton) toggle;
                button.setDisable(false);
            });
            cButton_loyal.setDisable(true);
            cButton_loyal.setSelected(false);
        }
        else if (rButton_S_OC.isSelected())
        {
            collegeCodes.getToggles().forEach(toggle ->
            {
                RadioButton button = (RadioButton) toggle;
                button.setDisable(true);
                button.setSelected(false);
            });
            cButton_loyal.setDisable(false);
        }
        else if (rButton_MM_OC.isSelected())
        {
            collegeCodes.getToggles().forEach(toggle ->
            {
                RadioButton button = (RadioButton) toggle;
                button.setDisable(true);
                button.setSelected(false);
            });
            cButton_loyal.setSelected(true);
            cButton_loyal.setDisable(true);
        }
        else
        {
            collegeCodes.getToggles().forEach(toggle ->
            {
                RadioButton button = (RadioButton) toggle;
                button.setDisable(true);
                button.setSelected(false);
            });
            cButton_loyal.setDisable(true);
            cButton_loyal.setSelected(false);
        }
    }


    /**
     * Trigger and initiate closing an account when close button is clicked.
     * @param event Action event to close an account.
     */
    @FXML
    void trigger_closeAccount(ActionEvent event)
    {
        Account transaction = null;
        try
        {
            if(isFullyInputted_OC() == true)
            {
                transaction = accountCreator_C();
                if (transaction.isValid_C())
                {
                    if (accntDB.close(transaction) == true)
                    {
                        outputTextArea.appendText(transaction.getType() + " account of " + transaction.getHolder().toString() + " has been successfully closed. \n");
                    }
                    else
                    {
                        outputTextArea.appendText(transaction.getType() + " account of " + transaction.getHolder().toString() + " is closed already.\n");
                    }
                }
                else
                {
                    invalidInfoSearcher_C(transaction);
                }
            }
            else
            {
                outputTextArea.appendText("Missing data for closing an account. \n");
            }

        }
        catch (NoSuchObjectException e)
        {
            outputTextArea.appendText(e.getMessage());
        }
        catch (NoSuchElementException e) //for debugging. This should never happen.
        {
            outputTextArea.appendText("Debug! Missing data for closing an account.\n");
        }
    }

    /**
     * Trigger and initiate opening an account when open button is clicked.
     * @param event Action event to open an account.
     */
    @FXML
    void trigger_openAccount(ActionEvent event)
    {
        Account transaction = null;
        try
        {
            if (isFullyInputted_OC() == true)
            {
                transaction = accountCreator_O();
                if (transaction.isValid_O() == true)
                {
                    if (accntDB.open(transaction) == true)
                    {
                        outputTextArea.appendText(transaction.getType() + " account of " + transaction.getHolder().toString() + " is newly opened!\n");
                    }
                    else
                    {
                        outputTextArea.appendText(transaction.getType() + " account of " + transaction.getHolder().toString() + " has been REopened!\n");
                    }
                }
                else
                {
                    outputTextArea.appendText("ERROR occurs to open an account!:\n");
                    invalidInfoSearcher_O(transaction);
                    outputTextArea.appendText("End of list of error(s).\n");
                }
            }
            else
            {
                outputTextArea.appendText("Missing data for opening an account. \n");
            }
        }
        catch (DuplicateRequestException e)
        {
            outputTextArea.appendText(transaction.getHolder().toString() + " already has same type of account in the database.\n");
        }
        catch (NoSuchElementException e) //for debugging. This should never happen.
        {
            outputTextArea.appendText("Debug! Missing data for opening an account.\n");
        }
        catch (NumberFormatException e)
        {
            outputTextArea.appendText("Not a valid amount for initial deposit\n");
        }
    }

    /**
     * Checks to see if all the fields in the Open/Close tab are fully inputted.
     * @return true, if all the fields are complete, false otherwise.
     */
    private boolean isFullyInputted_OC()
    {
        if(fname_OC.getText().isBlank() || lname_OC.getText().isBlank() || date_OC.getValue() == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Checks to see if all the fields in the Deposit/Withdraw tab are fully inputted.
     * @return true, if all the fields are complete, false otherwise.
     */
    private boolean isFullyInputted_WD()
    {
        if(fname_WD.getText().isBlank() || lname_WD.getText().isBlank() || date_WD.getValue() == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Search and print out errors of the current opening transaction.
     * @param account Account info to be checked for validity.
     */
    private void invalidInfoSearcher_O(Account account) //initiate where we got wrong.
    {
        if (!account.holder.getDob().isValid())
        {
            outputTextArea.appendText("The date of birth is today or future!\n");
        }
        if (account.getBalance() <= 0)
        {
            outputTextArea.appendText("Negative or zero is not valid for initial deposit!\n");
        }
        else if (account.getType().equals("Savings") && account.getBalance() < Savings.MIN_BALANCE)
        {
            outputTextArea.appendText("Minimum amount of initial deposit for Savings account is 300!\n");
        }
        if (account.getType().equals("Money Market") && account.getBalance() < MoneyMarket.MIN_BALANCE)
        {
            outputTextArea.appendText("Minimum amount of initial deposit for Money Market account is 2500!\n");
        }
        if (account.getType().equals("College Checking") && !((CollegeChecking) account).isValidCollegeCode())
        {
            outputTextArea.appendText("Select one of the colleges in the option!\n");
        }
    }

    /**
     * Search and print out errors of the current closing transaction.
     * @param account Account info to be checked for validity.
     */
    private void invalidInfoSearcher_C(Account account)
    {
        if (!account.holder.getDob().isValid())
        {
            outputTextArea.appendText("The date of birth is today or future!\n");
        }
    }

    /**
     * Create an account based on input info for opening an account.
     * @return Account, current transaction for opening.
     */
    private Account accountCreator_O()
    {
        Account result = null;
        String fname = fname_OC.getText();
        String lname = lname_OC.getText();
        String dob = "";
        double balance = -1.0;
        if (date_OC.getValue() != null)
        {
            dob = date_OC.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        if (initialDeposit.getText() != null)
        {
            balance = Double.parseDouble(initialDeposit.getText());
        }
        if (getAccountType_OC().equals("C")) //C do not need appendix token.
        {
            result = new Checking(fname, lname, dob, balance);
        }
        else if (getAccountType_OC().equals("CC"))
        {
            result = new CollegeChecking(fname, lname, dob, balance, getCollegeCode_O());
        }
        else if (getAccountType_OC().equals("S"))
        {
            if (cButton_loyal.isSelected())
            {
                result = new Savings(fname, lname, dob, balance, true);
            }
            else
            {
                result = new Savings(fname, lname, dob, balance, false);
            }
        }
        else if (getAccountType_OC().equals("MM")) //MM do not need appendix token.
        {
            result = new MoneyMarket(fname, lname, dob, balance);
        }
        return result;
    }

    /**
     * Create an account based on input info for closing an account.
     * @return account, current transaction for opening.
     */
    private Account accountCreator_C()
    {
        Account result = null;
        String fname = fname_OC.getText();
        String lname = lname_OC.getText();
        String dob = "";
        if (date_OC.getValue() != null)
        {
            dob = date_OC.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        if (getAccountType_OC().equals("C")) //C do not need appendix token.
        {
            result = new Checking(fname, lname, dob, 0);
        }
        else if (getAccountType_OC().equals("CC"))
        {
            result = new CollegeChecking(fname, lname, dob, 0, getCollegeCode_O());
        }
        else if (getAccountType_OC().equals("S"))
        {
            if (cButton_loyal.isSelected())
            {
                result = new Savings(fname, lname, dob, 0, true);
            }
            else
            {
                result = new Savings(fname, lname, dob, 0, false);
            }
        }
        else if (getAccountType_OC().equals("MM")) //MM do not need appendix token.
        {
            result = new MoneyMarket(fname, lname, dob, 0);
        }
        return result;
    }

    /**
     * Search and print out errors of the current withdrawing transaction.
     * @param account Account info to be checked for validity.
     */
    private void invalidInfoSearcher_W(Account account)
    {
        if (!account.holder.getDob().isValid())
        {
            outputTextArea.appendText("The date of birth is today or future!\n");
        }
        if (account.getBalance() <= 0)
        {
            outputTextArea.appendText("Negative or zero is not valid for withdrawals!\n");
        }
    }

    /**
     * Search and print out errors of the current depositing transaction.
     * @param account Account info to be checked for validity.
     */
    private void invalidInfoSearcher_D(Account account)
    {
        if (!account.holder.getDob().isValid())
        {
            outputTextArea.appendText("The date of birth is today or future!\n");
        }
        if (account.getBalance() <= 0)
        {
            outputTextArea.appendText("Negative or zero is not valid for deposits!\n");
        }
    }


    /**
     * Read what type of account is selected in the Open/Close page by the user
     * @return "C";"CC";"S";"MM" based on what type of account is selected.
     */
    private String getAccountType_OC()
    {
        if (rButton_C_OC.isSelected())
        {
            return "C";
        }
        else if (rButton_CC_OC.isSelected())
        {
            return "CC";
        }
        else if (rButton_S_OC.isSelected())
        {
            return "S";
        }
        else if (rButton_MM_OC.isSelected())
        {
            return "MM";
        }
        else
        {
            return null;
        }
    }

    /**
     * Read what type of account is selected in the Deposit/Withdraw page by the user
     * @return "C";"CC";"S";"MM" based on what type of account is selected.
     */
    private String getAccountType_WD()
    {
        if (rButton_C_WD.isSelected())
        {
            return "C";
        }
        else if (rButton_CC_WD.isSelected())
        {
            return "CC";
        }
        else if (rButton_S_WD.isSelected())
        {
            return "S";
        }
        else if (rButton_MM_WD.isSelected())
        {
            return "MM";
        }
        else
        {
            return null;
        }
    }

    /**
     * Read which college is selected by the user.
     * @return 0, NB; 1, NWK; 2, CMD; -1, if nothing selected.
     */
    private int getCollegeCode_O()
    {
        if (rButton_NB.isSelected())
        {
            return CollegeChecking.NEWBRUNSWICK_CODE; //0
        }
        else if (rButton_NWK.isSelected())
        {
            return CollegeChecking.NEWARK_CODE; //1
        }
        else if (rButton_CMD.isSelected())
        {
            return CollegeChecking.NEWARK_CODE; //2
        }
        else
        {
            return CollegeChecking.INVALID_COLLEGE_CODE; //-1
        }
    }

    /**
     * Create an account based on input info for withdrawing/depositing an account.
     * @return account, current transaction for withdrawing/depositing.
     */
    private Account accountCreator_WD()
    {
        Account result = null;
        String fname = fname_WD.getText();
        String lname = lname_WD.getText();
        String dob = "";
        double balance = -1.0; //magic number??
        if (date_WD.getValue() != null)
        {
            dob = date_WD.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        if (amount_WD.getText() != null)
        {
            balance = Double.parseDouble(amount_WD.getText());
        }
        if (getAccountType_WD().equals("C"))
        {
            result = new Checking(fname, lname, dob, balance);
        }
        else if (getAccountType_WD().equals("CC"))
        {
            result = new CollegeChecking(fname, lname, dob, balance, CollegeChecking.INVALID_COLLEGE_CODE);
        }
        else if (getAccountType_WD().equals("S"))
        {
            result = new Savings(fname, lname, dob, balance, false);
        }
        else if (getAccountType_WD().equals("MM"))
        {
            result = new MoneyMarket(fname, lname, dob, balance);
        }
        return result;
    }

}
