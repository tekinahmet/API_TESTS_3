package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthenticationHerokuApp {
//    public static void main(String[] args) {
//        //generateToken();
//        System.out.println("generateToken() = " + generateToken());
//    }
    public static String generateToken(){
        String body = "{ \"username\": \"admin\", \"password\": \"password123\"}";
        Response response = given().contentType(ContentType.JSON).body(body).post("https://restful-booker.herokuapp.com/auth");
        //response.prettyPrint();

        return response.jsonPath().getString("token");
    }
}