package client;

import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String CREATE = "/api/v1/courier";
    private static final String LOGIN = "/api/v1/courier/login";
    private static final String DELETE = "/api/v1/courier/";

    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(BaseUrl.BASE_URL + CREATE);
    }

    public Response login(CourierCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(BaseUrl.BASE_URL + LOGIN);
    }

    public Response loginWithBody(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(BaseUrl.BASE_URL + LOGIN);
    }

    public Response delete(int id) {
        return given()
                .when()
                .delete(BaseUrl.BASE_URL + DELETE + id);
    }
}