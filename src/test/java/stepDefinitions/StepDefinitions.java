package stepDefinitions;

import APIResources.APIResources;
import io.cucumber.java.en.*;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import DataBuild.TestDataBuild;
import io.restassured.specification.ResponseSpecification;
import utils.Utils;

import java.io.IOException;

public class StepDefinitions extends Utils {
    ResponseSpecification resSpec;
    RequestSpecification addPlacePostReq;
    Response res;
    //JsonPath jp;
    TestDataBuild data = new TestDataBuild();
    static String place_id;

    @Given("Add place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String lang, String add) throws IOException {
        //System.out.println("preparing payload ");
        //System.out.println("HashCOde: " + getRequestSpecification().toString());
        addPlacePostReq = given().spec(getRequestSpecification()).body(data.AddPlacePayload(name, lang, add));
        // System.out.println("payload preparation done ");
    }

    @When("user calls {string} with {string} HTTP request")
    public void user_calls_with_http_request(String APIpath, String httpMethod) {
        resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        APIResources apiResources = APIResources.valueOf(APIpath);

        System.out.println(">>>resource API: " + apiResources.getResource());

        if (httpMethod.equalsIgnoreCase("GET")) {
            res = addPlacePostReq.when().get(apiResources.getResource());
            System.out.println("this get response: " + res.prettyPrint());
        } else if (httpMethod.equalsIgnoreCase("POST")) {
            res = addPlacePostReq.when().post(apiResources.getResource());
            System.out.println("this post response: " + res.prettyPrint());
        }
//        else if (httpMethod.equalsIgnoreCase("PUT")) {
//            //res = addPlacePostReq.when().delete(apiResources.getResource());
//        }
        else if (httpMethod.equalsIgnoreCase("DELETE")) {
            res = addPlacePostReq.when().delete(apiResources.getResource());
            System.out.println("this delete response: " + res.prettyPrint());
        }
        System.out.println("this >>> response empty: " + res.then().spec(resSpec).extract().response().asString().isEmpty());

    }

    @Then("API call is success with status {int}")
    public void api_call_is_success_with_status(Integer int1) {
        Assert.assertEquals(res.getStatusCode(), int1.intValue(), "Failed to verify response status code");
    }

    @And("{string} in response is {string}")
    public void in_response_is(String status, String string2) {
        if (res.asString().isEmpty()) {
            System.out.println(">>>> pretty response : empty " + res.prettyPrint());
        } else {
            System.out.println("its not empty");
        }
        System.out.println("res status code: " + res.getStatusCode());
        Assert.assertEquals(getValueFromResponse(res, status), string2, "Response status is ok");
    }

    @And("{string} in response body is {string}")
    public void in_response_body_is(String scope, String string2) {
        Assert.assertEquals(getValueFromResponse(res, scope), string2, "Response scope is not APP");
    }

    @And("verify the place_id created, maps to the {string} using {string}")
    public void verify_the_place_id_created_maps_to_the_using(String expectedName, String getPlaceAPI) throws IOException {
        // get place_id from add place response
        // create req spec for get place & make a get request using place id
        // assert that name from add place's req payload is matching with get place's res payload
        place_id = getValueFromResponse(res, "place_id");
        addPlacePostReq = given().spec(getRequestSpecification()).queryParam("place_id", place_id);
        user_calls_with_http_request(getPlaceAPI, "GET");
        Assert.assertEquals(getValueFromResponse(res, "name"), expectedName, "Failed to locate name in response");
    }

    @Given("user has payload with place_id of place to be deleted")
    public void user_has_payload_with_place_id_of_place_to_be_deleted() throws IOException {
        addPlacePostReq = given().spec(getRequestSpecification()).body(data.deletePlacePayload(place_id));
    }
}
