package herokuapp_smoketest;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;

import static herokuapp_smoketest.C01_Post_CreateBooking.bookingId;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.junit.Assert.assertEquals;

public class C06_Get_ReadBooking_Negative extends HerokuAppBaseURL {
    /*
    iven
        https://restful-booker.herokuapp.com/booking/:id
    When
        send GET request
    Then
        status code is 404
    And
        response is "Not Found"
     */

    @Test
    public void readBooking() {
//      SET THE URL
        spec.pathParams("first", "booking", "second", bookingId);

//      SET THE EXPECTED DATA
        String expectedData = "Not Found";

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        assertEquals(404, response.statusCode());
        assertEquals(expectedData, response.asString());

    }
}
