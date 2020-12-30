package com.acme.orders.lib.v1.common;

public enum OrderServiceErrorCode {

    ORDER_STATE_INCORRECT("order.state.incorrect"),
    ORDER_CART_ITEM_INVALID("order.cart.item.invalid"),
    ORDER_CART_EMPTY("order.cart.empty"),

    UNKNOWN("unknown");

    private String code;

    OrderServiceErrorCode(String code) {
        this.code = code;
    }

    public static OrderServiceErrorCode findByCode(String code) {

        for (OrderServiceErrorCode errorCode : values()) {

            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }

        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }
}
