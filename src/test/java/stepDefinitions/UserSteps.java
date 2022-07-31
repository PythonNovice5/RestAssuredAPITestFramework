package stepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import pages.Users;



public class UserSteps extends AbstractSteps{

    RequestSpecification req;
    Response resp;
    String token;
    Users u = new Users();

    protected static Logger LOGGER = Logger.getLogger(UserSteps.class.getName() );

    @Given("Set the base url$")
    public void verifyURl() {
        u.setBaseURI();
    }

    @Then("Verify that a new user with (.*) can be created with valid mandatory details with (.*)$")
    public void create_user(String id,String inputType) throws Exception{
        u.createUserAndVerify(id,inputType);
    }


    @Then("Update the user(.*) with (.*)$")
    public void updateUser(String valueToBeUpdated,String id) throws Exception{
        u.updateUserDetails(valueToBeUpdated,id);
    }


    @Then("Verify duplicate user (.*) can't be created and returns (.*)$")
    public void createDuplicateUser(String id,String status_code) throws Exception{
        u.createVerifyDuplicateUser(id,status_code);
    }


    @Then("Verify that a new user with (.*) can be created with valid mandatory details along with optional values(.*)$")
    public void create_user_withOptional(String id,String datatype) throws Exception{
        u.createUserAndVerify(id,datatype);
    }


    @Then("Create a new user with (.*) with Invalid mandatory details$")
    public void createUserWithInvalidValues(String id) throws Exception{
        u.CreateUserWithInvalidFields(id);
    }


    @Then("Verify that user (.*) with invalid details is not saved and returns (.*)$")
    public void verifyNotSaved(String id,String status_code) throws Exception{
        u.readUserByID(id,status_code);
    }

    @And("Verify that a newly created user(.*) is saved successfully and returns (.*)$")
    public void verifySave(String id,String statusCode) throws Exception{
        u.readUserByID(id,statusCode);
    }

    @And("Verify that updated user(.*) is saved successfully and contains (.*)$")
    public void verifyUpdatedUser(String id,String valueToBeVerified) throws Exception{
        u.verifyUpdatedUser(id,valueToBeVerified);
    }



    @And("Verify that user with (.*) can be deleted successfully$")
    public void deleteUser(String id) throws Exception{

        u.deleteUser(id);
    }


    @And("Delete the user with id '([^\"]*)' if already exists before starting test$")
    public void deleteUserBeforeTest(String id) throws Exception{
        u.deleteUserBeforeTest(id);
    }
}
