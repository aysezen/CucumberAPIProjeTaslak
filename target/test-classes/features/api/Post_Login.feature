Feature: You can log in to the system with your email and password

  Scenario: Success Response

    Given Api kullanicisi "api/login" path parametreleri set eder.
    Then Login icin "email" ve "password" girilir.
    Then Login icin Post request gonderilir.