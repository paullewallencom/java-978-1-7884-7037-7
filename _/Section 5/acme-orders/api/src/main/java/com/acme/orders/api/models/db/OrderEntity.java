package com.acme.orders.api.models.db;

import com.acme.orders.api.models.db.common.BaseEntity;
import com.acme.orders.lib.v1.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "OrderEntity.findAll", query = "SELECT o FROM OrderEntity o"),
        @NamedQuery(name = "OrderEntity.findAllCount", query = "SELECT count(o) FROM OrderEntity o")
})
public class OrderEntity extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @NotNull
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "transaction_id")
    private String transactionId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItemEntity> cart;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Set<OrderItemEntity> getCart() {
        return cart;
    }

    public void setCart(Set<OrderItemEntity> cart) {
        this.cart = cart;
    }
}
