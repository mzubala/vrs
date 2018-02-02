Feature: Video rental

  Background:
    Given inventory contains following items:
      | name          | category    |
      | Matrix 11     | NEW_RELEASE |
      | Spider Man    | REGULAR     |
      | Spider Man 2  | REGULAR     |
      | Out of Africa | OLD_FILM    |
    And there is a customer "John"
    And there is a customer "Jane"

  Scenario: Calculating rental price
    Then customer gets following prices:
      | name          | length | money     |
      | Matrix 11     | 1      | 40.00 SEK |
      | Spider Man    | 5      | 90.00 SEK |
      | Spider Man 2  | 2      | 30.00 SEK |
      | Out of Africa | 7      | 90.00 SEK |
    And the total price is "250.00 SEK"

  Scenario: Renting movies
    When "John" rents following movies:
      | name       | length |
      | Matrix 11  | 1      |
      | Spider Man | 5      |
    Then the movies are marked as rent

  Scenario: Keeping track of bonus points
    When "John" rents following movies:
      | name          | length |
      | Matrix 11     | 1      |
      | Spider Man    | 5      |
      | Out of Africa | 2      |
    Then "John" has 4 bonus points

  Scenario: Renting unavailable movies
    When "John" rents following movies:
      | name      | length |
      | Matrix 11 | 1      |
    And "Jane" rents following movies:
      | name      | length |
      | Matrix 11 | 3      |
    Then the system responds that movies are unavailable

  Scenario: Calculating surcharges
    When "John" rents following movies:
      | name       | length |
      | Matrix 11  | 3      |
      | Spider Man | 4      |
    And 5 days pass
    Then the system calculates following surcharges:
      | name       | length | money     |
      | Matrix 11  | 2      | 80.00 SEK |
      | Spider Man | 1      | 30.00 SEK |
    And the total price is "110.00 SEK"

  Scenario: Returning movies
    When "John" rents following movies:
      | name       | length |
      | Matrix 11  | 3      |
      | Spider Man | 4      |
    And returns following movies:
      | Matrix 11 |
    Then "Matrix 11" is available for rent

  Scenario: Calculating surcharges when some movie already returned
    When "John" rents following movies:
      | name       | length |
      | Matrix 11  | 3      |
      | Spider Man | 4      |
    And 5 days pass
    And returns following movies:
      | Matrix 11 |
    Then the system calculates following surcharges:
      | name       | length | money     |
      | Spider Man | 1      | 30.00 SEK |
    And the total price is "30.00 SEK"
