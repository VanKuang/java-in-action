package cn.van.kuang.java.core.design.pattern.observer;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Reuters {

    private Set<Observer> observers = Collections.synchronizedSet(new LinkedHashSet<>());

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    public void bid(String ccyPair, BigDecimal price) {
        Action bid = new Bid(ccyPair, price);

        notify(bid);
    }

    public void ask(String ccyPair) {
        Action ask = new Ask(ccyPair);

        notify(ask);
    }

    private void notify(Action action) {
        for (Observer observer : observers) {
            observer.onAction(action);
        }
    }

    public static void main(String[] args) {
        Recorder recorder = new Recorder();
        Trader as = new Trader("AS");
        Trader ra = new Trader("RA");

        Reuters reuters = new Reuters();
        reuters.register(recorder);
        reuters.register(as);
        reuters.register(ra);

        reuters.ask("CNHHKD");
        reuters.bid("CNHHKD", new BigDecimal(1.5));

        reuters.unregister(as);

        reuters.ask("USDHKD");
    }

}
