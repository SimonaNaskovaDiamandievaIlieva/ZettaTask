package testData;

import com.github.javafaker.Faker;

public class TestDataGenerator {

    private final Faker faker = new Faker();

    public String firstName() {
        return faker.name().firstName();
    }

    public String lastName() {
        return faker.name().lastName();
    }

    public String email() {
        return faker.internet().emailAddress();
    }

    public String phone() {
        return faker.phoneNumber().cellPhone();
    }
}
