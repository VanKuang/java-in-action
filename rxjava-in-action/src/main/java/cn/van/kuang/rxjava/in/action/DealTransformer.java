package cn.van.kuang.rxjava.in.action;

import rx.Observable;

import java.math.BigDecimal;

public class DealTransformer {

    public static void main(String[] args) {
        Deal spt = new Deal
                .Builder(1, Deal.Type.INTERNAL, Deal.Product.SPT)
                .priCcy("GBP")
                .secCcy("USD")
                .priAmount(new BigDecimal("1000"))
                .secAmount(new BigDecimal("1500"))
                .wholesaleSpotRate(new BigDecimal("1.5"))
                .contractSpotRate(new BigDecimal("1.5"))
                .wholesaleSwapPoint(new BigDecimal("0.0"))
                .contractSwapPoint(new BigDecimal("0.0"))
                .build();

        Deal fwd = new Deal
                .Builder(2, Deal.Type.EXTERNAL, Deal.Product.FWD)
                .priCcy("GBP")
                .secCcy("USD")
                .priAmount(new BigDecimal("1000"))
                .secAmount(new BigDecimal("2000"))
                .wholesaleSpotRate(new BigDecimal("1.5"))
                .contractSpotRate(new BigDecimal("1.6"))
                .wholesaleSwapPoint(new BigDecimal("0.3"))
                .contractSwapPoint(new BigDecimal("0.5"))
                .build();

        Observable
                .just(spt, fwd)
                .doOnNext(d -> System.out.println("Before filter: " + d))
                .filter(d -> Deal.Product.FWD.name().equals(d.getProduct()))
                .doOnNext(d -> System.out.println("Before map: " + d))
                .map(new SecAmountCalculateFunc())
                .subscribe(d -> System.out.println("After map: " + d));
    }

}
