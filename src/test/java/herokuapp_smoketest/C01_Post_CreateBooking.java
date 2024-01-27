package herokuapp_smoketest;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.BookingResponsePojo;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class C01_Post_CreateBooking extends HerokuAppBaseURL {
    /*
    Given
        https://restful-booker.herokuapp.com/booking
    And
            {
                "firstname": "John",
                "lastname": "Doe",
                "totalprice": 100,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2023-01-01",
                    "checkout": "2024-01-01"
            },
                "additionalneeds": "Breakfast with black tea"
            }
      When
            send POST request
      Then
            status code is 200
      And
            response body like
            {
                "bookingid": 915,
                "booking": {
                "firstname": "John",
                "lastname": "Doe",
                "totalprice": 100,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2023-01-01",
                    "checkout": "2024-01-01"
            },
                "additionalneeds": "Breakfast with black tea"
            }
        }
     */

    public static Integer bookingId;//this is the bookingId of the created booking, so we can use it int other classes
    @Test
    public void createBooking() {
//      SET THE URL
        spec.pathParam("first", "booking");

//      SET THE EXPECTED DATA
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2023-01-01", "2024-01-01");
        BookingPojo expectedData = new BookingPojo("John", "Doe", 100, true, bookingDatesPojo, "Breakfast with black tea");
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).post("{first}");
        response.prettyPrint();

//      DO ASSERTION
        BookingResponsePojo actualData = response.as(BookingResponsePojo.class);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getFirstname(), actualData.getBooking().getFirstname());
        assertEquals(expectedData.getLastname(), actualData.getBooking().getLastname());
        assertEquals(expectedData.getTotalprice(), actualData.getBooking().getTotalprice());
        assertEquals(expectedData.getDepositpaid(), actualData.getBooking().getDepositpaid());
        assertEquals(expectedData.getAdditionalneeds(), actualData.getBooking().getAdditionalneeds());

        //we have bookingDatesPojo class
        assertEquals(bookingDatesPojo.getCheckin(), actualData.getBooking().getBookingdates().getCheckin());
        assertEquals(bookingDatesPojo.getCheckout(), actualData.getBooking().getBookingdates().getCheckout());
        //assertEquals(expectedData.getBookingdates().getCheckin(), actualData.getBooking().getBookingdates().getCheckin());
        //assertEquals(expectedData.getBookingdates().getCheckout(), actualData.getBooking().getBookingdates().getCheckout());

      bookingId = actualData.getBookingid();
    }
}
