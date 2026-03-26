import client.OrderClient;
import org.junit.jupiter.api.DisplayName;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Проверка, что в тело ответа возвращается список заказов")
    public void shouldReturnOrdersList() {
        Response response = orderClient.getList();

        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}