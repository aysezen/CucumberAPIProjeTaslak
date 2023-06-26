
Feature: It is used to list all the countries registered in the database.

  Scenario: Success Response

    Given Api kullanicisi "api/profile/allCountries" path parametreleri set eder.
    Then AllCountries icin Get request gonderilir.