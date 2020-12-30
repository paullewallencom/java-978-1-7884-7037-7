package com.acme.orders.api.services.exceptions;

/**
 * @author Tilen Faganel, Sunesis ltd.
 * @since 1.0.0
 */
public class IdMismatchException extends RuntimeException {

    private String pathId;
    private String entityId;

    public IdMismatchException(String pathId, String entityId) {
        this.pathId = pathId;
        this.entityId = entityId;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
