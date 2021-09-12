Feature: TopUp Account
  This feature describes various scenarios for users adding funds to their revolut account(s)

  #As a user, I can topup my Revolut account using my debit card

  Scenario: Add money to Revolut account using debit card
    Given "Danny" has 10 euro in his euro Revolut account
    And "Danny" selects 100 euro as the topUp amount
    And  "Danny" selects his DebitCard as his topUp method
    And The PaymentService "accepts" the request
    #And  Danny selects his BankAccount as his topUp method
    When "Danny" tops up
    Then The new balance of "Danny"'s "EUR" account should now be 110


  Scenario: Add money to Revolut account using bank account
    Given "Danny" has 20 euro in his euro Revolut account
    And "Danny" selects 230 euro as the topUp amount
    And  "Danny" selects his BankAccount as his topUp method
    And The PaymentService "accepts" the request
    #And  Danny selects his BankAccount as his topUp method
    When "Danny" tops up
    Then The new balance of "Danny"'s "EUR" account should now be 250



  #ToDo implement the remaining scenarios listed below

  #To implement this scenario you will need to use data tables
    # https://cucumber.io/docs/cucumber/api/
  Scenario Outline: Add various amounts to Revolut account
    Given "Danny" has a starting balance of <startBalance> in his "EUR" account
    And "Danny" selects his DebitCard as his topUp method
    And The PaymentService "accepts" the request
    When "Danny" now tops up by <topUpAmount>
    Then The new balance of "Danny"'s "EUR" account should now be <newBalance>
    Examples:
      | startBalance| topUpAmount | newBalance  |
      | 0           | 100         | 100         |
      | 14          | 20          | 34          |
      | 23          | 30          | 53          |

  #Rule: The account balance shouldn't change if the topup payment request is rejected by the payment service

    #The scenarios below will need a payment service that accepts or rejects a request to add funds
  Scenario: Payment service rejects the request
    Given "Danny" has a starting balance of 100 in his "EUR" account
    And "Danny" selects his DebitCard as his topUp method
    And The PaymentService "rejects" the request
    When "Danny" now tops up by 60
    Then The new balance of "Danny"'s "EUR" account should now be 100


  Scenario: Payment service accepts the request
    Given "Danny" has a starting balance of 100 in his "EUR" account
    And "Danny" selects his DebitCard as his topUp method
    And The PaymentService "accepts" the request
    When "Danny" now tops up by 60
    Then The new balance of "Danny"'s "EUR" account should now be 160

  Scenario: Sending money to a friend using Revolut
    Given "Danny" has a starting balance of 100 in his "EUR" account
    And "Sean" has a starting balance of 50 in his "EUR" account
    When "Danny" transfers 20 from his "EUR" account to "Sean"'s "EUR" account
    Then The new balance of "Danny"'s "EUR" account should now be 80
    And The new balance of "Sean"'s "EUR" account should now be 70