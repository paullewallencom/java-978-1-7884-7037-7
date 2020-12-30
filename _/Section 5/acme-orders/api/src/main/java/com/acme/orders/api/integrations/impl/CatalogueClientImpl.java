package com.acme.orders.api.integrations.impl;

import com.acme.orders.api.integrations.CatalogueClient;
import com.acme.orders.api.integrations.lib.catalogue.*;
import com.acme.orders.api.services.exceptions.GeneralServiceException;
import com.acme.orders.api.services.exceptions.ResourceNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;

public class CatalogueClientImpl implements CatalogueClient {

    private ECommerce port;

    public CatalogueClientImpl(String wsdl) {

        try {
            URL wsdlUrl = new URL(wsdl);

            ECommerceWsService service = new ECommerceWsService(wsdlUrl);

            port = service.getECommercePort();
        } catch (MalformedURLException e) {

            throw new GeneralServiceException(e);
        }
    }

    @Override
    public Product findProductById(String id) {

        try {
            RetrieveProductRequestMessage retrieveProductRequestMessage = new RetrieveProductRequestMessage();
            retrieveProductRequestMessage.setId(Long.valueOf(id));

            RetrieveProductResponseMessage response = port.retrieveProduct(retrieveProductRequestMessage);

            return response.getProduct();
        } catch (ECommerceErrorFault_Exception e) {

            String code = e.getFaultInfo().getFault().getCode();

            switch (code) {

                case "resource.not.found":
                    throw new ResourceNotFoundException(Product.class.getSimpleName(), id);
            }

            throw new GeneralServiceException(e);
        }
    }
}
