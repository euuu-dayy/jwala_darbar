package com.jwalanavdurga.dto;

import jakarta.validation.constraints.NotBlank;

public class PaymentWebhookRequest {

    @NotBlank(message = "Gateway is required.")
    private String gateway;

    @NotBlank(message = "Gateway transaction ID is required.")
    private String gatewayTransactionId;

    @NotBlank(message = "Payment status is required.")
    private String status;

    private String paymentMethod;

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public void setGatewayTransactionId(String gatewayTransactionId) {
        this.gatewayTransactionId = gatewayTransactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}