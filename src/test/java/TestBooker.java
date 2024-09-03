import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.not;

public class TestBooker {

    @Test
    public void getBookingbyIdTest(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = RestAssured
                .given().pathParam("id", "199")
                .when().get("/booking/{id}");
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", not(0));
    }

    @Test
    public void getBookingNotFoundTest(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = RestAssured
                .given().pathParam("id", "999999999")
                .when().get("/booking/{id}");
        response.then().log().body();
        response.then().assertThat().statusCode(404);
    }

    @Test
    public void getBookingbyBadIdTest(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = RestAssured
                .given().pathParam("id", "aaa@")
                .when().get("/booking/{id}");
        response.then().log().body();
        response.then().assertThat().statusCode(400);
    }

    @Test
    public void postBookingTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("1963-02-19");
        bookingDates.setCheckout("1963-11-11");
        Booking booking = new Booking();
        booking.setFirstname("Marcelo");
        booking.setLastname("Sarabia");
        booking.setTotalprice(777);
        booking.setDepositpaid(true);
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("TV");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(payload)
                .when().post("/booking");

        response.then().log().body();
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void postBookingWithWrongFieldsTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("aaa@");
        bookingDates.setCheckout("");
        Booking booking = new Booking();
        booking.setFirstname("Marcelo");
        booking.setLastname("Sarabia");
        booking.setTotalprice(777);
        booking.setDepositpaid(true);
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("TV");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(payload)
                .when().post("/booking");

        response.then().log().body();
        response.then().assertThat().statusCode(400);
    }



}
