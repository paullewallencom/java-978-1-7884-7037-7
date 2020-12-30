package com.acme.orders.api.integrations;

import com.acme.orders.api.integrations.lib.catalogue.Product;

public interface CatalogueClient {

    Product findProductById(String id);
}