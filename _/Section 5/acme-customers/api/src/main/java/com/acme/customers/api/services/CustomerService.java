package com.acme.customers.api.services;

import com.acme.customers.lib.v1.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {

    Customer findCustomerById(String id) throws SQLException;

    List<Customer> findCustomers(Integer limit, Integer offset) throws SQLException;

    Customer createCustomer(Customer customer) throws SQLException;

    Customer updateCustomer(String id, Customer customer) throws SQLException;

    void deleteCustomerById(String id) throws SQLException;
}
