@orders
  Feature: Validate Web Ordering


    Scenario Outline: Place a single item in the shopping cart
      Given The user is on the Home Page
      And The user provides the username as "<username>" and password as "<password>"
      And The user clicks the 'Login' button
      And The user chooses a "<item>" by clicking 'Add To Cart'
      And The user clicks on the shopping cart
      Then There should be "1" items in the shopping cart
    Examples:
      |username|password|item|
      |standard_user  |secret_sauce |Sauce Labs Backpack|



    Scenario Outline: Place multiple items in the shopping cart
      Given The user is on the Home Page
      And The user provides the username as "<username>" and password as "<password>"
      And The user clicks the 'Login' button
      And The user selects
        |Sauce Labs Backpack    |
        |Sauce Labs Bolt T-Shirt|
        |Sauce Labs Onesie      |
      And The user clicks on the shopping cart
      Then There should be "3" items in the shopping cart
      Examples:
        |username|password|
        |standard_user  |secret_sauce |



