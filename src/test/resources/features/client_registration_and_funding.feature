Feature: Client registration and wallet funding

  Scenario: Client registration, personal data, wallet funding, and balance check
    Given a client registration payload exists
    When the client is registered
    And the client submits personal data
    And funds of 100.0 are added to the client's wallet
    Then the client's balance should be 100.0
    Then the payments list should contain 1 item

  Scenario: Client makes a repayment and balance is increased
    Given a client registration payload exists
    When the client is registered
    And the client submits personal data
    And funds of 100.0 are added to the client's wallet
    And a repayment of 30.0 is made for the client
    Then the client's balance should be 130.0
    Then the payments list should contain 2 items
