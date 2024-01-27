package get_requests;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

public class Get05_QueryParams extends HerokuAppBaseURL {
    @Test
    public void get05(){
//      SET THE URL
        spec.pathParam("first", "booking").queryParams("firstname", "John", "lastname", "Smith");

//      SET THE EXPECTED DATA

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}");
        response.prettyPrint();

//      DO ASSERTION
        //1. way
        response
                .then()
                .statusCode(200)
                .body("bookingid", hasSize(greaterThan(0)));
        //2. way
        assertTrue(response.asString().contains("bookingid"));//boolean

    }
}
