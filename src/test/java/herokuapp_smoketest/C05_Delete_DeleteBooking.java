package herokuapp_smoketest;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import test_data.HerokuAppTestData;

import java.util.HashMap;
import java.util.Map;

import static herokuapp_smoketest.C01_Post_CreateBooking.bookingId;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utils.ObjectMapperUtils.convertJsonToJava;

public class C05_Delete_DeleteBooking extends HerokuAppBaseURL {
    /*
    Given
        https://restful-booker.herokuapp.com/booking/:id
    When
        send DELETE request
    Then
        status code is 201
    And
        response is "Created"
     */
    @Test
    public void deleteBooking() {
//      SET THE URL
        //spec.pathParams("first", "booking", "second", C01_CreateBooking.bookingId);//import
        spec.pathParams("first", "booking", "second", bookingId);

//      SET THE EXPECTED DATA
        String expectedData = "Created";

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).delete("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        //String actualData = response.asString();
        //System.out.println("actualData = " + actualData);
        //assertEquals(expectedData, actualData);

        assertEquals(201, response.statusCode());
        assertEquals(expectedData, response.asString());


    }

}
