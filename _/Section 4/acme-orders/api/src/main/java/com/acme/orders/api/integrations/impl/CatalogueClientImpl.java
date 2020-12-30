package com.acme.orders.api.integrations.impl;

import com.acme.orders.api.integrations.CatalogueClient;
import com.acme.orders.api.integrations.lib.catalogue.*;
import com.acme.orders.api.services.exceptions.GeneralServiceException;
import com.acme.orders.api.services.exceptions.ResourceNotFoundException;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class CatalogueClientImpl implements CatalogueClient {

    private ECommerce port;

    private final Timer findProductByIdTimer;

    public CatalogueClientImpl(String wsdlUrlString, MetricRegistry metricRegistry) {

        this.findProductByIdTimer = metricRegistry.timer(CatalogueClientImpl.class.getName() + ".request-find-product-by-id");

        try {
            URL wsdlUrl = new URL(wsdlUrlString);

            ECommerceWsService service = new ECommerceWsService(wsdlUrl);

            port = service.getECommercePort();
        } catch (MalformedURLException e) {

            throw new GeneralServiceException(e);
        }
    }

    @Override
    public Product findProductById(String id) {

        final Timer.Context context = findProductByIdTimer.time();

        RetrieveProductRequestMessage message = new RetrieveProductRequestMessage();
        message.setId(Long.parseLong(id));

        try {
            RetrieveProductResponseMessage responseMessage = port.retrieveProduct(message);

            return responseMessage.getProduct();
        } catch (ECommerceErrorFault_Exception e) {

            String code = e.getFaultInfo().getFault().getCode();

            switch (code) {

                case "resource.not.found":
                    throw new ResourceNotFoundException(Product.class.getSimpleName(), id);
            }

            throw new GeneralServiceException(e);
        } finally {

            context.stop();
        }
    }
}
