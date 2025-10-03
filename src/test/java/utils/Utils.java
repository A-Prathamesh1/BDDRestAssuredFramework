
package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification addPlaceReqSpec;

    public RequestSpecification getRequestSpecification() throws IOException {
        //System.out.println("Inside getRequestSpecification");
        if(addPlaceReqSpec == null) {
            PrintStream log = new PrintStream(new FileOutputStream(System.getProperty("user.dir") + "\\logging.txt"));
            //System.out.println("Logging at: " + System.getProperty("user.dir") + "\\logging.txt");

            addPlaceReqSpec = new RequestSpecBuilder()
                    .setBaseUri(getGlobalProperty("baseURL"))
                    .setContentType(ContentType.JSON)
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .build();
            //System.out.println("spec done, logging done: " + addPlaceReqSpec.toString());
            return addPlaceReqSpec;
        }

       // System.out.println("Appending into existing log");
        return addPlaceReqSpec;

    }

    public String getGlobalProperty(String key) throws IOException {
        Properties p = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Global.properties");
        p.load(fis);
        return p.getProperty(key);
    }

    public String getValueFromResponse(Response res, String resourcePath){
        JsonPath jp = new JsonPath(res.asString());
//        System.out.println("response: " + jp.prettyPrint());
        return jp.get(resourcePath).toString();
    }
}
