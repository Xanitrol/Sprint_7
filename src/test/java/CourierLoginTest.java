import client.CourierClient;
import generator.CourierGenerator;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    private final CourierClient courierClient = new CourierClient();
    private Courier courier;
    private Integer courierId;

    @BeforeEach
    public void setUp() {
        courier = CourierGenerator.random();
        courierClient.create(courier);

        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        courierId = courierClient.login(credentials).path("id");
    }

    @AfterEach
    public void tearDown() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void shouldLoginCourier() {
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        Response response = courierClient.login(credentials);

        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void shouldNotLoginWithoutLogin() {
        Map<String, String> body = new HashMap<>();
        body.put("password", courier.getPassword());

        Response response = courierClient.loginWithBody(body);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void shouldNotLoginWithWrongLogin() {
        CourierCredentials credentials = new CourierCredentials("wrong_login", courier.getPassword());

        Response response = courierClient.login(credentials);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void shouldNotLoginWithWrongPassword() {
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), "wrong_password");

        Response response = courierClient.login(credentials);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void shouldNotLoginNonExistentCourier() {
        CourierCredentials credentials = new CourierCredentials("nonexistent_login", "nonexistent_password");

        Response response = courierClient.login(credentials);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}