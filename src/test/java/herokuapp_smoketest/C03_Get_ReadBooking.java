package herokuapp_smoketest;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;

import static herokuapp_smoketest.C01_Post_CreateBooking.bookingId;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utils.ObjectMapperUtils.convertJsonToJava;

public class C03_Get_ReadBooking extends HerokuAppBaseURL {
    /*
    Given
        https://restful-booker.herokuapp.com/booking/:id
    When
        send get request
    Then
        Status code is 200
    And
        response body
            {
                "firstname": "Mark",
                "lastname": "Twain",
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
    public void readBooking() {
//      SET THE URL
        //spec.pathParams("first", "booking", "second", C01_CreateBooking.bookingId);
        spec.pathParams("first", "booking", "second", bookingId);

//      SET THE EXPECTED DATA
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2023-01-01", "2024-01-01");
        BookingPojo expectedData = new BookingPojo("Mark", "Twain", 200, true, bookingDatesPojo, "Dinner");
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        BookingPojo actualData = convertJsonToJava(response.asString(), BookingPojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getFirstname(), actualData.getFirstname());
        assertEquals(expectedData.getLastname(), actualData.getLastname());
        assertEquals(expectedData.getTotalprice(), actualData.getTotalprice());
        assertEquals(expectedData.getDepositpaid(), actualData.getDepositpaid());
        assertEquals(expectedData.getBookingdates().getCheckin(), actualData.getBookingdates().getCheckin());
        assertEquals(expectedData.getBookingdates().getCheckout(), actualData.getBookingdates().getCheckout());
        assertEquals(expectedData.getAdditionalneeds(), actualData.getAdditionalneeds());


    }
}
