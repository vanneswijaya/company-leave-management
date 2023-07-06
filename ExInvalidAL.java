public class ExInvalidAL extends Exception {
    public ExInvalidAL(int balance) { super("The annual leave is invalid.\nThe current balance is " + balance + " days only.\nThe employee has not taken any block leave yet.\nThe employee can take at most " + (balance - 7) + " days of non-block annual leave\nbecause of the need to reserve 7 days for a block leave."); }
}
