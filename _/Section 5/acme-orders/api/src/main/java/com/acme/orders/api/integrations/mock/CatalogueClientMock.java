package com.acme.orders.api.integrations.mock;

import com.acme.orders.api.integrations.CatalogueClient;
import com.acme.orders.api.integrations.lib.catalogue.Product;

import java.math.BigDecimal;

public class CatalogueClientMock implements CatalogueClient {

    @Override
    public Product findProductById(String id) {

        Product product = new Product();
        product.setId(100L);
        product.setCurrency("EUR");
        product.setPrice(new BigDecimal("9.99"));
        product.setTitle("Gift Card");

        return product;
    }
}
