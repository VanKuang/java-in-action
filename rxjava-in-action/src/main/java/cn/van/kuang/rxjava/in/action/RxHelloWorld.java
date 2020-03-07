package cn.van.kuang.rxjava.in.action;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class RxHelloWorld {

    private final static Logger LOGGER = LoggerFactory.getLogger(RxHelloWorld.class);

    public static void main(String[] args) throws Exception {

        Observable<String> observable = Observable.create(subscriber -> {
            LOGGER.info("call()");
            subscriber.onNext("Hello world");
            subscriber.onComplete();
        });

        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onComplete() {
                LOGGER.info("onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("onError", e);
            }

            @Override
            public void onSubscribe(Subscription subscription) {
                LOGGER.info("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                LOGGER.info("onNext({})", s);
            }
        };

        Consumer<String> consumer = o -> LOGGER.info("accept");

        observable.subscribe(consumer);

        Observable
                .just("Hi, world")
                .subscribe(s -> LOGGER.info("{}", s));

        Observable
                .just("Hey, world")
                .map(s -> "[" + Thread.currentThread().getName() + "] " + s)
                .subscribe(System.out::println);

        Observable
                .just(1)
                .map(count -> count + 1)
                .map(count -> count * 10)
                .map(string -> "[" + Thread.currentThread().getName() + "] " + string)
                .subscribe(s -> LOGGER.info("{}", s));

        Observable.fromArray(new String[]{"A", "B", "C"})
                .subscribe(s -> LOGGER.info("{}", s), Throwable::printStackTrace);

        Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribe(s -> LOGGER.info("timer {}", s));

        Observable
                .interval(10, TimeUnit.SECONDS)
                .subscribe(s -> LOGGER.info("Interval {}", s));

        Observable
                .just(8, 9, 10)
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> LOGGER.info("{}", i))
                .map(s -> "#" + s * 10)
                .doOnNext(i -> LOGGER.info("{}", i))
                .filter(s -> s.length() < 4)
                .subscribe(i -> LOGGER.info("{}", i));

        Observable
                .just(1, 2, 3)
                .flatMap(x -> Observable.just(x).delay(x, TimeUnit.SECONDS), 10)
                .subscribe(s -> LOGGER.info("{}", s));


        Observable
                .just("A", "AB", "ABC", "ABCD", "X", "XX", "XXX", "XXXX")
                .delay(word -> Observable.timer(word.length(), TimeUnit.SECONDS))
                .subscribe(s -> LOGGER.info("{}", s));

        Observable<Long> red = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> green = Observable.interval(1, TimeUnit.SECONDS);

        Observable
                .zip(
                        red.timestamp(),
                        green.timestamp(),
                        (r, g) -> r.time() - g.time()
                )
                .subscribe(s -> LOGGER.info("ZIP: {}", s));

        Flowable.fromCallable(() -> {
            TimeUnit.SECONDS.sleep(1);
            return "DONE";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(s -> LOGGER.info("{}", s), Throwable::printStackTrace);


        TimeUnit.SECONDS.sleep(15);
    }
}
