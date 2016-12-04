package cn.van.kuang.rxjava.in.action;

import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.TimeUnit;

public class RxHelloWorld {

    public static void main(String[] args) throws Exception {

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                System.out.println("[" + Thread.currentThread().getName() + "] call()");

                subscriber.onNext("Hello world");

                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {
                System.out.println("[" + Thread.currentThread().getName() + "] onComplete()");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("[" + Thread.currentThread().getName() + "] onError");
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("[" + Thread.currentThread().getName() + "] onNext(), " + s);
            }
        };

        observable.subscribe(subscriber);

        Observable
                .just("Hi, world")
                .subscribe(s -> System.out.println("[" + Thread.currentThread().getName() + "] " + s));

        Observable
                .just("Hey, world")
                .map(s -> "[" + Thread.currentThread().getName() + "] " + s)
                .subscribe(System.out::println);

        Observable
                .just(1)
                .map(count -> count + 1)
                .map(count -> count * 10)
                .map(string -> "[" + Thread.currentThread().getName() + "] " + string)
                .subscribe(System.out::println);

        Observable
                .from(new String[]{"A", "B", "C"})
                .subscribe(System.out::println, Throwable::printStackTrace);

        Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribe(s -> System.out.println("Timer"));

        Observable
                .interval(10, TimeUnit.SECONDS)
                .subscribe(s -> System.out.println("Interval"));

        Observable
                .just(8, 9, 10)
                .filter(i -> i % 3 > 0)
                .doOnNext(System.out::println)
                .map(s -> "#" + s * 10)
                .doOnNext(System.out::println)
                .filter(s -> s.length() < 4)
                .subscribe(System.out::println);

        Observable
                .just(1, 2, 3)
                .flatMap(x -> Observable.just(x).delay(x, TimeUnit.SECONDS), 10)
                .subscribe(s -> System.out.println("[" + Thread.currentThread().getName() + "] " + s));


        Observable
                .just("A", "AB", "ABC", "ABCD", "X", "XX", "XXX", "XXXX")
                .delay(word -> Observable.timer(word.length(), TimeUnit.SECONDS))
                .subscribe(System.out::println);

        Observable<Long> red = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> green = Observable.interval(1, TimeUnit.SECONDS);

        Observable
                .zip(
                        red.timestamp(),
                        green.timestamp(),
                        (r, g) -> r.getTimestampMillis() - g.getTimestampMillis()
                )
                .subscribe(s -> System.out.println("ZIP: " + s));

        TimeUnit.SECONDS.sleep(15);
    }
}
