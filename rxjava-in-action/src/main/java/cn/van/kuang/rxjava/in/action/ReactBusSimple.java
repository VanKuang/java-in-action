package cn.van.kuang.rxjava.in.action;

import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.MatchAllSelector;
import reactor.fn.Consumer;

public class ReactBusSimple {

    public static void main(String[] args) {
        EventBus eventBus = EventBus.create(Environment.initializeIfEmpty(), Environment.THREAD_POOL);

        eventBus.on(new MatchAllSelector(), new Receiver());

        eventBus.notify("key", Event.wrap(1));
    }

    static class Receiver implements Consumer<Event<Integer>> {

        @Override
        public void accept(Event<Integer> event) {
            System.out.println("Thread:" + Thread.currentThread().getName());
            System.out.println(event.toString());
            System.out.println(event.getData());
        }
    }

}
