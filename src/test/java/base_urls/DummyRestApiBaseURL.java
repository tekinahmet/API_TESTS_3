package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

public class DummyRestApiBaseURL {
//this class is created for preventing repetitions such as bas url, content type, header, authorization etc
    protected RequestSpecification spec;//do not forget type protected
    @Before //annotation will run this method before @Test methods. So spec won't be null
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://dummy.restapiexample.com/api/v1")
                .setContentType(ContentType.JSON)
                .build();
    }
}
