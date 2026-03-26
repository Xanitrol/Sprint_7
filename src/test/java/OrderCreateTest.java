import client.OrderClient;
import io.restassured.response.Response;
import model.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

public class OrderCreateTest {

    private final OrderClient orderClient = new OrderClient();

    public static Stream<List<String>> orderColorData() {
        return Stream.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("orderColorData")
    public void shouldCreateOrderWithDifferentColorOptions(List<String> color) {
        Order order = new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color
        );

        Response response = orderClient.create(order);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}