package com.example.ms.customers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerRepo {

    private List<Customer> allCustomers = new ArrayList<>();

    @PostConstruct
    private void init() {

        Customer cs = new Customer();
        cs.setId("4c22c463-50c6-418d-8b6f-d2d9316ec754");
        cs.setFirstname("Stoddard");
        cs.setLastname("Burfield");
        cs.setEmail("sburfield0@facebook.com");
        cs.setDateOfBirth(Date.from(Instant.parse("1991-01-09T07:18:52Z")));

        allCustomers.add(cs);

        cs = new Customer();
        cs.setId("e72bf51d-5187-4477-9ead-8639357353cc");
        cs.setFirstname("Tamiko");
        cs.setLastname("Haspineall");
        cs.setEmail("thaspineall1@macromedia.com");
        cs.setDateOfBirth(Date.from(Instant.parse("1990-04-10T17:41:47Z")));

        allCustomers.add(cs);

        cs = new Customer();
        cs.setId("a0f77d77-3635-480f-9da1-f669056fb54c");
        cs.setFirstname("Estelle");
        cs.setLastname("Innwood");
        cs.setEmail("einnwood2@about.me");
        cs.setDateOfBirth(Date.from(Instant.parse("1998-02-07T13:25:05Z")));

        allCustomers.add(cs);

        cs = new Customer();
        cs.setId("38dda745-26e5-4154-9c6f-42840feff079");
        cs.setFirstname("Zondra");
        cs.setLastname("Mackstead");
        cs.setEmail("zmackstead3@oaic.gov.au");
        cs.setDateOfBirth(Date.from(Instant.parse("1981-06-12T22:13:45Z")));

        allCustomers.add(cs);

        cs = new Customer();
        cs.setId("fc895461-70cc-48a8-8137-78a72abd3845");
        cs.setFirstname("Denni");
        cs.setLastname("Cardiff");
        cs.setEmail("dcardiff4@instagram.com");
        cs.setDateOfBirth(Date.from(Instant.parse("1980-08-22T03:46:00Z")));

        allCustomers.add(cs);
    }

    public List<Customer> findAllCustomers() {
        return allCustomers;
    }
}
