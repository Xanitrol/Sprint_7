package generator;

import model.Courier;

import java.util.UUID;

public class CourierGenerator {

    public static Courier random() {
        String login = "user_" + UUID.randomUUID().toString().substring(0, 8);
        String password = "pass_" + UUID.randomUUID().toString().substring(0, 8);
        String firstName = "name_" + UUID.randomUUID().toString().substring(0, 5);

        return new Courier(login, password, firstName);
    }
}