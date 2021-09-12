package revolut;


import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        HashMap<String, Person> people = new HashMap<String, Person>();
        people.put("Danny", new Person("Danny"));
        people.put("Sean", new Person("Sean"));

        String sender = "Danny";
        double amount = 20;
        String senderAcc = "EUR";
        String receiver = "Sean";
        String receiverAcc = "EUR";


        people.get(sender).getAccount(senderAcc).removeFunds(amount);
        people.get(receiver).getAccount(receiver).addFunds(amount);

    }
}
