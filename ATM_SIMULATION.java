
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {

    public LocalDate curdate; //TO STORE TRANSACTION DATE
    public LocalTime curtime; //TO STORE TRANSACTION TIME 
    public String transactionType; //TO STORE TRANSACTION TYPE
    public float before_transaction_bal; // TO STORE BALANCE BEFORE TRANSACTION
    public float after_transaction_bal; // TO STORE BALANCE AFTER TRANSACTION

    public Transaction(LocalDate curdate, LocalTime curtime, String transactionType, float amount) {
        this.curdate = curdate;
        this.curtime = curtime;
        this.transactionType = transactionType;
        before_transaction_bal = ATM_SIMULATION.balance;

        //CHECKS FOR CREDIT TYPE TRANSACTION OR DEBIT TYPE TRANSACTION
        if ("Credit".equals(transactionType)) { 

            ATM_SIMULATION.balance += amount; //UPDATING THE BALANCE
            after_transaction_bal = ATM_SIMULATION.balance; // STORING BALANCE AFTER TRANSACTION FOR TRANSACTION HISTORY
        } else {
            ATM_SIMULATION.balance -= amount; //UPDATING THE BALANCE
            after_transaction_bal = ATM_SIMULATION.balance; // STORING BALANCE AFTER TRANSACTION FOR TRANSACTION HISTORY
        }
    }
}

// PUBLIC CLASS TO CARRY ALL THE FUNCTIONALITIES OF ATM
public class ATM_SIMULATION {

    public static int Pin = 0;
    public static int choice;
    public static float balance = 0;
    public static List<Transaction> transactions = new ArrayList<>();

    public static void Balance_Enquiry() {
        // BALANCE ENQUIRY
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your Pin number: ");
        
        //PIN CHECK
        if (sc.nextInt() == Pin) {
            System.out.println("YOUR BALANCE AMOUNT: " + balance);
            // PRINTS BALANCE AMOUNT
        } else {
            System.out.println("INCORRECT PIN!");
            // DEPICTS AS INVALID PIN IN CASE OF MISMATCH
        }
    }

    public static void Cash_Deposit() {

        // CASH DEPOSIT
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your Pin number: ");

        // PIN CHECK
        if (sc.nextInt() == Pin) {
            System.out.println("PLEASE ENTER THE AMOUNT TO BE DEPOSITED: ");
            float amount = sc.nextFloat(); // INTAKES THE AMOUNT TO BE DEPOSITED
            
            String transactionType = "Credit"; //DEFINES TRANSACTION TYPE AS CREDIT TO DEPOSIT

            
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), transactionType, amount); //CALLS TRANSACTION FUNCTION TO UPDATE BALANCE AND UPDATE TRANSACTION HISTORY

            
            transactions.add(transaction); // ADDS TO TRANSACTION HISTORY
 
            System.out.println("AMOUNT OF Rs" + amount + " DEPOSITED SUCCESFULLY!");
        } else {
            System.out.println("INCORRECT PIN!");
        }
    }

    public static void Cash_Withdrawl() {
        //CASH WITHDRWAL
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your Pin number: ");
        int enteredPin = sc.nextInt();

        //PIN CHECK
        if (enteredPin == Pin) {
            System.out.println("PLEASE ENTER THE AMOUNT TO BE WITHDRAWED: ");
            float amount = sc.nextFloat();//INTAKES AMOUNT TO BE DEBITED
            
            if (amount > balance) {
                
                System.out.println("INSUFFICIENT FUNDS"); // DEPICTS INSUFFICIENT FUNDS IF BALANCE IS LESS THAN AMOUNT TO BE DEBITED
            } else {
                String transactionType = "Debit"; //DEFINES TRANSACTION TYPE AS DEBIT TO DEPOSIT

                Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), transactionType, amount); //CALLS TRANSACTION FUNCTION TO UPDATE BALANCE AND UPDATE TRANSACTION HISTORY

                transactions.add(transaction); // ADDS TO TRANSACTION HISTORY
                System.out.println("AMOUNT OF " + amount + " WITHDRWAN SUCCESSFULLY!\nPLEASE COLLECT THE CASH!"); 
            }
        } else {
            System.out.println("INCORRECT PIN!");
        }

    }

    public static void Pin_Change() {
        Scanner sc = new Scanner(System.in);
        System.out.println("PLEASE ENTER YOUR PIN NUMBER: ");
        int enterPin = sc.nextInt();

        //PIN CHECK
        if (enterPin == Pin) {

            int new_pin, new_confirm_pin;
            do {
                System.out.println("PLEASE ENTER YOUR NEW PIN NUMBER: "); //NEW PIN
                new_pin = sc.nextInt();
                System.out.println("PLEASE CONFIRM YOUR NEW PIN NUMBER: "); //CONFIRM NEW PIN
                new_confirm_pin = sc.nextInt();

                // CONFIRMS BOTH ENTERED PINS ARE SAME
                if (new_pin == new_confirm_pin) {
                    Pin = new_pin;
                    System.out.println("PIN CHANGED SUCCESSFULLY");
                } else {
                    System.out.println("PIN MISMATCHED \n  PLEASE RE-DO AGAIN ");
                    Pin = enterPin; // RESETS THE PIN TO OLD VALUE , WHEN PINS DON'T MATCH
                }
            } while (new_pin != new_confirm_pin); // CONTINUES THE LOOP UNTIL THE BOTH ENETERED PIN AND CONFIRMATION PIN IS SAME

        } else {
            System.out.println("Incorrect Pin number: ");
        }
    }

    public static void Transaction_History() {

        // TO SHOW TRANSACTION HISTORY
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your Pin number: ");

        //PIN CHECK
        if (sc.nextInt() == Pin) {
            System.out.println("Transaction Date\tTransaction Time\tType of Transaction\tInitial Balance\t\tFinal Balance");
            for (Transaction transaction : transactions) { //PRINTS EACH TRANSACTIONS STORED IN THE LIST
                
                System.out.println(transaction.curdate + "\t\t" + transaction.curtime + "\t" + transaction.transactionType + "\t\t" + transaction.before_transaction_bal + "\t\t\t" + transaction.after_transaction_bal);
            }
        } else {
            System.out.println("Wrong Pin entered! ");
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        for (;;) {

            // INITIAL PIN SETUP
            System.out.println("Please set your Pin number: ");
            Pin = sc.nextInt();
            System.out.println("Please confirm your pin:");
            int some_pin = sc.nextInt();
            if (Pin == some_pin) {
                System.out.println("Pin set successfully!");
                //LOOP BREAKS WHEN PIN IS SETUP SUCCESSFULLY
                break;
            } else {
                //LOOP IS CONTINUED WHEN PIN CONFIRMATION GOES WRONG
                System.out.println("Wrong pin! Please set it again!");
            }
        }
        for (;;) {
            
            // USER CHOICE LIST
            System.out.print("\nSERVICES:-\n1. BALANCE ENQUIRY \n2. CASH WITHDRAWL \n3. CASH DEPOSIT \n4. PIN CHANGE \n5. TRANSACTION HISTORY\n6. EXIT\n ENTER YOUR CHOICE: ");
            choice = sc.nextInt();
            System.out.println();
            if (choice == 6) {
                break;
            }

            //FUNCTION AS PER USER CHOICE 
            // CHOICE 6 = EXIT
            switch (choice) {
                case 1 ->
                    Balance_Enquiry(); // TO ENQUIRE ABOUT THE USER BALANCE
                case 2 ->
                    Cash_Withdrawl(); // TO WITHDRAW CERTAIN AMOUNT OF CASH
                case 3 ->
                    Cash_Deposit(); // TO DEPOSIT CERTAIN AMOUNT OF CASH
                case 4 ->
                    Pin_Change(); // TO CHANGE THE PIN 
                case 5 ->
                    Transaction_History(); // TO GET THE TRANSACTION HISTORY
                default ->
                    System.out.println("INVALID CHOICE!!"); // RESULTS IN INVALID CHOICE FOR ANY INPUT OTHER THAN 1-6
            }
        }
    }
}
