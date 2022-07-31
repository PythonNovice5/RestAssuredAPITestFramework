package pages;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import static org.hamcrest.Matchers.equalTo;
import java.io.File;


public class Users extends AbstractPage{

    protected static Logger LOGGER = Logger.getLogger(Users.class.getName());

    String token = prop.getProperty("token");
    String base_url=prop.getProperty("baseurl");


    public void createUserAndVerify(String id,String datatype) throws Exception{

        String inputType;
        if(datatype.contains("OnlyValidMandatory") ){
            inputType = "valid_data";
        }else if(datatype.contains("IncludeOptionalData")){
            inputType="including_optional_data";
        }
        else{
            System.out.println("Please provide a valid user id and try again");
            return;
        }
        File valid_json_file = new File(prop.getProperty(inputType));

        req.body(valid_json_file);

        String createUserEndPoint= prop.getProperty("create_user_end_point");


        resp =req.log().all().when().post(createUserEndPoint);
        System.out.println("Valid Status code: "+resp.statusLine());

        validatable_response = resp.then();
        resp=resp.thenReturn();

        String response_statusCode = String.valueOf(resp.statusCode());
        System.out.println("Response Received: "+resp.prettyPrint());

        prop.setProperty("statusCode",response_statusCode);
        prop.setProperty("response",resp.body().asPrettyString());
        prop.setProperty("refJsonInput",valid_json_file.toString());


        //Verify if the response status code is 200 ok
        validatable_response.statusCode(200);

        //Verify the user details in the response
        validatable_response.body("account.id",equalTo("user-121355"));
        validatable_response.body("account.username",equalTo("esh1"));
        validatable_response.body("account.email",equalTo("esh12@example.com"));
        RestAssured.reset();
       }

    public void verifyUserCreated(){
        prop.get("statusCode");
        Object responsehere = prop.get("response");
        System.out.println("Response we got here --------"+responsehere);
    }

    public void updateUserDetails(String value,String id){
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", id);
        requestParams.put("email",value);
        req.body(requestParams.toJSONString());
        System.out.println("Trying to connect and send Update request ----------------------------");
        String base_url=prop.getProperty("baseurl");
        String update_end_point= prop.getProperty("update_user");
        resp = req.baseUri(base_url).contentType(ContentType.JSON).log().all().when().post(update_end_point);
        validatable_response = resp.then();

        //Verify if the response status code is 200 ok
        validatable_response.statusCode(200);
        System.out.println("Status code recieved for Update: "+resp.statusCode());

    }

    public void createVerifyDuplicateUser(String id,String statusCode){
        File valid_json_file = new File(prop.getProperty("valid_data"));
        req.body(valid_json_file);

        String createUserEndPoint= prop.getProperty("create_user_end_point");


        resp =req.log().all().when().post(createUserEndPoint);
        System.out.println("Valid Status code: "+resp.statusLine());

        validatable_response = resp.then();
        resp=resp.thenReturn();

        String response_statusCode = String.valueOf(resp.statusCode());
        System.out.println("Response Received: "+resp.prettyPrint());

        //Verify if the response status code is 400
        validatable_response.statusCode(Integer.parseInt(statusCode));

        //Verify the user details in the response
        validatable_response.body("id",equalTo("users-userId-check"));
        validatable_response.body("detail",equalTo("account already exists"));
        validatable_response.body("status",equalTo("Bad Request"));
        RestAssured.reset();
    }

    public void CreateUserWithInvalidFields(String id)
    {
        File valid_json_file = new File(prop.getProperty("invalid_data"));
        req.body(valid_json_file);

        String createUserEndPoint= prop.getProperty("create_user_end_point");


        resp =req.log().all().when().post(createUserEndPoint);
        System.out.println("Valid Status code: "+resp.statusLine());

        validatable_response = resp.then();
        resp=resp.thenReturn();

        String response_statusCode = String.valueOf(resp.statusCode());
        System.out.println("Response Received: "+resp.prettyPrint());

        //Verify if the response status code is 400
        validatable_response.statusCode(400);

        //Verify the user details in the response
        validatable_response.body("id",equalTo("users-email-format-check"));
        validatable_response.body("detail",equalTo("email has wrong format"));
        validatable_response.body("status",equalTo("Bad Request"));
        RestAssured.reset();
    }

    public void deleteUser(String id) throws InterruptedException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", id);
        req.body(requestParams.toJSONString());
        System.out.println("Trying to connect and send delete request ----------------------------");
        String base_url=prop.getProperty("baseurl");
        String del_end_point= prop.getProperty("delete_user");
        resp = req.baseUri(base_url).contentType(ContentType.JSON).log().all().when().delete(del_end_point);
        validatable_response = resp.then();

        //Verify if the response status code is 200 ok
        validatable_response.statusCode(200);
        System.out.println("Response Received: "+resp.prettyPrint());
        System.out.println("Response for Delete: "+resp.statusLine());
    }

    public Response readUserByID(String id, String expectedStatusCode){
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", id);
        req.body(requestParams.toJSONString());
        System.out.println("Trying to connect and send delete request ----------------------------");
        String base_url=prop.getProperty("baseurl");
        String read_end_point = prop.getProperty("read_user");
        resp = req.baseUri(base_url).contentType(ContentType.JSON).log().all().when().post(read_end_point);

        System.out.println("Response Received: "+resp.prettyPrint());
        System.out.println("Response for Delete: "+resp.statusLine());

        //Verify if the response status code is 200 ok
        validatable_response= resp.then();
        validatable_response.statusCode(Integer.parseInt(expectedStatusCode));


        if(resp.statusCode()==200) {
            //Verify the user details in the response
            validatable_response.body("account.id", equalTo("user-121355"));
            validatable_response.body("account.username", equalTo("esh1"));
            validatable_response.body("account.email", equalTo("esh12@example.com"));
        }else if(resp.statusCode()==500){
            //Verify the user details in the response
            validatable_response.body("code", equalTo(500));
            validatable_response.body("detail", equalTo("not found"));
            validatable_response.body("status", equalTo("Internal Server Error"));
        }
        return resp;
    }

    public void verifyUpdatedUser(String id,String valueTobeVerified){
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", id);
        req.body(requestParams.toJSONString());
        System.out.println("---------------Trying to connect and send POST request for Reading user details ----------------------------");
        String base_url=prop.getProperty("baseurl");
        String read_end_point = prop.getProperty("read_user");
        resp = req.baseUri(base_url).contentType(ContentType.JSON).log().all().when().post(read_end_point);

        System.out.println("Response Received: "+resp.prettyPrint());
        System.out.println("Response for Update: "+resp.statusLine());

        //Verify if the response status code is 200 ok
        validatable_response= resp.then();
        validatable_response.statusCode(200);

        System.out.println("\nVerifying updated email is "+valueTobeVerified+" for id: "+id+"\n");
        validatable_response.body("account.email", equalTo(valueTobeVerified));
    }

}
