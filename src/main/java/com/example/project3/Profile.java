package com.example.project3;

import java.util.StringTokenizer;

/**
 * This class defines the profile of the account holder.
 * Each account holder has a fname (first name), lname (last name), dob (date of birth).
 * @author Minseok Park, Amogh Sarangdhar
 */
public class Profile
{
    private String fname;
    private String lname;
    private Date dob;

    private static final int TOKEN_FNAME = 0;
    private static final int TOKEN_LNAME = 1;
    private static final int TOKEN_DOB = 2;

    /**
     * Parameterized constructor of Profile for GUI program
     * This takes two Strings, firstname and lastname, and object of Date for DOB.
     * @param fname first name of the holder.
     * @param lname last name of the holder.
     * @param dob date of birth of the holder.
     */
    public Profile(String fname, String lname, Date dob)
    {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Parameterized constructor that takes an input String from terminal.
     * Tokenizes the input string, and initializes the fields as well as passes a token for dob to Date class to further process.
     * @param inputString partially processed transaction string.
     */
    public Profile(String inputString)
    {
        int tokenCnt = 0;
        StringTokenizer st = new StringTokenizer(inputString, " ");
        while (st.hasMoreTokens())
        {
            String token = st.nextToken();
            if (tokenCnt == TOKEN_FNAME)
            {
                this.fname = token;
            }
            else if (tokenCnt == TOKEN_LNAME)
            {
                this.lname = token;
            }
            else if (tokenCnt == TOKEN_DOB)
            {
                dob = new Date(token);
            }
            tokenCnt++;
        }
    }


    /**
     * Getter for the lname (last name) of the account holder.
     * @return String lname (last name).
     */
    public String getLname()
    {
        return lname;
    }

    /**
     * Getter for the fname (first name) of the account holder.
     * @return String fname (first name).
     */
    public String getFname()
    {
        return fname;
    }

    /**
     * Getter for the dob (date of birth) of the account holder.
     * @return Date dob (date of birth).
     */
    public Date getDob()
    {
        return dob;
    }

    /**
     * String representation of Profile of the account holder.
     * @return String holder in the form of "fname lname dob"
     */
    @Override
    public String toString()
    {
        return getFname() + " " + getLname() + " " + getDob();
    }

    /**
     * Equals method that compares if two profiles are the same.
     * @param profile profile of the holder to be compared
     * @return true if the profile holder is the same person, false otherwise.
     */
    public boolean equals(Profile profile)
    {
        if ((fname).equalsIgnoreCase(profile.fname)
                && (lname).equalsIgnoreCase(profile.lname))
        {   //if the profile holder is the same person
            if (dob.compareTo(profile.dob) == 0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isValid()
    {
        if (fname.isEmpty() || fname.isBlank() || fname == null)
        {
            return false;
        }
        if (lname.isEmpty() || lname.isBlank() || lname == null)
        {
            return false;
        }
        if (!dob.isValid())
        {
            return false;
        }
        return true;
    }


    /**
     * Testbed main to test the code in the Profile class.
     * @param args
     */
    public static void main(String[] args)
    {
        Profile prof1 = new Profile("John Doe 2/19/1989");
        Profile prof2 = new Profile("John Doe 2/19/1989");
        System.out.println(prof1.equals(prof2));
        System.out.println(prof1.getFname());
        System.out.println(prof1.getLname());
        System.out.println(prof1.getDob());

    }
}
