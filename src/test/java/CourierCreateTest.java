import client.CourierClient;
import generator.CourierGenerator;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierCreateTest {

    private final CourierClient courierClient = new CourierClient();
    private Courier courier;
    private Integer courierId;

    @AfterEach
    public void tearDown() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void shouldCreateCourier() {
        courier = CourierGenerator.random();

        Response createResponse = courierClient.create(courier);

        createResponse.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        Response loginResponse = courierClient.login(credentials);

        courierId = loginResponse.then()
                .statusCode(200)
                .extract()
                .path("id");

        loginResponse.then().body("id", notNullValue());
    }

    @Test
    public void shouldNotCreateDuplicateCourier() {
        courier = CourierGenerator.random();

        courierClient.create(courier);

        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        courierId = courierClient.login(credentials).path("id");

        Response secondCreateResponse = courierClient.create(courier);

        secondCreateResponse.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void shouldNotCreateCourierWithoutLogin() {
        courier = new Courier(null, "1234", "Ahmed");

        Response response = courierClient.create(courier);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void shouldNotCreateCourierWithoutPassword() {
        courier = new Courier("fast_ahmed", null, "Ahmed");

        Response response = courierClient.create(courier);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}