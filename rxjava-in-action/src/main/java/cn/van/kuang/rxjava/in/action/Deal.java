package cn.van.kuang.rxjava.in.action;

import java.math.BigDecimal;
import java.util.Date;

public class Deal {

    private final int id;
    private final Date transactionTime;

    private final String type;
    private final String product;

    private String priCcy;
    private String secCcy;

    private BigDecimal priAmount;
    private BigDecimal secAmount;

    private BigDecimal wholesaleSpotRate;
    private BigDecimal contractSpotRate;
    private BigDecimal wholesaleSwapPoint;
    private BigDecimal contractSwapPoint;

    public Deal(int id,
                Type type,
                Product product) {
        this.id = id;
        this.transactionTime = new Date();
        this.type = type.name();
        this.product = product.name();
    }

    public int getId() {
        return id;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public String getType() {
        return type;
    }

    public String getProduct() {
        return product;
    }

    public String getPriCcy() {
        return priCcy;
    }

    public String getSecCcy() {
        return secCcy;
    }

    public BigDecimal getPriAmount() {
        return priAmount;
    }

    public BigDecimal getSecAmount() {
        return secAmount;
    }

    public BigDecimal getWholesaleSpotRate() {
        return wholesaleSpotRate;
    }

    public BigDecimal getContractSpotRate() {
        return contractSpotRate;
    }

    public BigDecimal getWholesaleSwapPoint() {
        return wholesaleSwapPoint;
    }

    public BigDecimal getContractSwapPoint() {
        return contractSwapPoint;
    }

    public void setSecAmount(BigDecimal secAmount) {
        this.secAmount = secAmount;
    }

    private void setPriCcy(String priCcy) {
        this.priCcy = priCcy;
    }

    private void setSecCcy(String secCcy) {
        this.secCcy = secCcy;
    }

    private void setPriAmount(BigDecimal priAmount) {
        this.priAmount = priAmount;
    }

    private void setWholesaleSpotRate(BigDecimal wholesaleSpotRate) {
        this.wholesaleSpotRate = wholesaleSpotRate;
    }

    private void setContractSpotRate(BigDecimal contractSpotRate) {
        this.contractSpotRate = contractSpotRate;
    }

    private void setWholesaleSwapPoint(BigDecimal wholesaleSwapPoint) {
        this.wholesaleSwapPoint = wholesaleSwapPoint;
    }

    private void setContractSwapPoint(BigDecimal contractSwapPoint) {
        this.contractSwapPoint = contractSwapPoint;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", transactionTime=" + transactionTime +
                ", type='" + type + '\'' +
                ", product='" + product + '\'' +
                ", priCcy='" + priCcy + '\'' +
                ", secCcy='" + secCcy + '\'' +
                ", priAmount=" + priAmount +
                ", secAmount=" + secAmount +
                ", wholesaleSpotRate=" + wholesaleSpotRate +
                ", contractSpotRate=" + contractSpotRate +
                ", wholesaleSwapPoint=" + wholesaleSwapPoint +
                ", contractSwapPoint=" + contractSwapPoint +
                '}';
    }

    public static class Builder {
        private final int id;
        private final Type type;
        private final Product product;

        private String priCcy;
        private String secCcy;

        private BigDecimal priAmount;
        private BigDecimal secAmount;

        private BigDecimal wholesaleSpotRate;
        private BigDecimal contractSpotRate;
        private BigDecimal wholesaleSwapPoint;
        private BigDecimal contractSwapPoint;

        public Builder(int id, Type type, Product product) {
            this.id = id;
            this.type = type;
            this.product = product;
        }

        public Builder priCcy(String priCcy) {
            this.priCcy = priCcy;
            return this;
        }

        public Builder secCcy(String secCcy) {
            this.secCcy = secCcy;
            return this;
        }

        public Builder priAmount(BigDecimal priAmount) {
            this.priAmount = priAmount;
            return this;
        }

        public Builder secAmount(BigDecimal secAmount) {
            this.secAmount = secAmount;
            return this;
        }

        public Builder wholesaleSpotRate(BigDecimal wholesaleSpotRate) {
            this.wholesaleSpotRate = wholesaleSpotRate;
            return this;
        }

        public Builder contractSpotRate(BigDecimal contractSpotRate) {
            this.contractSpotRate = contractSpotRate;
            return this;
        }

        public Builder wholesaleSwapPoint(BigDecimal wholesaleSwapPoint) {
            this.wholesaleSwapPoint = wholesaleSwapPoint;
            return this;
        }

        public Builder contractSwapPoint(BigDecimal contractSwapPoint) {
            this.contractSwapPoint = contractSwapPoint;
            return this;
        }

        public Deal build() {
            Deal deal = new Deal(this.id, this.type, this.product);
            deal.setPriCcy(this.priCcy);
            deal.setSecCcy(this.secCcy);
            deal.setPriAmount(this.priAmount);
            deal.setSecAmount(this.secAmount);
            deal.setWholesaleSpotRate(this.wholesaleSpotRate);
            deal.setContractSpotRate(this.contractSpotRate);
            deal.setWholesaleSwapPoint(this.wholesaleSwapPoint);
            deal.setContractSwapPoint(this.contractSwapPoint);
            return deal;
        }
    }

    public enum Type {
        EXTERNAL,
        INTERNAL
    }

    public enum Product {
        SPT,
        FWD
    }

}
