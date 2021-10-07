@ACHWorkflow
Feature: Verify Google page

  Scenario: SC_01_Verify google page
    Given user launch the google page application
#    When user login with UserName as "edge" and Password as "dummy"
#    Then user should be able to see the homepage
#
#  Scenario: SC_02_Verify the logged in user name in homepage
#    When user is on homepage
#    Then verify the message "Welcome edge!!"
#
#  Scenario: SC_03_Verify the Navigation menu items
#    When user is on homepage
#    Then verify Navigation menu item "Home" is displayed
#    And verify Navigation menu item "Todos" is displayed
#
#  Scenario: SC_04_Verify the Pactera Edge tab functionality
#    When user is on homepage
#    And user click on "Pactera Edge"
#    Then PacteraEdge website should be displayed
#    And user click on back button
#    And user should be able to see the homepage
#
#  Scenario Outline: SC_05_verify Add a todo list functionality
#    When user is on homepage
#    And user click on "Todos"
#    And user click on "Add a Todo"
#    And Enter the Todo list details
#      | Description   | TargetDate   | IsDone   |
#      | <Description> | <TargetDate> | <IsDone> |
#    And user click on Add button
#    Then verify the Add a todo list functionality
#      | Expected_Result   |
#      | <Expected_Result> |
#    And user click on "Todos"
#
#    Examples:
#      | Description       | TargetDate | IsDone | Expected_Result                 |
#      | Description_TestJ | 25/05/2021 | Yes    | Success                         |
#      | Test              | 25/05/2021 | Yes    | Enter at least 10 Characters... |
#
#  Scenario Outline: SC_06_verify Update Todo list functionality
#    When user is on homepage
#    And user click on "Todos"
#    And user update the "<Description_Old>" todo
#    And Enter the Todo list details
#      | Description   | TargetDate   | IsDone   |
#      | <Description> | <TargetDate> | <IsDone> |
#    And user click on Add button
#    Then verify the user is able to "<Description>" the todo list
#    Examples:
#      | Description_Old   | Description         | TargetDate | IsDone |
#      | Description_TestJ | Description_TestJ12 | 25/05/2021 | Yes    |
#
#  Scenario Outline: SC_07_verify DeleteTodo list functionality
#    When user is on homepage
#    And user click on "Todos"
#    And user delete the "<Description>" todo
#    Then verify the user is able to delete "<Description>" from the todo list
#    Examples:
#      | Description   |
#      | Testing 12345 |
#
#  Scenario: SC_08_Verify logout functionality
#    When user is on homepage
#    And user click on "Logout"
#    Then user should be able to see the login page

    #target grater than the system date ---