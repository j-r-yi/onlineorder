package com.laioffer.onlineorder.hello;


import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// For SpringBoot to scan code for endpoints or HTTP map requests
@RestController
public class HelloController {

    // http://localhost:8080/hello
    @GetMapping("/hello")
    // Function name doesn't matter, but should be representative of purpose
        // @RequestParam allows for query parameters, optional required and defaultValue
    public Person sayHello(@RequestParam(required = false, defaultValue = "Guest") String name) {
        Faker faker = new Faker();
        String company = faker.company().name();
        String street = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String bookTitle = faker.book().title();
        String bookAuthor = faker.book().author();

        return new Person(name, company, new Address(street, city, state, null), new Book(bookTitle, bookAuthor));
    }
}