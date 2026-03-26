package client;

import io.restassured.response.Response;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String CREATE = "/api/v1/orders";
    private static final String GET_LIST = "/api/v1/orders";

    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(BaseUrl.BASE_URL + CREATE);
    }

    public Response getList() {
        return given()
                .when()
                .get(BaseUrl.BASE_URL + GET_LIST);
    }
}