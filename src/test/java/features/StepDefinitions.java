package features;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import revolut.Account;
import revolut.PaymentService;
import revolut.Person;

import java.util.*;


public class StepDefinitions {

    private double topUpAmount;
    //private String topUpMethod;
    PaymentService topUpMethod;
    HashMap<String, Person> people = new HashMap<String, Person>();
    boolean transactionValidated;

    @Before//Before hooks run before the first step in each scenario
    public void setUp(){
        //We can use this to setup test data for each scenario
        people.put("Danny", new Person("Danny"));
        people.put("Sean", new Person("Sean"));
    }
    @Given("{string} has {double} euro in his euro Revolut account")
    public void hasEuroInHisEuroRevolutAccount(String name, double startingBalance) {
        //System.out.println("Danny has starting account balance of: " + String.valueOf(startingBalance));
        people.get(name).setAccountBalance(startingBalance, "EUR");
        //double newAccountBalance = danny.getAccountBalance("EUR");
        //System.out.println("Danny's new account balance is: " + String.valueOf(newAccountBalance));
    }

    @Given("{string} selects {double} euro as the topUp amount")
    public void selects_euro_as_the_top_up_amount(String name, double topUpAmount) {
        // Write code here that turns the phrase above into concrete actions
        this.topUpAmount = topUpAmount;
        //throw new io.cucumber.java.PendingException();
    }

    //@Given("Danny selects his {word} as his topUp method")
    @Given("{string} selects his {paymentService} as his topUp method")
    //public void danny_selects_his_debit_card_as_his_top_up_method(String topUpSource) {
    public void selects_his_debit_card_as_his_top_up_method(String name, PaymentService topUpSource) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("The selected payment service type was " + topUpSource.getType());
        topUpMethod = topUpSource;
    }

    @When("{string} tops up")
    public void tops_up(String name) {
        // Write code here that turns the phrase above into concrete actions
        if (transactionValidated) {
            people.get(name).getAccount("EUR").addFunds(topUpAmount);
        }
        transactionValidated = false;
        //throw new io.cucumber.java.PendingException();
    }




    @Given("{string} has a starting balance of {double} in his {string} account")
    public void danny_has_a_starting_balance_of_in_his_account(String name, double startingBalance, String accCurrency) {
        // Write code here that turns the phrase above into concrete actions
        people.get(name).setAccountBalance(startingBalance, accCurrency);
    }

    @When("{string} now tops up by {int}")
    public void danny_now_tops_up_by(String name, double topUpAmount) {
        // Write code here that turns the phrase above into concrete actions
        if (transactionValidated) {
            people.get(name).getAccount("EUR").addFunds(topUpAmount);
        }
        transactionValidated = false;

    }

    @Then("The new balance of {string}'s {string} account should now be {double}")
    public void the_new_balance_of_his_account_should_now_be(String name, String accCurrency, double expectedResult) {
        // Write code here that turns the phrase above into concrete actions
        double actualResult = people.get(name).getAccount(accCurrency).getBalance();
        //Assert
        Assert.assertEquals(expectedResult, actualResult,0);
    }


    @Given("The PaymentService {string} the request")
    public void the_payment_service_the_request(String string) {
        // Write code here that turns the phrase above into concrete actions
        if (string.equals("accepts")){
            transactionValidated = topUpMethod.acceptRequest();
        }
        else{
            transactionValidated = topUpMethod.rejectRequest();
        }
    }

    @When("{string} transfers {double} from his {string} account to {string}'s {string} account")
    public void transfers_from_his_account_to_s_account(String sender, double amount, String senderAcc,
                                                        String receiver, String receiverAcc) {
        people.get(sender).getAccount(senderAcc).removeFunds(amount);
        people.get(receiver).getAccount(receiverAcc).addFunds(amount);


    }



}
