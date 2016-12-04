package cn.van.kuang.rxjava.in.action;

import rx.functions.Func1;

import java.math.BigDecimal;

public class SecAmountCalculateFunc implements Func1<Deal, Deal> {

    @Override
    public Deal call(Deal d) {
        BigDecimal secAmount = d.getPriAmount().multiply(d.getContractSpotRate().add(d.getContractSwapPoint()));
        d.setSecAmount(secAmount);

        return d;
    }

}