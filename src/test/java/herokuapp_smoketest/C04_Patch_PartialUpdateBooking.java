package herokuapp_smoketest;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import test_data.HerokuAppTestData;

import java.util.HashMap;
import java.util.Map;

import static herokuapp_smoketest.C01_Post_CreateBooking.bookingId;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utils.ObjectMapperUtils.convertJsonToJava;

public class C04_Patch_PartialUpdateBooking extends HerokuAppBaseURL {
    /*
    Given
        https://restful-booker.herokuapp.com/booking/:id
    And
        {
            "firstname": "Mary",
            "lastname": "Star",
        }
    When
        send PATCH request
    Then
        status code is 200
    And
        response body
        {
                "firstname": "Mary",
                "lastname": "Star",
                "totalprice": 200,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2023-01-01",
                    "checkout": "2024-01-01"
            },
                "additionalneeds": "Dinner"
            }
     */
    @Test
    public void partialUpdateBooking() {
//      SET THE URL
        //spec.pathParams("first", "booking", "second", C01_CreateBooking.bookingId);
        spec.pathParams("first", "booking", "second", bookingId);

//      SET THE EXPECTED DATA
        Map<String, Object> expectedData = HerokuAppTestData.bookingdatesOuterMap("Mary", "Star", null, null,null,null);
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).patch("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        Map<String, Object> actualData = convertJsonToJava(response.asString(), HashMap.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.get("firstname"), actualData.get("firstname"));
        assertEquals(expectedData.get("lastname"), actualData.get("lastname"));
    }
}
