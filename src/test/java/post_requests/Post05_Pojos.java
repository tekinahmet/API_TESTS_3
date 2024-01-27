package post_requests;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.BookingResponsePojo;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Post05_Pojos extends HerokuAppBaseURL {
      /*
    Given
       1. https://restful-booker.herokuapp.com/booking/1175
       2.
          {
                "firstname": "John",
                "lastname": "Doe",
                "totalprice": 999,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2021-09-21",
                    "checkout": "2021-12-21"
                },
                "additionalneeds": "Breakfast"
            }
        When
           I send POST request to the url
        Then
            Status code is 200
        And
            response body should be like
            {
                "bookingid": 4568,
                "booking": {
                "firstname": "John",
                "lastname": "Doe",
                "totalprice": 999,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2021-09-21",
                    "checkout": "2021-12-21"
            },
            "additionalneeds": "Breakfast"
            }
      */

    @Test
    public void post05() {
//      SET THE URL
        spec.pathParam("first", "booking");

//      SET THE EXPECTED DATA
        //BookingDatesPojo bookingDatesPojo = new BookingDatesPojo();        //internal body error

        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2021-09-21", "2021-12-21");
        BookingPojo expectedData = new BookingPojo("John", "Doe", 999, true, bookingDatesPojo, "Breakfast");
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).post("{first}");
        response.prettyPrint();

//      DO ASSERTION
        BookingResponsePojo actualData = response.as(BookingResponsePojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getFirstname(), actualData.getBooking().getFirstname());
        assertEquals(expectedData.getLastname(), actualData.getBooking().getLastname());
        assertEquals(expectedData.getTotalprice(), actualData.getBooking().getTotalprice());
        assertEquals(expectedData.getDepositpaid(), actualData.getBooking().getDepositpaid());
        assertEquals(expectedData.getAdditionalneeds(), actualData.getBooking().getAdditionalneeds());

        //
        assertEquals(expectedData.getBookingdates().getCheckin(), actualData.getBooking().getBookingdates().getCheckin());
        assertEquals(expectedData.getBookingdates().getCheckout(), actualData.getBooking().getBookingdates().getCheckout());


    }
}
