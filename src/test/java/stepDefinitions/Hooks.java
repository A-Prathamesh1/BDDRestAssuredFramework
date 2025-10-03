package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {
    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        // write a code to get place_id.
        // execute the code that will get place_id only it's null.
        System.out.println("executing before hook");
        StepDefinitions sd = new StepDefinitions();
        if(StepDefinitions.place_id == null) {
            sd.add_place_payload_with("Nityshree", "France", "French");
            sd.user_calls_with_http_request("addPlaceAPI", "POST");
            sd.verify_the_place_id_created_maps_to_the_using("Nityshree", "getPlaceAPI");
        }
    }
}
