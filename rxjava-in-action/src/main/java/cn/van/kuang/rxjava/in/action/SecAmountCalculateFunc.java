package cn.van.kuang.rxjava.in.action;

import io.reactivex.rxjava3.functions.Function;

import java.math.BigDecimal;

public class SecAmountCalculateFunc implements Function<Deal, Deal> {

    @Override
    public Deal apply(Deal d) throws Throwable {
        BigDecimal secAmount = d.getPriAmount().multiply(d.getContractSpotRate().add(d.getContractSwapPoint()));
        d.setSecAmount(secAmount);

        return d;
    }
}