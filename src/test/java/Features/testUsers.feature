@UserFeatureTesting
Feature: Verify User functionality
  Background: Delete user if already exists
    Given Set the base url
    When Delete the user with id 'user-121355' if already exists before starting test

  @CreatingAndDeletingUser
  Scenario Outline: Verify if the user can be created with mandatory valid credentials and can be deleted
    Then Verify that a new user with <id> can be created with valid mandatory details with <inputType>
    And Verify that a newly created user<id> is saved successfully and returns <SuccessCode>
    And Verify that user with <id> can be deleted successfully

    Examples:
      |id|SuccessCode|inputType|
      |user-121355|200|OnlyValidMandatory|


  @CreateUserWithInvalidCredentials
  Scenario Outline: Verify if the user can not be created with mandatory Invalid credentials
    When Create a new user with <id> with Invalid mandatory details
    Then Verify that user <id> with invalid details is not saved and returns <InternalServerStatusCode>

    Examples:
      |id|InternalServerStatusCode|
      | user-121355|500|

  @CreateUserWithOptionalInputs
  Scenario Outline: Verify if the user can be created along with Optional Inputs
    Then Verify that a new user with <id> can be created with valid mandatory details along with optional values<inputType>
    Then Verify that a newly created user<id> is saved successfully and returns <SuccessCode>
    And Verify that user with <id> can be deleted successfully

    Examples:
      |id|SuccessCode|inputType|
      | user-121355|200| IncludeOptionalData|

  @CreateUserWithDuplicateData
  Scenario Outline: Verify the response for duplicate data for users
    Then Verify that a new user with <id> can be created with valid mandatory details with <inputType>
    And Verify duplicate user <id> can't be created and returns <Code>
    And Verify that user with <id> can be deleted successfully

    Examples:
      |id|Code|inputType|
      | user-121355|400|OnlyValidMandatory|

  @CreateAndUpdateUser

  Scenario Outline: Verify if existing user can be updated
    Then Verify that a new user with <id> can be created with valid mandatory details with <inputType>
    And Update the user <email> with <id>
    And Verify that updated user<id> is saved successfully and contains <email>
    And Verify that user with <id> can be deleted successfully

    Examples:
      |id|inputType|email|
      |user-121355|OnlyValidMandatory|joe12345@gmail.com|



