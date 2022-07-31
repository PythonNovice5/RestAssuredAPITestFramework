### Assignment
Please create a simple test suite that verifies functionality of a public user management API available at https://m3o.com/user/api.

### Some example flows that you could cover in your tests
* Creation of a new user with mandatory/optional fields and verification that it was saved successfully
* Update of existing user 
* Deletion of existing user
* Some error scenarios, like trying to create a user with duplicated id or missing/invalid information

### Our expectations for this task
* Please use Java or Kotlin as programming language and Maven or Gradle as a build tool.
* Use API testing library of your preference (for example, RestAssured, Retrofit or similar).
* Optional: think about reporting or some other way to make test results quick to interpret and test failures easier to investigate.
* Include description of your solution, choice of tools and instruction on how to execute the tests in the readme file.
* Please push your code to the provided git repository and submit your assignment within 5 calendar days.

### Tips
* Let us know if you have any questions - we're happy to help.
* Keep it simple, no need to spend too much time on this task or implement more than 5 test cases.
* Think about easiness of adding new test cases and making changes when building your test suite/framework.
* If there is something you would have improved or done differently if you had more time, please feel free to mention it in the readme file.

### Tools and technologies used
1. Java as programming language
2. Cucumber testing framework
3. Restassured for validating the APIs
4. html plugin for reporting (report-html.html)
5. Maven, Junit

### Instructions to execute the tests
Open termernal and execute below commands:
1. git clone http://unzer-vvjpzm@git.codesubmit.io/unzer/user-management-api-test-suite-ikdekq
2. cd user-management-api-test-suite-ikdekq
3. mvn test 

**if you want to run specific scenario, just overwrite the tag name in RunCukeTest.java file by executing - mvn test -Dcucumber.options="--tags @CreatingAndDeletingUser" 

## Reporting
HTML report can be found at  - .\user-management-api-test-suite-ikdekq\target\report-html.html

## Problems faced
1. Insufficient credit issue for Lists endpoint always
2. Insufficient credit issue happens for other User end points as well, sometimes, after multiple requests to the server

## If I had more time
1. I have covered 5 scenarios currently, I would have covered more scenarios
2. I would make this code more scalable by making it more generic/dyanamic
3. I would improve the architecture of this by making it more modular
4. Logging could not be implemented properly so I had to use print statements, I dont like print statements but couldn't spend time to resolve log4j issues
5. I would create resualbe methods, utils etc to reduce the lines of code and increase efficiency
