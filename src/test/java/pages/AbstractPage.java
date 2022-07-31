package pages;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public abstract class AbstractPage {
    final RequestSpecification req = RestAssured.given();
    protected Response resp;
    protected ValidatableResponse validatable_response;
    protected static Logger LOGGER = Logger.getLogger(AbstractPage.class.getName());
    Properties prop = new Properties();
//    public ResponseSpecification response = RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

      AbstractPage() {

        try {
            //load a properties file from class path, inside static method
            prop.load(new FileInputStream( ".\\config.properties"));
            //get the property value and print it out
            System.out.println("--------------------------------");
            System.out.println(prop.getProperty("baseurl"));


        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

//        RestAssured.baseURI=prop.getProperty("baseurl");

    }

    public void setBaseURI(){
          String token = prop.getProperty("token");
          String base_url=prop.getProperty("baseurl");
          System.out.println("------------ Setting up the BASE URL --------"+base_url);
          LOGGER.info("------------ Setting up the BASE URL --------"+base_url);
          RestAssured.baseURI=base_url;
          req.baseUri(base_url);

        req.header("authorization","Bearer "+token);
        req.contentType(ContentType.JSON);

    }
    public void deleteUserBeforeTest(String id) throws InterruptedException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", id);
        req.body(requestParams.toJSONString());
        System.out.println("Trying to connect and send delete request ----------------------------");
        String base_url=prop.getProperty("baseurl");
        String del_end_point= prop.getProperty("delete_user");
        resp = req.baseUri(base_url).contentType(ContentType.JSON).log().all().when().delete(del_end_point);

        if (resp.statusCode()==200){
            System.out.println("Delete executed successfully "+resp.statusLine());
        }
        else if(resp.statusCode()==500){
            System.out.println("\n User doesn't exit in the system, so nothing to delete \n");
        }
        else{
            throw new AssertionError();
        }

    }


}

