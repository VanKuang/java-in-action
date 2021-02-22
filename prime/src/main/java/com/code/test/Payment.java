package com.code.test;

import java.math.BigDecimal;

public class Payment {

    private final String ccy;
    private BigDecimal amount;

    public Payment(String ccy) {
        this(ccy, BigDecimal.ZERO);
    }

    public Payment(String ccy, BigDecimal amount) {
        this.ccy = ccy;
        this.amount = amount;
    }

    public String getCcy() {
        return ccy;
    }

    public void addAmount(BigDecimal amount) {
        this.amount.add(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "ccy='" + ccy + '\'' +
                ", amount=" + amount +
                '}';
    }
}
