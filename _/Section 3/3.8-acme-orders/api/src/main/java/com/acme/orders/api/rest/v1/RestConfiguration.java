package com.acme.orders.api.rest.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RestConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private String customersUrl;

    @NotNull
    private String catalogueUrl;

    @NotNull
    private String authPublicKey;

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public String getCustomersUrl() {
        return customersUrl;
    }

    public String getCatalogueUrl() {
        return catalogueUrl;
    }

    public String getAuthPublicKey() {
        return authPublicKey;
    }
}
