package com.acme.payments.api.models.db;

import com.acme.payments.api.models.db.common.BaseEntity;
import com.acme.payments.lib.PaymentMethodType;
import com.acme.payments.lib.TransactionStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity {

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "nonce")
    private String nonce;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "gateway_decline_code")
    private String gatewayDeclineCode;

    @Column(name = "processor_response_code")
    private String processorResponseCode;

    @Column(name = "processor_response_text")
    private String processorResponseText;

    @Column(name = "payment_method_type")
    private PaymentMethodType paymentMethodType;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getGatewayDeclineCode() {
        return gatewayDeclineCode;
    }

    public void setGatewayDeclineCode(String gatewayDeclineCode) {
        this.gatewayDeclineCode = gatewayDeclineCode;
    }

    public String getProcessorResponseCode() {
        return processorResponseCode;
    }

    public void setProcessorResponseCode(String processorResponseCode) {
        this.processorResponseCode = processorResponseCode;
    }

    public String getProcessorResponseText() {
        return processorResponseText;
    }

    public void setProcessorResponseText(String processorResponseText) {
        this.processorResponseText = processorResponseText;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public void setGatewayTransactionId(String gatewayTransactionId) {
        this.gatewayTransactionId = gatewayTransactionId;
    }
}
